package com.tiany.ibator.infs;

import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.common.meta.Table;

/**
 * sql执行器
 */
public interface Executable {
    /**
     * 执行
     *
     * @param table
     * @param sql
     */
    void execute(Table table, SQL sql);

    /**
     * 是否可以执行
     *
     * @param sql
     * @return
     */
    boolean accept(SQL sql);

    /**
     * 得到表的名称
     *
     * @param sql
     * @return
     */
    String getTableName(SQL sql);

}
