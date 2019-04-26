package com.tiany.ibator.common.meta;

import java.util.List;

public class SQL {
    private String sql;
    private List<String> sqlWords;
    private boolean cleared = false;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<String> getSqlWords() {
        return sqlWords;
    }

    public void setSqlWords(List<String> sqlWords) {
        this.sqlWords = sqlWords;
    }

    public SQL(String sql, List<String> sqlWords) {
        this.sql = sql;
        this.sqlWords = sqlWords;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }
}
