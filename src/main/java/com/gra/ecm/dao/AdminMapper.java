package com.gra.ecm.dao;

import com.gra.ecm.model.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Admin findByUsername(String username);
}