package com.tiany.ibator.impl.filter;

import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.infs.FilterAccept;
import com.tiany.util.CollectionUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateTableFilterAccept implements FilterAccept {
    @Override
    public boolean accept(SQL sql) {
        List<String> sqlWords = sql.getSqlWords();
        boolean size = sqlWords.size() > 2;
        boolean create = CollectionUtil.containsIgnoreCase(sqlWords, "CREATE");
        boolean table = CollectionUtil.containsIgnoreCase(sqlWords, "TABLE");
        boolean left = CollectionUtil.containsIgnoreCase(sqlWords, "(");
        boolean right = CollectionUtil.containsIgnoreCase(sqlWords, ")");

        return size && create && table && left && right;
    }
}
