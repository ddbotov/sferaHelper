package com.botov.sferaHelper;

import com.botov.sferaHelper.bo.TicketType;
import com.botov.sferaHelper.dto.GetTicketDto;
import com.botov.sferaHelper.dto.ListTicketShortDto;
import com.botov.sferaHelper.dto.ListTicketsDto;
import com.botov.sferaHelper.dto.TicketCopyResponseDto;
import com.botov.sferaHelper.service.SferaHelperMethods;
import com.botov.sferaHelper.service.SferaMonitoringService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.botov.sferaHelper.service.SferaService.SFERA_TICKET_START_PATH;

//мониторит корректность задач, по возможности простые проблемы исправляет сам
public class SferaMonitoring {


    public static void main(String... args) throws IOException {
        int errorsCount = 0;
        //errorsCount += SferaMonitoringService.checkProdBugs();
        //errorsCount += SferaMonitoringService.checkTechDeptIB();
        errorsCount += SferaMonitoringService.checkClosedTicketsWithoutResolution();
        errorsCount += SferaMonitoringService.checkTicketsDone();
        errorsCount += SferaMonitoringService.checkTicketsWithoutEpics();
        errorsCount += SferaMonitoringService.checkTicketsWithoutEstimation();
        errorsCount += SferaMonitoringService.checkTicketsWithoutSprint();
        errorsCount += SferaMonitoringService.checkEpicsWithWrongSystems();
        errorsCount += SferaMonitoringService.checkEpicsWithEmptySystems();
        errorsCount += SferaMonitoringService.checkTicketsWithWrongSystems();
        errorsCount += SferaMonitoringService.checkTicketsWithEmptySystems();
        errorsCount += SferaMonitoringService.checkTicketsWithWrongProject();
        errorsCount += SferaMonitoringService.checkTicketsWithWrongTypes();
        errorsCount += SferaMonitoringService.checkTicketsWithBigEstimation();
        errorsCount += SferaMonitoringService.checkTicketsClosedWithoutResolution();
        errorsCount += SferaMonitoringService.checkOverdueRDSs();
        errorsCount += SferaMonitoringService.checkNotOnBotovRDSs();
        errorsCount += SferaMonitoringService.checkOnBotovNotMySystemRDSs();
        errorsCount += SferaMonitoringService.checkYellowDeadlineRDSs();
        errorsCount += SferaMonitoringService.checkRedDeadlineRDSs();
        errorsCount += SferaMonitoringService.checkPossibleRedOrYellowDeadlineRDSs();
        errorsCount += SferaMonitoringService.checkRDSsStatus();
        errorsCount += SferaMonitoringService.checkOverdueFRNRSAs();
        errorsCount += SferaMonitoringService.checkRDSWithOpenQuestions();
        errorsCount += SferaMonitoringService.checkStoriesWithoutAcceptanceCriteria();
        errorsCount += SferaMonitoringService.checkEpicsWithoutEstimation();
        errorsCount += SferaMonitoringService.checkEpicsWithoutAcceptanceCriteria();
        errorsCount += SferaMonitoringService.checkMyDoneRDS();

        errorsCount += SferaMonitoringService.checkOldTechDept();
        errorsCount += SferaMonitoringService.checkCoreApiEpicAndProjects();


        errorsCount += SferaMonitoringService.checkFeatures();
        //checkEpicsWithoutOpenedChildren();

        //Выводить в конце итогое кол-во проблем?

        //новые эпики на мне??

        //найти эпики без фичей
        //найти фичи без эпиков
        //найти фичи без подзадач
        //найти фичи без сторей
        //найти эпики, на которых хоть что то , кроме фичей
        //найти фичи, в которых трудооценка не на 0,87 больше, чем в эпиках
        //найти эпики без суперспринта и срока
        //найти фичи без суперспринта и срока
        //найти все мои STROMS со связями "Склонирован"

        //выполненные задачи, но не закрытые

        //удалить связи "склонирована" у всех тикетов, эпиков и фичей ???

        System.err.println("Всего проблем найдено: " + errorsCount);
    }

}
