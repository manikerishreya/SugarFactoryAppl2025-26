package com.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "labs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long labId;

    private String labName; // Soil Testing Lab
    private String labAssistantName; // Person doing the testing
    private String contactNumber; // Lab assistant contact
    private Integer otp;
    private boolean verified = false;
    private boolean active = true;  // Handled by the Admin

}