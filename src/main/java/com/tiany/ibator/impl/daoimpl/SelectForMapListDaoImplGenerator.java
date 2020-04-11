package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForMapListDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table) + " {\n";
        ret += "    return sqlMap.queryForList(\""+table.getEntityName()+".selectForMapList\",params);\n";
        ret += "  }\n\n";
        return ret;
    }
}
