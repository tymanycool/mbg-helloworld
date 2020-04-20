package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class UpdateListDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if (!hasPrimatyKey(table)) {
            return "";
        }
        String ret = "";
        ret += getDaoString(table) + " {\n";
        ret += "    Map<String, Object> params = new HashMap<>();\n";
        ret += "    params.put(\"" + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List" + "\", " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List" + ");\n";
        ret += "    params.put(\"" + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "LimitSize" + "\", " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List.size() + 1" + ");\n";
        ret += "    params.put(\"" + getBeanNameByClassName(table.getEntityName()) + "\", " + getBeanNameByClassName(table.getEntityName()) + ");\n";
        ret += "    int result = sqlMap.update(\"" + table.getEntityName() + ".updateList\",params);\n";
        ret += "    if (result != " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List.size()" + ") {\n";
        ret += "      throw new UnsupportedOperationException(\"update_result_check_failed\");\n";
        ret += "    }\n";
        ret += "    return result;\n";
        ret += "  }\n\n";
        return ret;
    }
}
