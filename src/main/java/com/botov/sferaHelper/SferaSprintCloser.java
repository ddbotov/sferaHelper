package com.botov.sferaHelper;

import com.botov.sferaHelper.dto.ListTicketShortDto;
import com.botov.sferaHelper.dto.ListTicketsDto;
import com.botov.sferaHelper.dto.TicketCopyResponseDto;
import com.botov.sferaHelper.service.SferaHelperMethods;

public class SferaSprintCloser {

    public static String sprint = "4360";
    public static String nextSprint = "4361";

    public static void main(String... args) throws Exception {
        //Получить список тикетов не в статусе CLOSED
        String notClosedTicketsInActiveSprintQuery = "area=\"FRNRSA\" and status not in ('closed') and sprint=" + sprint;
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(notClosedTicketsInActiveSprintQuery);
        System.err.println();
        System.err.println("Задач не в статусе \"Закрыто\" (кол-во " + listTicketsDto.getContent().size() + ")");
        //Каждый открытый тикет скопировать в спринт n+1, проставить у копии трудооценку, изначальный тикет закрыть и проставить резолюцию.
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            //Copy ticket
            TicketCopyResponseDto ticketCopy = SferaHelperMethods.copyTicket(ticket);
            SferaHelperMethods.setSprint(ticketCopy.getNumber(), nextSprint);
            SferaHelperMethods.setEstimation(ticketCopy.getNumber(), ticket.getEstimation());
            System.err.println("Ticket " + ticketCopy.getNumber() +
                    " '" + ticket.getName() + "' created in sprint " + nextSprint);

            SferaHelperMethods.close(ticket.getNumber());
        }

        //Получить список тикетов без резолюций - должно быть 0. У кого нет - проставить резолюцию
        String ticketsWithoutResolutionInActiveSprintQuery = "area=\"FRNRSA\" and resolution = null and sprint=" + sprint;
        listTicketsDto = SferaHelperMethods.listTicketsByQuery(ticketsWithoutResolutionInActiveSprintQuery);
        System.err.println();
        System.err.println("Кол-во задач без резолюции:" + listTicketsDto.getContent().size());
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            SferaHelperMethods.setResolution(ticket.getNumber(), "Готово");
        }
        if (listTicketsDto.getContent().size() > 0) {
            listTicketsDto = SferaHelperMethods.listTicketsByQuery(ticketsWithoutResolutionInActiveSprintQuery);
            System.err.println();
            System.err.println("Кол-во задач без резолюции:" + listTicketsDto.getContent().size());
        }

        //Получить список тикетов не в статусе CLOSED - должно быть 0
        listTicketsDto = SferaHelperMethods.listTicketsByQuery(notClosedTicketsInActiveSprintQuery);
        System.err.println();
        System.err.println("Кол-во задач не в статусе \"Закрыто\":" + listTicketsDto.getContent().size());
    }
}
