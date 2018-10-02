package com.tiany.ibator;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
@Ignore
public class SqlRowTokenizerTest {
    @Test
    public void toke() throws Exception {
        SqlTokenizer sqlRowTokenizer = new SqlTokenizer();
        String in = "`acct_pkey` varchar ( 64.2336 22256 ) NOT NULL COMMENT '存管账户  主键'";
        List<String> toke = sqlRowTokenizer.split(in);
        //System.out.println(split);
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