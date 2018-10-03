package com.tiany.logo;

import com.tiany.util.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logo {
    private static final Logger logger = LoggerFactory.getLogger(Logo.class);
    private static String message = "author:tianyao email:tianyao1916@163.com [However, a question about something you believe to be a bug or possibly a valuable enhancement is definitely valid -- indeed, it's very welcome]\n";
    public static void logo(String logo, Object... args){
        String msg = FileUtil.getClassPathAsString("META-INF/"+logo+".logo");
        logger.info(message+msg,args);
    }
}
