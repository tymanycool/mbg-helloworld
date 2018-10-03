package com.tiany.ibator;

import com.csii.pmis.admin.dao.AccountDao;
import com.csii.pmis.service.bean.model.Account;
import com.csii.pmis.service.bean.model.AccountExample;
import com.tiany.util.io.FileUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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
        List<Account> list = accountDao.selectForListNot(null);
    }

    @Test
    public void test3() throws Exception {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountTypeEqualTo("1");
        List<Account> accounts = accountDao.selectByExample(example);
    }

    @Test
    public void test4() throws Exception {
        Account account = new Account();
        account.setAcNo("1223322");
        account.setBankName("田耀");
        account.setBankId("2");
        int count = accountDao.updateByPrimaryKeyForcible(account);
    }

    @Test
    public void test5() throws Exception {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andBankIdEqualTo("2");
        Account account = new Account();
        account.setBankName("田耀2");
        account.setBankId("2");
        int count = accountDao.updateByExample(account, example);
    }
}