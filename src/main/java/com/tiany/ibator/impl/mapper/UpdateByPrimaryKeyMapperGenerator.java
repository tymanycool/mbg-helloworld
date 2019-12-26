package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperUpdateGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateByPrimaryKeyMapperGenerator extends AbstractBaseSqlibator implements MapperUpdateGenerator {

    @Override
    public String getUpdateId() {
        return "updateByPrimaryKey";
    }

    @Override
    public String generate(Table table) {
        String entityPackageName = tibatisConfig.get("entityPackageName");
        if (!hasPrimatyKey(table)) {
            return "";
        }
        List<Field> primaryKeys = table.getPrimaryKeys();
        String ret = "<update id=\"" + getUpdateId() + "\"  parameterClass=\"" + entityPackageName + "." + table.getEntityName() + "\" >\r\n";
        ret += "\tUPDATE " + table.getName() + "\r\n";
        List<Field> fields = table.getFields();
        ret += "\t<dynamic prepend=\"set\" >\r\n";
        for (int i = 0; i < fields.size(); i++) {
            // 不是主键时
            if (!primaryKeys.get(0).getName().toUpperCase().equals(fields.get(i).getName().toUpperCase())) {
                ret += "\t\t<" + getPropertyDynamicLabel(fields.get(i)) + " prepend=\",\" property=\"" + StringUtil.getCamelProperty(fields.get(i).getName()) + "\" >";
                ret += " " + fields.get(i).getName() + " = #" + StringUtil.getCamelProperty(fields.get(i).getName()) + "#";
                ret += " </" + getPropertyDynamicLabel(fields.get(i)) + ">\r\n";
            }
        }
        ret += "\t</dynamic>\r\n";
        ret += "\tWHERE " + primaryKeys.get(0).getName() + " = #" + StringUtil.getCamelProperty(primaryKeys.get(0).getName()) + "#\r\n";
        ret += "\tLIMIT 1\r\n";
        ret += "</update>\r\n";
        return ret;
    }
}
