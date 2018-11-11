package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForObjectDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table) + "{\r\n";
        ret += "\t\t"+table.getEntityName()+" result = null;\n";
        ret += "\t\ttry {\n";
        ret += "\t\t\tresult= ("+table.getEntityName()+")sqlMap.queryForObject(\""+table.getEntityName()+".selectForObject\","+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "\t\t}catch (Exception e) {\n";
        ret += "\t\t\tlogger.log(Level.INFO, \"查询参数对象:\"+"+getBeanNameByClassName(table.getEntityName())+",e);\n";
        ret += "\t\t\tlogger.log(Level.INFO, \"查询结果:\"+result);\n";
        ret += "\t\t}\n";
        ret += "\t\treturn result;\n";
        ret += "\t}\r\n\r\n";
        return ret;
    }
}
