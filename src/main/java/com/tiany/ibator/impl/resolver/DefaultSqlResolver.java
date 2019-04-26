package com.tiany.ibator.impl.resolver;

import com.tiany.ibator.common.BaseComponent;
import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.infs.SqlResolver;
import com.tiany.inf.Condition;
import com.tiany.util.CollectionUtil;
import com.tiany.util.io.FileUtil;
import com.tiany.util.tokenizer.DefaultTokenizer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultSqlResolver extends BaseComponent implements SqlResolver {

    public List<SQL> resolve(File sqlFile) {
        try {
            logger.info("正在读取sql文件...");
            String sql = FileUtil.read(sqlFile);
            logger.info("读取sql文件完成");
            return resolve(sql);
        } catch (IOException e) {
            logger.error("读取SQL文件出错", e);
            return null;
        }
    }

    @Override
    public List<SQL> resolve(String sql) {
        if (logger.isDebugEnabled()) {
            logger.debug("正在生成sql模型...");
        }
        DefaultTokenizer defaultTokenizer = new DefaultTokenizer();
        List<String> split = defaultTokenizer.split(sql);
        List<List<String>> result = new ArrayList<>();
        List<List<String>> data = CollectionUtil.split(split, new Condition() {
            @Override
            public boolean matches(Object o, Object o1) {
                return o.equals(";");
            }
        });
        // 移除空白
        for (int i = 0; i < data.size(); i++) {
            List<String> strings = data.get(i);
            List<String> list = CollectionUtil.removeEmpty(strings);
            if (CollectionUtil.isNotEmpty(list)) {
                result.add(list);
            }
        }

        List<SQL> retList = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            String str = "";
            for (String s : result.get(i)) {
                str += " " + s;
            }
            SQL sqlObj = new SQL(str, result.get(i));
            retList.add(sqlObj);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("生成sql模型完成");
        }
        return retList;
    }
}
