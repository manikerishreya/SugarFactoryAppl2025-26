package com.project.service;

import com.project.entity.OfficerEntity;
import com.project.repository.FarmerRegRepo;
import com.project.repository.OfficerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class OfficerSer {
    @Autowired
    private OfficerRepo officerRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private FarmerRegRepo farmerRegRepo;

//    @Autowired
//    private OfficerEntity officer;

//    public String verify(OfficerEntity officerEntity) {
//        Authentication authentication =
//                authManager.authenticate(new UsernamePasswordAuthenticationToken(
//                        officerEntity.getUsername(), officerEntity.getPassword()));
//        if (authentication.isAuthenticated()) {
////            return jwtService.generateToken(officerEntity.getUsername(),"OFFICER");
//            return jwtService.generateToken(officerEntity.getUsername(), officerEntity.getRole());
//
//        } else {
//            return "fail";
//        }
//    }
public String verify(OfficerEntity officerEntity) {

    // Authenticate username + password
    Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    officerEntity.getUsername(),
                    officerEntity.getPassword()
            )
    );

    if (authentication.isAuthenticated()) {

        // Fetch officer from DB to get correct role
        OfficerEntity dbOfficer = officerRepo.findByUsername(officerEntity.getUsername())
                .orElseThrow(() -> new RuntimeException("Officer not found in DB"));

        // Generate token with DB role
        return jwtService.generateToken(dbOfficer.getUsername(), dbOfficer.getRole());
    }

    return "fail";
}

    public OfficerEntity getOfficerByUsername(String username) {
        return officerRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found with username: " + username));
    }
}

