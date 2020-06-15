package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class InsertListDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table) + " {\n";
        ret += "    try {\n";
        ret += "      Map<String,Object> params = new HashMap<>();\n";
        ret += "      params.put(\"list\", " + getBeanNameByClassName(table.getEntityName()) + "List);\n";
        ret += "      sqlMap.insert(\"" + table.getEntityName() + ".insertList\", params);\n";
        ret += "      return true;\n";
        ret += "    } catch (Exception e) {\n";
        ret += "      logger.log(Level.INFO, \"插入失败：\" + buildErrorMessage(e));\n";
        ret += "      return false;\n";
        ret += "    }\n";
        ret += "  }\n\n";
        return ret;
    }
}
