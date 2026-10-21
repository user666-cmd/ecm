package com.gra.ecm.service;

import com.gra.ecm.model.Admin;

public interface AdminService {
    Admin login(String username, String password);
}