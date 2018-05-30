package com.tiany.impl;

import com.tiany.inf.Word;
import org.junit.Test;

public class KeyWordTest {
    @Test
    public void getValue() throws Exception {
        Word word =  new KeyWord("tianyao     Ti");
        System.out.println(word.getValue());
    }

}