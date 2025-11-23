package com.project.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabModel {

    private Long labId;             // ID of the lab
    private String labName;         // Soil Testing Lab name
    private String labAssistantName;// Responsible person
    private String contactNumber;   // Lab contact
    private boolean active;

}