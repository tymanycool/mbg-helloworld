package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class DeleteByPrimaryKeyDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table)+" {\n";
        ret += "    return sqlMap.delete(\"" + table.getEntityName() + ".deleteByPrimaryKey\"," + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + ");\n";
        ret += "  }\n\n";
        return ret;
    }
}
