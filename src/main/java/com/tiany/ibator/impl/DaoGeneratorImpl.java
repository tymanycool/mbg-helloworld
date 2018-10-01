package com.tiany.ibator.impl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.inf.DaoGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.DateUtil;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class DaoGeneratorImpl extends AbstractBaseSqlibator implements DaoGenerator ,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(DaoGeneratorImpl.class);
    private List<Generator> generators;
    @Override
    public String generate(Table table) {
        return generateDao(table,generators);
    }

    @Override
    public String generateDao(Table table, List<Generator> generators) {
        String ret = "";
        ret += "package "+ daoPackageName +";\r\n\r\n";
        ret += "import java.util.List;\r\n";
        ret += "import java.util.Map;\r\n\r\n";
        ret += "import "+entityPackageName+"."+table.getEntityName()+";\r\n\r\n";
        ret += "/*\r\n";
        ret += " * @description "+getCommentString(table.getComment())+"Dao\r\n";
        ret += " * @author "+ System.getProperty("user.name")+"\r\n";
        ret += " * @version "+ DateUtil.thisDate()+" modify: "+System.getProperty("user.name")+"\r\n";
        ret += " * @since 1.0\r\n";
        ret += " */\r\n";
        ret += "public interface " + table.getEntityName() + "Dao {\r\n";
        for(Generator g:generators){
            if (g instanceof AbstractBaseDaoGenerator){
                AbstractBaseDaoGenerator baseDaoGenerator = (AbstractBaseDaoGenerator)g;
                ret += baseDaoGenerator.getComment(table);
            }
            ret += g.generate(table)+";\r\n\r\n";
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
        for(String name : names){
            Generator bean = applicationContext.getBean(name, Generator.class);
            generators.add(bean);
        }
        logger.debug("generators=================:"+ Arrays.toString(names));
    }


}
