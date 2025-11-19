package com.project.repository;

import com.project.entity.OfficerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OfficerRepo extends JpaRepository<OfficerEntity,Integer> {
    Optional<OfficerEntity> findByUsername(String username);

}
