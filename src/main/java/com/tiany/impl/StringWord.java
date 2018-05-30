package com.tiany.impl;

import com.tiany.inf.Word;
import com.tiany.inf.Condition;
import com.tiany.util.StringUtil;

public class StringWord implements Word {
    private String firstWord;

    private static final char prefix_1 = '\'';

    private static final char prefix_2 = '\"';

    private String strData;

    public StringWord(String strData) {
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
        return getFistWordIndex() + length();
    }

    private String getFirstWord() {
        if (strData == null) {
            return null;
        }
        int fistWordIndex = getFistWordIndex();
        if (prefix_1 == strData.charAt(fistWordIndex)) {
            firstWord = StringUtil.substringUntil(strData, fistWordIndex, new Condition() {
                @Override
                public boolean matches(Object obj, Object nextObj) {
                    return prefix_1 == (Character) nextObj;
                }
            });
            firstWord += prefix_1;
        } else if (prefix_2 == strData.charAt(fistWordIndex)) {
            firstWord = StringUtil.substringUntil(strData, fistWordIndex, new Condition() {
                @Override
                public boolean matches(Object obj, Object nextObj) {
                    return prefix_2 == (Character) nextObj;
                }
            });
            firstWord += prefix_2;
        }
        return firstWord;
    }

    public static boolean isString(char ch) {
        return ch == prefix_1 || ch == prefix_2;
    }

    private int getFistWordIndex() {
        for (int i = 0; i < strData.length(); i++) {
            if (prefix_1 == strData.charAt(i) || prefix_2 == strData.charAt(i)) {
                return i;
            }
        }
        return -1;
    }

}
