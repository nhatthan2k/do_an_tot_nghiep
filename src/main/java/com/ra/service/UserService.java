package com.ra.service;

import com.ra.model.dto.request.UserLogin;
import com.ra.model.dto.request.UserRegister;
import com.ra.model.dto.response.JwtResponse;
import com.ra.model.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Users handleRegister(Users user);
    Page<Users> getAll(Pageable pageable);
    Users findById(Long id);
    void delete(Long id);
    Users save(Users users);
    Users updateAcc(Users user, Long id);
    Optional<Users> findByUsername(String username);
    List<Users> searchByName(String keyword);
}
