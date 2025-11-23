package com.project.controller;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.entity.OfficerEntity;
import com.project.repository.OfficerRepo;
import com.project.service.FarmerSer;
import com.project.service.OfficerSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class OfficerCont {

    @Autowired
    private OfficerSer officerSer;

    @Autowired
    private OfficerRepo officerRepo;

    @Autowired
    private FarmerSer farmerSer;
//    // LOGIN → Returns JWT Token
//    @PostMapping("/officerLogin")
//    @CrossOrigin(origins = "http://localhost:5173")
//    public String officerlogin(@RequestBody OfficerEntity officerEntity) {
//        return officerSer.verify(officerEntity);
//    }

    // LOGIN → Returns JWT Token
    @PostMapping("/officerLogin")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> officerLogin(@RequestBody OfficerEntity officerEntity) {

        String token = officerSer.verify(officerEntity);
        // If login failed → token will be null
        if (token == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", "OFFICER"); // Frontend stores only raw role
        response.put("username", officerEntity.getUsername());
        // Success → send token
        return ResponseEntity.ok(response);
    }




    // OFFICER PROFILE (Slip Boy Profile)
    @GetMapping("/officerProfile")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> getOfficerProfile(Principal principal) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = authentication.getName(); // safely get username
        OfficerEntity officer = officerSer.getOfficerByUsername(username);
        return ResponseEntity.ok(officer);
        }


    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/FarmerList")
    public ResponseEntity<List<FarmerPlotRegisterEntity>> getAllFarmersSorted() {

//      List<FarmerPlotRegisterEntity> farmers = farmerSer.getAllFarmersSorted(village);
        // Get the currently logged-in officer's village
        String officerVillage = officerSer.getLoggedInOfficerVillage();

        // Fetch farmers for that village, sorted by cutting date
        List<FarmerPlotRegisterEntity> farmers = farmerSer.getAllFarmersSorted(officerVillage);
        return ResponseEntity.ok(farmers);
    }
}
