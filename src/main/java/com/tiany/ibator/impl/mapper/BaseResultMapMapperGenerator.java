package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractSqlibator;
import com.tiany.ibator.inf.MapperResultMapGenerator;
import com.tiany.ibator.meta.Field;
import com.tiany.ibator.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaseResultMapMapperGenerator extends AbstractSqlibator implements MapperResultMapGenerator {
    @Override
    public String generate(Table table) {
        List<Field> fields = table.getFields();
        String ret = "<resultMap id=\""+table.getEntityName()+""+getResultMapId()+"\" class=\""+ entityPackageName +"."+table.getEntityName()+"\" >\r\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t<result column=\"" + fields.get(i).getName()+"\" property=\"" + StringUtil.getCamelProperty(fields.get(i).getName()) + "\" jdbcType=\"" + fields.get(i).getType().toUpperCase()+"\" />\r\n";
        }
        ret += "</resultMap>\r\n\r\n";
        return ret;
    }
    @Override
    public String getResultMapId() {
        return "BaseResultMap";
    }
}
