package com.tiany.ibator.impl.daoimpl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.inf.Comment;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Table;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AbstractBaseDaoImplGenerator extends AbstractBaseSqlibator implements ApplicationContextAware{
    private ApplicationContext applicationContext;
    private String daoImplSuffix = "DaoImplGenerator";
    private String daoSuffix = "DaoGenerator";
    private String commentSuffix = "Comment";
    public String getComment(Table table){
        String simpleName = this.getClass().getSimpleName();
        String first = String.valueOf(simpleName.charAt(0)).toLowerCase();
        Comment comment = (Comment)applicationContext.getBean(first+simpleName.substring(1,simpleName.length()- daoImplSuffix.length())+ commentSuffix);
        return comment.remark(table);
    }

    public String getDaoString(Table table){
        String simpleName = this.getClass().getSimpleName();
        String first = String.valueOf(simpleName.charAt(0)).toLowerCase();
        Generator generator = (Generator)applicationContext.getBean(first+simpleName.substring(1,simpleName.length()- daoImplSuffix.length())+ daoSuffix);
        return "  public "+String.valueOf(generator.generate(table)).trim();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
