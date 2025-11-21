package com.project.controller;
import com.project.entity.AdminEntity;
import com.project.service.AdminSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AdminCont {
    @Autowired
    private AdminSer adminSer;
//    @PostMapping("/adminlogin")
//    @CrossOrigin(origins = "http://localhost:5173")
//    public String adminlogin(@RequestBody AdminEntity adminEntity) {
//        return adminSer.verify(adminEntity);
//    }


    @PostMapping("/adminlogin")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> adminlogin(@RequestBody AdminEntity adminEntity) {

        String token = adminSer.verify(adminEntity);

        // Failed login → return HTTP 401
        if (token == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        // Successful → return token
        return ResponseEntity.ok(token);
    }

}
