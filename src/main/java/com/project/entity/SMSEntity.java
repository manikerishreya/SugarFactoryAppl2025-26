package com.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class SMSEntity {
    //for farmer login
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sn;
    private String phoneNo;
    private Integer otp;
}