package com.tiany.ibator;

import com.tiany.util.tokenizer.NumberWord;
import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class DoubleWordTest {
    @Test
    public void getValue() throws Exception {
        NumberWord DoubleWord = new NumberWord("23.00 ,\'存管账户  主键\'");
        System.out.println(DoubleWord.getValue());
    }

}