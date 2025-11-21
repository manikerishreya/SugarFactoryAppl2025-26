package com.project.service;
import com.project.entity.AdminEntity;
import com.project.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.project.entity.AdminEntity;
import com.project.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminSer {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // 🔑 Important

    // LOGIN (verify admin)
    public String verify(AdminEntity adminEntity) {

        System.out.println("Received username: " + adminEntity.getUsername());
        System.out.println("Received password: " + adminEntity.getPassword());

        Optional<AdminEntity> adminOpt = adminRepo.findByUsername(adminEntity.getUsername());

        if (!adminOpt.isPresent()) {
            System.out.println("Admin not found in DB");
            return null;
        }

        AdminEntity dbAdmin = adminOpt.get();

        System.out.println("DB username: " + dbAdmin.getUsername());
        System.out.println("DB password: " + dbAdmin.getPassword());

        // 🔥 bcrypt password comparison
        boolean matches = passwordEncoder.matches(
                adminEntity.getPassword(),   // plaintext entered
                dbAdmin.getPassword()        // bcrypt hash from DB
        );

        if (!matches) {
            System.out.println("Password mismatch (bcrypt)!");
            return null;
        }

        System.out.println("Password correct! Generating token...");
        return jwtService.generateToken(dbAdmin.getUsername(), dbAdmin.getRole());
    }

    // Fetch Admin by username
    public AdminEntity getAdminByUsername(String username) {
        return adminRepo.findByUsername(username).orElse(null);
    }
}
