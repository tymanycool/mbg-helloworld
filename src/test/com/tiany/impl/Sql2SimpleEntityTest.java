package com.tiany.impl;

import com.tiany.util.io.FileUtil;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class Sql2SimpleEntityTest {
    @Test
    public void test() throws Exception {
        Sql2SimpleEntity sql2SimpleEntity = new Sql2SimpleEntity();
        Map map = (Map)sql2SimpleEntity.convert(FileUtil.read("src/main/resources/sql.txt"));
        System.out.println(map.get("java"));
        System.out.println(map.get("xml"));
//        System.out.println(map.get("resultMap"));
//        System.out.println(map.get("select"));
//        System.out.println(map.get("insert"));
//        System.out.println(map.get("update"));
//        System.out.println(map.get("delete"));
    }
}