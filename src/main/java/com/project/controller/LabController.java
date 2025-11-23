package com.project.controller;

import java.util.List;

import com.project.entity.FarmerPlotRegisterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.entity.LabEntity;
import com.project.service.LabService;

@RestController
@RequestMapping("/lab")
@CrossOrigin
public class LabController {

    @Autowired
    private LabService labService;

    // Add new lab (Admin)
    @PostMapping("/add")
    public ResponseEntity<LabEntity> addLab(@RequestBody LabEntity lab) {
        return ResponseEntity.ok(labService.addLab(lab));
    }
    // Update lab details
    @PutMapping("/update/{labId}")
    public ResponseEntity<LabEntity> updateLab(@PathVariable Long labId,
                                               @RequestBody LabEntity updatedLab) {
        return ResponseEntity.ok(labService.updateLab(labId, updatedLab));
    }
    // Activate lab
    @PutMapping("/activate/{labId}")
    public ResponseEntity<LabEntity> activateLab(@PathVariable Long labId) {
        return ResponseEntity.ok(labService.activateLab(labId));
    }
    // Deactivate lab
    @PutMapping("/deactivate/{labId}")
    public ResponseEntity<LabEntity> deactivateLab(@PathVariable Long labId) {
        return ResponseEntity.ok(labService.deactivateLab(labId));
    }
    // Get all labs
    @GetMapping("/all")
    public ResponseEntity<List<LabEntity>> getAllLabs() {
        return ResponseEntity.ok(labService.getAllLabs());
    }
    // Get lab by ID
    @GetMapping("/{labId}")
    public ResponseEntity<LabEntity> getLabById(@PathVariable Long labId) {
        return ResponseEntity.ok(labService.getLabById(labId));
    }
    // Delete lab
    @DeleteMapping("/delete/{labId}")
    public ResponseEntity<String> deleteLab(@PathVariable Long labId) {
        labService.deleteLab(labId);
        return ResponseEntity.ok("Lab deleted successfully");
    }



    // Get Lab profile by phone number
//    @GetMapping("/profile")
//    public ResponseEntity<?> getLabProfile(@RequestParam String phoneNo) {
//        LabEntity lab = labService.getLabByContact(phoneNo);
//
//        if (lab == null) {
//            return ResponseEntity
//                    .status(404)
//                    .body("Lab profile not found");
//        }
//
//        return ResponseEntity.ok(lab);
//    }

    //for getting farmer profile after farmer login
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/labProfile/{phoneNo}")
    public ResponseEntity<LabEntity> getLabProfile(@PathVariable String phoneNo){
        LabEntity lab = labService.getLabByContact(phoneNo);
        System.out.println("in cont"+lab);
        if(lab != null) return ResponseEntity.ok(lab);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

//    @PostMapping("/send-otp")
//    public ResponseEntity<String> sendOtp(@RequestParam String phoneNo) {
//
//        boolean sent = labService.sendOtpToLab(phoneNo);
//
//        if (!sent) {
//            return ResponseEntity.badRequest().body("Phone number not found!");
//        }
//
//        return ResponseEntity.ok("OTP sent to lab assistant");
//    }
//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOtp(@RequestParam String phoneNo,
//                                       @RequestParam Integer otp) {
//
//        LabEntity lab = labService.verifyOtp(phoneNo, otp);
//
//        if (lab == null) {
//            return ResponseEntity.badRequest().body("Invalid OTP");
//        }
//
//        return ResponseEntity.ok(lab); // login successful
//    }

}









