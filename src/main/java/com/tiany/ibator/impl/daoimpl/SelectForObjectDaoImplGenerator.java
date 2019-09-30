package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
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
        ret += "      String message = String.valueOf(e.getMessage());\n";
        ret += "      if (message.contains(\"returned too many results\")) {\n";
        ret += "        logger.log(Level.INFO, \"查询参数对象:\" + "+getBeanNameByClassName(table.getEntityName())+");\n";
        ret += "        logger.log(Level.INFO, \"查询到多条数据:\" + e.getMessage());\n";
        ret += "      } else {\n";
        ret += "        logger.log(Level.INFO, \"查询参数对象:\" + "+getBeanNameByClassName(table.getEntityName())+");\n";
        ret += "        logger.log(Level.INFO, \"查询异常：\" + buildErrorMessage(e));\n";
        ret += "        throw new RuntimeException(\"查询异常\", e);\n";
        ret += "      }\n";
        ret += "    }\n";
        ret += "    return result;\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
