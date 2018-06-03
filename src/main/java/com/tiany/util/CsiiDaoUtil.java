package com.tiany.util;

import com.tiany.impl.Sql2SimpleEntity;
import com.tiany.impl.Table;
import com.tiany.util.io.FileUtil;

import java.io.IOException;
import java.util.Map;

public class CsiiDaoUtil {
    public static void main(String[] args){
        start();
    }

    public static void start(){
        try {
            Sql2SimpleEntity sql2SimpleEntity = new Sql2SimpleEntity();
            Map map = (Map)sql2SimpleEntity.convert(FileUtil.read("src/main/resources/sql.txt"));
            Table table = sql2SimpleEntity.getTable();
            System.out.println(map.get("java"));
            FileUtil.write("src/main/java/"+sql2SimpleEntity.getPackageName().replace(".","/")+"/"+StringUtil.getCamelClassName(table.getName())+".java",(String)map.get("java"));
            System.out.println(map.get("xml"));
            FileUtil.write("src/main/resources/service/db/sql-mapping/service/"+StringUtil.getCamelClassName(table.getName())+"Mapper.xml",(String)map.get("xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
