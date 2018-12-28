package com.tiany.ibator.impl.dao;

import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import org.springframework.stereotype.Component;

@Component
public class SelectForMapDaoGenerator extends AbstractBaseDaoGenerator implements Generator {
    @Override
    public String generate(Table table) {
        return "  Map<String,Object> selectForMap(Map<String, Object> params)";
    }
}
