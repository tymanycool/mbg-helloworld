package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class UpdateByExampleDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        table.getEntityName();
        String ret = "";
        ret += getDaoString(table) + " {\n";
        ret += "    Map<String, Object> params = new HashMap<>();\n";
        ret += "    params.put(\"example\", example);\n";
        ret += "    params.put(\""+getBeanNameByClassName(table.getEntityName())+"\", "+getBeanNameByClassName(table.getEntityName())+");\n";
        ret += "    return sqlMap.update(\""+ table.getEntityName() +".updateByExample\",params);\n";
        ret += "  }\n\n";
        return ret;
    }
}
