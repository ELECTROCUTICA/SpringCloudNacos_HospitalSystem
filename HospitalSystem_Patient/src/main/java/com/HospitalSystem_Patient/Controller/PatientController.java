package com.HospitalSystem_Patient.Controller;

import com.HospitalSystem_Patient.Service.PatientFeignService;
import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import com.HospitalSystem_Pojo.Response.PatientArrangementResponse;
import com.HospitalSystem_Pojo.Response.PatientRecordsResponse;
import com.HospitalSystem_Pojo.Utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("patient")
public class PatientController {


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
    public Map<String, Object> loginHandle(@RequestParam("patient_id") String patient_id, @RequestParam("patient_password") String patient_password) {
        return patientFeignService.loginHandle(patient_id, patient_password);
    }


    @PostMapping("/register/registerHandle")
    public Map<String, Object> registerHandle(@RequestParam("patient_id") String patient_id,
                                              @RequestParam("patient_name") String patient_name,
                                              @RequestParam("patient_sex") String patient_sex,
                                              @RequestParam("patient_birthdate") String patient_birthdate,
                                              @RequestParam("patient_phone") String patient_phone,
                                              @RequestParam("patient_password") String patient_password) {
        return patientFeignService.registerHandle(patient_id, patient_name, patient_sex, patient_birthdate, patient_phone, patient_password);
    }

    @PostMapping("/edit/editHandle")
    public Map<String, Object> editHandle(@RequestParam("patient_id") String patient_id,
                                          @RequestParam("patient_name") String patient_name,
                                          @RequestParam("patient_sex") String patient_sex,
                                          @RequestParam("patient_birthdate") String patient_birthdate,
                                          @RequestParam("patient_phone") String patient_phone,
                                          @RequestParam("patient_password") String patient_password,
                                          HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        return patientFeignService.editHandle(JWTUtils.getPatientFromToken(token).getPatient_id(), patient_name, patient_sex, patient_birthdate, patient_phone, patient_password);
    }


    @GetMapping("/getRecords")
    public Map<String, Object> getRecords(@RequestParam(value = "p", required = false) String p, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return patientFeignService.getRecords(p, JWTUtils.getPatientFromToken(token));
    }


    @PostMapping("/cancelRegistration")
    public Map<String, Object> cancelRegistration(@RequestParam("register_id") Integer register_id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return patientFeignService.cancelRegistration(register_id, JWTUtils.getPatientFromToken(token));
    }


    @GetMapping("/logout")
    public Map<String, Object> logoutInterface(HttpServletRequest request) {
        return Map.of(
                "status", "ok",
                "message", "登出成功"
        );
    }

    @GetMapping( "/getSchedule")
    public Map<String, Object> getSchedule() {
        return patientFeignService.getSchedule();
    }

    @GetMapping("/searchDoctor/search")
    public ArrayList<Doctor> searchDoctor(@RequestParam("keyword") String keyword) {
        return patientFeignService.searchDoctor(keyword);
    }

    @GetMapping("/searchDoctor/getDoctorSchedule")
    ArrayList<DoctorScheduleMap> getDoctorSchedule(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("start_date") String start_date, @RequestParam("end_date")  String end_date) {
        return patientFeignService.getDoctorSchedule(doctor_id, start_date, end_date);
    }

    @GetMapping("/registration/getDoctorsWorkAtDateAndNoon")
    public ArrayList<DoctorScheduleMap> getDoctorsWorkAtDateAndNoon(@RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id, @RequestParam("dep_no") Integer dep_no) {
        return patientFeignService.getDoctorsWorkAtDateAndNoon(date, noon_id, dep_no);
    }

    @GetMapping("/registration/getDescription")
    public String getDoctorDescription(@RequestParam("doctor_id") Integer doctor_id) {
        return patientFeignService.getDoctorDescription(doctor_id);
    }

    //病人提交挂号预约
    @PostMapping("/registration/submit")
    public Map<String, Object> submitRegistration(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return patientFeignService.submitRegistration(doctor_id, date, noon_id, JWTUtils.getPatientFromToken(token));
    }

    @PostMapping("/requestAI")
    public Map<String, Object> requestAI(@RequestParam("message") String message) {
        return patientFeignService.requestAI(message);
    }



}
