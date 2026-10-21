package com.gra.ecm.service;

import com.gra.ecm.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> findByUserId(Integer userId);
    Address findById(Integer id);
    Address addAddress(Address address);
    boolean updateAddress(Address address);
    boolean deleteAddress(Integer id);
    boolean setDefaultAddress(Integer userId, Integer addressId);
}