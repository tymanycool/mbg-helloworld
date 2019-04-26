package com.tiany.ibator.infs;

import com.tiany.ibator.common.meta.Table;

import java.util.List;

public interface DaoGenerator extends Generator{
    public String generateDao(Table table, List<Generator> list);
}
