package com.project.repository;

import com.project.entity.FarmerPlotRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FarmerRegRepo extends JpaRepository<FarmerPlotRegisterEntity,Integer> {
    FarmerPlotRegisterEntity findByPhoneNo(String phoneNo);
    List<FarmerPlotRegisterEntity> findAllByOrderByRegDateDesc();


    @Query("SELECT r FROM FarmerPlotRegisterEntity r ORDER BY r.plantingDate ASC")
    List<FarmerPlotRegisterEntity> findAllSortedByPlantingDate();


}
