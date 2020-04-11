package com.tiany.ibator.impl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.common.RemarkHelper;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.impl.comment.DaoImplClassComment;
import com.tiany.ibator.impl.daoimpl.AbstractBaseDaoImplGenerator;
import com.tiany.ibator.infs.DaoImplGenerator;
import com.tiany.ibator.infs.Generator;
import com.tiany.util.CastUtil;
import com.tiany.util.DateUtil;
import com.tiany.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DaoImplGeneratorImpl extends AbstractBaseSqlibator implements DaoImplGenerator, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(DaoImplGeneratorImpl.class);
    private List<Generator> generators;
    @Autowired
    private DaoImplClassComment classComment;
    @Autowired
    private RemarkHelper remarkHelper;

    @Override
    public String generateDaoImpl(Table table, List<Generator> list) {
        boolean generateInterface = CastUtil.castBoolean(tibatisConfig.get("generateInterface"));
        String daoPackageName = tibatisConfig.get("daoPackageName");
        String entityPackageName = tibatisConfig.get("entityPackageName");
        String ret = "";
        ret += "package " + daoPackageName + (CastUtil.castBoolean(tibatisConfig.get("generateInterface")) ? ".impl" : "") + ";\n\n";

        List<String> imports = new ArrayList<>();
        imports.add(entityPackageName + "." + table.getEntityName());
        imports.add(entityPackageName + "." + table.getEntityName() + "Example");
        imports.add("java.util.List");
        imports.add("java.util.Map");
        imports.add("java.util.ArrayList");
        imports.add("java.util.HashMap");
        imports.add("java.util.logging.Level");
        imports.add("java.util.logging.Logger");
        imports.add("org.springframework.beans.factory.annotation.Autowired");
        imports.add("org.springframework.orm.ibatis.SqlMapClientOperations");
        imports.add("org.springframework.stereotype.Repository");

//        if(hasClass(table,"BigInteger")){
//            imports.add("java.math.BigInteger");
//        }
//        if(hasClass(table,"Date")){
//            imports.add("java.util.Date");
//        }
//        if(hasClass(table,"BigDecimal")) {
//            imports.add("java.math.BigDecimal");
//        }

        if (generateInterface) {
            imports.add(daoPackageName + "." + table.getEntityName() + "Dao");
        }
        Collections.sort(imports);

        for (String s : imports) {
            ret += "import " + s + ";\n";
        }
        ret += "\n";

        String classRemark = remarkHelper.getClassRemark(classComment, table);
        ret += classRemark;
//        ret += "/**\n";
//        ret += " * " + getCommentString(table.getComment()) + "Dao" + (generateInterface ? "Impl" : "") + " . \n";
//        ret += " * @author " + System.getProperty("user.name") + "\n";
//        ret += " * @version " + DateUtil.thisDate() + " modify: " + System.getProperty("user.name") + "\n";
//        ret += " * @since 1.0\n";
//        ret += " */\n";
//        ret += "\n";
        ret += "@Repository\n";
        ret += "@SuppressWarnings(\"unchecked\")\n";
        if (generateInterface) {
            ret += "public class " + table.getEntityName() + "DaoImpl implements " + table.getEntityName() + "Dao {\n";
            ret += "  private static final Logger logger = Logger.getLogger(" + table.getEntityName() + "DaoImpl.class.getName());\n";
        } else {
            ret += "public class " + table.getEntityName() + "Dao {\n";
            ret += "  private static final Logger logger = Logger.getLogger(" + table.getEntityName() + "Dao.class.getName());\n";
        }

        ret += "  @Autowired\n";
        ret += "  private SqlMapClientOperations sqlMap;\n";
        ret += "  private List<String> fields = new ArrayList<>();\n\n";

        ret += "  /**\n" + "   * constructor .\n" + "   */\n";
        if (generateInterface) {
            ret += "  public " + table.getEntityName() + "DaoImpl() {\n";
        } else {
            ret += "  public " + table.getEntityName() + "Dao() {\n";
        }
        for (Field field : table.getFields()) {
            ret += "    fields.add(\"" + getJavaName(field) + "\");\n";
        }
        ret += "  }\n\n";

        ret += "  private static String getStackTraceString(Throwable ex) {\n";
        ret += "    StackTraceElement[] traceElements = ex.getStackTrace();\n";
        ret += "    StringBuilder traceBuilder = new StringBuilder();\n";
        ret += "    if (traceElements != null && traceElements.length > 0) {\n";
        ret += "      for (StackTraceElement traceElement : traceElements) {\n";
        ret += "        traceBuilder.append(traceElement.toString());\n";
        ret += "        traceBuilder.append(\"\\n\");\n";
        ret += "      }\n";
        ret += "    }\n";
        ret += "    return traceBuilder.toString();\n";
        ret += "  }\n\n";

        ret += "  // 构造异常堆栈信息\n";
        ret += "  private static String buildErrorMessage(Exception ex) {\n";
        ret += "    String stackTrace = getStackTraceString(ex);\n";
        ret += "    String exceptionType = ex.toString();\n";
        ret += "    String exceptionMessage = ex.getMessage();\n";
        ret += "    return String.format(\"%s : %s \\r\\n %s\", exceptionType, exceptionMessage, stackTrace);\n";
        ret += "  }\n\n";

        for (Generator g : generators) {
            String generate = g.generate(table);
            if (g instanceof AbstractBaseDaoImplGenerator && !generateInterface) {
                AbstractBaseDaoImplGenerator baseDaoImplGenerator = (AbstractBaseDaoImplGenerator) g;
                ret += baseDaoImplGenerator.getComment(table);
            } else {
                if (StringUtil.isNotEmpty(generate)) {
                    ret += "  @Override\n";
                }
            }

            if (StringUtil.isNotEmpty(generate)) {
                ret += generate;
            }

        }
        ret += "}\n";
        return ret;
    }

    @Override
    public String generate(Table table) {
        return generateDaoImpl(table, generators);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] names = applicationContext.getBeanNamesForType(AbstractBaseDaoImplGenerator.class);
        generators = new ArrayList<>();
        for (String name : names) {
            Generator bean = applicationContext.getBean(name, Generator.class);
            generators.add(bean);
        }
        logger.debug("generators=================:" + Arrays.toString(names));
    }
}
