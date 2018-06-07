package com.tiany.util;

import com.tiany.impl.Sql2SimpleEntity;
import com.tiany.impl.Table;
import com.tiany.util.io.FileUtil;
import org.junit.Test;

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
            System.out.println(map.get("entity"));
            FileUtil.write("src/main/java/"+sql2SimpleEntity.getEntityPackageName().replace(".","/")+"/"+table.getEntityName()+".java",(String)map.get("entity"));
            System.out.println(map.get("dao"));
            FileUtil.write("src/main/java/"+sql2SimpleEntity.getDaoPackageName().replace(".","/")+"/"+table.getEntityName()+"Dao.java",(String)map.get("dao"));
            System.out.println(map.get("daoImpl"));
            FileUtil.write("src/main/java/"+sql2SimpleEntity.getDaoPackageName().replace(".","/")+"/impl/"+table.getEntityName()+"DaoImpl.java",(String)map.get("daoImpl"));
            System.out.println(map.get("xml"));
            FileUtil.write(mapperLocation+table.getEntityName()+"Mapper.xml",(String)map.get("xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
