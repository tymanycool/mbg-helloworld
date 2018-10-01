package com.tiany.ibator.inf;

import com.tiany.ibator.meta.Table;

import java.util.List;

public interface DaoImplGenerator extends Generator{
    public String generateDaoImpl(Table table, List<Generator> list);
}
