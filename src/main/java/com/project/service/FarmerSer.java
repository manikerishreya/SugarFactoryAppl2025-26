package com.project.service;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.repository.FarmerRegRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.io.IOException;

@Service
public class FarmerSer {
 @Autowired
 private FarmerRegRepo farmerRegRepo;

    public FarmerPlotRegisterEntity addFarmer(FarmerPlotRegisterEntity farmerRegisterEntity) throws IOException{
        FarmerPlotRegisterEntity savedFarmer = farmerRegRepo.save(farmerRegisterEntity);
        String farmerCode = "CULT" + String.format("%05d", savedFarmer.getSrNo());
        savedFarmer.setFarmerCode(farmerCode);
        return farmerRegRepo.save(savedFarmer);
        }
        public FarmerPlotRegisterEntity findByPhoneNo(String phoneNo) {
        System.out.println("in service "+farmerRegRepo.findByPhoneNo(phoneNo));
            return farmerRegRepo.findByPhoneNo(phoneNo);
                     //.orElse(null);
    }
    public List<FarmerPlotRegisterEntity> getAllFarmersSorted() {
        return farmerRegRepo.findAllSortedByPlantingDate();
    }


}

