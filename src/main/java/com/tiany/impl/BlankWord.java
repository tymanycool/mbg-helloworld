package com.tiany.impl;

import com.tiany.inf.Word;
import com.tiany.inf.Condition;
import com.tiany.util.StringUtil;

public class BlankWord implements Word {
    private String firstWord;

    private String strData;


    public BlankWord(String str) {
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
        if (isBlank(strData.charAt(fistWordIndex))) {
            firstWord = StringUtil.substringUntil(strData, fistWordIndex, new Condition() {
                @Override
                public boolean matches(Object obj, Object nextObj) {
                    if(nextObj == null){
                        return true;
                    }
                    return !isBlank((Character)nextObj);
                }
            });
        }
        return firstWord;
    }

    /**
     * ch时候是空白字符
     * @param ch
     * @return
     */
    public static boolean isBlank(char ch) {
        return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
    }

    private int getFistWordIndex(){
        for (int i = 0;i < strData.length();i++){
            if(isBlank(strData.charAt(i))){
                return i;
            }
        }
        return -1;
    }
}
