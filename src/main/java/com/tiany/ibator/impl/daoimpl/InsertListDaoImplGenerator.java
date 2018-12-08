package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class InsertListDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table)+" {\r\n";
        ret += "    return sqlMap.insert(\""+table.getEntityName()+".insertList\", "+getBeanNameByClassName(table.getEntityName())+"List);\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
