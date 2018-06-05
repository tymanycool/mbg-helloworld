package com.tiany.impl;

import com.tiany.inf.Word;
import com.tiany.util.tokenizer.BlankWord;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

public class BlankWordTest {
    @Test
    public void getFirstWord() throws Exception {

        Word blankWord = new BlankWord("tttt   `acct_pkey` varchar ( 64.2336 22256 ) NOT NULL COMMENT '存管账户  主键'");
        System.out.println("====="+StringEscapeUtils.escapeJava(blankWord.getValue())+"======");
    }

}