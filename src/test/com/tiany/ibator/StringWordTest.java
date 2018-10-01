package com.tiany.ibator;

import com.tiany.util.tokenizer.StringWord;
import org.junit.Test;

public class StringWordTest {
    @Test
    public void getValue() throws Exception {
        StringWord stringWord = new StringWord("\'存管账户  主键\'");
        System.out.println(stringWord.getValue());
    }

}