package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForMapDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table) + " {\r\n";
        ret += "    return (Map<String,Object>)sqlMap.queryForObject(\""+table.getEntityName()+".selectForMap\",params);\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
