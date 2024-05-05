package com.ra.repository;

import com.ra.model.entity.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a from Address a where a.users.id = :id")
    List<Address> findAllByUserId(Long id);
    @Modifying
    @Query("delete from Address a where a.id = :addressId and a.users.id = :userId")
    void deleteByIdAndUserId(Long addressId, Long userId);

    @Query("SELECT a from Address a where a.id = :addressId and a.users.id = :userId")
    Address findByIdAndUserId(Long addressId, Long userId);
}
