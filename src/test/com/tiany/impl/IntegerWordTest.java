package com.tiany.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerWordTest {
    @Test
    public void getValue() throws Exception {
        IntegerWord IntegerWord = new IntegerWord("23.00 ,\'存管账户  主键\'");
        System.out.println(IntegerWord.getValue());
    }

}