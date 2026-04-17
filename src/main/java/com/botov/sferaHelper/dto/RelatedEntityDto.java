package com.botov.sferaHelper.dto;

import lombok.Data;

@Data
public class RelatedEntityDto {

    private String number;//like "STROMS-6117"
    private String name;//like "[1553] RDS-343061, Хранение заявок на запланированные каникулы"
    private TypeDto type;
}
