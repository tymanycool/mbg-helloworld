package com.tiany.ibator.impl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.infs.DaoGenerator;
import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.DateUtil;
import com.tiany.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DaoGeneratorImpl extends AbstractBaseSqlibator implements DaoGenerator, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(DaoGeneratorImpl.class);
    private List<Generator> generators;

    @Override
    public String generate(Table table) {
        return generateDao(table, generators);
    }

    @Override
    public String generateDao(Table table, List<Generator> generators) {
        String ret = "";
        ret += "package " + tibatisConfig.get("daoPackageName") + ";\r\n\r\n";

        List<String> imports = new ArrayList<>();
        imports.add(tibatisConfig.get("entityPackageName") + "." + table.getEntityName());
        imports.add(tibatisConfig.get("entityPackageName") + "." + table.getEntityName() + "Example");
        imports.add("java.util.List");
        imports.add("java.util.Map");
//        if(hasClass(table,"BigInteger")){
//            imports.add("java.math.BigInteger");
//        }
//        if(hasClass(table,"Date")){
//            imports.add("java.util.Date");
//        }
//        if(hasClass(table,"BigDecimal")) {
//            imports.add("java.math.BigDecimal");
//        }
        Collections.sort(imports);

        for (String s : imports) {
            ret += "import " + s + ";\n";
        }

        ret += "\n";

        ret += "/**\r\n";
        ret += " * " + getCommentString(table.getComment()) + "Dao .\r\n";
        ret += " * @author " + System.getProperty("user.name") + "\r\n";
        ret += " * @version " + DateUtil.thisDate() + " modify: " + System.getProperty("user.name") + "\r\n";
        ret += " * @since 1.0\r\n";
        ret += " */\r\n\n";
        ret += "public interface " + table.getEntityName() + "Dao {\r\n";
        for (Generator g : generators) {
            if (g instanceof AbstractBaseDaoGenerator) {
                AbstractBaseDaoGenerator baseDaoGenerator = (AbstractBaseDaoGenerator) g;
                ret += baseDaoGenerator.getComment(table);
            }
            String generate = g.generate(table);
            if (StringUtil.isNotEmpty(generate)) {
                ret += generate + ";\r\n\r\n";
            }
        }
        ret += "}\r\n";
        return ret;
    }

    public List<Generator> getGenerators() {
        return generators;
    }

    public void setGenerators(List<Generator> generators) {
        this.generators = generators;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] names = applicationContext.getBeanNamesForType(AbstractBaseDaoGenerator.class);
        generators = new ArrayList<>();
        for (String name : names) {
            Generator bean = applicationContext.getBean(name, Generator.class);
            generators.add(bean);
        }
        logger.debug("generators=================:" + Arrays.toString(names));
    }


}
