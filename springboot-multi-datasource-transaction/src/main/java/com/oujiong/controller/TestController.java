package com.oujiong.controller;

import com.alibaba.fastjson.JSON;
import com.oujiong.aop.TransactionAnnotation;
import com.oujiong.mapper.one.User;
import com.oujiong.mapper.one.UserMapper;
import com.oujiong.mapper.two.Stock;
import com.oujiong.mapper.two.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.System.currentTimeMillis;

@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StockMapper stockMapper;

    @RequestMapping("query")
    public void select() {
        User user = new User();
        user.setUserName("张三");
        user.setPassword("123");
        user.setTbPhone("123919239");
        userMapper.insert(user);
        Stock stock = new Stock();
        stock.setName("小明");
        stockMapper.insert(stock);
        user = userMapper.selectByPrimaryKey(user.getUserId());
        stock = stockMapper.selectByPrimaryKey(stock.getId());
        System.out.println(JSON.toJSONString(user));
        System.out.println(JSON.toJSONString(stock));
    }

    @RequestMapping("install")
    @Transactional() //只会对默认事物有效果
    public void install() {
        createAndInstall();
        int i = 1 / 0;
    }

    /**
     * 这样就可以一条也插入不进去了
     */
    @TransactionAnnotation({TransactionAnnotation.TransactionEnum.ONETransaction, TransactionAnnotation.TransactionEnum.TWOTransaction})
    @RequestMapping("install2")
    public void install2() {
        createAndInstall();
        int i = 1 / 0;
    }


    private void createAndInstall() {
        User user = new User();
        user.setUserId((int) currentTimeMillis());
        user.setUserName("张三");
        user.setPassword("123");
        user.setTbPhone("123919239");
        userMapper.insert(user);
        Stock stock = new Stock();
        stock.setName("小明");
        stockMapper.insert(stock);
        System.out.println(JSON.toJSONString(user));
        System.out.println(JSON.toJSONString(stock));
    }

}
