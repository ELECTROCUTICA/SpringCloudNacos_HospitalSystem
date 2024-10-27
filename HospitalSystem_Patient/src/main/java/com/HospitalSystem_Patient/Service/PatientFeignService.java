package com.HospitalSystem_Patient.Service;


import com.HospitalSystem_Pojo.Entity.Patient;
import com.HospitalSystem_Pojo.Map.DoctorArrangementMap;
import com.HospitalSystem_Pojo.Response.PatientArrangementResponse;
import com.HospitalSystem_Pojo.Response.PatientRecordsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;


@FeignClient("HospitalSystem-Interface")
public interface PatientFeignService {

    String PREFIX = "/patient";

    @GetMapping(PREFIX + "/getServerTime")
    Map<String, Object> getServerTime();

    @GetMapping(PREFIX + "/getRegistrationsToday")
    Map<String, Object> getRegistrationsToday(@RequestParam("dateParam") String dateParam, @SpringQueryMap Patient patient);

    @PostMapping(PREFIX + "/login/loginHandle")
    Map<String, Object> loginHandle(@RequestParam("id") String id, @RequestParam("password") String password);


    @PostMapping(PREFIX + "/register/registerHandle")
    Map<String, Object> registerHandle(@RequestParam("id") String id,
                                       @RequestParam("name") String name,
                                       @RequestParam("sex") String sex,
                                       @RequestParam("birthdate") String birthdate,
                                       @RequestParam("password") String password);

    @PostMapping(PREFIX + "/edit/editHandle")
    Map<String, Object> editHandle(@RequestParam("id") String id,
                                   @RequestParam("name") String name,
                                   @RequestParam("sex") String sex,
                                   @RequestParam("birthdate") String birthdate,
                                   @RequestParam("password") String password,
                                   @SpringQueryMap Patient patient);


    @GetMapping(PREFIX + "/getRecords")
    PatientRecordsResponse getRecords(@RequestParam(value = "p", required = false) String p, @SpringQueryMap Patient patient);


    @PostMapping(PREFIX + "/cancelRegistration")
    Map<String, Object> cancelRegistration(@RequestParam("reg_id") String reg_id, @SpringQueryMap Patient patient);

    @GetMapping( PREFIX + "/getArrangement")
    PatientArrangementResponse getArrangement();

    @GetMapping(PREFIX + "/registration/getDoctorsWorkAtDate")
    ArrayList<DoctorArrangementMap> getDoctorsWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date);

    @GetMapping(PREFIX + "/registration/getDescription")
    String getDoctorDescription(@RequestParam("doctor_id") String doctor_id, @RequestParam("date") String date);


    @PostMapping(PREFIX + "/registration/submit")
    Map<String, Object> submit(@RequestParam("doctor_id") String doctor_id, @RequestParam("date") String date, @SpringQueryMap Patient patient);

    @PostMapping(PREFIX + "/requestAI")
    String requestAI(@RequestParam("message") String message);

    @GetMapping(PREFIX + "/test")
    String test(@RequestParam("p") String p);

    @GetMapping(PREFIX + "/getPatientOffline")
    Patient getPatientOffline(@RequestParam("id") String id);

}
