package com.project.repository;

import com.project.entity.AdminEntity;
import com.project.entity.OfficerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<AdminEntity,Integer> {

    Optional<AdminEntity> findByUsername(String username);
}
