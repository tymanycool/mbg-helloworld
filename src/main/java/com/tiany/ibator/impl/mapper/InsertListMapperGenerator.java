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
        String ret = "<insert id=\"" + getInsertId() + "\"  parameterClass=\"java.util.Map\" >\n";
        ret += "\tINSERT INTO " + table.getName() + " (\n\t\t";
        List<Field> fields = table.getFields();
        for (int i = 0; i < fields.size(); i++) {
            ret += fields.get(i).getName();
            if (i != fields.size() - 1) {
                ret += ",";
            }
        }
        ret += "\n\t) VALUES \n";
        ret += "\t<iterate property=\"list\" conjunction =\",\">(\n";
        ret += "\t\t";
        boolean lastIsTimeType = false;
        for (int i = 0; i < fields.size(); i++) {
            String type = fields.get(i).getType();
            String camelProperty = StringUtil.getCamelProperty(fields.get(i).getName());
            if (!isTimeType(type)) {
                if (lastIsTimeType) {
                    ret += "\t\t";
                }
                lastIsTimeType = false;
                ret += "#list[]." + camelProperty + "#";
                if (i != fields.size() - 1) {
                    ret += ",";
                }
            } else {
                lastIsTimeType = true;
                if (!ret.endsWith("\n")) {
                    ret += "\n";
                }
                ret += "\t\t<isNotEqual property=\"list[]." + camelProperty + "\" compareValue=\"NOW()\"> #list[]." + camelProperty + "#";
                if (i != fields.size() - 1) {
                    ret += ",";
                }
                ret += " </isNotEqual>\n";
                ret += "\t\t<isEqual property=\"list[]." + camelProperty + "\" compareValue=\"NOW()\"> $list[]." + camelProperty + "$";
                if (i != fields.size() - 1) {
                    ret += ",";
                }
                ret += " </isEqual>";

                if (i != fields.size() - 1) {
                    ret += "\n";
                }

            }
        }
        ret += "\n\t)</iterate>\n";
        ret += "</insert>\n";
        return ret;
    }
}
