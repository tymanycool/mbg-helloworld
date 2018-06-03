package com.tiany.impl;

import com.tiany.inf.Condition;
import com.tiany.inf.Word;
import com.tiany.util.CollectionUtil;
import com.tiany.util.StringUtil;

public class SingleWord implements Word{
    // 单独处理的字符列表
    private static final char[] singleList = {'(',')','[',']','{','}',',',';','-','+','#','&','$','@','*','/'};

    private String firstWord;

    private String strData;

    private Condition condition = new Condition() {
        private int count = 0;
        @Override
        public boolean matches(Object obj, Object nextObj) {
            return 1==count++;
        }
    };


    public SingleWord(String str) {
        strData = str;
    }

    @Override
    public int length() {
        return firstWord.length();
    }

    @Override
    public String getValue() {
        return getFirstWord();
    }

    @Override
    public int beginIndex() {
        return getFistWordIndex();
    }

    @Override
    public int endIndex() {
        return getFistWordIndex()+length();
    }

    private String getFirstWord() {
        if (strData == null) {
            return null;
        }
        int fistWordIndex = getFistWordIndex();
        if (isSingle(strData.charAt(fistWordIndex))) {
            firstWord = StringUtil.substringUntil(strData, fistWordIndex, condition);
        }
        return firstWord;
    }

    /**
     * ch是否是特殊的单个字符
     * @param ch
     * @return
     */
    public static boolean isSingle(char ch) {
        boolean contains = CollectionUtil.contains(singleList, ch);
        return contains;
    }

    private int getFistWordIndex(){
        for (int i = 0;i < strData.length();i++){
            if(isSingle(strData.charAt(i))){
                return i;
            }
        }
        return -1;
    }
}
