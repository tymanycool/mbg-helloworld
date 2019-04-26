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
        String ret = "<insert id=\""+getInsertId()+"\"  parameterClass=\"java.util.List\" >\r\n";
        ret += "\tINSERT INTO "+table.getName()+" (\r\n\t\t";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += fields.get(i).getName();
            if(i != fields.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t) VALUES \r\n";
        ret += "\t<iterate conjunction =\",\">(\r\n";
        ret +="\t\t";
        for(int i =0;i<fields.size();i++){
            ret +="#list[]."+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            if(i != fields.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t)</iterate>\r\n";
        ret += "</insert>\r\n";
        return ret;
    }
}
