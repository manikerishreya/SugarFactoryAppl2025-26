
//@RestController
//public class SMSCont {
//
//    @Autowired
//    private SMSSer smsSer;
//
//    public static final String ACCOUNT_SID = "";
//    public static final String AUTH_TOKEN = "";
//    public static final String from_number = "";
//    public String to_number = "";
//    public Integer saveotp;
//
//    static {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//    }
//
//    @PostMapping("/generateotp")
//    public String getOpt(@ModelAttribute SMSModel smsmodel) {
//        String to_number = smsmodel.getPhone_no();
//        System.out.println(to_number);
//        Random random = new Random();
//        Integer otp = random.nextInt(1000000);
//        // this.saveotp=otp;
//        smsSer.saveotptodb(otp);
//        System.out.println(otp);
//        String ot = String.valueOf(otp);
//        Message message = Message.creator(new PhoneNumber(to_number), new PhoneNumber(from_number), ot).create();
//        System.out.println(message.getBody());
//        return "HomePage";
//    }
//    @PostMapping("/checkvalidotp")
//    public String userlogin(@RequestParam("receivedotp") Integer receivedotp) {
//        System.out.println(receivedotp);
//
//        EnteringOtpModel userdata = smsSer.userlogin(receivedotp);
//        if (userdata != null) {
//
//            return "LoginSuccess";
//        } else
//
//            return "LoginFail";
//    }
//
//    @GetMapping("/otp")
//    public String homePage1() {
//        return "HomePage";
//    }
//
//}
package com.project.controller;

import com.project.model.EnteringOtpModel;
import com.project.model.SMSModel;
import com.project.repository.FarmerRegRepo;
import com.project.service.FarmerSer;
import com.project.service.JWTService;
import com.project.service.SMSSer;
import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.twilio.exception.ApiException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SMSCont {

    @Autowired
    private SMSSer smsSer;
    @Autowired
 private FarmerRegRepo farmerRegRepo;
    @Autowired
    private FarmerSer farmerSer;
    @Autowired
    private JWTService jwtService;
    // Use environment variables instead of hardcoding in production
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";;
    public static final String FROM_NUMBER = "";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @PostMapping("/generateotp")
    @CrossOrigin(origins = "http://localhost:5173")
    public String getOtp(@RequestBody SMSModel smsModel) {
        String toNumber = smsModel.getPhoneNo();
        if (!toNumber.startsWith("+91")) {
            toNumber = "+91" + toNumber;
        }

        // Generate a 6-digit OTP
        Random random = new Random();
        Integer otp = 100000 + random.nextInt(900000);

        boolean isRegistered = smsSer.saveOtpToDb(toNumber, otp);
        if(!isRegistered){
            return "Phone number not registered. Please register first.";
        }

        try {
            Message message = Message.creator(
                    new PhoneNumber(toNumber),
                    new PhoneNumber(FROM_NUMBER),
                    "Your OTP is: " + otp
            ).create();
            return "OTP Sent Successfully";
        } catch (ApiException e) {
            e.printStackTrace();
            return "Failed to send OTP: " + e.getMessage();
        }
    }

    @PostMapping("/checkvalidotp")
    public  ResponseEntity<Map<String,String>> userLogin(@RequestBody EnteringOtpModel otpRequest) {
        String phoneNo = otpRequest.getPhoneNo();
        Integer receivedOtp = otpRequest.getOtp();

        EnteringOtpModel userData = smsSer.userlogin(phoneNo, receivedOtp);

        if (userData != null) {
////            String token = jwtService.generateToken(phoneNo);
////            return ResponseEntity.ok(token);
//            return ResponseEntity.ok(jwtService.generateToken(phoneNo,"FARMER"));
            String token = jwtService.generateToken(phoneNo, "FARMER");

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", "FARMER");
            response.put("username", phoneNo);
            return ResponseEntity.ok(response);
    }
//    else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
//        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid OTP"));
        }
    }
    @GetMapping("/otp")
    public String homePage() {
        return "HomePage";
    }
}
