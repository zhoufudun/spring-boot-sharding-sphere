package com.oujiong.controller;


import com.google.common.collect.Lists;
import com.oujiong.model.User;
import com.oujiong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * @Description: 接口测试
 *
 * @author xub
 * @date 2019/8/24 下午6:31
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 模拟插入数据
     */
    List<User> userList = Lists.newArrayList();
    /**
     * 初始化插入数据
     */
    @PostConstruct
    private void getData() {

        for(int i=0;i<1000;i++){
            userList.add(new User(new Random().nextLong(),"mock_name_"+i, "女", new Random().nextInt(10),i%2));
        }
    }
    /**
     * @Description: 批量保存用户
     */
    @PostMapping("save-user")
    public Object saveUser() {
        return userService.insertForeach(userList);
    }
    /**
     * @Description: 获取用户列表
     */
    @GetMapping("list-user")
    public Object listUser() {
        return userService.list();
    }

    @GetMapping("query-user/{birthdayMonth}/{age}")
    public List<User> query(@PathVariable("birthdayMonth")Integer birthdayMonth, @PathVariable("age")Integer age) {

        List<User> query = userService.query(birthdayMonth, age);

        List<User> collect = query.stream().filter(user -> {
            // 过滤出数据
            return !user.getBirthdayMonth().equals(birthdayMonth);
        }).collect(Collectors.toList());

        List<User> collect2 = query.stream().filter(user -> {
            // 过滤出数据
            return !user.getAge().equals(age);
        }).collect(Collectors.toList());



        log.info("总共数据条数{}",query.size());
        // 正常必须是0
        log.info("生日不满足条件的数据条数：{}",collect.size());
        // 正常必须是0
        log.info("年龄不满足条件的数据条数：{}",collect2.size());

        return query;

    }

    @GetMapping("deleteAllUser")
    public void deleteAllUser() {
        userService.deleteAllUser();
    }


}
