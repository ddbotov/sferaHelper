package com.botov.sferaHelper.bo;

import com.botov.sferaHelper.dto.GetTicketDto;
import com.botov.sferaHelper.dto.PatchTicketDto;

import java.util.Collections;
import java.util.function.Supplier;

public enum TicketType {
    TECH_DEBT(true, () -> {
        PatchTicketDto ticketDto = new PatchTicketDto();
        ticketDto.setWorkGroup("Технический долг");
        ticketDto.setTechDebtConsequence("Другое");
        return ticketDto;
    }),
    ARH_OTHER(true, () -> {
        PatchTicketDto ticketDto = new PatchTicketDto();
        ticketDto.setWorkGroup("Архитектурная задача");
        ticketDto.setArchTaskReason("Прочие архитектурные задачи");
        return ticketDto;
    }),
    ARH_RELIABILITY(true, () -> {
        PatchTicketDto ticketDto = new PatchTicketDto();
        ticketDto.setWorkGroup("Архитектурная задача");
        ticketDto.setArchTaskReason("Надежность и Производительность");//
        ticketDto.setReliabilityPattern(Collections.singleton("НА.ПН.02 - Stand In"));
        return ticketDto;
    }),
    NEW_FUNC(true, () -> {
        PatchTicketDto ticketDto = new PatchTicketDto();
        ticketDto.setNewFunctionalityReasons("");
        ticketDto.setWorkGroup("Новая функциональность");
        return ticketDto;
    }),
    NEW_FUNC_CLIENT_EXP(true, () -> {
        PatchTicketDto ticketDto = new PatchTicketDto();
        ticketDto.setWorkGroup("Новая функциональность");
        ticketDto.setNewFunctionalityReasons("Долг клиентского опыта");
        return ticketDto;
        // PATCH https://sfera.inno.local/app/tasks/api/v1/entities/FRNRSA-10321
        // {newFunctionalityReasons: "Долг клиентского опыта"}
        //    "newFunctionalityReasons": {
        //        "identifier": "Долг клиентского опыта",
        //        "name": "Долг клиентского опыта",
        //        "viewText": "Долг клиентского опыта",
        //        "viewVariant": "avatar"
        //    },
    }),
    DEFECT(false, () -> {
        throw new RuntimeException("DEFECT is not for patch");
    }),
    LINEAR(true, () -> {
        PatchTicketDto ticketDto = new PatchTicketDto();
        ticketDto.setWorkGroup("Линейная деятельность");
        return ticketDto;
    }),
    SUPPORT(true, () -> {
        PatchTicketDto ticketDto = new PatchTicketDto();
        ticketDto.setWorkGroup("Сопровождение");
        return ticketDto;
    }),
    OTHER(false, () -> {
        throw new RuntimeException("OTHER is not for patch");
    });

    private final boolean canChange;//TODO тех.долг ИБ не может меняться
    private final Supplier<PatchTicketDto> patchTicketDtoSupplier;

    TicketType(boolean canChange, Supplier<PatchTicketDto> patchTicketDtoSupplier) {
        this.canChange = canChange;
        this.patchTicketDtoSupplier = patchTicketDtoSupplier;
    }

    public static TicketType getTicketType(GetTicketDto ticket) {
        if (ticket.getType().getName().equals("Дефект")) {
            return DEFECT;
        }
        if (ticket.getWorkGroup().getName().equals("Новая функциональность")) {
            if ((ticket.getNewFunctionalityReasons() != null)
                    && ("Долг клиентского опыта".equals(ticket.getNewFunctionalityReasons().getIdentifier()))) {
                return NEW_FUNC_CLIENT_EXP;
            }
            return NEW_FUNC;
        }
        if (ticket.getWorkGroup().getName().equals("Технический долг")) {
            return TECH_DEBT;
        }
        if (ticket.getWorkGroup().getName().equals("Архитектурная задача")) {
            if ((ticket.getArchTaskReason() != null)
                    && ("Надежность и Производительность".equals(ticket.getArchTaskReason().getIdentifier()))) {
                return ARH_RELIABILITY;
            }
            return ARH_OTHER;
        }
        if (ticket.getWorkGroup().getName().equals("Линейная деятельность")) {
            return LINEAR;
        }
        if (ticket.getWorkGroup().getName().equals("Сопровождение")) {
            return SUPPORT;
        }
        return OTHER;
    }

    public boolean isCanChange() {
        return canChange;
    }

    public boolean isCanChange(GetTicketDto ticket) {
        if (!isCanChange()) {
            return false;
        }

        if (ticket.isTechDebtIB()) {
            return false;
        }
        return true;
    }

    public PatchTicketDto getPatchTicketDto() {
        return patchTicketDtoSupplier.get();
    }


}
