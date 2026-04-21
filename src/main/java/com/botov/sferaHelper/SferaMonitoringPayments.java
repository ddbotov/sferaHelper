package com.botov.sferaHelper;

import com.botov.sferaHelper.dto.GetTicketDto;
import com.botov.sferaHelper.dto.ListTicketShortDto;
import com.botov.sferaHelper.dto.ListTicketsDto;
import com.botov.sferaHelper.dto.RelationDto;
import com.botov.sferaHelper.service.SferaHelperMethods;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.botov.sferaHelper.service.SferaService.SFERA_TICKET_START_PATH;

public class SferaMonitoringPayments {

    public static int errorsCount = 0;

    public static void main(String... args) throws IOException {
        checkRDSLabels();
        checkRDSPayments();
        checkRDSsStatus();
        checkOverdueRDSs();
        printPaymentRDSs();

        // Проверить, что у фичи есть трудооценка?
        // глазами смотрим все эпики, фичи и РДС-ы - где есть косяки?
        // смотрим КЗ и дашборд по КЗ

        System.err.println("Всего проблем найдено: " + errorsCount);
    }

    private static void printPaymentRDSs() throws IOException {
        //РДС-ы лейбла 'PAYMENT'

        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('PAYMENT')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("РДС-ы лейбла 'PAYMENT' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();
    }

    private static void checkRDSLabels() throws IOException {
        //РДС-ы без лейбла 'FREE', 'PAYMENT', 'PROJECT'

        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label not in ('FREE', 'PAYMENT', 'PROJECT')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("РДС-ы без лейбла 'FREE', 'PAYMENT', 'PROJECT' (кол-во " + listTicketsDto.getContent().size() + "):");
        errorsCount += listTicketsDto.getContent().size();
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();
    }

    private static void checkRDSPayments() throws IOException {
        //РДС-ы с лейблом PAYMENT должны быть привязаны к фиче с трудооценкой
        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('PAYMENT')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        List<GetTicketDto> rdsWithoutFeatures = new ArrayList<>();
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            GetTicketDto ticketDto = SferaHelperMethods.ticketByNumber(ticket.getNumber());

            List<RelationDto> features = new ArrayList<>();
            if (ticketDto.getRelations() != null) {
                for (RelationDto relationDto : ticketDto.getRelations()) {
                    if (relationDto.getType().equals("rdsImplementation")
                            && relationDto.getRelatedEntity() != null
                            && relationDto.getRelatedEntity().getType() != null
                            && relationDto.getRelatedEntity().getType().getName() != "feature") {
                        features.add(relationDto);
                    }
                }
            }

            if (features.isEmpty()) {
                rdsWithoutFeatures.add(ticketDto);
            }
        }

        System.err.println();
        System.err.println("РДС-ы с лейблом PAYMENT без корректной привязки к фиче (кол-во " + rdsWithoutFeatures.size() + "):");
        errorsCount += rdsWithoutFeatures.size();
        for (GetTicketDto ticket: rdsWithoutFeatures) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();
    }

    private static void checkRDSsStatus() throws IOException {
        //RDS не в статусe "В очереди"
        String query = "area='RDS' and status not in ('closed', 'rejectedByThePerformer', 'onTheQueue') and assignee in (\"vtb70166052@corp.dev.vtb\", \"vtb4065673@corp.dev.vtb\", \"vtb70190852@corp.dev.vtb\", \"vtb4075541@corp.dev.vtb\", \"VTB4075541@corp.dev.vtb\")";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("RDS не в статусe \"В очереди\" (кол-во " + listTicketsDto.getContent().size() + "):");
        errorsCount += listTicketsDto.getContent().size();
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            SferaHelperMethods.setStatus(ticket.getNumber(), "onTheQueue");
        }
        System.err.println();
    }

    private static void checkOverdueRDSs() throws IOException {
        checkOverdue("RDS", "assignee in (\"vtb70166052@corp.dev.vtb\", \"vtb4065673@corp.dev.vtb\", \"vtb70190852@corp.dev.vtb\", \"vtb4075541@corp.dev.vtb\", \"vtb4078565@corp.dev.vtb\", \"VTB4075541@corp.dev.vtb\")");
    }

    private static void checkOverdue(String area, String filter) throws IOException {
        //просроченные РДСы
        String dueDate = LocalDate.now().plusDays(60).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String query = "area='" + area + "' and status not in ('closed', 'rejectedByThePerformer') and " +
                "((dueDate = null) or (dueDate < '"
                + dueDate +
                "'))";
        if (filter != null) {
            query = query + " and " + filter;
        }
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("просроченные " + area + " (кол-во " + listTicketsDto.getContent().size() + "):");
        errorsCount += listTicketsDto.getContent().size();
        String newDueDate = LocalDate.now().plusDays(60).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            SferaHelperMethods.setDueDate(ticket.getNumber(), newDueDate);
        }
        System.err.println();
    }

}
