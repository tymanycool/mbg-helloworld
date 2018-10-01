package com.tiany.ibator.inf;

import com.tiany.ibator.meta.Table;

import java.util.List;

public interface DaoGenerator extends Generator{
    public String generateDao(Table table, List<Generator> list);
}
