package com.tiany.util;

import com.tiany.ibator.CreateTableFilter;
import com.tiany.ibator.SimpleSqlibator;
import com.tiany.ibator.meta.Table;
import com.tiany.util.io.FileUtil;
import com.tiany.util.io.PropertiesUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CsiiDaoUtil {
    private static final Logger logger = LoggerFactory.getLogger(CsiiDaoUtil.class);
    private static ApplicationContext ioc;
    @Test
    public void test(){
        String sqlFilePath = "src/main/resources/xq_account.sql";
        start(sqlFilePath);
    }

    @Test
    public void test2(){
        String sqlFilePath = "src/main/resources/sql2.txt";
        start(sqlFilePath);
    }
    @Test
    public void test3(){
        String sqlFilePath = "src/main/resources/sql3.txt";
        start(sqlFilePath);
    }

    @Test
    public void test4(){
        String sqlFilePath = "src/main/resources/sql_temp.txt";
        start(sqlFilePath);
    }

    @Test
    public void test5(){
        String sqlFilePath = "src/main/resources/sql_temp.txt";
        List<File> files = FileUtil.listAllFiles(new File("."));

        //start(sqlFilePath);
    }


    public static void start(String sqlFilePath){
        try {

            if(ioc==null){
                ioc =  new ClassPathXmlApplicationContext("classpath*:spring.xml");
                logger.debug("ioc==================:{}",ioc);
            }else{
                logger.info("ioc==================:{}",ioc);
            }
            SimpleSqlibator simpleSqlibator = (SimpleSqlibator)ioc.getBean(SimpleSqlibator.class);

            // 加载配置
            String removePrefix = PropertiesUtil.getProperty("tibatis.properties", "removePrefix");
            String prefix = PropertiesUtil.getProperty("tibatis.properties", "prefix");
            String suffix = PropertiesUtil.getProperty("tibatis.properties", "suffix");
            String entityPackageName = PropertiesUtil.getProperty("tibatis.properties", "entityPackageName");
            String daoPackageName = PropertiesUtil.getProperty("tibatis.properties", "daoPackageName");
            String mapperLocation = PropertiesUtil.getProperty("tibatis.properties", "mapperLocation");
            String gf = PropertiesUtil.getProperty("tibatis.properties", "generateInterface");
            boolean generateInterface = true;
            try {
                generateInterface = Boolean.valueOf(gf);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(StringUtil.isNotEmpty(removePrefix)){
                simpleSqlibator.setRemovePrefix(removePrefix);
                logger.debug("配置已经设置，要移除的前缀："+removePrefix);
            };
            if(StringUtil.isNotEmpty(prefix)){
                simpleSqlibator.setPrefix(prefix);
                logger.debug("配置已经设置，新的前缀："+prefix);
            };
            if(StringUtil.isNotEmpty(suffix)){
                simpleSqlibator.setSuffix(suffix);
                logger.debug("配置已经设置，后缀："+suffix);
            };
            if(StringUtil.isNotEmpty(entityPackageName)){
                simpleSqlibator.setEntityPackageName(entityPackageName);
                logger.debug("配置已经设置，生成实体的包路径："+entityPackageName);
            };
            if(StringUtil.isNotEmpty(daoPackageName)){
                simpleSqlibator.setDaoPackageName(daoPackageName);
                logger.debug("配置已经设置，生成Dao的包路径："+daoPackageName);
            };
            if(StringUtil.isNotEmpty(mapperLocation)){
                simpleSqlibator.setMapperLocation(mapperLocation);
                logger.debug("配置已经设置，生成Mapper的路径："+mapperLocation);
            };

            if(StringUtil.isNotEmpty(gf)){
                SimpleSqlibator.setGenerateInterface(generateInterface);
                logger.debug("配置已经设置，是否生成接口："+(generateInterface?"是":"否"));
            };

            String read = FileUtil.read(sqlFilePath);
            CreateTableFilter createTableFilter = new CreateTableFilter(read);
            List<String> sqls = createTableFilter.getSqls();
            for(String sql:sqls){
                //simpleSqlibator = ioc.getBean(SimpleSqlibator.class);
                Map map = (Map)simpleSqlibator.convert(sql);
                Table table = simpleSqlibator.getTable();

                FileUtil.write("src/main/java/"+simpleSqlibator.getEntityPackageName().replace(".","/")+"/"+table.getEntityName()+".java",(String)map.get("entity"));
                FileUtil.write("src/main/java/"+simpleSqlibator.getEntityPackageName().replace(".","/")+"/"+table.getEntityName()+"Example.java",(String)map.get("entityExample"));
                FileUtil.write("src/main/java/"+simpleSqlibator.getDaoPackageName().replace(".","/")+"/"+table.getEntityName()+"Dao.java",(String)map.get("dao"));
                if(generateInterface) {
                    FileUtil.write("src/main/java/" + simpleSqlibator.getDaoPackageName().replace(".", "/") + "/impl/" + table.getEntityName() + "DaoImpl.java", (String) map.get("daoImpl"));
                }
                FileUtil.write("src/main/resources/"+simpleSqlibator.getMapperLocation()+table.getEntityName()+"Mapper.xml",(String)map.get("xml"));

                logger.debug(table.getEntityName()+",已经生成完成，正在生成下一个...");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成出现了异常："+e.getMessage(),e);
        }
    }
}
