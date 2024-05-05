package com.ra.service.impl;

import com.ra.model.entity.ERoles;
import com.ra.model.entity.Roles;
import com.ra.repository.RoleRepository;
import com.ra.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceIMPL implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Roles findByRoleName(ERoles name) {
        Roles roles = roleRepository.findByRoleName(name).orElseThrow(() -> new RuntimeException("role not found"));
        return roles;
    }

    @Override
    public List<Roles> getAll() {
        return roleRepository.findAll();
    }

}
