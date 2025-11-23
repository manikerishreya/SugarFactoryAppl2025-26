//package com.project.service;
//
//import com.project.entity.FarmerPlotRegisterEntity;
//import com.project.entity.SMSEntity;
//import com.project.model.EnteringOtpModel;
//import com.project.repository.FarmerRegRepo;
//import com.project.repository.SMSRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
////
//@Service
//public class SMSSer {
//    @Autowired
//    private SMSRepo smsrepo;
//
//    @Autowired
//    private FarmerRegRepo farmerRegRepo;
//
//    // Save OTP only if farmer is registered
//    public boolean saveOtpToDb(String phoneNo, Integer otp) {
//        // Use String directly
//        FarmerPlotRegisterEntity farmer = farmerRegRepo.findByPhoneNo(phoneNo);
//        if(farmer == null){
//            return false; // phone number not registered
//        }
//        SMSEntity ent = new SMSEntity();
//        ent.setPhoneNo(phoneNo);
//        ent.setOtp(otp);
//        smsrepo.save(ent);
//        return true;
//    }
//
//    public EnteringOtpModel userlogin(String phoneNo, Integer receivedOtp) {
//        FarmerPlotRegisterEntity farmer = farmerRegRepo.findByPhoneNo(phoneNo);
//        if(farmer == null) return null;
//
//        SMSEntity userdata = smsrepo.findByPhoneNoAndOtp(phoneNo, receivedOtp);
//        if (userdata != null) {
//            return new EnteringOtpModel(phoneNo, receivedOtp);
//        }
//        return null;
//    }
//}

package com.project.service;

import com.project.entity.FarmerPlotRegisterEntity;
import com.project.entity.LabEntity;
import com.project.entity.SMSEntity;
import com.project.model.EnteringOtpModel;
import com.project.repository.FarmerRegRepo;
import com.project.repository.LabRepo;
import com.project.repository.SMSRepo;
import com.project.repository.SMSRepo; // Make sure you have this repo for SMSEntity
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


@Service
public class SMSSer {

    @Autowired
    private SMSRepo smsRepo;

    @Autowired
    private LabRepo labRepo;

    @Autowired
    private FarmerRegRepo farmerRepo;
    /**
     * Save or update OTP for a phone number.
     * @param phoneNo Phone number
     * @param otp Generated OTP
     * @return true if saved/updated successfully
     */
    @Transactional
    public boolean saveOtpToDb(String phoneNo, Integer otp) {
        SMSEntity entity = smsRepo.findByPhoneNo(phoneNo);
        if (entity != null) {
            entity.setOtp(otp); // update existing OTP
        } else {
            entity = new SMSEntity();
            entity.setPhoneNo(phoneNo);
            entity.setOtp(otp);
        }
        smsRepo.save(entity);
        return true;
    }
    /**
     * Verify OTP for login.
     * @param phoneNo Phone number
     * @param receivedOtp OTP received by user
     * @return EnteringOtpModel if OTP matches, else null
     */
    public EnteringOtpModel userlogin(String phoneNo, Integer receivedOtp) {
        SMSEntity entity = smsRepo.findByPhoneNo(phoneNo);
        if (entity != null && entity.getOtp().equals(receivedOtp)) {
            return new EnteringOtpModel(phoneNo,receivedOtp);
        }
        return null;
    }

//    public EnteringOtpModel userlogin(String phoneNo, Integer otp) {
//
//        // 1️⃣ Check Lab login first
//        LabEntity lab = labRepo.findByContactNumber(phoneNo);
//        if (lab != null) {
//            EnteringOtpModel model = new EnteringOtpModel();
//            model.setPhoneNo(lab.getContactNumber();
//            model.setRole("LAB");
//            return model;
//        }
//
//        // 2️⃣ Check Farmer login
//        FarmerPlotRegisterEntity farmer = farmerRepo.findByPhoneNoAndOtp(phoneNo);
//        if (farmer != null) {
//            EnteringOtpModel model = new EnteringOtpModel();
//            model.setPhoneNo(farmer.getPhoneNo());
//            model.setRole("FARMER");
//            return model;
//        }
//
//        return null;
//    }

    /**
     * Generate a random 6-digit OTP.
     * @return OTP
     */
    public Integer generateOtp() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
