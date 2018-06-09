package com.tiany;

import com.tiany.util.CsiiDaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main {
    private static final Logger logger= LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String sqlFilePath = "sql.txt";
        logger.debug("正在生成，请稍后...");
        CsiiDaoUtil.start(sqlFilePath);
        logger.debug("已经成功生成");
    }
}
