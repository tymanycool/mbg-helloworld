package com.tiany.inf;

/**
 * 分词接口
 */
public interface Word{
    int length();
    String getValue();
    int beginIndex();
    int endIndex();
}
