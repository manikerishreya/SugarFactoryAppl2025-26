package com.project.controller;

import com.project.entity.FertilizerAllocation;
import com.project.repository.FarmerRequestRepo;
import com.project.service.FertilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class FertilizerCont {

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private FarmerRequestRepo requestRepo;

    @PostMapping("/allocate/{phoneNo}")
    public ResponseEntity<?> allocate(@PathVariable String phoneNo) {

        FertilizerAllocation saved = fertilizerService.allocateFertilizer(phoneNo);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/requests/pending")
    public ResponseEntity<?> getPendingRequests() {
        return ResponseEntity.ok(requestRepo.findByStatus("PENDING"));
    }

}
