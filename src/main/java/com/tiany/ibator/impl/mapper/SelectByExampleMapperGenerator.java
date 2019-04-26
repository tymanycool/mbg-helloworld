package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperSelectGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        String entityPackageName = tibatisConfig.get("entityPackageName");
        boolean generatePage = CastUtil.castBoolean(tibatisConfig.get("generatePage"));
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
        ret += "\t<include refid=\"" + whereSqlMapperGenerator.getSqlId() + "\" />\n";
        //ret += "\t</isNotNull>\n";
        ret += "\t<isNotNull property=\"orderByClause\">\n";
        ret += "\t\tORDER BY $orderByClause$\n";
        ret += "\t</isNotNull>\n";
        if (generatePage) {
            ret += "\t<isNotEqual property=\"pageSize\" compareValue=\"0\">\n";
            ret += "\t\tLIMIT #pageStartIndex#,#pageSize#\n";
            ret += "\t</isNotEqual>\n";
        }
        ret += "</select>\r\n";
        return ret;
    }
}
