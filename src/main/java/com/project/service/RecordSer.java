//package com.project.service;
//
//import com.project.entity.FarmerPlotRegisterEntity;
//import com.project.repository.FarmerRegRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RecordSer {
//    @Autowired
//   private FarmerRegRepo farmerRegRepo;
//
//    public List<FarmerPlotRegisterEntity> getAllRecordsSorted() {
//        return farmerRegRepo.findByVillageOrderByCuttingDateAsc();
//    }
//}
