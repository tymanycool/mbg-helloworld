package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class DeleteByParamsDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        String ret = "";
        ret += getDaoString(table)+"{\r\n";
        ret += "\t\treturn sqlMap.delete(\""+table.getEntityName()+".deleteByParams\",params);\r\n";
        ret += "\t}\r\n\r\n";
        return ret;
    }
}
