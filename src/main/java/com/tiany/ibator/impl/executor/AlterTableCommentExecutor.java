package com.tiany.ibator.impl.executor;

import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.util.ListUtil;
import com.tiany.util.CollectionUtil;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlterTableCommentExecutor extends AbstractRemoveCommentExecutor {
    @Override
    protected void handle(Table table, List<String> words) {
        String comment = ListUtil.getAfter(words, "COMMENT", 1);
        if (StringUtil.isNotEmpty(comment)) {
            table.setComment(comment);
        }
    }

    @Override
    public boolean accept(SQL sql) {
        if (!sql.isCleared()) {
            clear(sql);
        }
        List<String> words = sql.getSqlWords();
        if (!CollectionUtil.containsIgnoreCase(words, "ALTER")) {
            return false;
        }
        if (!CollectionUtil.containsIgnoreCase(words, "TABLE")) {
            return false;
        }
        if (!CollectionUtil.containsIgnoreCase(words, "COMMENT")) {
            return false;
        }
        return true;
    }

    @Override
    public String getTableName(SQL sql) {
        if (!sql.isCleared()) {
            clear(sql);
        }
        List<String> sqlWords = sql.getSqlWords();
        if (accept(sql)) {
            String table = ListUtil.getAfter(sqlWords, "TABLE", 1);
            return table;
        }
        return null;
    }
}
