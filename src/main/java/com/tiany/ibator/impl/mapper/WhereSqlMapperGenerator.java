package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractSqlibator;
import com.tiany.ibator.infs.MapperSqlGenerator;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.io.FileUtil;
import com.tiany.util.io.StreamUtil;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class WhereSqlMapperGenerator extends AbstractSqlibator implements MapperSqlGenerator {
    @Override
    public String generate(Table table) {
        String ret = "";
        try {
            InputStream is = FileUtil.getClassPathInputStream("example-where.sql");
            ret = StreamUtil.streamToString(is);
        } catch (Exception e) {
            logger.error("加载资源出错了...",e);
        }
        return ret;
    }

    @Override
    public String getSqlId() {
        return "ExampleWhereClause";
    }
}
