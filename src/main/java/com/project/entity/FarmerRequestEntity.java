package com.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private String phoneNo;
    private String farmerCode;
    private String plotVillage;
    private String farmerName;
    private Integer landArea;

    private String que1;
    private String que2;

    private String status = "PENDING";// PENDING / APPROVED / REJECTED
}

