package com.tiany;

import com.tiany.ibator.SimpleSqlibator;
import com.tiany.ibator.common.logo.Logo;
import com.tiany.util.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.List;

/**
 * 启动类
 *
 * @author tiany
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
        try {
            context = new ClassPathXmlApplicationContext("classpath*:spring.xml");
            String sqlFilePath = "sql.txt";
            Logo.logo("tianyao");
            logger.debug("正在生成，请稍后...");
            List<File> files = FileUtil.listFiles(new File("."), "sql");
            SimpleSqlibator simpleSqlibator = context.getBean(SimpleSqlibator.class);

            for (File file : files) {
                logger.info("SQL文件：{}", file.getAbsolutePath());
            }

            Object result = simpleSqlibator.generate(files);
            if ((boolean) result) {
                Logo.logo("tianyao");
                Logo.logo("success");
            } else {
                Logo.logo("failed");
            }
        } catch (Exception e) {
            Logo.logo("failed", e);
        }
    }
}
