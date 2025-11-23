package com.project.service;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.model.CaneUtils;
import com.project.repository.FarmerRegRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.io.IOException;

//@Service
//public class FarmerSer {
// @Autowired
// private FarmerRegRepo farmerRegRepo;
//
//    public FarmerPlotRegisterEntity addFarmer(FarmerPlotRegisterEntity farmerRegisterEntity) throws IOException{
//        FarmerPlotRegisterEntity savedFarmer = farmerRegRepo.save(farmerRegisterEntity);
//        String farmerCode = "CULT" + String.format("%05d", savedFarmer.getSrNo());
//        savedFarmer.setFarmerCode(farmerCode);
//        return farmerRegRepo.save(savedFarmer);
//        }
//        public FarmerPlotRegisterEntity findByPhoneNo(String phoneNo) {
//        System.out.println("in service "+farmerRegRepo.findByPhoneNo(phoneNo));
//            return farmerRegRepo.findByPhoneNo(phoneNo);
//                     //.orElse(null);
//    }
//    public List<FarmerPlotRegisterEntity> getAllFarmersSorted() {
//        return farmerRegRepo.findAllSortedByPlantingDate();
//    }
//
//
//}

import java.util.Date;

@Service
public class FarmerSer {

    @Autowired
    private FarmerRegRepo farmerRegRepo;

    // Add farmer plot and calculate cutting date
    public FarmerPlotRegisterEntity addFarmer(FarmerPlotRegisterEntity farmerRegisterEntity) throws IOException {

        // Save initially to generate srNo
        FarmerPlotRegisterEntity savedFarmer = farmerRegRepo.save(farmerRegisterEntity);

        // Generate farmer code
        String farmerCode = "CULT" + String.format("%05d", savedFarmer.getSrNo());
        savedFarmer.setFarmerCode(farmerCode);

        // Calculate cutting date based on plantingDate, caneVariety, and cropType
        Date cuttingDate = CaneUtils.calculateCuttingDate(
                savedFarmer.getPlantingDate(),
                savedFarmer.getCaneVariety(),
                savedFarmer.getCropType()
        );
        savedFarmer.setCuttingDate(cuttingDate);

        // Save again with cutting date
        return farmerRegRepo.save(savedFarmer);
    }

    // Find farmer by phone number for otp verification
    public FarmerPlotRegisterEntity findByPhoneNo(String phoneNo) {
        System.out.println("In service: " + farmerRegRepo.findByPhoneNo(phoneNo));
        return farmerRegRepo.findByPhoneNo(phoneNo);
    }
    //while the officer looks for farmer info
    public List<FarmerPlotRegisterEntity> findAllByPhoneNo(String phoneNo) {
        System.out.println("In service: " + farmerRegRepo.findByPhoneNo(phoneNo));
        return farmerRegRepo.findAllByPhoneNo(phoneNo);
    }

    // Get all farmers sorted by cutting date to the officer
    public List<FarmerPlotRegisterEntity> getAllFarmersSorted(String village) {
        return farmerRegRepo.findByPlotVillageOrderByCuttingDateAsc(village);
    }
}

