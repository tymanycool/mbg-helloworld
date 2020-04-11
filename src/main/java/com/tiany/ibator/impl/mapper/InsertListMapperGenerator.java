package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperInsertGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InsertListMapperGenerator extends AbstractBaseSqlibator implements MapperInsertGenerator {
    @Override
    public String getInsertId() {
        return "insertList";
    }

    @Override
    public String generate(Table table) {
        String ret = "<insert id=\""+getInsertId()+"\"  parameterClass=\"java.util.List\" >\n";
        ret += "\tINSERT INTO "+table.getName()+" (\n\t\t";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += fields.get(i).getName();
            if(i != fields.size()-1){
                ret += ",";
            }
        }
        ret += "\n\t) VALUES \n";
        ret += "\t<iterate conjunction =\",\">(\n";
        ret +="\t\t";
        for(int i =0;i<fields.size();i++){
            ret +="#list[]."+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            if(i != fields.size()-1){
                ret += ",";
            }
        }
        ret += "\n\t)</iterate>\n";
        ret += "</insert>\n";
        return ret;
    }
}
