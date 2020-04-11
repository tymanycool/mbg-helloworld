package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperSelectGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectForListMapperGenerator extends AbstractBaseSqlibator implements MapperSelectGenerator {
    @Override
    public String getSelectId() {
        return "selectForList";
    }

    @Override
    public String generate(Table table) {
        String ret = "<select id=\""+getSelectId()+"\" resultMap=\""+table.getEntityName()+"BaseResultMap\" parameterClass=\"java.util.Map\" >\n";
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
        ret += "\t<dynamic prepend=\"where\" >\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\"and\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\n";
        }
        ret += "\t</dynamic>\n";
        ret += "</select>\n";
        return ret;
    }
}
