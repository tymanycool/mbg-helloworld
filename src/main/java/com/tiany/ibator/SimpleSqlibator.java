package com.tiany.ibator;

import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.impl.EntityExampleGenerator;
import com.tiany.ibator.impl.EntityGenerator;
import com.tiany.ibator.infs.*;
import com.tiany.util.CastUtil;
import com.tiany.util.DateUtil;
import com.tiany.util.StringUtil;
import com.tiany.util.io.FileUtil;
import com.tiany.util.io.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过SQL语句逆向工程
 *
 * @author tianyao
 * @version 1.6
 */
@Component
//@Scope("prototype")
public class SimpleSqlibator extends AbstractBaseSqlibator implements IbatorGenerator {
    @Autowired
    private EntityExampleGenerator entityExampleGenerator;
    @Autowired
    private EntityGenerator entityGenerator;
    @Autowired
    private DaoGenerator daoGenerator;
    @Autowired
    private DaoImplGenerator daoImplGenerator;
    @Autowired
    private MapperGenerator mapperGenerator;
    @Autowired
    @Qualifier("defaultTableParser")
    private TableParser tableParser;

    @Override
    public Object generate(Object data) throws Exception {
        String sqlString = "";
        if (data instanceof File) {
            sqlString = FileUtil.read((File) data);
        } else if (data instanceof List) {
            List list = (List) data;
            for (Object obj : list) {
                if (obj instanceof File) {
                    sqlString += FileUtil.read((File) obj);
                } else if (obj instanceof String) {
                    sqlString += obj;
                } else {
                    logger.error("暂时不支持{}类型", obj.getClass());
                    throw new Exception("暂时不支持该类型");
                }
                sqlString += "\n";
            }
        } else if (data instanceof String) {
            sqlString = (String) data;
        } else {
            logger.error("暂时不支持{}类型", data.getClass());
            throw new Exception("暂时不支持该类型");
        }

        // 历史版本记录备份
        File history = new File("history.json");
        File historyBak = new File("history/" + DateUtil.thisDateTime() + "/history.json");
        try {
            boolean b = FileUtil.copyFile(history, historyBak, true);
            if (b) {
                logger.info("配置备份成功：{}", historyBak.getAbsolutePath());
            } else {
                logger.info("配置备份失败：{}", historyBak.getAbsolutePath());
                return false;
            }
        } catch (IOException e) {
            logger.info("配置备份失败：{},{}", historyBak.getAbsolutePath(), e.getMessage());
            return false;
        }


        // 配置
        String removePrefix = tibatisConfig.get("removePrefix");
        String prefix = tibatisConfig.get("prefix");
        String suffix = tibatisConfig.get("suffix");
        String entityPackageName = tibatisConfig.get("entityPackageName");
        String daoPackageName = tibatisConfig.get("daoPackageName");
        String mapperLocation = tibatisConfig.get("mapperLocation");
        boolean generateInterface = CastUtil.castBoolean(tibatisConfig.get("generateInterface"));
        boolean generatePage = CastUtil.castBoolean(tibatisConfig.get("generatePage"));

        if (logger.isInfoEnabled()) {
            logger.info("配置：removePrefix={}", removePrefix);
            logger.info("配置：prefix={}", prefix);
            logger.info("配置：suffix={}", suffix);
            logger.info("配置：entityPackageName={}", entityPackageName);
            logger.info("配置：daoPackageName={}", daoPackageName);
            logger.info("配置：mapperLocation={}", mapperLocation);
            logger.info("配置：generateInterface={}", generateInterface);
            logger.info("配置：generatePage={}", generatePage);
        }


        List<Table> tables = tableParser.parse(sqlString);

        if (logger.isInfoEnabled()) {
            logger.info("the parse tables size : {}", tables.size());
        }
        String entity = null;
        String entityExample = null;
        String mapper = null;
        String dao = null;
        String daoImpl = null;
        for (Table table : tables) {
            String outStr = "";
            entityExample = entityExampleGenerator.generate(table);
            entity = entityGenerator.generate(table);
            mapper = mapperGenerator.generate(table);
            if (generateInterface) {
                dao = daoGenerator.generate(table);
                daoImpl = daoImplGenerator.generate(table);
            } else {
                dao = daoImplGenerator.generate(table);
            }
            String path = "src/main/java/" + entityPackageName.replace(".", "/") + "/" + table.getEntityName() + ".java";
            FileUtil.write(path, entity);
            if (logger.isDebugEnabled()) {
                logger.debug("已生成：{}", path);
            }
            path = "src/main/java/" + entityPackageName.replace(".", "/") + "/" + table.getEntityName() + "Example.java";
            FileUtil.write(path, entityExample);
            if (logger.isDebugEnabled()) {
                logger.debug("已生成：{}", path);
            }
            path = "src/main/java/" + daoPackageName.replace(".", "/") + "/" + table.getEntityName() + "Dao.java";
            FileUtil.write(path, dao);
            if (logger.isDebugEnabled()) {
                logger.debug("已生成：{}", path);
            }
            if (generateInterface) {
                path = "src/main/java/" + daoPackageName.replace(".", "/") + "/impl/" + table.getEntityName() + "DaoImpl.java";
                FileUtil.write(path, daoImpl);
                if (logger.isDebugEnabled()) {
                    logger.debug("已生成：{}", path);
                }
            }
            path = "src/main/resources/" + mapperLocation + table.getEntityName() + "Mapper.xml";
            FileUtil.write(path, mapper);
            if (logger.isDebugEnabled()) {
                logger.debug("已生成：{}", path);
            }

            if (logger.isDebugEnabled()) {
                logger.debug(table.getEntityName() + ",已经生成{}相关文件完成，正在生成下一个...", table.getEntityName());
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("生成完毕，欢迎使用！");
        }
        return true;
    }
}
