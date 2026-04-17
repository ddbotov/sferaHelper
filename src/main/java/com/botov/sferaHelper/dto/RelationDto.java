package com.botov.sferaHelper.dto;

import lombok.Data;

@Data
public class RelationDto {
    private String name; //like "Исполнение RDS"
    private String type; //like "rdsImplementation"
    private RelatedEntityDto relatedEntity;
}
