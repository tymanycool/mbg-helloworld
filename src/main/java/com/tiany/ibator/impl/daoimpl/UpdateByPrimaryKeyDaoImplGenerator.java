package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class UpdateByPrimaryKeyDaoImplGenerator extends AbstractBaseDaoImplGenerator implements Generator {
    @Override
    public String generate(Table table) {
        if(!hasPrimatyKey(table)){
            return "";
        }
        String ret = "";
        ret += getDaoString(table) + " {\n";
        ret += "    if (transactionTemplate == null) {\n";
        ret += "      return sqlMap.update(\""+table.getEntityName()+".updateByPrimaryKey\","+getBeanNameByClassName(table.getEntityName())+");\n";
        ret += "    } else {\n";
        ret += "      return transactionTemplate.execute(new TransactionCallback<Integer>() {\n";
        ret += "        @Override\n";
        ret += "        public Integer doInTransaction(TransactionStatus status) {\n";
        ret += "          int result = sqlMap.update(\""+table.getEntityName()+".updateByPrimaryKey\","+getBeanNameByClassName(table.getEntityName())+");\n";
        ret += "          if (result != 1) {\n";
        ret += "            throw new UnsupportedOperationException(\"update_result_check_failed\");\n";
        ret += "          }\n";
        ret += "          return result;\n";
        ret += "        }\n";
        ret += "      });\n";
        ret += "    }\n";
        ret += "  }\n\n";
        return ret;
    }
}
