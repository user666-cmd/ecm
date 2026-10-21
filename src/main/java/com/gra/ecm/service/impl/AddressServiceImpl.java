package com.gra.ecm.service.impl;

import com.gra.ecm.dao.AddressMapper;
import com.gra.ecm.model.Address;
import com.gra.ecm.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> findByUserId(Integer userId) {
        return addressMapper.findByUserId(userId);
    }

    @Override
    public Address findById(Integer id) {
        return addressMapper.findById(id);
    }

    @Override
    @Transactional
    public Address addAddress(Address address) {
        if (address.getIsDefault() == null) {
            address.setIsDefault(0);
        }
        if (address.getIsDefault() == 1) {
            addressMapper.updateDefaultStatus(address.getUserId(), 0);
        }
        addressMapper.insert(address);
        return address;
    }

    @Override
    @Transactional
    public boolean updateAddress(Address address) {
        Address existingAddress = addressMapper.findById(address.getId());
        if (existingAddress == null) {
            return false;
        }
        if (address.getIsDefault() == 1) {
            addressMapper.updateDefaultStatus(existingAddress.getUserId(), 0);
        }
        return addressMapper.update(address) > 0;
    }

    @Override
    @Transactional
    public boolean deleteAddress(Integer id) {
        return addressMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public boolean setDefaultAddress(Integer userId, Integer addressId) {
        Address address = addressMapper.findById(addressId);
        if (address == null || !address.getUserId().equals(userId)) {
            return false;
        }
        addressMapper.setDefaultAddress(userId, addressId);
        return true;
    }
}