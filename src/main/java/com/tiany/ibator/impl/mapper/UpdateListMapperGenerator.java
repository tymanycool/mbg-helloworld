package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperUpdateGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateListMapperGenerator extends AbstractBaseSqlibator implements MapperUpdateGenerator {

    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "<update id=\""+getUpdateId()+"\"  parameterClass=\"java.util.Map\" >\n";
        ret += "\tUPDATE "+table.getName()+"\n";
        List<Field> fields = table.getFields();
        ret += "\t<dynamic prepend=\"set\" >\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\",\" property=\""+getBeanNameByClassName(table.getEntityName())+"."+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+getBeanNameByClassName(table.getEntityName())+"."+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\n";
        }
        ret += "\t</dynamic>\n";

        ret += "\tWHERE "+fields.get(0).getName() +" IN(\n";
        List<Field> primaryKeys = table.getPrimaryKeys();
        ret += "\t<iterate conjunction =\",\" property=\""+StringUtil.getCamelProperty(primaryKeys.get(0).getName())+"List\">\n";
        ret +="\t\t";
        for(int i =0;i<primaryKeys.size();i++){
            ret +="#"+StringUtil.getCamelProperty(primaryKeys.get(0).getName())+"List[]#";
            if(i != primaryKeys.size()-1){
                ret += ",";
            }
        }
        ret += "\n\t</iterate>)\n";
        ret += "\tLIMIT #"+StringUtil.getCamelProperty(primaryKeys.get(0).getName())+"LimitSize#\n";
        ret += "</update>\n";
        return ret;
    }

    @Override
    public String getUpdateId() {
        return "updateList";
    }
}
