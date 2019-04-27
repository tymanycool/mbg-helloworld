package com.tiany.ibator.infs;


import com.tiany.ibator.common.meta.Remark;
import com.tiany.ibator.common.meta.Table;

import java.util.Map;


public interface ClassComment {
    Remark remark(Table table, Map history);
}
