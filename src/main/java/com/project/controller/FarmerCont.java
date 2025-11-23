package com.project.controller;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.entity.FarmerRequestEntity;
import com.project.model.FarmerRequestDto;
import com.project.repository.FarmerRegRepo;
import com.project.repository.FarmerRequestRepo;
import com.project.service.FarmerSer;
import com.project.service.JWTService;
import com.project.service.OfficerSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
//@CrossOrigin
@CrossOrigin(origins = "http://localhost:5173")

public class FarmerCont {

    @Autowired
    private FarmerSer farmerSer;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private FarmerRegRepo farmerRegRepo;
    @Autowired
    private FarmerRequestRepo requestRepo;
    @Autowired
    private OfficerSer officerSer;
        @RequestMapping("/")
        @ResponseBody
        public String greet(){
            return "Welcome to me";
        }

    //to add a new farmer to the website
    @PostMapping("/farmerReg")
    public ResponseEntity<?> addProduct(@RequestBody FarmerPlotRegisterEntity farmerRegisterEntity) {
        try{
            FarmerPlotRegisterEntity product1= farmerSer.addFarmer(farmerRegisterEntity);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  //for getting farmer profile after farmer login
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/farmerProfile/{phoneNo}")
    public ResponseEntity<FarmerPlotRegisterEntity> getFarmerProfile(@PathVariable String phoneNo){
        FarmerPlotRegisterEntity farmer = farmerSer.findByPhoneNo(phoneNo);
        System.out.println("in cont"+farmer);
        if(farmer != null) return ResponseEntity.ok(farmer);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

//    @CrossOrigin(origins = "http://localhost:5173")
//    @GetMapping("/FarmerList")
//    public ResponseEntity<List<FarmerPlotRegisterEntity>> getAllFarmersSorted() {
//
////        List<FarmerPlotRegisterEntity> farmers = farmerSer.getAllFarmersSorted(village);
//        // Get the currently logged-in officer's village
//        String officerVillage = officerSer.getLoggedInOfficerVillage();
//
//        // Fetch farmers for that village, sorted by cutting date
//        List<FarmerPlotRegisterEntity> farmers = farmerSer.getAllFarmersSorted(officerVillage);
//        return ResponseEntity.ok(farmers);
//    }
    @PostMapping("/farmer/request")
    public ResponseEntity<?> submitRequest(@RequestBody FarmerRequestDto dto,
                                           @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String phoneNo = jwtService.extractUserName(token);
        FarmerPlotRegisterEntity farmer = farmerRegRepo.findByPhoneNo(phoneNo);
        FarmerRequestEntity req = new FarmerRequestEntity();
        req.setPhoneNo(farmer.getPhoneNo());
        req.setFarmerCode(farmer.getFarmerCode());
        req.setPlotVillage(farmer.getPlotVillage());
        req.setLandArea(farmer.getLandArea());
        req.setQue1(dto.getQue1());
        req.setQue2(dto.getQue2());
        requestRepo.save(req);

        return ResponseEntity.ok("Request submitted");
    }
    //officer gets info of farmers with same phoneaNo
    @GetMapping("/farmerRecords/{phoneNo}")
    public ResponseEntity<List<FarmerPlotRegisterEntity>> getFarmerRecords(@PathVariable String phoneNo) {
        List<FarmerPlotRegisterEntity> records = farmerRegRepo.findAllByPhoneNo(phoneNo);
        if (records.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(records);
    }







}


