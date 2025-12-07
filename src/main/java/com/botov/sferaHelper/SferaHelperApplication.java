package com.botov.sferaHelper;

import com.botov.sferaHelper.dto.ListTicketShortDto;
import com.botov.sferaHelper.dto.ListTicketsDto;
import com.botov.sferaHelper.service.SferaHelperMethods;

import java.io.IOException;

//делает произвольные простые изменения в Сфере
class SferaHelperApplication {
		public static void main(String... args) throws IOException {
			//String query = "area='RDS'";
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and estimation>" + (3600L * 8 * 4) ;
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and parent=null";
			//String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and parent='STROMS-5076'";
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
			String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and name~'1553_1, текучка РДС'";

			ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

			for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
				//SferaHelperMethods.setSystem(ticket.getNumber(), "1672_3 Аутентификация подтверждение операций");
				SferaHelperMethods.setParent(ticket.getNumber(), "STROMS-5471");
				//SferaHelperMethods.setEstimation(ticket.getNumber(), 3600L);
				//SferaHelperMethods.setDueDate(ticket.getNumber(), "2025-12-31");
				//SferaHelperMethods.setProject(ticket.getNumber(), "f9696ccf-0f8d-431e-a803-9d00ee6e3329");// проект 2973
				//SferaHelperMethods.setProject(ticket.getNumber(), "895c11de-b178-4fe8-9977-75f527ce29a1");// проект 2971 // db27d591-cbd3-4fcd-a7b6-d64d0cd11a3b 2974
				//SferaHelperMethods.setSystem(ticket.getNumber(), "1553 Заявки ФЛ");
				//if (ticket.getSystems() != null && !ticket.getSystems().isEmpty() && !ticket.getSystems().contains("1553 Заявки ФЛ")) {
				//SferaHelperMethods.setSystem(ticket.getNumber(), "1553 Заявки ФЛ");
				//}
				//SferaHelperMethods.setSprint(ticket.getNumber(), null);
			}
			System.out.println("end");
		}

}
