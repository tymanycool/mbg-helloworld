package com.tiany.ibator.infs;

import com.tiany.ibator.common.meta.SQL;

import java.util.List;

public interface SqlResolver {
    List<SQL> resolve(String sql);
}
