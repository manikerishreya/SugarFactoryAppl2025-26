package com.project.service;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.model.FarmerPrincipal;
import com.project.repository.FarmerRegRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FarmerDetailsService implements UserDetailsService {

    @Autowired
    private FarmerRegRepo farmerRegRepo;

    @Override
    public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {

        FarmerPlotRegisterEntity farmerEntity = farmerRegRepo.findByPhoneNo(phoneNo);

        if (farmerEntity == null) {
            System.out.println("Farmer not found");
            throw new UsernameNotFoundException("Farmer not found");
        }

        return new FarmerPrincipal(farmerEntity); // wrap and return
    }
}
