package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.LabEntity;
import java.util.List;


@Repository
public interface LabRepo extends JpaRepository<LabEntity, Long>{

    // Optional custom methods if needed later:
    // List<LabEntity> findByActive(boolean active);
    LabEntity findByContactNumber(String contactNumber);

}