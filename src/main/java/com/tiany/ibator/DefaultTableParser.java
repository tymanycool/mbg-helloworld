package com.tiany.ibator;

import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.impl.EntityExampleGenerator;
import com.tiany.ibator.impl.EntityGenerator;
import com.tiany.ibator.impl.executor.AlterTableCommentExecutor;
import com.tiany.ibator.impl.executor.CreateTableExecutor;
import com.tiany.ibator.infs.*;
import com.tiany.inf.Convert;
import com.tiany.util.CastUtil;
import com.tiany.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析sqlString生成tables模型
 *
 * @author tiany
 * @version 1.0
 */
@Component
//@Scope("prototype")
public class DefaultTableParser extends AbstractBaseSqlibator implements TableParser {
    protected List<Executable> executables;
    private List<Table> tables;
    @Autowired
    @Qualifier("defaultSqlFilter")
    private SqlFilter sqlFilter;
    @Autowired
    @Qualifier("defaultSqlResolver")
    private SqlResolver sqlResolver;

    @Override
    public List<Table> parse(String sqlString) {
        executables = new ArrayList<>();
        tables = new ArrayList<>();
        sqlString = sqlString.trim();
        // 转换成一行
        sqlString = sqlString.replaceAll("\r\n", " ");
        sqlString = sqlString.replaceAll("\n", " ");
        sqlString = sqlString.replaceAll("\r", " ");
        List<SQL> sqlList = sqlResolver.resolve(sqlString);

        logger.info("after resolve the number : {}", sqlList.size());

        List<SQL> sqls = sqlFilter.doFilter(sqlList, sqlString);

        logger.info("after doFilter the number : {}", sqls.size());

        String[] names = applicationContext.getBeanNamesForType(Executable.class);
        if (logger.isInfoEnabled()) {
            logger.info("executeables : {}", names);
        }
        for (String name : names) {
            Executable bean = applicationContext.getBean(name, Executable.class);
            executables.add(bean);
        }
        // init table
        for (SQL sql : sqls) {
            logger.info("一条SQL正在执行...");
            for (Executable executable : executables) {
                if (executable.accept(sql)) {
                    Table table = resolveCurSQLTable(sql, executable);
                    try {
                        executable.execute(table, sql);
                    } catch (Exception e) {
                        logger.info("该SQL未被{}执行！！！，SQL====> {}", executable.getClass().getSimpleName(), sql.getSqlWords());
                        throw new RuntimeException("该SQL未被执行！！！");
                    }
                    logger.info("一条SQL已经执行成功~~~");
                } else {
                    logger.info("该SQL未被{}执行！！！，SQL====> {}", executable.getClass().getSimpleName(), sql.getSqlWords());
                }
            }
        }
        return tables;
    }

    /**
     * table的初始化和定位
     *
     * @param sql
     * @param executable
     * @return
     */
    private Table resolveCurSQLTable(SQL sql, Executable executable) {
        Table table = null;
        String tableName = executable.getTableName(sql);
        for (Table t : tables) {
            String s = StringUtil.toStringAndTrim(t.getName()).toUpperCase();
            String s2 = StringUtil.toStringAndTrim(tableName).toUpperCase();
            if (s.toUpperCase().equals(s2)) {
                table = t;
            }
        }

        if (table == null) {
            table = new Table();
            tables.add(table);
        }
        return table;
    }
}
