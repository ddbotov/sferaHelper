package com.botov.sferaHelper;

import com.botov.sferaHelper.dto.*;
import com.botov.sferaHelper.service.SferaHelperMethods;

import java.io.IOException;
import java.util.Collections;

//делает произвольные простые изменения в Сфере
class SferaHelperApplication {
		public static void main(String... args) throws IOException {
			//String query = "area='RDS'";
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and estimation>" + (3600L * 8 * 4) ;
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and parent=null";
			String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and parent='STROMS-5472'";
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and systems = \"1553 Заявки ФЛ\"";
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and systems = \"1672_3 Аутентификация подтверждение операций\"";
			//String query = "area=\"FRNRSA\" and systems = \"1672_3 Аутентификация подтверждение операций\"";
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer')";
			//String query = "area=\"FRNRSA\" and updateDate > '2024-09-30'";
			//String query = "area=\"FRNRSA\" and number='FRNRSA-7669'";
			//String query =  "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and systems = \"1672_3 Аутентификация подтверждение операций\"";

			//String query =  "area='RDS' and status not in ('closed', 'done', 'rejectedByThePerformer') and assignee in (\"vtb70166052@corp.dev.vtb\", \"vtb4065673@corp.dev.vtb\", \"vtb70190852@corp.dev.vtb\", \"vtb4075541@corp.dev.vtb\", \"vtb4078565@corp.dev.vtb\", \"VTB4075541@corp.dev.vtb\") and name ~ '1553'";
			//String query = "area=\"EKA\" and status not in ('closed') and sprint in (4350,4351,4352,4353,4354,4355) and projectConsumer='db27d591-cbd3-4fcd-a7b6-d64d0cd11a3b'";
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and systems = null";
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer')";
			//String query = "number= 'FRNRSA-9740'";

			//String query = "area=\"FRNRSA\" and sprint = '4357'";
			//String query = "area=\"STROMS\" and status not in ('closed', 'done', 'rejectedByThePerformer') and assignee in (\"vtb70166052@corp.dev.vtb\")";
			//4357

			String sprint = "4358";

			ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);
			for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
				//Copy ticket
/*
				GetTicketDto getTicketDto = SferaHelperMethods.ticketByNumber(ticket.getNumber());
				TicketCopyResponseDto ticketCopy = SferaHelperMethods.copyTicket(getTicketDto);
				SferaHelperMethods.setSprint(ticketCopy.getNumber(), sprint);
				SferaHelperMethods.setEstimation(ticketCopy.getNumber(), ticket.getEstimation());
				System.err.println("Ticket '" + ticket.getName() + "' created in sprint " + sprint);*/


				//SferaHelperMethods.setSystem(ticket.getNumber(), "1672_3 Аутентификация подтверждение операций");
				SferaHelperMethods.setParent(ticket.getNumber(), "STROMS-5470");
				//SferaHelperMethods.setEstimation(ticket.getNumber(), 3600L);
				//SferaHelperMethods.setDueDate(ticket.getNumber(), "2026-04-07");
				//SferaHelperMethods.setProject(ticket.getNumber(), "f9696ccf-0f8d-431e-a803-9d00ee6e3329");// проект 2973
				//SferaHelperMethods.setProject(ticket.getNumber(), "4b4c6fcc-7125-41ca-a014-02014a5c800c");// проект 3556 // проект 2971 895c11de-b178-4fe8-9977-75f527ce29a1 // db27d591-cbd3-4fcd-a7b6-d64d0cd11a3b 2974
				//SferaHelperMethods.setSystem(ticket.getNumber(), "1553 Заявки ФЛ");
				//if (ticket.getSystems() != null && !ticket.getSystems().isEmpty() && !ticket.getSystems().contains("1553 Заявки ФЛ")) {
				//SferaHelperMethods.setSystem(ticket.getNumber(), "1553 Заявки ФЛ");
				//}
				//SferaHelperMethods.setSprint(ticket.getNumber(), null);

			//	SferaHelperMethods.setResolution(ticket.getNumber(), "Готово");

/*				PatchTicketDto patch = new PatchTicketDto();
				patch.setWorkGroup("Архитектурная задача");
				patch.setArchTaskReason("Надежность и Производительность");//
				patch.setReliabilityPattern(Collections.singleton("НА.ПН.02 - Stand In"));
				SferaHelperMethods.patchTicket2(ticket.getNumber(), patch);*/
			}
			System.out.println("end");
		}

}
