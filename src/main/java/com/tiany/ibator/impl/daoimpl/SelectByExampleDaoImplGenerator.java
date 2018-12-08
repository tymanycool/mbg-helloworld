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
        ret += getDaoString(table)+" {\r\n";
        ret += "    return (List<"+table.getEntityName()+">)sqlMap.queryForList(\""+table.getEntityName()+".selectByExample\",example);\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
