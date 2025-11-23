package com.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VillageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String villageName;
    private String villageCode;

//    // Each village has exactly one officer assigned
    @ManyToOne
    @JoinColumn(name = "officer_id")  // foreign key column
    private OfficerEntity officer;
}
