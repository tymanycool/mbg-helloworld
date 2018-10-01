package com.tiany.ibator.inf;

import com.tiany.ibator.meta.Table;

import java.util.List;

public interface MapperGenerator extends Generator{
   public String generateMapper(Table table,List<Generator> list);
}
