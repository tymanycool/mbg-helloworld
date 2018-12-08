package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DeleteListDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table)+" {\r\n";
        ret += "    return sqlMap.delete(\"" + table.getEntityName() + ".deleteList\"," + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List);\r\n";
        ret += "  }\n\n";
        return ret;
    }
}
