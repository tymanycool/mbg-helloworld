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
        ret += "\t\tif(params == null||params.size()==0){\n";
        ret += "\t\t\tthrow new IllegalArgumentException(\"缺少参数,请检查...\");\n";
        ret += "\t\t}\n";
        ret += "\t\tboolean flag = false;\n";
        ret += "\t\tfor(String key:fields){\n";
        ret += "\t\t\tif(params.containsKey(key)){\n";
        ret += "\t\t\t\tflag = true;\n";
        ret += "\t\t\t\tbreak;\n";
        ret += "\t\t\t}\n";
        ret += "\t\t}\n";
        ret += "\t\tif(!flag){\n";
        ret += "\t\t\tthrow new IllegalArgumentException(\"缺少参数,请检查...\");\n";
        ret += "\t\t}\n";
        ret += "\t\treturn sqlMap.delete(\""+table.getEntityName()+".deleteByParams\",params);\r\n";
        ret += "\t}\r\n\r\n";
        return ret;
    }
}
