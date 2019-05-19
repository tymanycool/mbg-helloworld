package com.tiany.ibator.impl.comment;

import com.tiany.ibator.common.DefaultClassComment;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EntityExampleClassComment extends DefaultClassComment {
    @Autowired
    private Map versionUpdateInfos;

    @Override
    protected String getTypeDesc() {
        return "EntityExample";
    }

    @Override
    protected void updateInfo(Table table, List<String> updateDescs) {
        if (CollectionUtil.isNotEmpty(updateDescs)) {
            versionUpdateInfos.put(table.getName(), updateDescs);
        }
    }
}
