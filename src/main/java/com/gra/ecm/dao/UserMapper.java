package com.gra.ecm.dao;

import com.gra.ecm.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    int insert(User user);
    List<User> findAll();
    int updateById(User user);
    int deleteById(Integer id);
}