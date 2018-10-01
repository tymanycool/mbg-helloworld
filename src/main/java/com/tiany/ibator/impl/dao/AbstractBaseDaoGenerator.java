package com.tiany.ibator.impl.dao;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.inf.Comment;
import com.tiany.ibator.meta.Table;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


public class AbstractBaseDaoGenerator extends AbstractBaseSqlibator implements ApplicationContextAware{
    private ApplicationContext applicationContext;
    private String daoSuffix = "DaoGenerator";
    private String commentSuffix = "Comment";
    public String getComment(Table table){
        String simpleName = this.getClass().getSimpleName();
        String first = String.valueOf(simpleName.charAt(0)).toLowerCase();
        Comment comment = (Comment)applicationContext.getBean(first+simpleName.substring(1,simpleName.length()- daoSuffix.length())+ commentSuffix);
        return comment.remark(table);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
