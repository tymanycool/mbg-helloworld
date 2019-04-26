package com.tiany.ibator.infs;

import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.common.meta.Table;

import java.util.List;

public interface TableParser {
    List<Table> parse(String sql);
}
