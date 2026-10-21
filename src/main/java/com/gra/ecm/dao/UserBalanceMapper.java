package com.gra.ecm.dao;

import com.gra.ecm.model.UserBalance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface UserBalanceMapper {
    UserBalance findByUserId(Integer userId);
    int insert(UserBalance userBalance);
    int updateBalance(@Param("userId") Integer userId, @Param("amount") BigDecimal amount);
    int freezeAmount(@Param("userId") Integer userId, @Param("amount") BigDecimal amount);
    int unfreezeAmount(@Param("userId") Integer userId, @Param("amount") BigDecimal amount);
    int deductFrozenAmount(@Param("userId") Integer userId, @Param("amount") BigDecimal amount);
}