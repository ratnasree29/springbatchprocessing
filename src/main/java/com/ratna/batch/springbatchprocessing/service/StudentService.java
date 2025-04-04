//package com.ratna.batch.springbatchprocessing.service;
//
//import com.ratna.batch.springbatchprocessing.model.StudentResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class StudentService {
//
//    List<StudentResponse> list = null;
//
//    public List<StudentResponse> restCallToGetStudents() {
//        RestTemplate restTemplate = new RestTemplate();
//        StudentResponse[] studentResponseArray =
//                restTemplate.getForObject("http://localhost:1011/api/v1/students",
//                        StudentResponse[].class);
//
//        List<StudentResponse> list = new ArrayList<>();
//
//        for(StudentResponse sr : studentResponseArray) {
//            list.add(sr);
//        }
//
//        return list;
//    }
//
//    public StudentResponse getStudent() {
//       if (list == null || list.isEmpty()) {
//            restCallToGetStudents();
//        } else {
//            StudentResponse studentResponse = list.get(0);
//            return list.remove(0);
//        }
//       return null;
//    }
//}
