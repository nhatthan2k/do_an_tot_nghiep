package com.ra.service;

import com.ra.model.dto.request.AddressRequest;
import com.ra.model.entity.Address;

import java.util.List;

public interface AddressService {
    Address add(AddressRequest addressRequest, Long userId);
    void delete(Long addressId, Long userId);
    List<Address> getAll(Long id);
    Address findById(Long addressId, Long userId);
}
