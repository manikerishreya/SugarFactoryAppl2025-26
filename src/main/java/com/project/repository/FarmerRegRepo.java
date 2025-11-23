package com.project.repository;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.entity.FarmerRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FarmerRegRepo extends JpaRepository<FarmerPlotRegisterEntity,Integer> {
    FarmerPlotRegisterEntity findByPhoneNo(String phoneNo);
    List<FarmerPlotRegisterEntity> findAllByOrderByRegDateDesc();


    List<FarmerPlotRegisterEntity> findAllByPhoneNo(String phoneNo);
//    @Query("SELECT r FROM FarmerPlotRegisterEntity r ORDER BY r.plantingDate ASC")
//    List<FarmerPlotRegisterEntity> findAllSortedByPlantingDate();
//@Query("SELECT r FROM FarmerPlotRegisterEntity r ORDER BY r.cuttingDate ASC")
//List<FarmerPlotRegisterEntity> findAllSortedByCuttingDate();


//    @Query("SELECT r FROM FarmerPlotRegisterEntity r " +
//            "WHERE r.village = :villageName " +
//            "ORDER BY r.cuttingDate ASC")
//    List<FarmerPlotRegisterEntity> findByVillageSorted(@Param("villageName") String villageName);
List<FarmerPlotRegisterEntity> findByPlotVillageOrderByCuttingDateAsc(String village);

//
//    @Query("SELECT r FROM FarmerPlotRegisterEntity r ORDER BY r.village ASC")
//    List<FarmerPlotRegisterEntity> findAllSortedByVillage();
}

