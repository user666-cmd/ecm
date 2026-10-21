package com.gra.ecm.dao;

import com.gra.ecm.model.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<Address> findByUserId(Integer userId);
    Address findById(Integer id);
    int insert(Address address);
    int update(Address address);
    int deleteById(Integer id);
    int updateDefaultStatus(@Param("userId") Integer userId, @Param("isDefault") Integer isDefault);
    int setDefaultAddress(@Param("userId") Integer userId, @Param("addressId") Integer addressId);
}