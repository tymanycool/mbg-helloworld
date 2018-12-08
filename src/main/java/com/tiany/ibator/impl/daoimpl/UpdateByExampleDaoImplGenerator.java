package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class UpdateByExampleDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        table.getEntityName();
        String ret = "";
        ret += getDaoString(table) + " {\r\n";
        ret += "    Map<String, Object> params = new HashMap<>();\r\n";
        ret += "    params.put(\"example\", example);\r\n";
        ret += "    params.put(\""+getBeanNameByClassName(table.getEntityName())+"\", "+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "    return sqlMap.update(\""+ table.getEntityName() +".updateByExample\",params);\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
