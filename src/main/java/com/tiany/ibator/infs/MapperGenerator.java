package com.tiany.ibator.infs;

import com.tiany.ibator.common.meta.Table;

import java.util.List;

public interface MapperGenerator extends Generator{
   public String generateMapper(Table table,List<Generator> list);
}
