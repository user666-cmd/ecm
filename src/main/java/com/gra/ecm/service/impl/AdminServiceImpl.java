package com.gra.ecm.service.impl;

import com.gra.ecm.dao.AdminMapper;
import com.gra.ecm.model.Admin;
import com.gra.ecm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminMapper adminMapper;

    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin login(String username, String password) {
        Admin admin = adminMapper.findByUsername(username);
        if (admin == null) {
            return null;
        }
        if (!admin.getPassword().equals(password)) {
            return null;
        }
        return admin;
    }
}