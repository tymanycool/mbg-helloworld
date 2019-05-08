package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class InsertListDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table)+" {\r\n";
        ret += "    try {\n";
        ret += "      sqlMap.insert(\""+table.getEntityName()+".insertList\", "+getBeanNameByClassName(table.getEntityName())+"List);\r\n";
        ret += "      return true;\r\n";
        ret += "    } catch (Exception e) {\n";
        ret += "      logger.log(Level.INFO, \"插入失败：\" + e.getMessage());\n";
        ret += "      return false;\n";
        ret += "    }\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
