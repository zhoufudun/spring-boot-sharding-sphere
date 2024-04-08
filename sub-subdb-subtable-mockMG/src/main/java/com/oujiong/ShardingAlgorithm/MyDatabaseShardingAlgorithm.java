package com.oujiong.ShardingAlgorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 分库算法
 * 参考：https://blog.csdn.net/qq_45367825/article/details/132420473
 */
public class MyDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {


	//返回算法的名字
    public String getType() {
        return "DATABASE_INLINE";
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        return null;
    }
}

