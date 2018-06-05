package com.tiany.impl;

import com.tiany.inf.Tokenizer;
import com.tiany.inf.Word;
import com.tiany.util.tokenizer.DefaultTokenizer;
import com.tiany.util.tokenizer.WordUtil;

import java.util.ArrayList;
import java.util.List;

public class SqlTokenizer implements Tokenizer {
    @Override
    public List<String> split(String str) {
        DefaultTokenizer defaultTokenizer = new DefaultTokenizer();
        List<String> split = defaultTokenizer.split(str);
        return split;
    }



}
