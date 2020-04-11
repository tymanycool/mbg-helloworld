package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperUpdateGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateByPrimaryKeyForcibleMapperGenerator extends AbstractBaseSqlibator implements MapperUpdateGenerator {

    @Override
    public String getUpdateId() {
        return "updateByPrimaryKeyForcible";
    }

    @Override
    public String generate(Table table) {
        String entityPackageName = tibatisConfig.get("entityPackageName");
        if (!hasPrimatyKey(table)) {
            return "";
        }
        List<Field> primaryKeys = table.getPrimaryKeys();
        String ret = "<update id=\"" + getUpdateId() + "\"  parameterClass=\"" + entityPackageName + "." + table.getEntityName() + "\" >\n";
        ret += "\tUPDATE " + table.getName() + " SET\n";
        List<Field> fields = table.getFields();
        for (int i = 0; i < fields.size(); i++) {
            // 不是主键时
            if (!primaryKeys.get(0).getName().toUpperCase().equals(fields.get(i).getName().toUpperCase())) {
                ret += "\t" + fields.get(i).getName() + " = #" + StringUtil.getCamelProperty(fields.get(i).getName());
                if (i <= fields.size() - 2) {
                    ret += "# ,\n";
                } else {
                    ret += "# \n";
                }
            }
        }
        ret += "\tWHERE " + primaryKeys.get(0).getName() + " = #" + StringUtil.getCamelProperty(primaryKeys.get(0).getName()) + "#\n";
        ret += "\tLIMIT 2\n";
        ret += "</update>\n";
        return ret;
    }
}
