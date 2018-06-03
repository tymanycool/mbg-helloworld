package com.tiany.util;

import com.tiany.impl.Sql2SimpleEntity;
import com.tiany.impl.Table;
import com.tiany.util.io.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class CsiiDaoUtil {
    private static final String mapperLocation = "src/main/resources/service/db/sql-mapping/service/";

    @Test
    public void test(){
        String sqlFilePath = "src/main/resources/sql.txt";
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
        String sqlFilePath = "src/main/resources/sql4.txt";
        start(sqlFilePath);
    }


    public static void start(String sqlFilePath){
        try {
            Sql2SimpleEntity sql2SimpleEntity = new Sql2SimpleEntity();
            Map map = (Map)sql2SimpleEntity.convert(FileUtil.read(sqlFilePath));
            Table table = sql2SimpleEntity.getTable();
            System.out.println(map.get("java"));
            FileUtil.write("src/main/java/"+sql2SimpleEntity.getPackageName().replace(".","/")+"/"+StringUtil.getCamelClassName(table.getName())+".java",(String)map.get("java"));
            System.out.println(map.get("xml"));
            FileUtil.write(mapperLocation+StringUtil.getCamelClassName(table.getName())+"Mapper.xml",(String)map.get("xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
