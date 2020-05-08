package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperInsertGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InsertMapperGenerator extends AbstractBaseSqlibator implements MapperInsertGenerator {
    @Override
    public String getInsertId() {
        return "insert";
    }

    @Override
    public String generate(Table table) {
        String entityPackageName = tibatisConfig.get("entityPackageName");
        String ret = "<insert id=\"" + getInsertId() + "\"  parameterClass=\"" + entityPackageName + "." + table.getEntityName() + "\" >\n";
        ret += "\tINSERT INTO " + table.getName() + " (\n";
        ret += "\t<dynamic prepend=\" \">\n";
        List<Field> fields = table.getFields();
        for (int i = 0; i < fields.size(); i++) {
            ret += "\t\t<" + getPropertyDynamicLabel(fields.get(i)) + " property=\"" + StringUtil.getCamelProperty(fields.get(i).getName()) + "\" prepend=\",\">" + fields.get(i).getName() + "</" + getPropertyDynamicLabel(fields.get(i)) + "> \n";
        }

        ret += "\t</dynamic>";
        ret += "\n\t) VALUES (\n";
        ret += "\t<dynamic prepend=\" \">\n";
        for (int i = 0; i < fields.size(); i++) {
            String type = fields.get(i).getType();
            String camelProperty = StringUtil.getCamelProperty(fields.get(i).getName());
            if (!isTimeType(type)) {
                ret += "\t\t<" + getPropertyDynamicLabel(fields.get(i)) + " property=\"" + camelProperty + "\" prepend=\",\"> #" + camelProperty + "# </" + getPropertyDynamicLabel(fields.get(i)) + "> \n";
            } else {
                ret += "\t\t<" + getPropertyDynamicLabel(fields.get(i)) + " property=\"" + camelProperty + "\" prepend=\",\">\n";
                ret += "\t\t\t<isNotEqual property=\"" + camelProperty + "\" compareValue=\"NOW()\"> #" + camelProperty + "# </isNotEqual>\n";
                ret += "\t\t\t<isEqual property=\"" + camelProperty + "\" compareValue=\"NOW()\"> $" + camelProperty + "$ </isEqual>\n";
                ret += "\t\t</" + getPropertyDynamicLabel(fields.get(i)) + "> \n";
            }
        }
        ret += "\t</dynamic>";
        ret += "\n\t)\n";
        ret += "</insert>\n";
        return ret;
    }
}
