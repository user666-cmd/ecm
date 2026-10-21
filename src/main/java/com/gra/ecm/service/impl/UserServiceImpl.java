package com.gra.ecm.service.impl;

import com.gra.ecm.dao.UserMapper;
import com.gra.ecm.model.User;
import com.gra.ecm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    @Override
    public User register(User user) {
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            return null;
        }
        userMapper.insert(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User updateById(User user) {
        userMapper.updateById(user);
        return userMapper.findByUsername(user.getUsername());
    }

    @Override
    public boolean deleteById(Integer id) {
        return userMapper.deleteById(id) > 0;
    }
}