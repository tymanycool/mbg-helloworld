package com.tiany;

import com.tiany.util.CsiiDaoUtil;
import com.tiany.util.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * 启动类
 * @author tiany
 */
public class Main {
    private static final Logger logger= LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String sqlFilePath = "sql.txt";
        logger.debug("正在生成，请稍后...");
        List<File> files = FileUtil.listFiles(new File("."),"txt","sql");
        for (File file : files){
            if(file.getName().equals(sqlFilePath)||file.getName().endsWith(".sql")){
                CsiiDaoUtil.start(file.getPath());
            }
        }
        logger.debug("已经成功生成");
    }
}
