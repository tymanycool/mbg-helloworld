package com.tiany.ibator;

import com.tiany.ibator.common.BaseComponent;
import com.tiany.ibator.impl.comment.BaseComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public abstract class AbstractSqlibator extends BaseComponent implements ApplicationContextAware {
    protected ApplicationContext applicationContext;
    @Autowired
    protected Map<String,String> tibatisConfig;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
