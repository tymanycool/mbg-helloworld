package com.tiany.ibator;

import com.tiany.util.io.FileUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;
@Ignore
public class Sql2SimpleEntityTest {
    @Test
    public void test() throws Exception {
        SimpleSqlibator sql2SimpleEntity = new SimpleSqlibator();
        Map map = (Map)sql2SimpleEntity.convert(FileUtil.read("src/main/resources/sql.txt"));
        System.out.println(map.get("entity"));
        System.out.println(map.get("xml"));
        System.out.println(map.get("dao"));
        System.out.println(map.get("daoImpl"));
//        System.out.println(map.get("resultMap"));
//        System.out.println(map.get("select"));
//        System.out.println(map.get("insert"));
//        System.out.println(map.get("update"));
//        System.out.println(map.get("delete"));
    }
}