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
        String ret = "<insert id=\"" + getInsertId() + "\"  parameterClass=\"" + entityPackageName + "." + table.getEntityName() + "\" >\r\n";
        ret += "\tINSERT INTO " + table.getName() + " (\r\n";
        ret += "\t<dynamic prepend=\" \">\r\n";
        List<Field> fields = table.getFields();
        for (int i = 0; i < fields.size(); i++) {
            ret += "\t\t<" + getPropertyDynamicLabel(fields.get(i)) + " property=\"" + StringUtil.getCamelProperty(fields.get(i).getName()) + "\" prepend=\",\">" + fields.get(i).getName() + "</" + getPropertyDynamicLabel(fields.get(i)) + "> \r\n";
        }

        ret += "\t</dynamic>";
        ret += "\r\n\t) VALUES (\r\n";
        ret += "\t<dynamic prepend=\" \">\r\n";
        for (int i = 0; i < fields.size(); i++) {
            ret += "\t\t<" + getPropertyDynamicLabel(fields.get(i)) + " property=\"" + StringUtil.getCamelProperty(fields.get(i).getName()) + "\" prepend=\",\"> #" + StringUtil.getCamelProperty(fields.get(i).getName()) + "# </" + getPropertyDynamicLabel(fields.get(i)) + "> \r\n";
        }
        ret += "\t</dynamic>";
        ret += "\r\n\t)\r\n";
        ret += "</insert>\r\n";
        return ret;
    }
}
