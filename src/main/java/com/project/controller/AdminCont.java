package com.project.controller;
import com.project.entity.AdminEntity;
import com.project.entity.FarmerPlotRegisterEntity;
import com.project.entity.VillageEntity;
import com.project.repository.FarmerRegRepo;
import com.project.repository.VillageRepo;
import com.project.service.AdminSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AdminCont {
    @Autowired
    private AdminSer adminSer;
    @Autowired
    private VillageRepo villageRepo;
    @Autowired
    private FarmerRegRepo farmerRegRepo;
//    @PostMapping("/adminlogin")
//    @CrossOrigin(origins = "http://localhost:5173")
//    public String adminlogin(@RequestBody AdminEntity adminEntity) {
//        return adminSer.verify(adminEntity);
//    }


//    @PostMapping("/adminlogin")
//    @CrossOrigin(origins = "http://localhost:5173")
//    public ResponseEntity<?> adminlogin(@RequestBody AdminEntity adminEntity) {
//        String token = adminSer.verify(adminEntity);
//        // Failed login → return HTTP 401
//        if (token == null) {
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body("Invalid username or password");
//        }
//        // Successful → return token
//        return ResponseEntity.ok(token);
//    }

    @PostMapping("/adminlogin")
    public ResponseEntity<?> adminlogin(@RequestBody AdminEntity adminEntity) {
        String token = adminSer.verify(adminEntity);

        if (token == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", "ADMIN");
        response.put("username", adminEntity.getUsername());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/villages")
    public ResponseEntity<List<VillageEntity>> getAllVillages() {
        return ResponseEntity.ok(villageRepo.findAll());
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/farmers/by-village/{villageName}")
    public ResponseEntity<List<FarmerPlotRegisterEntity>> getFarmersByVillage(@PathVariable String villageName) {
        List<FarmerPlotRegisterEntity> farmers = farmerRegRepo.findByPlotVillageOrderByCuttingDateAsc(villageName);
        return ResponseEntity.ok(farmers);
    }



}
