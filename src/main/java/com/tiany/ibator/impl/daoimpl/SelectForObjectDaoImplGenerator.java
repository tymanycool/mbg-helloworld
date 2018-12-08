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
        ret += getDaoString(table) + " {\r\n";
        ret += "    "+table.getEntityName()+" result = null;\n";
        ret += "    try {\n";
        ret += "      result = ("+table.getEntityName()+")sqlMap.queryForObject(\""+table.getEntityName()+".selectForObject\",\n          "+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "    } catch (Exception e) {\n";
        ret += "      logger.log(Level.INFO, \"查询参数对象:\" + "+getBeanNameByClassName(table.getEntityName())+", e);\n";
        ret += "      logger.log(Level.INFO, \"查询结果:\" + result);\n";
        ret += "    }\n";
        ret += "    return result;\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
