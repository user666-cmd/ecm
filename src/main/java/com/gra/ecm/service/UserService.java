package com.gra.ecm.service;

import com.gra.ecm.model.User;

import java.util.List;

public interface UserService {
    User login(String username, String password);
    User register(User user);
    List<User> findAll();
    User updateById(User user);
    boolean deleteById(Integer id);
}