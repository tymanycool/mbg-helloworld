package com.tiany.impl;

import com.tiany.inf.Tokenizer;
import com.tiany.inf.Word;
import com.tiany.util.WordUtil;

import java.util.ArrayList;
import java.util.List;

public class SqlTokenizer implements Tokenizer {
    @Override
    public List<String>  toke(String str) {
        List<String> list = new ArrayList<>();
        String word = "";
        while (true){
            Word w = WordUtil.getWord(str);
            if (w == null){
                break;
            }
            word = w.getValue();
            list.add(word);
            str = str.substring(w.endIndex());
        }
        return list;
    }



}
