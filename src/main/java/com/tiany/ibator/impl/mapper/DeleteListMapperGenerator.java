package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperDeleteGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.validate.AssertUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteListMapperGenerator extends AbstractBaseSqlibator implements MapperDeleteGenerator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        List<Field> fields = table.getPrimaryKeys();
        AssertUtil.isTrue(fields.size() == 1);
        String ret = "<delete id=\""+getDeleteId()+"\"  parameterClass=\"java.util.List\" >\n";
        ret += "\tDELETE FROM ";
        ret += table.getName() +" \n";
        ret += "\tWHERE "+fields.get(0).getName() +" IN(\n";

        ret += "\t<iterate conjunction =\",\">\n";
        ret +="\t\t";
        for(int i =0;i<fields.size();i++){
            ret +="#[]#";
            if(i != fields.size()-1){
                ret += ",";
            }
        }
        ret += "\n\t</iterate>)\n";
        ret += "</delete>\n";
        return ret;
    }

    @Override
    public String getDeleteId() {
        return "deleteList";
    }
}
