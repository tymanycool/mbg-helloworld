package com.tiany.ibator.impl.executor;

import com.tiany.ibator.common.BaseComponent;
import com.tiany.ibator.common.SqlTokenizer;
import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.infs.Executable;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRemoveCommentExecutor extends BaseComponent implements Executable {
    protected abstract void handle(Table table, List<String> words);

    @Override
    public void execute(Table table, SQL sql) {
        List<String> wordList = sql.getSqlWords();
        if (!sql.isCleared()) {
            clear(sql);
        }
        if (accept(sql)) {
            handle(table, sql.getSqlWords());
        } else {
            logger.error("执行SQL：{}，失败", sql);
        }
    }

    protected void clear(SQL sql) {
        List<String> wordList = sql.getSqlWords();
        logger.info("清理前wordList大小 : {}", wordList.size());
        List<String> newWordList = new ArrayList<>();
        for (int i = 0; i < wordList.size(); i++) {
            // 过滤不必要的字段
            if (!StringUtil.isEmpty(wordList.get(i).trim()) && !"`".equals(wordList.get(i).trim())) {
                newWordList.add(wordList.get(i).trim());
            }
        }
        logger.info("过滤不必要的字段之后wordList大小：{}", newWordList.size());

        // 移除/*  */注释
        List<String> beforeList = newWordList;
        List<String> afterList = null;
        while (true) {
            afterList = removeComment(beforeList);
            if (afterList.equals(beforeList)) {
                break;
            } else {
                beforeList = afterList;
            }
            if(logger.isDebugEnabled()){
                logger.debug("/ 正在移除/*  */注释 ... ");
            }
        }
        sql.setCleared(true);
        sql.setSqlWords(afterList);

        logger.info("移除/*  */注释之后wordList大小：{}", afterList.size());
    }

    /**
     * 如果返回的List和传入的List相同则跳出循环
     *
     * @param newWordList
     * @return
     */
    private List<String> removeComment(List<String> newWordList) {
        int combegin = -1;
        int comend = -1;
        for (int i = 0; i < newWordList.size() - 1; i++) {
            String s0 = newWordList.get(i);
            String s1 = newWordList.get(i + 1);

            if ("/".equals(s0) && "*".equals(s1)) {
                // 存在注释
                combegin = i;
                for (int j = i + 2; j < newWordList.size() - 1; j++) {
                    String s2 = newWordList.get(j);
                    String s3 = newWordList.get(j + 1);
                    if ("*".equals(s2) && "/".equals(s3)) {
                        comend = j + 1;
                        List<String> afterList = new ArrayList<>();
                        if (combegin != -1 && comend != -1) {
                            if (combegin > 0) {
                                List<String> childList1 = newWordList.subList(0, combegin);
                                List<String> childList2 = newWordList.subList(combegin, comend + 1);
                                afterList.addAll(childList1);
                                afterList.addAll(childList2);
                            } else {
                                List<String> childList2 = newWordList.subList(comend + 1, newWordList.size());
                                afterList.addAll(childList2);
                            }
                            return afterList;
                        }
                    }
                }
            }
        }
        return newWordList;
    }


}
