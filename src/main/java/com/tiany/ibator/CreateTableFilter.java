package com.tiany.ibator;

import com.tiany.inf.Condition;
import com.tiany.util.CollectionUtil;
import com.tiany.util.io.FileUtil;
import com.tiany.util.tokenizer.DefaultTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.get;

/**
 * 从一个sql文件中筛选出Create Table 语句
 * @author tiany
 * @version 1.0
 */
public class CreateTableFilter {
    private static final Logger logger = LoggerFactory.getLogger(CreateTableFilter.class);

    private String content;
    private List<String> sqls = new ArrayList<>();

    public CreateTableFilter(File sql){
        try {
            content = FileUtil.read(sql);
            logger.debug("正在加载sql文件...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        filter();
    }

    public CreateTableFilter(String sql){
        content = sql;
        filter();
    }

    private void filter(){
        logger.debug("正在分析sql...");
        DefaultTokenizer defaultTokenizer = new DefaultTokenizer();
        List<String> split = defaultTokenizer.split(content);
        List<List<String>> result = new ArrayList<>();
        List<List<String>> data = CollectionUtil.split(split, new Condition() {
            @Override
            public boolean matches(Object o, Object o1) {
                return o.equals(";");
            }
        });
        for (int i = 0;i< data.size();i++){
            List<String> strings = data.get(i);
            List<String> list = CollectionUtil.removeEmpty(strings);
            if(CollectionUtil.isNotEmpty(list)){
                result.add(list);
            }
        }
        for (int i = 0;i< result.size();i++){
            String sqlString = "";
            List<String> strings = result.get(i);
            if(strings.size() >= 2 && CollectionUtil.containsIgnoreCase(strings,"CREATE")&&CollectionUtil.containsIgnoreCase(strings,"TABLE")&&CollectionUtil.containsIgnoreCase(strings,"(")){
                for(String s: strings){
                    sqlString += s + " ";
                    logger.debug("正在标准化sql...");
                }
            }
            if(!"".equals(sqlString)){
                sqls.add(sqlString);
            }
        }
        logger.debug("分析sql已经完成!!!");
    }


    public List<String> getSqls() {
        return sqls;
    }

    public void setSqls(List<String> sqls) {
        this.sqls = sqls;
    }
}
