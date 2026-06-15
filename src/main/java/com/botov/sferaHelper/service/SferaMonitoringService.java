package com.botov.sferaHelper.service;

import com.botov.sferaHelper.bo.TicketType;
import com.botov.sferaHelper.dto.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.botov.sferaHelper.service.SferaService.SFERA_TICKET_START_PATH;

public class SferaMonitoringService {


    public static int checkCoreApiEpicAndProjects() throws IOException {
        //Задачи под фичей STROMS-5885 (отказ от core-api-gateway) должны быть в проекте 3848
        // Сама фича должна быть открыта, чтобы не забыть поменять её в след. суперспринте
        String feature = "STROMS-5885";
        String projectConsumer = "caec6e6b-037e-4016-a0f0-0806b6472047"; // проект 3848

        String query1 = "status not in ('closed', 'done', 'rejectedByThePerformer') and number='" + feature + "'";
        ListTicketsDto listTicketsDto1 = SferaHelperMethods.listTicketsByQuery(query1);
        if (listTicketsDto1.getContent().size() == 0) {
            System.err.println("Фича по отказу от core-api-gateway должна быть открыта: " + feature);
            return listTicketsDto1.getContent().size();
        }

        String query2 = "area=\"FRNRSA\" and parent='" + feature + "' and projectConsumer != '" + projectConsumer + "'";

        ListTicketsDto listTicketsDto2 = SferaHelperMethods.listTicketsByQuery(query2);

        System.err.println();
        System.err.println("Задачи под фичей " + feature + " (отказ от core-api-gateway) должны быть в проекте 3848 (кол-во " + listTicketsDto2.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto2.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();

        //Все задачи на Сергее Афанасьеве на проекте 3848 и на фиче по отказу от core-api-gateway
        String query3 = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and assignee in (\"vtb4067243@corp.dev.vtb\") " +
                "and projectConsumer != '" + projectConsumer + "' " +
                "and parent='" + feature + "'";

        ListTicketsDto listTicketsDto3 = SferaHelperMethods.listTicketsByQuery(query3);
        System.err.println("Все задачи на Сергее Афанасьеве на проекте 3848 и на фиче по отказу от core-api-gateway (кол-во " + listTicketsDto3.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto3.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            SferaHelperMethods.setParent(ticket.getNumber(), feature);
            SferaHelperMethods.setProject(ticket.getNumber(), projectConsumer);
        }

        //на проекте 3848 не должно быть никого, кроме Афанасьева Сергея
        String query4 = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and assignee not in (\"vtb4067243@corp.dev.vtb\") " +
                "and projectConsumer = '" + projectConsumer + "' " +
                "and parent='" + feature + "'";

        ListTicketsDto listTicketsDto4 = SferaHelperMethods.listTicketsByQuery(query4);
        System.err.println("На проекте 3848 не должно быть никого, кроме Афанасьева Сергея (кол-во " + listTicketsDto4.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto4.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }

        return listTicketsDto2.getContent().size() + listTicketsDto3.getContent().size() + listTicketsDto4.getContent().size();
    }

    public static int printKzCompleteRDSs() throws IOException {
        //РДС-ы лейбла 'KZ_COMPLETE'
        String query = "area=\"RDS\" " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('KZ_COMPLETE')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("РДС-ы лейбла 'KZ_COMPLETE' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();
        return listTicketsDto.getContent().size();
    }

    public static int printWaintingForPaymentRDSs() throws IOException {
        //РДС-ы лейбла 'WAITING_FOR_PAYMENT'

        int maxDays = 28;
        String dueDate = LocalDate.now().minusDays(maxDays).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "area=\"RDS\" " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('WAITING_FOR_PAYMENT') and createDate < '" + dueDate + "'";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("РДС-ы лейбла 'WAITING_FOR_PAYMENT' старше " + maxDays + " дня (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();
        return listTicketsDto.getContent().size();
    }

    public static int printOpenProjectRDSs() throws IOException {
        //РДС-ы лейбла 'PROJECT'

        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('PROJECT')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Открытые РДС-ы лейбла 'PROJECT' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();
        return listTicketsDto.getContent().size();
    }

    public static int printOpenPaymentRDSs() throws IOException {
        //РДС-ы лейбла 'PAYMENT' и без KZ_COMPLETE

        String query = "area=\"RDS\" " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\") and label in ('PAYMENT') and label not in ('KZ_COMPLETE')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Открытые РДС-ы лейбла 'PAYMENT' и без 'KZ_COMPLETE' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();
        return listTicketsDto.getContent().size();
    }

    public static int checkRDSLabels() throws IOException {
        //РДС-ы без лейбла 'FREE', 'PAYMENT', 'PROJECT', 'WAITING_FOR_PAYMENT'

        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and assignee in (\"vtb70166052@corp.dev.vtb\", \"vtb4102499@corp.dev.vtb\") and label not in ('FREE', 'PAYMENT', 'PROJECT', 'WAITING_FOR_PAYMENT')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("РДС-ы без лейбла 'FREE', 'PAYMENT', 'PROJECT', 'WAITING_FOR_PAYMENT' (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        System.err.println();
        return listTicketsDto.getContent().size();
    }

    public static int checkRDSPayments() throws IOException {
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
        }
        System.err.println();
        return rdsWithoutFeatures.size();
    }

    public static int checkTechDeptIB() throws IOException {
        String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and workGroup=\"Технический долг\"";
        List<ListTicketShortDto> content = SferaHelperMethods.listTicketsByQuery(query).getContent();
        System.err.println();

        List<ListTicketShortDto> result = new ArrayList();
        for (ListTicketShortDto ticket: content) {
            GetTicketDto ticketDto = SferaHelperMethods.ticketByNumber(ticket.getNumber());

            if (ticketDto.isTechDebtIB()) {//тех. долг ИБ переоткрывать нельзя
                result.add(ticket);
            }
        }

        System.err.println("Тех. долгов ИБ (кол-во " + result.size() + "):");


        for (ListTicketShortDto ticket: result) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return result.size();
    }


    //Не должно быть тех. долгов старше 3-х месяцев, это мониторит Седа (Критерии соответствия степени исполнения процессов производства и сопровождения техн. продуктов)
    // 75 дней - с запасом
    public static int checkOldTechDept() throws IOException {
        String minCreateDate = LocalDate.now().minusDays(70).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "area=\"FRNRSA\" and status not in ('closed', 'done', 'rejectedByThePerformer') and workGroup=\"Технический долг\" " +
                "and type='task' " +
                "and createDate < '" + minCreateDate + "'";
        List<ListTicketShortDto> content = SferaHelperMethods.listTicketsByQuery(query).getContent();
        // тех. долги по Арх сертам переоткрывать нельзя, на них ссылки идут из Сфера конфигурации
        content = content.stream().filter(o -> !o.getName().contains("Арх серт")).collect(Collectors.toList());

        System.err.println();
        System.err.println("Тех. долгов старше 3-х месяцев (75 дней - запас) (кол-во " + content.size() + "):");
        for (ListTicketShortDto ticket: content) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            GetTicketDto ticketDto = SferaHelperMethods.ticketByNumber(ticket.getNumber());

            if (ticketDto.isTechDebtIB()) {//тех. долг ИБ переоткрывать нельзя
                continue;
            }

            //Copy ticket
            TicketCopyResponseDto ticketCopy = SferaHelperMethods.copyTicket(ticket);
            SferaHelperMethods.setEstimation(ticketCopy.getNumber(), ticket.getEstimation());
            SferaHelperMethods.setTicketType(ticketCopy.getNumber(), TicketType.TECH_DEBT);
            System.err.println("Ticket " + ticketCopy.getNumber() + " '" + ticket.getName() + "' created");
            SferaHelperMethods.setSprint(ticket.getNumber(), null);
            SferaHelperMethods.close(ticket);
        }
        return content.size();
    }

    public static int checkMyDoneRDS() throws IOException {
        //Созданные мной РДС-ы в статусе "Выполнено"

        String query = "area=\"RDS\" and status in ('done') and owner in (\"vtb70166052@corp.dev.vtb\")";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Созданные мной РДС-ы в статусе \"Выполнено\" (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkClosedTicketsWithoutResolution() throws IOException {
        //Закрытые задачи без резолюции
        String query = "area=\"FRNRSA\" and status in ('closed') and resolution = null";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Закрытые задачи без резолюции (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber() + " \"" + ticket.getName() + "\"");
            SferaHelperMethods.setResolution(ticket.getNumber(), "Готово");
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkOnBotovNotMySystemRDSs() throws IOException {
        //РДСы не по 1553_1, 1553 или 1672_3 на Ботове
        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') and assignee in (\"vtb70166052@corp.dev.vtb\") ";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);


        List<ListTicketShortDto> notMySystemRDSs = new ArrayList<>();
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            if (!ticket.getName().startsWith("[1672_3]") &&
                    !ticket.getName().startsWith("[1553]") &&
                    !ticket.getName().startsWith("[1553_1]") &&
                    !ticket.getNumber().equals("RDS-293663") &&
                    !ticket.getNumber().equals("RDS-272592")) {//консультация по постпроцессорной очереди
                notMySystemRDSs.add(ticket);
            }
        }

        System.err.println();
        System.err.println("РДСы не по 1553_1, 1553 или 1672_3 на Ботове (кол-во " + notMySystemRDSs.size() + "):");
        for (ListTicketShortDto ticket: notMySystemRDSs) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber() + " \"" + ticket.getName() + "\"");
        }
        return notMySystemRDSs.size();
    }

    public static int checkNotOnBotovRDSs() throws IOException {
        //РДСы по 1553_1, 1553 или 1672_3 не на Ботове
        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') and assignee not in (\"vtb70166052@corp.dev.vtb\") " +
                "and (name ~ \"[1672_3]\" or name ~ \"[1553]\" or name ~ \"[1553_1]\") " +
                "and (streamExecutor = 'Омниканальный мидл' or streamExecutor = 'Базовые сервисы' or streamExecutor='Омниканальные микросервисные решения' or streamConsumer = 'Омниканальный мидл' or streamConsumer = 'Базовые сервисы' or streamConsumer='Омниканальные микросервисные решения')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        List<ListTicketShortDto> notMyRDSs = new ArrayList<>();
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            if (!ticket.getNumber().equals("RDS-313296") //консультация по постпроцессорной очереди
                    && !ticket.getNumber().equals("RDS-343776")   // РДС от Гузева на Базова про пайплайн
                    && !ticket.getNumber().equals("RDS-343447")   // Неправильно заведённый РДС у зарплатных карт
                    && !ticket.getNumber().equals("RDS-351096")   // наш РДС на 2079
                    && !ticket.getNumber().equals("RDS-347480")   // РДС на 2143
                    && !ticket.getNumber().equals("RDS-368080")   // наш РДС на 2079
                    && !ticket.getNumber().equals("RDS-368070")   // РДС на 2143
            ){
                notMyRDSs.add(ticket);
            }
        }
        System.err.println();
        System.err.println("РДСы по 1553_1, 1553 или 1672_3 не на Ботове (кол-во " + notMyRDSs.size() + "):");
        for (ListTicketShortDto ticket: notMyRDSs) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber() + " \"" + ticket.getName() + "\"");
        }
        return notMyRDSs.size();
    }

    public static int checkYellowDeadlineRDSs() throws IOException {
        //"Пожелтевшие RDS" https://sfera.inno.local/knowledge/pages?id=1675408
        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') and assignee in (\"vtb70166052@corp.dev.vtb\", \"vtb4102499@corp.dev.vtb\") " +
                "and label in ('YELLOW_DEADLINE_ALERT') " +
                " and (streamExecutor = 'Омниканальный мидл' or streamExecutor = 'Базовые сервисы' or streamExecutor='Омниканальные микросервисные решения' or streamConsumer = 'Омниканальный мидл' or streamConsumer = 'Базовые сервисы' or streamConsumer='Омниканальные микросервисные решения') ";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("\"Пожелтевшие RDS\" (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkPossibleRedOrYellowDeadlineRDSs() throws IOException {
        //"Покрасневшие RDS" c текстом 1553 или 1672_3
        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and (label in ('RED_DEADLINE_MISSED') or label in ('YELLOW_DEADLINE_ALERT') ) " +
                "and (name ~ \"1672_3\" or name ~ \"1553\") ";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        List<ListTicketShortDto> result = new ArrayList<>();
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            if (!ticket.getNumber().equals("RDS-364692")
                && !ticket.getNumber().equals("RDS-279211")
                && !ticket.getNumber().equals("RDS-377117")
                && !ticket.getNumber().equals("RDS-377113")){
                result.add(ticket);
            }
        }

        System.err.println();
        System.err.println("\"Покрасневшие или пожелтевшие RDS\" c текстом 1553 или 1672_3 (кол-во " + result.size() + "):");
        for (ListTicketShortDto ticket: result) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return result.size();
    }

    public static int checkRedDeadlineRDSs() throws IOException {
        //"Покрасневшие RDS" https://sfera.inno.local/knowledge/pages?id=1675665
        String query = "area=\"RDS\" and status not in ('closed', 'rejectedByThePerformer') and assignee in (\"vtb70166052@corp.dev.vtb\", \"vtb4102499@corp.dev.vtb\") " +
                "and label in ('RED_DEADLINE_MISSED') " +
                " and (streamExecutor = 'Омниканальный мидл' or streamExecutor = 'Базовые сервисы' or streamExecutor='Омниканальные микросервисные решения' or streamConsumer = 'Омниканальный мидл' or streamConsumer = 'Базовые сервисы' or streamConsumer='Омниканальные микросервисные решения') ";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("\"Покрасневшие RDS\" (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkEpicsWithoutOpenedChildren() throws IOException {
        //эпики без декопозиции
        String query = "area=\"STROMS\" and status not in ('closed', 'rejectedByThePerformer') and assignee in (\"vtb70166052@corp.dev.vtb\") " +
                "and not hasOpenedChildren()";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("эпики без декопозиции (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkEpicsWithoutAcceptanceCriteria() throws IOException {
        //эпики без критериев приёмки
        String query = "area=\"STROMS\" and status not in ('closed', 'rejectedByThePerformer') and assignee in (\"vtb70166052@corp.dev.vtb\") " +
                "and (acceptanceCriteria=null or acceptanceCriteria='' or acceptanceCriteria='!' or acceptanceCriteria='-' or acceptanceCriteria=' ')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("эпики без критериев приёмки (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkEpicsWithoutEstimation() throws IOException {
        //эпики без оценок
        String query = "area=\"STROMS\" and status not in ('closed', 'rejectedByThePerformer') and assignee in (\"vtb70166052@corp.dev.vtb\") " +
                "and estimation = null";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("эпики без оценок (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkStoriesWithoutAcceptanceCriteria() throws IOException {
        //истории без критериев приёмки
        String query = "type=\"story\" and area=\"FRNRSA\" and status not in ('closed', 'rejectedByThePerformer') " +
                "and (acceptanceCriteria=null or acceptanceCriteria='' or acceptanceCriteria='-' or acceptanceCriteria=' ')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("истории без критериев приёмки (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkRDSWithOpenQuestions() throws IOException {
        //RDS с открытыми вопросами
        String query = "area='RDS' and openQuestion = 'открытый вопрос' and status not in ('closed', 'rejectedByThePerformer') and assignee in (\"vtb70166052@corp.dev.vtb\", \"vtb4065673@corp.dev.vtb\", \"vtb70190852@corp.dev.vtb\", \"vtb4075541@corp.dev.vtb\", \"vtb4078565@corp.dev.vtb\", \"VTB4075541@corp.dev.vtb\")";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("RDS с открытыми вопросами (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkRDSsStatus() throws IOException {
        //RDS не в статусe "В очереди"
        String query = "area='RDS' and status not in ('closed', 'rejectedByThePerformer', 'onTheQueue') and assignee in (\"vtb70166052@corp.dev.vtb\", \"vtb4065673@corp.dev.vtb\", \"vtb70190852@corp.dev.vtb\", \"vtb4075541@corp.dev.vtb\", \"VTB4075541@corp.dev.vtb\")";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        List<ListTicketShortDto> result = new ArrayList<>();
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            if (!ticket.getNumber().equals("RDS-355494") &&
                    !ticket.getNumber().equals("RDS-355481") &&
                    !ticket.getNumber().equals("RDS-355461")) {//РДС-ы проекта 3556
                result.add(ticket);
            }
        }

        System.err.println();
        System.err.println("RDS не в статусe \"В очереди\" (кол-во " + result.size() + "):");
        for (ListTicketShortDto ticket: result) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber() + " \"" + ticket.getName() + "\"");
        }
        return result.size();
    }


    public static int checkOverdueRDSs() throws IOException {
        return checkOverdue("RDS", "assignee in (\"vtb70166052@corp.dev.vtb\", \"vtb4065673@corp.dev.vtb\", \"vtb70190852@corp.dev.vtb\", \"vtb4075541@corp.dev.vtb\", \"vtb4078565@corp.dev.vtb\", \"VTB4075541@corp.dev.vtb\")");
    }

    public static int checkOverdueFRNRSAs() throws IOException {
        return checkOverdue("FRNRSA", null);
    }

    public static int checkOverdue(String area, String filter) throws IOException {
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
        String newDueDate = LocalDate.now().plusDays(60).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            SferaHelperMethods.setDueDate(ticket.getNumber(), newDueDate);
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkTicketsClosedWithoutResolution() throws IOException {
        //Закрытые задачи без резолюции
        String query = "area=\"FRNRSA\" and status in ('closed') and resolution = null";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Закрытые задачи без резолюции (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkTicketsWithBigEstimation() throws IOException {
        //задачи с трудооценкой, большей чем 3 ч.д.
        String query = "area=\"FRNRSA\" and status not in ('closed', 'rejectedByThePerformer') and estimation>" + (3600L * 8 * 3) ;
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("задачи с трудооценкой, большей чем 3 ч.д. (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
            SferaHelperMethods.setEstimation(ticket.getNumber(), 3600L * 8 * 3);
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkTicketsWithWrongTypes() throws IOException {
        //задачи с неправильным типом
        String query = "area=\"FRNRSA\" and status not in ('closed', 'rejected', 'rejectedByThePerformer') and workGroup not in" +
                " (\"Технический долг\", \"Новая функциональность\", \"Архитектурная задача\") and type!=\"defect\"";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("задачи с неправильным типом (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkTicketsWithWrongProject() throws IOException {
        //задачи с неправильным проектом не на Афанасьеве (не 2973)
        String query = "area=\"FRNRSA\" and status not in ('closed', 'rejectedByThePerformer') and projectConsumer != '4b4c6fcc-7125-41ca-a014-02014a5c800c'" +
                "and assignee not in (\"vtb4067243@corp.dev.vtb\")";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("задачи с неправильным проектом (не 2973) не на Афанасьеве (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            //SferaHelperMethods.setProject(ticket.getNumber(), "f9696ccf-0f8d-431e-a803-9d00ee6e3329");// проект 2973
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkEpicsWithEmptySystems() throws IOException {
        //Эпики c пустым полем "Система"
        String query = "area=\"STROMS\" and status not in ('closed', 'rejectedByThePerformer')  and assignee in (\"vtb70166052@corp.dev.vtb\") and systems = null";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Эпики c пустым полем \"Система\" (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            SferaHelperMethods.setSystem(ticket.getNumber(), "1553 Заявки ФЛ");
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkEpicsWithWrongSystems() throws IOException {
        //"Эпики" не по 1553 (особенно по 1672_3)
        String query = "area=\"STROMS\" and status not in ('closed', 'rejectedByThePerformer')  and assignee in (\"vtb70166052@corp.dev.vtb\") and systems not in (\"1553 Заявки ФЛ\")";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Эпики не по 1553 (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            SferaHelperMethods.setSystem(ticket.getNumber(), "1553 Заявки ФЛ");
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkTicketsWithEmptySystems() throws IOException {
        //Задачи c пустыми полями "Система"
        String query = "area=\"FRNRSA\" and status not in ('closed', 'rejectedByThePerformer') and systems = null";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Задачи c пустыми полями \"Система\" (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            SferaHelperMethods.setSystem(ticket.getNumber(), "1553 Заявки ФЛ");
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkTicketsWithWrongSystems() throws IOException {
        //Задачи не по 1553 (особенно по 1672_3)
        String query = "area=\"FRNRSA\" and status not in ('closed', 'rejectedByThePerformer') and systems not in (\"1553 Заявки ФЛ\")";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("Задачи не по 1553 (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            SferaHelperMethods.setSystem(ticket.getNumber(), "1553 Заявки ФЛ");
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkProdBugs() throws IOException {
        //дефекты прода
        String query = "type=\"defect\" and area=\"FRNRSA\" and status not in ('closed', 'rejectedByThePerformer')";
        //String query = "type=\"defect\" and area=\"FRNRSA\" and createDate >= '2025-01-01' and systems != \"1672_3 Аутентификация подтверждение операций\"";
        //String query = "area=\"FRNRSA\" and number='FRNRSA-7274'";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        List<GetTicketDto> prodDefects = new ArrayList<>();
        for (ListTicketShortDto listTicketShortDto: listTicketsDto.getContent()) {
            GetTicketDto ticket = SferaHelperMethods.ticketByNumber(listTicketShortDto.getNumber());
            if (ticket.isProdDefect()) {
                prodDefects.add(ticket);
            }
        }

        System.err.println();
        System.err.println("дефекты прода (кол-во " + prodDefects.size() + "):");
        for (GetTicketDto ticket : prodDefects) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return prodDefects.size();
    }

    public static int checkTicketsDone() throws IOException {
        //задачи выполненные, но не закрытые
        String query = "area=\"FRNRSA\" and status in ('done')";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("задачи выполненные, но не закрытые (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkTicketsWithoutEpics() throws IOException {
        //задачи без эпиков
        String query = "area=\"FRNRSA\" and status not in ('closed', 'rejectedByThePerformer') and parent = null";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("задачи без эпиков (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkTicketsWithoutEstimation() throws IOException {
        //задачи без оценок
        String query = "area=\"FRNRSA\" and status not in ('closed', 'rejectedByThePerformer') and estimation = null";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("задачи без оценок (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            SferaHelperMethods.setEstimation(ticket.getNumber(), 3600L);
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

    public static int checkTicketsWithoutSprint() throws IOException {
        //задачи вне спринтов
        String query = "area=\"FRNRSA\" and status not in ('closed',  'rejected', 'rejectedByThePerformer') and sprint = null";
        ListTicketsDto listTicketsDto = SferaHelperMethods.listTicketsByQuery(query);

        System.err.println();
        System.err.println("задачи вне спринтов (кол-во " + listTicketsDto.getContent().size() + "):");
        for (ListTicketShortDto ticket: listTicketsDto.getContent()) {
            System.err.println(SFERA_TICKET_START_PATH + ticket.getNumber());
        }
        return listTicketsDto.getContent().size();
    }

}
