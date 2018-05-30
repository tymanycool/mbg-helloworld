package com.tiany.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoubleWordTest {
    @Test
    public void getValue() throws Exception {
        DoubleWord DoubleWord = new DoubleWord("23.00 ,\'存管账户  主键\'");
        System.out.println(DoubleWord.getValue());
    }

}