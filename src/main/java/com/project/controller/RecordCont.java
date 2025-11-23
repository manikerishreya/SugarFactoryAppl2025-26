//package com.project.controller;
//
//import com.project.entity.FarmerPlotRegisterEntity;
//import com.project.service.RecordSer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/records")
//@CrossOrigin(origins = "http://localhost:5173")
//public class RecordCont {
//    @Autowired
//    private RecordSer recordSer;
//    @GetMapping("/sorted")
//    public List<FarmerPlotRegisterEntity> getSortedRecords( ) {
//        return recordSer.getAllRecordsSorted();
//    }
//}
//
