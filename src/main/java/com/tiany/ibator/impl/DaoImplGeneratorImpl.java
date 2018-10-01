package com.tiany.ibator.impl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.impl.daoimpl.AbstractBaseDaoImplGenerator;
import com.tiany.ibator.inf.DaoImplGenerator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class DaoImplGeneratorImpl extends AbstractBaseSqlibator implements DaoImplGenerator ,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(DaoImplGeneratorImpl.class);
    private List<Generator> generators;

    @Override
    public String generateDaoImpl(Table table, List<Generator> list) {
        String ret = "";
        ret += "package "+ daoPackageName +(generateInterface?".impl":"")+";\r\n\r\n";
        ret += "import java.util.List;\r\n";
        ret += "import java.util.Map;\r\n\r\n";
        ret += "import java.util.HashMap;\r\n\r\n";
        ret += "import org.springframework.beans.factory.annotation.Autowired;\r\n";
        ret += "import org.springframework.orm.ibatis.SqlMapClientOperations;\r\n";
        ret += "import org.springframework.stereotype.Repository;\r\n\r\n";
        if(generateInterface) {
            ret += "import " + daoPackageName + "." + table.getEntityName() + "Dao;\r\n";
        }
        ret += "import "+entityPackageName+"."+table.getEntityName()+";\r\n\r\n";
        ret += "/*\r\n";
        ret += " * @description "+getCommentString(table.getComment())+"Dao"+(generateInterface?"Impl":"")+"\r\n";
        ret += " * @author "+ System.getProperty("user.name")+"\r\n";
        ret += " * @version "+ DateUtil.thisDate()+" modify: "+System.getProperty("user.name")+"\r\n";
        ret += " * @since 1.0\r\n";
        ret += " */\r\n";
        ret += "@Repository\r\n";
        if(generateInterface) {
            ret += "public class " + table.getEntityName() + "DaoImpl implements " + table.getEntityName() + "Dao {\r\n";
        }else{
            ret += "public class " + table.getEntityName() + "Dao {\r\n";
        }
        ret += "\t@Autowired\r\n";
        ret += "\tprivate SqlMapClientOperations sqlMap;\r\n\r\n";

        for(Generator g: generators){
            if (g instanceof AbstractBaseDaoImplGenerator&&!generateInterface){
                AbstractBaseDaoImplGenerator baseDaoImplGenerator = (AbstractBaseDaoImplGenerator)g;
                ret += baseDaoImplGenerator.getComment(table);
            }else {
                ret += "\t@Override\r\n";
            }
            ret += g.generate(table);
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
