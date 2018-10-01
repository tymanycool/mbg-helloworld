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
        String ret = "";
        ret += getDaoString(table) + "{\r\n";
        ret += "\t\tMap<String, Object> params = new HashMap<String,Object>();\r\n";
        ret += "\t\tparams.put(\""+StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List"+"\", "+StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List"+");\r\n";
        ret += "\t\tparams.put(\""+getBeanNameByClassName(table.getEntityName())+"\", "+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "\t\treturn sqlMap.update(\""+table.getEntityName()+".updateList\",params);\r\n";
        ret += "\t}\r\n\r\n";
        return ret;
    }
}
