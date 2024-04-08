package com.oujiong.ShardingAlgorithm;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @Description:
 *
 * 参考 :https://blog.csdn.net/qq_45367825/article/details/132420473
 *
 * @author zhoufudun
 */
@Slf4j
public class MyTableKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    private static final String sharding_birthdayMonth="birthdayMonth";

    private static final String sharding_age ="age";

    @Override
    public Collection<String> doSharding(Collection<String> tableNames, ComplexKeysShardingValue<String> complexKeysShardingValue) {
        log.info("availableTableNames:" + JSON.toJSONString(tableNames) + ",shardingValues:" + JSON.toJSONString(complexKeysShardingValue));
        //进入通用复杂分片算法-抽象类-表路由：availableTableNames:["t_user_1_0","t_user_1_1","t_user_2_0","t_user_2_1"],shardingValues:{"columnNameAndShardingValuesMap":{"age":[2],"birthdayMonth":[0]},"logicTableName":"t_user_"}
        // 返回真实表名集合
        Collection<String> tableNameList = new ArrayList<>();

        Map<String, Collection<String>> columnNameAndShardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();

        Collection<String>  age = columnNameAndShardingValuesMap.get(sharding_age);
        Collection<String> birthdayMonth = columnNameAndShardingValuesMap.get(sharding_birthdayMonth);

        log.info("sharding age Value:" + age);
        log.info("sharding birthdayMonth Value:" + birthdayMonth);

        //分片键缺任何一个字段均返回全部表
        if (age.isEmpty() || birthdayMonth.isEmpty()) {
            for (String tableName : tableNames) {
                tableNames.add(tableName);
            }
            log.info("分片键缺任何一个字段均返回全部表,tableNameList="+tableNameList);
            return tableNameList;//返回全部
        }

        Integer month = (Integer) birthdayMonth.toArray()[0];
        Integer ageInteger = (Integer) age.toArray()[0];

        // 计算月
        int m = (month % 2)+1;

        // 计算表的名字
        String caculateTableName="t_user_"+m+"_"+ageInteger%2;

        // 计算出的表名字在可以集合里
        if(tableNames.contains(caculateTableName)){
            tableNameList.add(caculateTableName);
            log.info("计算出的表名字在集合里,tableNameList="+tableNameList);
            return tableNameList;
        }else {

            // 计算出的表名字不在可以集合里
            for (String tableName : tableNames) {
                tableNameList.add(tableName);
            }
            log.info("计算出的表名字不在可以集合里,tableNameList={}, caculateTableName={}",tableNameList,caculateTableName);
            return tableNameList;
        }
    }
}