package com.project.service;

import com.project.entity.OfficerEntity;
import com.project.model.OfficerPrinciple;
import com.project.repository.OfficerRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OfficerDetailsService implements UserDetailsService {
    @Autowired
    private OfficerRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<OfficerEntity> officerEntity=repo.findByUsername(username);
        if(officerEntity==null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new OfficerPrinciple(officerEntity);  //wrapping  //This wraps your DB user (Users) into a UserDetails object that Spring can work with.
    }
}
