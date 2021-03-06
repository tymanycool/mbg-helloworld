package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperSelectGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectCountByParamsMapperGenerator extends AbstractBaseSqlibator implements MapperSelectGenerator {
    @Override
    public String getSelectId() {
        return "selectCountByParams";
    }

    @Override
    public String generate(Table table) {
        String ret = "<select id=\""+getSelectId()+"\" resultClass=\"java.lang.Integer\" parameterClass=\"java.util.Map\" >\n";
        ret += "\tSELECT COUNT(*)\n";
        ret += "\tFROM " + table.getName() +" \n";
        ret += "\t<dynamic prepend=\"where\" >\n";
        List<Field> fields = table.getFields();
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
