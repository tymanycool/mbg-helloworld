package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table) + "{\r\n";
        ret += "\t\treturn sqlMap.update(\""+table.getEntityName()+".updateByPrimaryKey\","+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "\t}\r\n\r\n";
        return ret;
    }
}