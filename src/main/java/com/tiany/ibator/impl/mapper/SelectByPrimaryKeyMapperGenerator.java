package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.inf.MapperSelectGenerator;
import com.tiany.ibator.meta.Field;
import com.tiany.ibator.meta.Table;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SelectByPrimaryKeyMapperGenerator extends AbstractBaseSqlibator implements MapperSelectGenerator {
    @Override
    public String getSelectId() {
        return "selectByPrimaryKey";
    }

    @Override
    public String generate(Table table) {
        List<Field> primaryKeys = table.getPrimaryKeys();
        String ret = "<select id=\""+getSelectId()+"\" resultMap=\""+table.getEntityName()+"BaseResultMap\" parameterClass=\""+ MapUtil.getIgnoreCase((Map)props,primaryKeys.get(0).getType())+"\" >\r\n";
        ret += "\tSELECT ";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += fields.get(i).getName();
            if(i<fields.size()-1){
                ret += ",";
            }
        }
        ret += "\t\r\n";
        ret += "\tFROM " + table.getName() +" \r\n";
        ret += "\tWHERE "+fields.get(0).getName()+" = #"+StringUtil.getCamelProperty(fields.get(0).getName())+"#\r\n";
        ret += "</select>\r\n";
        return ret;
    }
}
