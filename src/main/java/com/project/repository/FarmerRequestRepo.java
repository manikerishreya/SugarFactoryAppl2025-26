package com.project.repository;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.entity.FarmerRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRequestRepo extends JpaRepository<FarmerRequestEntity,Long> {
    List<FarmerRequestEntity> findByStatus(String status);

    FarmerRequestEntity findByPhoneNo(String phoneNo);

}
