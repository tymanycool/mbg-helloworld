package com.tiany.util;

import com.tiany.inf.Word;
import com.tiany.impl.*;

public class WordUtil {
    public static Word getWord(String str) {
        if (str == null||"".equals(str)) {
            return null;
        }
        if (KeyWord.isKey(str.charAt(0))) {
            return new KeyWord(str);
        }
        if (BlankWord.isBlank(str.charAt(0))) {
            return new BlankWord(str);
        }
        if (DoubleWord.isDouble(str.charAt(0))) {
            return new DoubleWord(str);
        }
        if (StringWord.isString(str.charAt(0))) {
            return new StringWord(str);
        }
        if (OtherWord.isOther(str.charAt(0))) {
            return new OtherWord(str);
        }
        return null;
    }
}
