package com.tiany.ibator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSqlibator {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractBaseSqlibator.class);

    // 需要去除的前缀
    protected static String removePrefix ="xq_,pmis_";

    // 定义新的前缀
    protected static String prefix = "";

    // 定义后缀
    protected static String suffix = "";

    // 实体类的存放的包路径
    protected static String entityPackageName = "com.csii.pmis.service.bean.model";

    // dao类的存放的包路径
    protected static String daoPackageName = "com.csii.pmis.admin.dao";

    // mapper文件的路径
    protected static String mapperLocation = "service/db/sql-mapping/service/";

    // 是否生成接口
    protected static boolean generateInterface = false;

    public static String getRemovePrefix() {
        return removePrefix;
    }

    public static void setRemovePrefix(String removePrefix) {
        AbstractSqlibator.removePrefix = removePrefix;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        AbstractSqlibator.prefix = prefix;
    }

    public static String getSuffix() {
        return suffix;
    }

    public static void setSuffix(String suffix) {
        AbstractSqlibator.suffix = suffix;
    }

    public static String getEntityPackageName() {
        return entityPackageName;
    }

    public static void setEntityPackageName(String entityPackageName) {
        AbstractSqlibator.entityPackageName = entityPackageName;
    }

    public static String getDaoPackageName() {
        return daoPackageName;
    }

    public static void setDaoPackageName(String daoPackageName) {
        AbstractSqlibator.daoPackageName = daoPackageName;
    }

    public static String getMapperLocation() {
        return mapperLocation;
    }

    public static void setMapperLocation(String mapperLocation) {
        AbstractSqlibator.mapperLocation = mapperLocation;
    }

    public static boolean isGenerateInterface() {
        return generateInterface;
    }

    public static void setGenerateInterface(boolean generateInterface) {
        AbstractSqlibator.generateInterface = generateInterface;
    }
}
