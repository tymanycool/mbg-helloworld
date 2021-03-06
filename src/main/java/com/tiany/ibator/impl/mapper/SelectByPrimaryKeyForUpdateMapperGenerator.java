package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.MapperSelectGenerator;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SelectByPrimaryKeyForUpdateMapperGenerator extends AbstractBaseSqlibator implements MapperSelectGenerator {
    @Override
    public String getSelectId() {
        return "selectByPrimaryKeyForUpdate";
    }

    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        List<Field> primaryKeys = table.getPrimaryKeys();
        String ret = "<select id=\""+getSelectId()+"\" resultMap=\""+table.getEntityName()+"BaseResultMap\" parameterClass=\""+ MapUtil.getIgnoreCase((Map) typesConfig,primaryKeys.get(0).getType())+"\" >\n";
        ret += "\tSELECT ";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += fields.get(i).getName();
            if(i<fields.size()-1){
                ret += ",";
            }
        }
        ret += "\t\n";
        ret += "\tFROM " + table.getName() +" \n";
        ret += "\tWHERE "+fields.get(0).getName()+" = #"+StringUtil.getCamelProperty(fields.get(0).getName())+"#\n";
        ret += "\tFOR UPDATE\n";
        ret += "</select>\n";
        return ret;
    }
}
