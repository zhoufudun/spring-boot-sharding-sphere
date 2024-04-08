package com.oujiong.service.impl;

import com.oujiong.entity.User;
import com.oujiong.mapper.UserMapper;
import com.oujiong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author xub
 * @Description: 用户实现类
 * @date 2019/8/8 上午9:13
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public  List<User> list() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public String insertForeach(List<User> userList) {
        for (User user : userList) {
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setStatus(0);
        }
        //批量插入数据
        userMapper.insertForeach(userList);
        log.info(userList.toString());
        return "保存成功";
    }

    @Override
    public void deleteAllUser() {
        userMapper.deleteAllUser();
    }

    @Override
    public List<User> query(Integer birthdayMonth, Integer age) {
        return userMapper.query(birthdayMonth,age);
    }
}
