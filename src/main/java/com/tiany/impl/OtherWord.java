package com.tiany.impl;

import com.tiany.inf.Word;
import com.tiany.inf.Condition;
import com.tiany.util.StringUtil;

public class OtherWord implements Word{

    private String firstWord;

    private String strData;

    public OtherWord(String strData) {
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
        if (isOther(strData.charAt(fistWordIndex))) {
            firstWord = StringUtil.substringUntil(strData, fistWordIndex, new Condition() {
                @Override
                public boolean matches(Object obj, Object nextObj) {
                    if(nextObj == null){
                        return true;
                    }
                    return !isOther((Character)nextObj);
                }
            });
        }
        return firstWord;
    }

    /**
     * ch是否是a,b,c,...,z,A,B,C,...,Z,_
     * @param ch
     * @return
     */
    public static boolean isOther(char ch) {
        return !DoubleWord.isDouble(ch)&&!BlankWord.isBlank(ch)&&!StringWord.isString(ch)&&!KeyWord.isKey(ch);
    }

    private int getFistWordIndex(){
        for (int i = 0;i < strData.length();i++){
            if(isOther(strData.charAt(i))){
                return i;
            }
        }
        return -1;
    }
}
