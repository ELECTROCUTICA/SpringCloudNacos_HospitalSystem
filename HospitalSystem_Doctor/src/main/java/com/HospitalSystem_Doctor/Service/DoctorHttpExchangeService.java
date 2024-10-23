//package com.HospitalSystem_Doctor.Service;
//
//
//import com.HospitalSystem_Pojo.Entity.Doctor;
//import com.HospitalSystem_Pojo.Map.RegistrationMap;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.service.annotation.GetExchange;
//import org.springframework.web.service.annotation.HttpExchange;
//import org.springframework.web.service.annotation.PostExchange;
//
//import java.util.Map;
//
//@Component
//@HttpExchange("doctor")
//public interface DoctorHttpExchangeService {
//
//    @PostExchange("/login/loginHandle")
//    Map<String, Object> doctorLoginHandle(@RequestParam("id") String id, @RequestParam("password") String password);
//
//    @GetExchange("/getPatientsList")
//    Map<Integer, RegistrationMap> getPatientsList(@RequestBody Doctor doctor);
//
//    @PostExchange("/changingStatus")
//    Map<String, Object> changingStatus(@RequestParam("id") String id, @RequestParam("status") int status);
//}




//保留方案：使用HttpExchange来远程调用服务