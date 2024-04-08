package com.oujiong.entity;

import lombok.Data;

import java.util.Date;

/**
 * user表
 */
@Data
public class User {

    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 是否删除 1删除 0未删除
     */
    private Integer status;

    /**
     * 生日月份
     *
     */
    private Integer birthdayMonth;

    public User(Long id, String name, String sex, Integer age, Integer birthdayMonth) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.birthdayMonth = birthdayMonth;
    }
}