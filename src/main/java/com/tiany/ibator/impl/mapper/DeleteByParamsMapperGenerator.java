package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.inf.MapperDeleteGenerator;
import com.tiany.ibator.inf.MapperSelectGenerator;
import com.tiany.ibator.meta.Field;
import com.tiany.ibator.meta.Table;
import com.tiany.util.StringUtil;
import com.tiany.util.validate.AssertUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteByParamsMapperGenerator extends AbstractBaseSqlibator implements MapperDeleteGenerator {
    @Override
    public String getDeleteId() {
        return "deleteByParams";
    }
    @Override
    public String generate(Table table) {
        String ret = "<delete id=\""+getDeleteId()+"\"  parameterClass=\"java.util.Map\" >\r\n";
        ret += "\tDELETE FROM ";
        ret += table.getName() +" \r\n";
        List<Field> fields = table.getPrimaryKeys();

        if(fields.size()<1){
            fields = table.getFields();
        }

        ret += "\tWHERE "+fields.get(0).getName()+" = #"+ StringUtil.getCamelProperty(fields.get(0).getName())+"#\r\n";
        ret += "</delete>\r\n";
        return ret;
    }
}
