package com.tiany.ibator;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;
@Ignore
public class CreateTableFilterTest {
    @Test
    public void getSqls() throws Exception {

        CreateTableFilter createTableFilter = new CreateTableFilter(new File("src/main/resources/sql5.txt"));
        List<String> sqls = createTableFilter.getSqls();

    }

}