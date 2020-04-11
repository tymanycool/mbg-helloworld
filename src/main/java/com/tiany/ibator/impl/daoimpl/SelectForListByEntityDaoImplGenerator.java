package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.Generator;
import org.springframework.stereotype.Component;

@Component
public class SelectForListByEntityDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table) + " {\n";
        ret += "    return sqlMap.queryForList(\"" + table.getEntityName() + ".selectForListByEntity\"," + getBeanNameByClassName(table.getEntityName()) + ");\n";
        ret += "  }\n\n";
        return ret;
    }
}
