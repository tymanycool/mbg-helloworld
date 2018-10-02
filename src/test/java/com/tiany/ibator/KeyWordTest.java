package com.tiany.ibator;

import com.tiany.inf.Word;
import com.tiany.util.tokenizer.KeyWord;
import org.junit.Ignore;
import org.junit.Test;
@Ignore
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