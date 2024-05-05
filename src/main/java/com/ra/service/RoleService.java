package com.ra.service;

import com.ra.model.entity.ERoles;
import com.ra.model.entity.Roles;

import java.util.List;

public interface RoleService {
    Roles findByRoleName(ERoles name);
    List<Roles> getAll();
}
