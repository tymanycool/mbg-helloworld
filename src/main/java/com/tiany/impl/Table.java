package com.tiany.impl;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private List<Field > fields = new ArrayList<>();
    private String comment;

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
}
