package com.tiany.impl;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class CreateTableFilterTest {
    @Test
    public void getSqls() throws Exception {

        CreateTableFilter createTableFilter = new CreateTableFilter(new File("src/main/resources/sql5.txt"));
        List<String> sqls = createTableFilter.getSqls();

    }

}