package com.tiany.ibator.common.meta;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private String entityName;
    private String comment;
    private List<Field> fields = new ArrayList<>();
    private List<Field> primaryKeys = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<Field> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<Field> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }
}
