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
        printOpenProjectRDSs();
        printWaintingForPaymentRDSs();
        printOpenPaymentRDSs();
        printKzCompleteRDSs();
        checkCoreApiEpicAndProjects();

        // Проверить, что у фичи есть трудооценка?
        // глазами смотрим все эпики, фичи и РДС-ы - где есть косяки?
        // смотрим КЗ и дашборд по КЗ

        System.err.println("Было всего 3 PAYMENT , а КЗ намного больше");
        System.err.println("Всего проблем найдено: " + errorsCount);
    }

    private static void checkCoreApiEpicAndProjects() throws IOException {
        //Задачи под фичей STROMS-5885 (отказ от core-api-gateway) должны быть в проекте 3848
        // Сама фича должна быть открыта, чтобы не забыть поменять её в след. суперспринте
        String feature = "STROMS-5885";
        String query1 = "area=\"FRNRSA\" and status in ('closed', 'done', 'rejectedByThePerformer') and number='" + feature + "'";
        ListTicketsDto listTicketsDto1 = SferaHelperMethods.listTicketsByQuery(query1);
        if (listTicketsDto1.getContent().size() == 0) {
            System.err.println("Фича по отказу от core-api-gateway должна быть открыта: " + feature);
        }

        String query2 = "area=\"FRNRSA\" and parent='" + feature + "' and projectConsumer != 'caec6e6b-037e-4016-a0f0-0806b6472047'";

        ListTicketsDto listTicketsDto2 = SferaHelperMethods.listTicketsByQuery(query2);

        System.err.println();
        System.err.println("Задачи под фичей " + feature + " (отказ от core-api-gateway) должны быть в проекте 3848 (кол-во " + listTicketsDto2.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto2.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();
    }

    private static void printKzCompleteRDSs() throws IOException {
        //РДС-ы лейбла 'KZ_COMPLETE'
        String query = "area=\"RDS\" " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('KZ_COMPLETE')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("РДС-ы лейбла 'KZ_COMPLETE' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            errorsCount ++;
        }
        System.err.println();
    }

    private static void printWaintingForPaymentRDSs() throws IOException {
        //РДС-ы лейбла 'WAITING_FOR_PAYMENT'

        String query = "area=\"RDS\" " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('WAITING_FOR_PAYMENT')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("РДС-ы лейбла 'WAITING_FOR_PAYMENT' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            errorsCount ++;
        }
        System.err.println();
    }

    private static void printOpenProjectRDSs() throws IOException {
        //РДС-ы лейбла 'PROJECT'

        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('PROJECT')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Открытые РДС-ы лейбла 'PROJECT' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            errorsCount ++;
        }
        System.err.println();
    }

    private static void printOpenPaymentRDSs() throws IOException {
        //РДС-ы лейбла 'PAYMENT' и без KZ_COMPLETE

        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('PAYMENT') and label not in ('KZ_COMPLETE')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Открытые РДС-ы лейбла 'PAYMENT' и без 'KZ_COMPLETE' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            errorsCount ++;
        }
        System.err.println();
    }

    private static void checkRDSLabels() throws IOException {
        //РДС-ы без лейбла 'FREE', 'PAYMENT', 'PROJECT', 'WAITING_FOR_PAYMENT'

        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label not in ('FREE', 'PAYMENT', 'PROJECT', 'WAITING_FOR_PAYMENT')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("РДС-ы без лейбла 'FREE', 'PAYMENT', 'PROJECT', 'WAITING_FOR_PAYMENT' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            errorsCount ++;
        }
        System.err.println();
    }

    private static void checkRDSPayments() throws IOException {
        //РДС-ы с лейблом PAYMENT должны быть привязаны к фиче с трудооценкой
        String query = "area=\"RDS\" " +
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
        for (GetTicketDto ticket: rdsWithoutFeatures) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            errorsCount ++;
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
