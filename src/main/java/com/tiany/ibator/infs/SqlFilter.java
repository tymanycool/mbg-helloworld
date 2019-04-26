package com.tiany.ibator.infs;

import com.tiany.ibator.common.meta.SQL;

import java.util.List;

public interface SqlFilter {
    List<SQL> doFilter(List<SQL> list, String sql);
}
