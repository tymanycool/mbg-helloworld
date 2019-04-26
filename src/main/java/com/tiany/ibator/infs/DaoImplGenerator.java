package com.tiany.ibator.infs;

import com.tiany.ibator.common.meta.Table;

import java.util.List;

public interface DaoImplGenerator extends Generator{
    public String generateDaoImpl(Table table, List<Generator> list);
}
