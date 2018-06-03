package com.tiany.impl;

import com.tiany.inf.Word;
import com.tiany.inf.Condition;
import com.tiany.util.StringUtil;

public class StringWord implements Word {
    private String firstWord;

    private static final char prefix_1 = '\'';

    private static final char prefix_2 = '\"';

    private String strData;

    private Condition condition_1 = new Condition() {
        private int count = 0;

        @Override
        public boolean matches(Object obj, Object nextObj) {
            return prefix_1 == (Character) obj && 0!=count++ ;
        }
    };

    private Condition condition_2 = new Condition() {
        private int count = 0;

        @Override
        public boolean matches(Object obj, Object nextObj) {
            return prefix_2 == (Character) obj && 0!=count++ ;
        }
    };

    public StringWord(String strData) {
        this.strData = strData;
        firstWord = getFirstWord();
    }

    @Override
    public int length() {
        return firstWord.length();
    }

    @Override
    public String getValue() {
        return firstWord;
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
        String ret = "";
        if (strData == null) {
            return null;
        }
        int fistWordIndex = getFistWordIndex();
        if (prefix_1 == strData.charAt(fistWordIndex)) {
            ret = StringUtil.substringUntil(strData, fistWordIndex, condition_1);
            ret += prefix_1;
        } else if (prefix_2 == strData.charAt(fistWordIndex)) {
            ret = StringUtil.substringUntil(strData, fistWordIndex, condition_2);
            ret += prefix_2;
        }
        return ret;
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
