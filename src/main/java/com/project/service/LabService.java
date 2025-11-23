package com.project.service;

import java.util.List;
import java.util.Random;

import com.project.entity.OfficerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.entity.LabEntity;
import com.project.repository.LabRepo;

@Service
public class LabService {
    @Autowired
    private LabRepo labRepository;
    @Autowired
    private SMSSer smsService;
    // Add new lab
    public LabEntity addLab(LabEntity lab) {
        // Normalize phone
        String phone = lab.getContactNumber()
                .replace("+91", "")
                .replace(" ", "")
                .trim();
        lab.setContactNumber(phone);

        // Save lab first
        LabEntity savedLab = labRepository.save(lab);

//        // Now generate OTP
//        int otp = new Random().nextInt(9000) + 1000;
//        savedLab.setOtp(otp);
//        labRepository.save(savedLab);
//
//        // Send OTP
//        smsService.sendSms("+91" + phone, "Your Lab Login OTP is: " + otp);

        return savedLab;
    }

    // Update lab details
    public LabEntity updateLab(Long labId, LabEntity updatedLab) {
        LabEntity lab = labRepository.findById(labId)
                .orElseThrow(() -> new RuntimeException("Lab not found"));

        lab.setLabName(updatedLab.getLabName());
        lab.setLabAssistantName(updatedLab.getLabAssistantName());

        String phone = updatedLab.getContactNumber()
                .replace("+91", "")
                .replace(" ", "")
                .trim();
        lab.setContactNumber(phone);

        return labRepository.save(lab);
    }

    // Activate Lab
    public LabEntity activateLab(Long labId) {
        LabEntity lab = labRepository.findById(labId)
                .orElseThrow(() -> new RuntimeException("Lab not found"));

        lab.setActive(true);
        return labRepository.save(lab);
    }

    // Deactivate Lab
    public LabEntity deactivateLab(Long labId) {
        LabEntity lab = labRepository.findById(labId)
                .orElseThrow(() -> new RuntimeException("Lab not found"));

        lab.setActive(false);
        return labRepository.save(lab);
    }

    // Get all labs
    public List<LabEntity> getAllLabs() {
        return labRepository.findAll();
    }

    // Get single lab
    public LabEntity getLabById(Long labId) {
        return labRepository.findById(labId)
                .orElseThrow(() -> new RuntimeException("Lab not found"));
    }

    // Delete lab
    public void deleteLab(Long labId) {
        labRepository.deleteById(labId);
    }
    public LabEntity getLabByContact(String contactNumber) {
        return labRepository.findByContactNumber(contactNumber);
    }

    // -------------------------------
    // OTP LOGIN LOGIC
    // -------------------------------

//    // Send OTP
//    public boolean sendOtpToLab(String phoneNo) {
//        phoneNo = phoneNo.replace("+91", "").replace(" ", "").trim();
//        LabEntity lab = labRepository.findByContactNumber(phoneNo);
//        if (lab == null) {
//            return false;
//        }
//        int otp = new Random().nextInt(9000) + 1000;
//        lab.setOtp(otp);
//        labRepository.save(lab);
//        smsService.sendSms("+91" + phoneNo, "Your Lab Login OTP is: " + otp);
//        return true;
//    }
//    // Verify OTP
//    public LabEntity verifyOtp(String phoneNo, Integer otp) {
//        phoneNo = phoneNo.replace("+91", "").replace(" ", "").trim();
//        LabEntity lab = labRepository.findByContactNumber(phoneNo);
//        if (lab == null) return null;
//        if (lab.getOtp() != null && lab.getOtp().equals(otp)) {
//            lab.setOtp(null);
//            lab.setVerified(true);
//            labRepository.save(lab);
//            return lab;
//        }
//        return null;
//    }
}