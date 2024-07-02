package com.oujiong.mapper.two;

public interface StockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);
}