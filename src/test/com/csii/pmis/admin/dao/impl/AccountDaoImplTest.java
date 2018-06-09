package com.csii.pmis.admin.dao.impl;

import com.csii.pmis.admin.dao.AccountDao;
import com.csii.pmis.service.bean.model.Account;
import com.tiany.util.MapUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class AccountDaoImplTest {
    @Autowired
    AccountDao accountDao;

    @Test
    public void insert() throws Exception {
        Account account = new Account();
        account.setAcctPkey("tianyao3");
        account.setAccount("accout");
        account.setAccState("4");
        account.setChannel("112");
        account.setCreateTime(new Date());
        account.setIdNo("522125199212211916");
        Object insert = accountDao.insert(account);
        System.out.println(insert);
    }

    @Test
    public void select() throws Exception {
        Account account = new Account();
        account.setAcctPkey("tianyao2");
        System.out.println(accountDao.selectForObject(account));
    }

    @Test
    public void select2() throws Exception {
        List<Account> accounts = accountDao.selectForList(null);
        System.out.println(accounts);
    }

    @Test
    public void select3() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("acctPkey", "tianyao2");
        List<Map<String, Object>> maps = accountDao.selectForMapList(map);

        System.out.println(maps.get(0).get("account"));
    }

    @Test
    public void select4() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("acctPkey", "tianyao2");
        Map<String, Object> result = accountDao.selectForMap(map);

        System.out.println(result.get("account"));
    }

    @Test
    public void update() throws Exception {
        Account account = new Account();
        account.setAcctPkey("tianyao");
        account.setAccount("accout");
        account.setAccState("2");
        account.setChannel("112");
        account.setCreateTime(new Date());
        account.setIdNo("522125199212211916");
        Map<String, Object> map = MapUtil.bean2Map(account);
        int update = accountDao.updateByParams(map);
        System.out.println(update);
    }

    @Test
    public void delete() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("acctPkey","1");
        int delete = accountDao.deleteByParams(map);
        System.out.println(delete);
    }

    @Test
    public void delete2() throws Exception {
        int delete = accountDao.deleteByPrimaryKey("tianyao3");
        System.out.println(delete);
    }

}