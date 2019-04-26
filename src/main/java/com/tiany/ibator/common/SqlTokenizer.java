package com.tiany.ibator.common;

import com.tiany.inf.Tokenizer;
import com.tiany.util.tokenizer.DefaultTokenizer;

import java.util.List;

public class SqlTokenizer implements Tokenizer {
    @Override
    public List<String> split(String str) {
        DefaultTokenizer defaultTokenizer = new DefaultTokenizer();
        List<String> split = defaultTokenizer.split(str);
        return split;
    }
}
