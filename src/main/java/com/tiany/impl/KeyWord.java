package com.tiany.impl;

import com.tiany.inf.Word;
import com.tiany.inf.Condition;
import com.tiany.util.StringUtil;

public class KeyWord implements Word{
    private String firstWord;

    private String strData;

    public KeyWord(String strData) {
        this.strData = strData;
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
        if (isKey(strData.charAt(fistWordIndex))) {
            firstWord = StringUtil.substringUntil(strData, fistWordIndex, new Condition() {
                @Override
                public boolean matches(Object obj, Object nextObj) {
                    return !isKey((Character)obj);
                }
            });
        }
        return firstWord;
    }

    /**
     * ch是否是a,b,c,...,z,A,B,C,...,Z,_,1,2,3,4,...
     * @param ch
     * @return
     */
    public static boolean isKey(char ch) {
        // TODO ???
        return Character.isLetter(ch)||ch=='_'||Character.isDigit(ch);
    }

    private int getFistWordIndex(){
        for (int i = 0;i < strData.length();i++){
            if(isKey(strData.charAt(i))){
                return i;
            }
        }
        return -1;
    }

}
