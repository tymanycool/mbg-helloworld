package com.tiany.impl;

import com.tiany.inf.Word;
import com.tiany.util.tokenizer.KeyWord;
import org.junit.Test;

public class KeyWordTest {
    @Test
    public void getValue() throws Exception {
        Word word =  new KeyWord("tianyao     Ti");
        System.out.println(word.getValue());
    }
    @Test
    public void test() throws Exception {
        System.out.println("==="+'\f'+"===");
        System.out.println("==="+' '+"===");
    }

}