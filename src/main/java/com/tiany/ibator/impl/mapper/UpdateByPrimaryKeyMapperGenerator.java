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
        String ret = "<update id=\"" + getUpdateId() + "\"  parameterClass=\"" + entityPackageName + "." + table.getEntityName() + "\" >\n";
        ret += "\tUPDATE " + table.getName() + "\n";
        List<Field> fields = table.getFields();

        boolean containsVersion = false;
        String version = "";
        ret += "\t<dynamic prepend=\"set\" >\n";
        for (int i = 0; i < fields.size(); i++) {
            // 不是主键时
            String db_name = fields.get(i).getName();
            if (!primaryKeys.get(0).getName().toUpperCase().equals(db_name.toUpperCase())) {
                String type = fields.get(i).getType();
                String camelProperty = StringUtil.getCamelProperty(db_name);
                String dynamicLabel = getPropertyDynamicLabel(fields.get(i));
                if (!isTimeType(type)) {
                    if (!db_name.equalsIgnoreCase("version")) {
                        ret += "\t\t<" + dynamicLabel + " prepend=\",\" property=\"" + camelProperty + "\" > " + db_name + " = #" + camelProperty + "# </" + dynamicLabel + ">\n";
                    } else {
                        ret += "\t\t<" + dynamicLabel + " prepend=\",\" property=\"" + camelProperty + "\" > " + db_name + " = #" + camelProperty + "# + 1</" + dynamicLabel + ">\n";
                        containsVersion = true;
                        version = db_name;
                    }
                } else {
                    ret += "\t\t<" + dynamicLabel + " prepend=\",\" property=\"" + camelProperty + "\" >\n ";
                    ret += "\t\t\t<isNotEqual property=\"" + camelProperty + "\" compareValue=\"NOW()\"> " + db_name + " = #" + camelProperty + "# </isNotEqual>\n";
                    ret += "\t\t\t<isEqual property=\"" + camelProperty + "\" compareValue=\"NOW()\"> " + db_name + " = $" + camelProperty + "$ </isEqual>\n";
                    ret += "\t\t</" + dynamicLabel + ">\n";
                }
            }
        }
        ret += "\t</dynamic>\n";
        ret += "\tWHERE " + primaryKeys.get(0).getName() + " = #" + StringUtil.getCamelProperty(primaryKeys.get(0).getName()) + "#\n";
        if (containsVersion) {
            String camelProperty = StringUtil.getCamelProperty(version);
            ret += "\t<isNotNull prepend=\"AND\" property=\"" + camelProperty + "\" > " + version + " = #" + camelProperty + "# </isNotNull>\n";
        }
        ret += "\tLIMIT 2\n";
        ret += "</update>\n";
        return ret;
    }
}
