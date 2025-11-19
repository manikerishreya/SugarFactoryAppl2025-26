package com.project.repository;

import com.project.entity.SMSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
//public interface SMSRepo extends JpaRepository<SMSEntity,Integer> {
//
////    findByUnameAndPassword(String uname,String password);
//    SMSEntity findByOtp(Integer receivedotp);
//
//
//}
@Repository
public interface SMSRepo extends JpaRepository<SMSEntity, Integer> {
    SMSEntity findByPhoneNoAndOtp(String phoneNo, Integer otp);
//    SMSEntity findByPhoneNo(String phoneNo);
}

