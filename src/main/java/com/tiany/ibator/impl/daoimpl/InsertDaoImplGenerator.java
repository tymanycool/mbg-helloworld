package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class InsertDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table)+" {\n";
        ret += "    try {\n";
        ret += "      sqlMap.insert(\""+table.getEntityName()+".insert\", "+getBeanNameByClassName(table.getEntityName())+");\n";
        ret += "      return true;\n";
        ret += "    } catch (Exception e) {\n";
        ret += "      logger.log(Level.INFO, \"插入失败：\" + buildErrorMessage(e));\n";
        ret += "      return false;\n";
        ret += "    }\n";
        ret += "  }\n\n";
        return ret;
    }
}
