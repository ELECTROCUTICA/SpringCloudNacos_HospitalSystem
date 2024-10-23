//package com.HospitalSystem_Patient.Service;
//
//
//import com.HospitalSystem_Pojo.Entity.Patient;
//import com.HospitalSystem_Pojo.Map.DoctorArrangementMap;
//import com.HospitalSystem_Pojo.Response.PatientArrangementResponse;
//import com.HospitalSystem_Pojo.Response.PatientRecordsResponse;
//import com.HospitalSystem_Pojo.Utils.ChatGPTAPI;
//import com.HospitalSystem_Pojo.Utils.JWTUtils;
//import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.service.annotation.GetExchange;
//import org.springframework.web.service.annotation.HttpExchange;
//import org.springframework.web.service.annotation.PostExchange;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@HttpExchange("patient")
//public interface PatientHttpExchangeService {
//
//    @GetExchange("/getServerTime")
//    Map<String, Object> getServerTime();
//
//    @GetExchange("/getRegistrationsToday")
//    Map<String, Object> getRegistrationsToday(@RequestParam("dateParam") String dateParam, @RequestBody Patient patient);
//
//    @PostExchange("/login/loginHandle")
//    Map<String, Object> loginHandle(@RequestParam("id") String id, @RequestParam("password") String password);
//
//
//    @PostExchange("/register/registerHandle")
//    Map<String, Object> registerHandle(@RequestParam("id") String id,
//                                              @RequestParam("name") String name,
//                                              @RequestParam("sex") String sex,
//                                              @RequestParam("birthdate") String birthdate,
//                                              @RequestParam("password") String password);
//
//    @PostExchange("/edit/editHandle")
//    Map<String, Object> editHandle(@RequestParam("id") String id,
//                                          @RequestParam("name") String name,
//                                          @RequestParam("sex") String sex,
//                                          @RequestParam("birthdate") String birthdate,
//                                          @RequestParam("password") String password,
//                                   @RequestBody Patient patient);
//
//
//    @GetExchange("/getRecords")
//    PatientRecordsResponse getRecords(@RequestParam(value = "p", required = false) String p, @RequestBody Patient patient);
//
//
//    @PostExchange("/cancelRegistration")
//    Map<String, Object> cancelRegistration(@RequestParam("id") String id, @RequestBody Patient patient);
//
//    @GetExchange( "/getArrangement")
//    PatientArrangementResponse getArrangement();
//
//    @GetExchange("/registration/getDoctorsWorkAtDate")
//    ArrayList<DoctorArrangementMap> getDoctorsWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date);
//
//    @GetExchange("/registration/getDescription")
//    String getDoctorDescription(@RequestParam("doctor_id") String doctor_id, @RequestParam("date") String date);
//
//
//    @PostExchange("/registration/submit")
//    Map<String, Object> submit(@RequestParam("doctor_id") String doctor_id, @RequestParam("date") String date, @RequestBody Patient patient);
//
//    @PostExchange("/requestAI")
//    String requestAI(@RequestParam("message") String message);
//
//    @GetExchange("/test")
//    String test(@RequestParam("p") String p);
//
//    @GetExchange("/getPatientOffline")
//    Patient getPatientOffline(@RequestParam("id") String id);
//
//}
