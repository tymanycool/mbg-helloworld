package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.inf.MapperSelectGenerator;
import com.tiany.ibator.meta.Field;
import com.tiany.ibator.meta.Table;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SelectByExampleMapperGenerator extends AbstractBaseSqlibator implements MapperSelectGenerator {
    @Autowired
    private WhereSqlMapperGenerator whereSqlMapperGenerator;
    @Override
    public String getSelectId() {
        return "selectByExample";
    }

    @Override
    public String generate(Table table) {
        List<Field> primaryKeys = table.getPrimaryKeys();
        String ret = "<select id=\"" + getSelectId() + "\" resultMap=\"" + table.getEntityName() + "BaseResultMap\" parameterClass=\"" + entityPackageName + "." + table.getEntityName() + "Example\" >\r\n";
        ret += "\tSELECT \n";
        ret += "\t<isEqual property=\"distinct\" compareValue=\"true\">\n";
        ret += "\t\tDISTINCT\n";
        ret += "\t</isEqual>\n";
        ret += "\t'false' as QUERYID,";
        List<Field> fields = table.getFields();
        for (int i = 0; i < fields.size(); i++) {
            ret += fields.get(i).getName();
            if (i < fields.size() - 1) {
                ret += ",";
            }
        }
        ret += "\t\r\n";
        ret += "\tFROM " + table.getName() + " \r\n";
        //ret += "<isNotNull property=\"_parameter\">\n";
        ret += "\t<include refid=\""+whereSqlMapperGenerator.getSqlId()+"\" />\n";
        //ret += "\t</isNotNull>\n";
        ret += "\t<isNotNull property=\"orderByClause\">\n";
        ret += "\t\tORDER BY $orderByClause$\n";
        ret += "\t</isNotNull>\n";
        ret += "</select>\r\n";
        return ret;
    }
}
