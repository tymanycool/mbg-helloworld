package com.tiany.impl;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SqlRowTokenizerTest {
    @Test
    public void toke() throws Exception {
        SqlRowTokenizer sqlRowTokenizer = new SqlRowTokenizer();
        String in = "`acct_pkey` varchar ( 64.2336 22256 ) NOT NULL COMMENT '存管账户  主键'";
        List<String> toke = sqlRowTokenizer.toke(in);
        //System.out.println(toke);
        String ret = "";
        for(String s:toke){
            System.out.println("=="+s+"==");
            ret += s;
        }
        System.out.println(ret.equals(in));
        System.out.println("==="+in);
        System.out.println("==="+ret);
    }

}