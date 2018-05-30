package com.tiany.impl;

import com.tiany.util.io.FileUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class Sql2SimpleEntityTest {
    @Test
    public void test() throws Exception {
        Sql2SimpleEntity sql2SimpleEntity = new Sql2SimpleEntity();
        Object convert = sql2SimpleEntity.convert(FileUtil.read("src/main/resources/sql2.txt"));
        System.out.println(convert);
    }
}