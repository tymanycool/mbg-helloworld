package com.tiany.ibator.common;

import com.tiany.ibator.util.MapUtil;
import com.tiany.ibator.util.PropsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Initializer extends BaseComponent {
    private Map<String, String> typesConfig = new HashMap<>();
    private Map<String, String> tibatisConfig = new HashMap<>();
    private Map versionUpdateInfos = new HashMap<>();

    @Bean
    public Map<String, String> typesConfig() {
        try {
            Map pros = PropsUtil.loadProps("type.properties");
            typesConfig = MapUtil.copyToMap(pros);
        } catch (Exception e) {
            logger.error("type.properties加载失败");
        }
        return typesConfig;
    }

    @Bean
    public Map<String, String> tibatisConfig() {
        try {
            Map pros = PropsUtil.loadProps("tibatis.properties");
            // 定义新的前缀
            MapUtil.ifEmptyPut(pros, "removePrefix", "xq_,pmis_");
            // 需要去除的前缀
            MapUtil.ifEmptyPut(pros, "prefix", "");
            // 定义后缀
            MapUtil.ifEmptyPut(pros, "suffix", "");
            // 实体类的存放的包路径
            MapUtil.ifEmptyPut(pros, "entityPackageName", "com.csii.pmis.service.bean.model");
            // dao类的存放的包路径
            MapUtil.ifEmptyPut(pros, "daoPackageName", "com.csii.pmis.admin.dao");
            // mapper文件的路径
            MapUtil.ifEmptyPut(pros, "mapperLocation", "service/db/sql-mapping/service/");
            // 是否生成接口
            MapUtil.ifEmptyPut(pros, "generateInterface", "true");
            // 是否生成分页
            MapUtil.ifEmptyPut(pros, "generatePage", "true");
            MapUtil.ifEmptyPut(pros, "productInfo", "All rights reserved by " + System.getProperty("user.name"));
            tibatisConfig = MapUtil.copyToMap(pros);
        } catch (Exception e) {
            logger.error("type.properties加载失败");
        }
        return tibatisConfig;
    }

    @Bean
    public Map versionUpdateInfos() {
        return versionUpdateInfos;
    }
}
