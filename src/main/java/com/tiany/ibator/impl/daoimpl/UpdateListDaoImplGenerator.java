package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UpdateListDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table) + " {\r\n";
        ret += "    Map<String, Object> params = new HashMap<>();\r\n";
        ret += "    params.put(\""+StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List"+"\", "+StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List"+");\r\n";
        ret += "    params.put(\""+getBeanNameByClassName(table.getEntityName())+"\", "+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "    return sqlMap.update(\""+table.getEntityName()+".updateList\",params);\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
