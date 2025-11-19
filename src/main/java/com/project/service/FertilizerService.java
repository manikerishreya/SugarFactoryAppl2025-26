package com.project.service;


import com.project.entity.FarmerPlotRegisterEntity;
import com.project.entity.FarmerRequestEntity;
import com.project.entity.FertilizerAllocation;
import com.project.repository.FarmerRegRepo;
import com.project.repository.FarmerRequestRepo;
import com.project.repository.FertilizerAllocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FertilizerService {

    @Autowired
    private FarmerRegRepo farmerRegRepo;

    @Autowired
    private FertilizerAllocationRepo allocationRepo;

    @Autowired
    private FarmerRequestRepo requestRepo;

    private final double fertilizerPerAcre = 50; // kg
    private final double pricePerKg = 20; // Rs

    public FertilizerAllocation allocateFertilizer(String phoneNo) {
        FarmerRequestEntity farmer = requestRepo.findByPhoneNo("+"+phoneNo);
//                .orElseThrow(() -> new RuntimeException("Farmer not found"));
        if (farmer == null) {
            throw new RuntimeException("Farmer not found");
        }

        farmer.setStatus("ACCEPTED");
        requestRepo.save(farmer);

        Integer landArea = farmer.getLandArea();
        double fertilizer = landArea * fertilizerPerAcre;
        double totalCost = fertilizer * pricePerKg;
        FertilizerAllocation allocation = new FertilizerAllocation();
        allocation.setLandArea(landArea);
        allocation.setFertilizerAllocated(fertilizer);
        allocation.setFarmerCode(farmer.getFarmerCode());
        allocation.setFarmerName(farmer.getFarmerName());
        allocation.setPlotVillage(farmer.getPlotVillage());
        allocation.setPhoneNo("+"+phoneNo);
        allocation.setTotalCost(totalCost);
        return allocationRepo.save(allocation);
    }
}

