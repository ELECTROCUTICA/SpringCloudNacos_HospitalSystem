package com.HospitalSystem_Patient.Controller;

import com.HospitalSystem_Patient.Service.PatientFeignService;
import com.HospitalSystem_Pojo.Entity.Patient;
import com.HospitalSystem_Pojo.Map.DoctorArrangementMap;
import com.HospitalSystem_Pojo.Response.PatientArrangementResponse;
import com.HospitalSystem_Pojo.Response.PatientRecordsResponse;
import com.HospitalSystem_Pojo.Utils.ChatGPTAPI;
import com.HospitalSystem_Pojo.Utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("patient")
public class PatientController {

//
//    @Autowired
//    private PatientHttpExchangeService patientHttpExchangeService;

    @Autowired
    private PatientFeignService patientFeignService;


    @GetMapping("/getServerTime")
    public Map<String, Object> getServerTime() {
        return patientFeignService.getServerTime();
    }

    @GetMapping("/getRegistrationsToday")
    public Map<String, Object> getRegistrationsToday(@RequestParam("dateParam") String dateParam, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return patientFeignService.getRegistrationsToday(dateParam, JWTUtils.getPatientFromToken(token));
    }

    @GetMapping("/getPatient")
    public Patient getPatient(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return JWTUtils.getPatientFromToken(token);
    }

    @PostMapping("/login/loginHandle")
    public Map<String, Object> loginHandle(@RequestParam("id") String id, @RequestParam("password") String password) {
        return patientFeignService.loginHandle(id, password);
    }


    @PostMapping("/register/registerHandle")
    public Map<String, Object> registerHandle(@RequestParam("id") String id,
                                              @RequestParam("name") String name,
                                              @RequestParam("sex") String sex,
                                              @RequestParam("birthdate") String birthdate,
                                              @RequestParam("password") String password) {
        return patientFeignService.registerHandle(id, name ,sex, birthdate, password);
    }

    @PostMapping("/edit/editHandle")
    public Map<String, Object> editHandle(@RequestParam("id") String id,
                                          @RequestParam("name") String name,
                                          @RequestParam("sex") String sex,
                                          @RequestParam("birthdate") String birthdate,
                                          @RequestParam("password") String password,
                                          HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        return patientFeignService.editHandle(id, name, sex, birthdate, password, JWTUtils.getPatientFromToken(token));
    }


    @GetMapping("/getRecords")
    public PatientRecordsResponse getRecords(@RequestParam(value = "p", required = false) String p, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return patientFeignService.getRecords(p, JWTUtils.getPatientFromToken(token));
    }


    @PostMapping("/cancelRegistration")
    public Map<String, Object> cancelRegistration(@RequestParam("id") String id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return patientFeignService.cancelRegistration(id, JWTUtils.getPatientFromToken(token));
    }


    @GetMapping("/logout")
    public Map<String, Object> logoutInterface(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("state", "ok");
        map.put("message", "登出完成");
        return map;
    }

    @GetMapping( "/getArrangement")
    public PatientArrangementResponse getArrangement() {
        return patientFeignService.getArrangement();
    }

    @GetMapping("/registration/getDoctorsWorkAtDate")
    public ArrayList<DoctorArrangementMap> getDoctorsWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date) {
        return patientFeignService.getDoctorsWorkAtDate(dep_no, date);
    }

    @GetMapping("/registration/getDescription")
    public String getDoctorDescription(@RequestParam("doctor_id") String doctor_id, @RequestParam("date") String date) {
        return patientFeignService.getDoctorDescription(doctor_id, date);
    }

    //病人提交挂号预约
    @PostMapping("/registration/submit")
    public Map<String, Object> submit(@RequestParam("doctor_id") String doctor_id, @RequestParam("date") String date, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return patientFeignService.submit(doctor_id, date, JWTUtils.getPatientFromToken(token));
    }

    @PostMapping("/requestAI")
    public String requestAI(@RequestParam("message") String message) {
        return patientFeignService.requestAI(message);
    }

    @GetMapping("/test")
    public String test(@RequestParam("p") String p) {
        return p;
    }

    @GetMapping("/getPatientOffline")
    Patient getPatientOffline(@RequestParam("id") String id) {
        return patientFeignService.getPatientOffline(id);
    }


}
