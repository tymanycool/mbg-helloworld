package com.csii.pmis.admin.dao.impl;

import com.csii.pmis.admin.dao.AccountDao;
import com.csii.pmis.service.bean.model.Account;
import com.tiany.util.DateUtil;
import com.tiany.util.MapUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class AccountDaoImplTest {
    @Autowired
    AccountDao accountDao;

    @Test
    public void insert() throws Exception {
        Account account = new Account();
        account.setAcctPkey("tianyao_3333");
        account.setAccount("accout");
        account.setAccState("4");
        account.setChannel("112");
        account.setCreateTime(new Date());
        account.setIdNo("522125199212211916");
        //account.setComment("tianyao田耀".getBytes());
        Object insert = accountDao.insert(account);
        System.out.println(insert);
    }

    @Test
    public void insertList() throws Exception {
        ArrayList<Account> list = new ArrayList<>();

        Account account = new Account();
        account.setAcctPkey("tianyao_blob_test7");
        account.setAccount("accout");
        account.setAccState("4");
        account.setChannel("112");
        account.setCreateTime(new Date());
        account.setIdNo("522125199212211916");
        //account.setComment("tianyao田耀".getBytes());


        Account account2 = new Account();
        account2.setAcctPkey("tianyao_blob_test8");
        account2.setAccount("accout");
        account2.setAccState("4");
        account2.setChannel("112");
        account2.setCreateTime(new Date());
        account2.setIdNo("522125199212211916");
        //account2.setComment("tianyao田耀".getBytes());

        list.add(account);
        list.add(account2);
        Object insert = accountDao.insertList(list);
        System.out.println(insert);
    }

    @Test
    public void select() throws Exception {
        Account account = new Account();
        account.setAcctPkey("tianyao_blob_test2");
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
        List<Map<String,Object>> maps = accountDao.selectForMapList(map);

        System.out.println(maps.get(0).get("account"));
    }

    @Test
    public void select4() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("acctPkey", "tianyao2");
        Map<String, ?> result = accountDao.selectForMap(map);

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
        account.setIdNo("522125199212211917");
        Map<String, Object> map =  MapUtil.obj2Map(account);
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

    @Test
    public void testSelectCountByParams() throws Exception {
        int count = accountDao.selectCountByParams(null);
    }


    @Test

    public void deleteList() throws Exception {

        ArrayList<String> list = new ArrayList<>();
        list.add("tianyao_blob_test7");
        list.add("tianyao_blob_test8");

        int ret = accountDao.deleteList(list);
    }

    @Test
    public void testUpdateList() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("tianyao_blob_test5");
        list.add("tianyao_blob_test6");

        Account account = new Account();
        account.setAccState("9");

        int ret = accountDao.updateList(list, account);

    }
}