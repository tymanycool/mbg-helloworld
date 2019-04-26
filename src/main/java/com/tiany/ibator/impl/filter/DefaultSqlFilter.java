package com.tiany.ibator.impl.filter;

import com.tiany.ibator.common.BaseComponent;
import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.infs.FilterAccept;
import com.tiany.ibator.infs.FilterRefuse;
import com.tiany.ibator.infs.SqlFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 从一个sql文件中筛选出Create Table 语句
 *
 * @author tiany
 * @version 1.0
 */
@Component
public class DefaultSqlFilter extends BaseComponent implements SqlFilter, ApplicationContextAware {
    private ApplicationContext applicationContext;


    @Override
    public List<SQL> doFilter(List<SQL> list, String sql) {
        List<SQL> retList = new ArrayList<>();
        String[] acceptList = applicationContext.getBeanNamesForType(FilterAccept.class);
        String[] refuseList = applicationContext.getBeanNamesForType(FilterRefuse.class);

        for (SQL sqlObj : list) {
            if (acceptList != null && acceptList.length > 0) {
                for (String filterAcceptName : acceptList) {
                    FilterAccept filterAccept = applicationContext.getBean(filterAcceptName, FilterAccept.class);
                    if (filterAccept.accept(sqlObj)) {
                        if (refuseList != null && refuseList.length > 0) {
                            boolean r = false;
                            for (String filterRefuseName : refuseList) {
                                FilterRefuse filterRefuse = applicationContext.getBean(filterRefuseName, FilterRefuse.class);
                                if (!filterRefuse.refuse(sqlObj)) {
                                    r = true;
                                    break;
                                }
                            }
                            if (!r) {
                                retList.add(sqlObj);
                            }
                        } else {
                            retList.add(sqlObj);
                        }
                    }
                }
            }
        }
        return retList;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
