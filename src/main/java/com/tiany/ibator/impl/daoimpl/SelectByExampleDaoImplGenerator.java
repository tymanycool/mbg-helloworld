package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectByExampleDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table)+" {\n";
        ret += "    try {\n";
        ret += "      return (List<"+table.getEntityName()+">)sqlMap.queryForList(\""+table.getEntityName()+".selectByExample\",example);\n";
        ret += "    } catch (Exception e) {\n";
        ret += "      logger.log(Level.INFO, \"查询失败:\" + buildErrorMessage(e) + \",可能没有查询到可用数据\");\n";
        ret += "      return null;\n";
        ret += "    }\n";
        ret += "  }\n\n";
        return ret;
    }
}
