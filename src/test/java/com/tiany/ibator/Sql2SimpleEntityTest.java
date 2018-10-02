package com.tiany.ibator;

import com.csii.pmis.admin.dao.AccountDao;
import com.csii.pmis.service.bean.model.Account;
import com.tiany.util.io.FileUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
//@Ignore
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class Sql2SimpleEntityTest {
    @Autowired
    private SimpleSqlibator simpleSqlibator;
    @Autowired
    private AccountDao accountDao;

    @Ignore
    @Test
    public void test() throws Exception {
        Map map = (Map)simpleSqlibator.convert(FileUtil.read("src/main/resources/sql.txt"));
        System.out.println(map.get("entity"));
        System.out.println(map.get("xml"));
        System.out.println(map.get("dao"));
        System.out.println(map.get("daoImpl"));
    }

    @Test
    public void test2() throws Exception {
        Account account = accountDao.selectByPrimaryKey("6215268404400094065");
    }
}