package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class SelectByExampleDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table)+" {\n";
        ret += "    try {\n";
        ret += "      return (List<"+table.getEntityName()+">)sqlMap.queryForList(\""+table.getEntityName()+".selectByExample\",example);\r\n";
        ret += "    } catch (Exception e) {\n";
        ret += "      logger.log(Level.INFO, \"查询结果:null\", e);\n";
        ret += "      return null;\n";
        ret += "    }\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
