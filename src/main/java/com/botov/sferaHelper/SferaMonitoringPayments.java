package com.botov.sferaHelper;

import com.botov.sferaHelper.dto.GetTicketDto;
import com.botov.sferaHelper.dto.ListTicketShortDto;
import com.botov.sferaHelper.dto.ListTicketsDto;
import com.botov.sferaHelper.dto.RelationDto;
import com.botov.sferaHelper.service.SferaHelperMethods;
import com.botov.sferaHelper.service.SferaMonitoringService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.botov.sferaHelper.service.SferaService.SFERA_TICKET_START_PATH;

public class SferaMonitoringPayments {

    public static void main(String... args) throws IOException {
        int errorsCount = 0;
        errorsCount += SferaMonitoringService.checkRDSsStatus();
        errorsCount += SferaMonitoringService.checkOverdueRDSs();
        errorsCount += SferaMonitoringService.checkRDSLabels();
        errorsCount += SferaMonitoringService.printWaintingForPaymentRDSs();
        errorsCount += SferaMonitoringService.printOpenProjectRDSs();
        errorsCount += SferaMonitoringService.checkRDSPayments();
        errorsCount += SferaMonitoringService.printOpenPaymentRDSs();
        errorsCount += SferaMonitoringService.printKzCompleteRDSs();
        errorsCount += SferaMonitoringService.checkCoreApiEpicAndProjects();

        // Проверить, что у фичи есть трудооценка?
        // глазами смотрим все эпики, фичи и РДС-ы - где есть косяки?
        // смотрим КЗ и дашборд по КЗ

        System.err.println("Было всего 3 PAYMENT , а КЗ намного больше");
        System.err.println("Всего проблем найдено: " + errorsCount);
    }

}
