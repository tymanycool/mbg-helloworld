package com.tiany.impl;

import com.tiany.util.tokenizer.StringWord;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringWordTest {
    @Test
    public void getValue() throws Exception {
        StringWord stringWord = new StringWord("\'存管账户  主键\'");
        System.out.println(stringWord.getValue());
    }

}