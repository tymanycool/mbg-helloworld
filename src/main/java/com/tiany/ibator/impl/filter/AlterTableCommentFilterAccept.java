package com.tiany.ibator.impl.filter;

import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.infs.FilterAccept;
import com.tiany.util.CollectionUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlterTableCommentFilterAccept implements FilterAccept {
    @Override
    public boolean accept(SQL sql) {
        List<String> sqlWords = sql.getSqlWords();
        boolean size = sqlWords.size() > 2;
        boolean alter = CollectionUtil.containsIgnoreCase(sqlWords, "ALTER");
        boolean table = CollectionUtil.containsIgnoreCase(sqlWords, "TABLE");
        boolean comment = CollectionUtil.containsIgnoreCase(sqlWords, "COMMENT");

        return size && alter && table && comment;
    }
}
