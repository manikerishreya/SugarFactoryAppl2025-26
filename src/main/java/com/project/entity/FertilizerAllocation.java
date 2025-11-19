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
public class FertilizerAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fertilizerAllocationId;
    private String phoneNo;
    private String farmerCode;
    private String plotVillage;
    private String farmerName;
    private Integer landArea;
    private double fertilizerAllocated; // kg
    private double totalCost; // Rs
//    private LocalDateTime allocatedAt = LocalDateTime.now();
}
