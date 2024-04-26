/*
 * Copyright (c) 2018 Wantu, All rights reserved.
 */
package com.oujiong.config;


import com.oujiong.ShardingAlgorithm.MyTableKeysShardingAlgorithm;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author xub
 * @Description: 连接数据库信息
 * @date 2019/8/19 下午12:31
 */
@Configuration
@ComponentScan(basePackageClasses = config.class)
@MapperScan(basePackages = "com.oujiong.mapper")
public class config {

    /**
     * SqlSessionFactory 实体
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setFailFast(true);
        sessionFactory.setMapperLocations(resolver.getResources("classpath:/mapper/*Mapper.xml"));
        return sessionFactory.getObject();
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getUserTableRuleConfiguration());
//        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());

//        shardingRuleConfig.getBindingTableGroups().add("t_user");
//        shardingRuleConfig.getBroadcastTables().add("t_config");
//      //根据年龄分库 一共分为2个库
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("age", "subdb${(age % 2)+1}"));
        //根据ID分表  一共分为2张表
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new ComplexShardingStrategyConfiguration("birthdayMonth,age", new MyTableKeysShardingAlgorithm()));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
    }

    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id");
        return result;
    }

    /**
     * 定义user表的分库分表键和分库分表算法
     * @return
     */
    TableRuleConfiguration getUserTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_user_", "subdb${1..2}.t_user_${1..2}_${0..1}");
//        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        //根据年龄分库 一共分为2个库
        result.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("age", "subdb${(age % 2)+1}"));
        //根据ID分表  一共分为2张表
        result.setTableShardingStrategyConfig(new ComplexShardingStrategyConfiguration("birthdayMonth,age", new MyTableKeysShardingAlgorithm()));
        return result;
    }
    /**
     * 定义order表的分库分表键和分库分表算法
     * @return
     */
    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_order_", "subdb${1..2}.t_order_${1..2}_${0..1}");
        //根据年龄分库 一共分为2个库
        result.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "subdb${(age % 2)+1}"));
        //根据ID分表  一共分为2张表
        result.setTableShardingStrategyConfig(new ComplexShardingStrategyConfiguration("order_id", new MyTableKeysShardingAlgorithm()));
        return result;
    }


    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("subdb1", DataSourceUtil.createDataSource("subdb1"));
        result.put("subdb2", DataSourceUtil.createDataSource("subdb2"));
        return result;
    }

}
