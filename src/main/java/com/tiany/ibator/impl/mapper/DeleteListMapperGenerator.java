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
public class DeleteListMapperGenerator extends AbstractBaseSqlibator implements MapperDeleteGenerator {
    @Override
    public String generate(Table table) {
        List<Field> fields = table.getPrimaryKeys();
        AssertUtil.isTrue(fields.size() == 1);
        String ret = "<delete id=\""+getDeleteId()+"\"  parameterClass=\"java.util.List\" >\r\n";
        ret += "\tDELETE FROM ";
        ret += table.getName() +" \r\n";
        ret += "\tWHERE "+fields.get(0).getName() +" IN(\r\n";

        ret += "\t<iterate conjunction =\",\">\r\n";
        ret +="\t\t";
        for(int i =0;i<fields.size();i++){
            ret +="#[]#";
            if(i != fields.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t</iterate>)\r\n";
        ret += "</delete>\r\n";
        return ret;
    }

    @Override
    public String getDeleteId() {
        return "deleteList";
    }
}
