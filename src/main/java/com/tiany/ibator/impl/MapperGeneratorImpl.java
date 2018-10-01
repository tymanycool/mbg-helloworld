package com.tiany.ibator.impl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.impl.dao.AbstractBaseDaoGenerator;
import com.tiany.ibator.inf.*;
import com.tiany.ibator.meta.Table;
import com.tiany.util.format.FormatUtil;
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
public class MapperGeneratorImpl extends AbstractBaseSqlibator implements MapperGenerator,ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(MapperGeneratorImpl.class);
    private List<Generator> generators;
    private List<Generator> resultMaps = new ArrayList<>();
    private List<Generator> selects = new ArrayList<>();
    private List<Generator> deletes = new ArrayList<>();
    private List<Generator> updates = new ArrayList<>();
    private List<Generator> insters = new ArrayList<>();
    private List<Generator> sqls = new ArrayList<>();
    private List<Generator> defaults = new ArrayList<>();

    @Override
    public String generateMapper(Table table, List<Generator> generators) {
        for(Generator g:generators){
            if(g instanceof MapperResultMapGenerator){
                resultMaps.add(g);
            }else if(g instanceof MapperSqlGenerator){
                sqls.add(g);
            }else if(g instanceof MapperDeleteGenerator){
                deletes.add(g);
            }else if(g instanceof MapperUpdateGenerator){
                updates.add(g);
            }else if(g instanceof MapperInsertGenerator){
                insters.add(g);
            }else if(g instanceof MapperSelectGenerator){
                selects.add(g);
            }else {
                defaults.add(g);
            }
        }
        String ret = "";
        ret += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
        ret += "<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\" >\r\n";
        ret += "<sqlMap namespace=\"" + table.getEntityName() + "\" >\r\n";
        for (Generator g:resultMaps){
            ret += FormatUtil.addTab(g.generate(table), 1);
            ret += "\r\n";
        }
        for (Generator g:sqls){
            ret += FormatUtil.addTab(g.generate(table), 1);
            ret += "\r\n";
        }
        for (Generator g:selects){
            ret += FormatUtil.addTab(g.generate(table), 1);
            ret += "\r\n";
        }
        for (Generator g:updates){
            ret += FormatUtil.addTab(g.generate(table), 1);
            ret += "\r\n";
        }
        for (Generator g:insters){
            ret += FormatUtil.addTab(g.generate(table), 1);
            ret += "\r\n";
        }
        for (Generator g:deletes){
            ret += FormatUtil.addTab(g.generate(table), 1);
            ret += "\r\n";
        }
        for (Generator g:defaults){
            ret += FormatUtil.addTab(g.generate(table), 1);
            ret += "\r\n";
        }
        ret += "</sqlMap>\r\n";
        return ret;
    }

    @Override
    public String generate(Table table) {
        return generateMapper(table,generators);
    }

    public List<Generator> getGenerators() {
        return generators;
    }

    public void setGenerators(List<Generator> generators) {
        this.generators = generators;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] names = applicationContext.getBeanNamesForType(MapperGeneratorContext.class);
        generators = new ArrayList<>();
        for(String name : names){
            Generator bean = applicationContext.getBean(name, Generator.class);
            generators.add(bean);
        }
        logger.debug("generators=================:"+ Arrays.toString(names));
    }
}
