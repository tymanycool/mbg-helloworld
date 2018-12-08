package com.tiany.ibator.impl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.impl.daoimpl.AbstractBaseDaoImplGenerator;
import com.tiany.ibator.inf.DaoImplGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Field;
import com.tiany.ibator.meta.Table;
import com.tiany.util.DateUtil;
import com.tiany.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Component
public class DaoImplGeneratorImpl extends AbstractBaseSqlibator implements DaoImplGenerator ,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(DaoImplGeneratorImpl.class);
    private List<Generator> generators;

    @Override
    public String generateDaoImpl(Table table, List<Generator> list) {
        String ret = "";
        ret += "package "+ daoPackageName +(generateInterface?".impl":"")+";\r\n\r\n";

        List<String> imports = new ArrayList<>();
        imports.add(entityPackageName+"."+table.getEntityName());
        imports.add(entityPackageName+"."+table.getEntityName()+"Example");
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

        if(generateInterface) {
            imports.add(daoPackageName + "." + table.getEntityName() + "Dao");
        }
        Collections.sort(imports);

        for (String s : imports){
            ret +="import "+ s + ";\n";
        }
        ret += "\n";

        ret += "/**\r\n";
        ret += " * "+getCommentString(table.getComment())+"Dao"+(generateInterface?"Impl":"")+" . \r\n";
        ret += " * @author "+ System.getProperty("user.name")+"\r\n";
        ret += " * @version "+ DateUtil.thisDate()+" modify: "+System.getProperty("user.name")+"\r\n";
        ret += " * @since 1.0\r\n";
        ret += " */\r\n";
        ret += "\n";
        ret += "@Repository\r\n";
        ret += "@SuppressWarnings(\"unchecked\")\r\n";
        if(generateInterface) {
            ret += "public class " + table.getEntityName() + "DaoImpl implements " + table.getEntityName() + "Dao {\r\n";
            ret += "  private static final Logger logger = Logger.getLogger("+table.getEntityName()+"DaoImpl.class.getName());\n";
        }else{
            ret += "public class " + table.getEntityName() + "Dao {\r\n";
            ret += "  private static final Logger logger = Logger.getLogger("+table.getEntityName()+"Dao.class.getName());\n";
        }

        ret += "  @Autowired\r\n";
        ret += "  private SqlMapClientOperations sqlMap;\r\n";
        ret += "  private List<String> fields = new ArrayList<>();\r\n\r\n";

        ret += "  /**\n" + "   * constructor .\n" + "   */\n";
        if(generateInterface) {
            ret += "  public "+table.getEntityName()+"DaoImpl() {\n";
        }else{
            ret += "  public "+table.getEntityName()+"Dao() {\n";
        }
        for (Field field:table.getFields()){
            ret += "    fields.add(\""+getJavaName(field)+"\");\n";
        }
        ret += "  }\n\n";

        for(Generator g: generators){
            String generate = g.generate(table);
            if (g instanceof AbstractBaseDaoImplGenerator&&!generateInterface){
                AbstractBaseDaoImplGenerator baseDaoImplGenerator = (AbstractBaseDaoImplGenerator)g;
                ret += baseDaoImplGenerator.getComment(table);
            }else {
                if(StringUtil.isNotEmpty(generate)) {
                    ret += "  @Override\r\n";
                }
            }

            if(StringUtil.isNotEmpty(generate)){
                ret += generate;
            }

        }
        ret += "}\r\n";
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
        for(String name : names){
            Generator bean = applicationContext.getBean(name, Generator.class);
            generators.add(bean);
        }
        logger.debug("generators=================:"+ Arrays.toString(names));
    }
}
