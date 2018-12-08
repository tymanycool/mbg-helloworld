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
        ret += getDaoString(table)+" {\r\n";
        ret += "    if (params == null || params.size() == 0) {\n";
        ret += "      throw new IllegalArgumentException(\"缺少参数,请检查...\");\n";
        ret += "    }\n";
        ret += "    boolean flag = false;\n";
        ret += "    for (String key : fields) {\n";
        ret += "      if (params.containsKey(key)) {\n";
        ret += "        flag = true;\n";
        ret += "        break;\n";
        ret += "      }\n";
        ret += "    }\n";
        ret += "    if (!flag) {\n";
        ret += "      throw new IllegalArgumentException(\"缺少参数,请检查...\");\n";
        ret += "    }\n";
        ret += "    return sqlMap.delete(\""+table.getEntityName()+".deleteByParams\",params);\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }
}
