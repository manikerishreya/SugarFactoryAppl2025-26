package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnteringOtpModel {
    private String phoneNo;
    private Integer otp;
//    private String role;
}
