package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.Generator;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class SelectByPrimaryKeyForUpdateDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table)+" {\r\n";
        ret += "    return ("+table.getEntityName()+")sqlMap.queryForObject(\""+table.getEntityName()+".selectByPrimaryKeyForUpdate\","+StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName())+");\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
