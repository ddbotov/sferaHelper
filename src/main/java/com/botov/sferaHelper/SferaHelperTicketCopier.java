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
			String[] sprints = new String[] {"4370", "4371", "4372", "4373", "4374", "4375", "4376"};
			String[] ticketNumbers = new String[] {"FRNRSA-11104", "FRNRSA-11973", "FRNRSA-10730", "FRNRSA-11972", "FRNRSA-11945", "FRNRSA-11121",
			"FRNRSA-11115", "FRNRSA-10471", "FRNRSA-10477", "FRNRSA-11971", "FRNRSA-10483", "FRNRSA-11133", "FRNRSA-10489",
			"FRNRSA-11970", "FRNRSA-10495", "FRNRSA-11300", "FRNRSA-11969", "FRNRSA-11145", "FRNRSA-10501", "FRNRSA-11968", "FRNRSA-11153"};

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
