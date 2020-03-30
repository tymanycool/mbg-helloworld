package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperDeleteGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import com.tiany.util.validate.AssertUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DeleteByPrimaryKeyMapperGenerator extends AbstractBaseSqlibator implements MapperDeleteGenerator {
    @Override
    public String getDeleteId() {
        return "deleteByPrimaryKey";
    }
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        List<Field> fields = table.getPrimaryKeys();
        AssertUtil.isTrue(fields.size() == 1);
        String ret = "<delete id=\""+getDeleteId()+"\"  parameterClass=\""+ MapUtil.getIgnoreCase((Map) typesConfig,fields.get(0).getType())+"\" >\r\n";
        ret += "\tDELETE FROM ";
        ret += table.getName() +" \r\n";
        ret += "\tWHERE "+fields.get(0).getName()+" = #"+StringUtil.getCamelProperty(fields.get(0).getName())+"#\r\n";
        ret += "\tLIMIT 2\r\n";
        ret += "</delete>\r\n";
        return ret;
    }
}
