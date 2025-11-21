package com.project.service;

import com.project.entity.AdminEntity;
import com.project.model.AdminPrincipal;
import com.project.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AdminEntity> adminEntity = repo.findByUsername(username);

        if (!adminEntity.isPresent()) {   // ✅ Correct null-check
            System.out.println("Admin not found");
            throw new UsernameNotFoundException("Admin not found");
        }

        return new AdminPrincipal(adminEntity); // Wrap DB user into UserDetails
    }
}
