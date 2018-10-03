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
        ret += getDaoString(table) + "{\r\n";
        ret += "\t\tMap<String, Object> params = new HashMap<String,Object>();\r\n";
        ret += "\t\tparams.put(\"example\", example);\r\n";
        ret += "\t\tparams.put(\""+getBeanNameByClassName(table.getEntityName())+"\", "+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "\t\treturn sqlMap.update(\""+ table.getEntityName() +".updateByExample\",params);\r\n";
        ret += "\t}\r\n\r\n";
        return ret;
    }
}
