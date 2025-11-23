package com.project.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "farmer_plot_register_entity")
public class FarmerPlotRegisterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer srNo;
    private String farmerCode;
    @Temporal(TemporalType.DATE)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date regDate;
//    private String farmerVillage;
     private String plotVillage;
    private String plotVillageCode;
    private String guttNo;
   private String farmerFName;
   private String farmerLName;
   private String fatherName;
    private String address;
   private String aadharNo;
   //temporal is annotation to take date as required datatype
    @Temporal(TemporalType.DATE)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dob;
    private String phoneNo;
    private Integer landArea;
    private String branchName;
    private Long accNo;
    private String panNo;
    private String caneVariety;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date plantingDate;
//    private String plantingSeason;
    private String irrigationType;
    private String cropType;
    private Integer spacing;
    private String sourceOfWater;
    private String plantingMethod;
    private String riverBelt;
    private String needFertilizer;
//    private String soilType;
//    private String interCrop;
@Temporal(TemporalType.DATE)
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
private Date cuttingDate;

}