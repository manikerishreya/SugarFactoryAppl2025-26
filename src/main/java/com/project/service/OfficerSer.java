package com.project.service;

import com.project.entity.OfficerEntity;
import com.project.repository.FarmerRegRepo;
import com.project.repository.OfficerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OfficerSer {

    @Autowired
    private OfficerRepo officerRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String verify(OfficerEntity officerEntity) {

        System.out.println("Received username: " + officerEntity.getUsername());
        System.out.println("Received password: " + officerEntity.getPassword());

        Optional<OfficerEntity> off = officerRepo.findByUsername(officerEntity.getUsername());

        if (!off.isPresent()) {
            System.out.println("User not found in DB");
            return null;
        }

        OfficerEntity db = off.get();

        System.out.println("DB username: " + db.getUsername());
        System.out.println("DB password: " + db.getPassword());

        boolean matches = passwordEncoder.matches(
                officerEntity.getPassword(),   // plaintext entered
                db.getPassword()               // bcrypt hash from DB
        );

        if (!matches) {
            System.out.println("Password mismatch (bcrypt)!");
            return null;
        }

        System.out.println("Password correct! Generating token...");
        return jwtService.generateToken(db.getUsername(), db.getRole());
    }

    public OfficerEntity getOfficerByUsername(String username) {
        return officerRepo.findByUsername(username).orElse(null);
    }
    public String getLoggedInOfficerVillage() {
        // Example: get logged-in officer by username from Spring Security context
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        OfficerEntity officer = officerRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));

        return officer.getVillage(); // assuming OfficerEntity has a 'village' field
    }

}
