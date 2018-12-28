package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.inf.MapperUpdateGenerator;
import com.tiany.ibator.meta.Field;
import com.tiany.ibator.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateByExampleMapperGenerator extends AbstractBaseSqlibator implements MapperUpdateGenerator {

    @Autowired
    private UpdateWhereSqlMapperGenerator updateWhereSqlMapperGenerator;

    @Override
    public String getUpdateId() {
        return "updateByExample";
    }

    @Override
    public String generate(Table table) {
        String ret = "<update id=\""+getUpdateId()+"\"  parameterClass=\"java.util.Map\" >\r\n";
        ret += "\tUPDATE "+table.getName()+"\r\n";
        List<Field> fields = table.getFields();
        List<Field> primaryKeys = table.getPrimaryKeys();
        String entityName = table.getEntityName();
        String camelProperty = getBeanNameByClassName(entityName);
        ret += "\t<dynamic prepend=\"set\" >\r\n";
        for(int i =0;i<fields.size();i++){
            // 不是主键时
            if(!hasPrimatyKey(table)){
                ret = getString(ret, fields, camelProperty, i);
            }else {
                if (!primaryKeys.get(0).getName().toUpperCase().equals(fields.get(i).getName().toUpperCase())) {
                    ret = getString(ret, fields, camelProperty, i);
                }
            }
        }
        ret += "\t</dynamic>\r\n";

        ret += "\t<include refid=\""+updateWhereSqlMapperGenerator.getSqlId()+"\" />\n";
        ret += "</update>\r\n";
        return ret;
    }

    private String getString(String ret, List<Field> fields, String camelProperty, int i) {
        ret += "\t\t<" + getPropertyDynamicLabel(fields.get(i)) + " prepend=\",\" property=\"" + camelProperty + "." + StringUtil.getCamelProperty(fields.get(i).getName()) + "\" >";
        ret += " " + fields.get(i).getName() + " = #" + camelProperty + "." + StringUtil.getCamelProperty(fields.get(i).getName()) + "#";
        ret += " </" + getPropertyDynamicLabel(fields.get(i)) + ">\r\n";
        return ret;
    }

}
