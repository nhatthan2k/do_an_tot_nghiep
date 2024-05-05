package com.ra.service.impl;

import com.ra.model.dto.request.AddressRequest;
import com.ra.model.entity.Address;
import com.ra.model.entity.Users;
import com.ra.repository.AddressRepository;
import com.ra.service.AddressService;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceIMPL implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserService userService;
    @Override
    public Address add(AddressRequest addressRequest, Long userId) {
        Users user = userService.findById(userId);

        Address address = Address.builder()
                .users(user)
                .address(addressRequest.getAddress())
                .phone(addressRequest.getPhone())
                .receiveName(addressRequest.getReceiceName())
                .build();
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long addressId, Long userId) {
        addressRepository.deleteByIdAndUserId(addressId, userId);
    }

    @Override
    public List<Address> getAll(Long id) {
        return addressRepository.findAllByUserId(id);
    }

    @Override
    public Address findById(Long addressId, Long userId) {
        return addressRepository.findByIdAndUserId(addressId, userId);
    }
}
