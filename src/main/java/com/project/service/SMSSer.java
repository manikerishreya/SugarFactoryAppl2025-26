package com.project.service;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.entity.SMSEntity;
import com.project.model.EnteringOtpModel;
import com.project.repository.FarmerRegRepo;
import com.project.repository.SMSRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//
@Service
public class SMSSer {
    @Autowired
    private SMSRepo smsrepo;

    @Autowired
    private FarmerRegRepo farmerRegRepo;

    // Save OTP only if farmer is registered
    public boolean saveOtpToDb(String phoneNo, Integer otp) {
        // Use String directly
        FarmerPlotRegisterEntity farmer = farmerRegRepo.findByPhoneNo(phoneNo);
        if(farmer == null){
            return false; // phone number not registered
        }
        SMSEntity ent = new SMSEntity();
        ent.setPhoneNo(phoneNo);
        ent.setOtp(otp);
        smsrepo.save(ent);
        return true;
    }

    public EnteringOtpModel userlogin(String phoneNo, Integer receivedOtp) {
        FarmerPlotRegisterEntity farmer = farmerRegRepo.findByPhoneNo(phoneNo);
        if(farmer == null) return null;

        SMSEntity userdata = smsrepo.findByPhoneNoAndOtp(phoneNo, receivedOtp);
        if (userdata != null) {
            return new EnteringOtpModel(phoneNo, receivedOtp);
        }
        return null;
    }


}
