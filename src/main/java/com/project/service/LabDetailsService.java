package com.project.service;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.entity.LabEntity;
import com.project.model.FarmerPrincipal;
import com.project.model.LabPrincipal;
import com.project.repository.FarmerRegRepo;
import com.project.repository.LabRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LabDetailsService implements UserDetailsService {
    @Autowired
    private LabRepo labRepo;

    @Override
    public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {
        LabEntity labEntity = labRepo.findByContactNumber(phoneNo);

        if (labEntity == null) {
            System.out.println("Lab not found");
            throw new UsernameNotFoundException("Lab not found");
        }
        return new LabPrincipal(labEntity); // wrap and return
    }
}
