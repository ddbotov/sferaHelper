package com.botov.sferaHelper;

import com.botov.sferaHelper.dto.GetTicketDto;
import com.botov.sferaHelper.dto.ListTicketShortDto;
import com.botov.sferaHelper.dto.ListTicketsDto;
import com.botov.sferaHelper.dto.TicketCopyResponseDto;
import com.botov.sferaHelper.service.SferaHelperMethods;

import java.io.IOException;

//копирует одинаковые задачи в несколько спринтов sprints
class SferaHelperTicketCopier {
		public static void main(String... args) throws IOException {
			String[] sprints = new String[] {"4356", "4357", "4358", "4359", "4360", "4361", "4362", "4363"};
			String[] ticketNumbers = new String[] {"FRNRSA-7910", "FRNRSA-7918", "FRNRSA-7925", "FRNRSA-7932", "FRNRSA-7939", "FRNRSA-9418"};

			for (String ticketNumber : ticketNumbers) {
				GetTicketDto ticket = SferaHelperMethods.ticketByNumber(ticketNumber);
				for (String sprint : sprints) {
					String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer')";
					query = query + " and name = '" + ticket.getName() + "'";
					query = query + " and assignee in (\"" + ticket.getAssignee().getIdentifier() + "\")";
					query = query + " and sprint = '" + sprint + "'";
					ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);
					if (listTicketsDto.getContent().size() > 0) {
						System.err.println("Ticket '" + ticket.getName() + "' already in sprint " + sprint);
						continue;
					}
					//Copy ticket
					TicketCopyResponseDto ticketCopy = SferaHelperMethods.copyTicket(ticket);
					SferaHelperMethods.setSprint(ticketCopy.getNumber(), sprint);
					SferaHelperMethods.setEstimation(ticketCopy.getNumber(), ticket.getEstimation());
					System.err.println("Ticket '" + ticket.getName() + "' created in sprint " + sprint);
				}
			}
			System.out.println("end");
		}

}
