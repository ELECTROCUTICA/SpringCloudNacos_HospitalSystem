package com.HospitalSystem_Interface.Controller;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;
import com.HospitalSystem_Pojo.Utils.*;
import com.HospitalSystem_Pojo.JSON.*;
import com.HospitalSystem_Interface.Service.PatientService;
import com.HospitalSystem_Pojo.Entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("patient")
public class PatientInterfaceController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/getServerTime")
    public Map<String, Object> getServerTime() {
        return patientService.getServerTime();
    }

    @GetMapping("/getRegistrationsToday")
    public Map<String, Object> getRegistrationsToday(@RequestParam("dateParam") String dateParam, @SpringQueryMap Patient patient) {
        return patientService.getRegistrationsToday(dateParam, patient);
    }

    @PostMapping("/login/loginHandle")
    public Map<String, Object> loginHandle(@RequestParam("id") String id, @RequestParam("password") String password) {
        return patientService.loginHandle(id, password);
    }


    @PostMapping("/register/registerHandle")
    public Map<String, Object> registerHandle(@RequestParam("id") String id,
                                              @RequestParam("name") String name,
                                              @RequestParam("sex") String sex,
                                              @RequestParam("birthdate") String birthdate,
                                              @RequestParam("password") String password) {
        return patientService.registerHandle(id, name ,sex, birthdate, password);
    }

    @PostMapping("/edit/editHandle")
    public Map<String, Object> editHandle(@RequestParam("id") String id,
                                          @RequestParam("name") String name,
                                          @RequestParam("sex") String sex,
                                          @RequestParam("birthdate") String birthdate,
                                          @RequestParam("password") String password,
                                          @SpringQueryMap Patient patient) {
        return patientService.editHandle(id, name, sex, birthdate, password, patient);
    }


    @GetMapping("/getRecords")
    public PatientRecordsResponse getRecords(@RequestParam(value = "p", required = false) String p, @SpringQueryMap Patient patient) {
        return patientService.getPatientRecords(p, patient);
    }


    @PostMapping("/cancelRegistration")
    public Map<String, Object> cancelRegistration(@RequestParam("id") String id, @SpringQueryMap Patient patient) {
        return patientService.cancelRegistration(id, patient);
    }


    @GetMapping( "/getArrangement")
    public PatientArrangementResponse getArrangement() {
        return patientService.getArrangement();
    }

    @GetMapping("/registration/getDoctorsWorkAtDate")
    public ArrayList<DoctorArrangementMap> getDoctorsWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date) {
        return patientService.getDoctorsWorkAtDate(dep_no, date);
    }

    @GetMapping("/registration/getDescription")
    public String getDoctorDescription(@RequestParam("doctor_id") String doctor_id, @RequestParam("date") String date) {
        return patientService.getDoctorDescription(doctor_id, date);
    }

    //病人提交挂号预约
    @PostMapping("/registration/submit")
    public Map<String, Object> submit(@RequestParam("doctor_id") String doctor_id, @RequestParam("date") String date, @SpringQueryMap Patient patient) {
        return patientService.registrationSubmit(doctor_id, date, patient);
    }

    @PostMapping("/requestAI")
    public String requestAI(@RequestParam("message") String message) {
        return ChatGPTAPI.sendRequestToChatGPT(message);
    }

    @GetMapping("/getStr")
    public String getStr() {
        return "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
    }

    @GetMapping("/getPatientOffline")
    public Patient getPatientOffline(@RequestParam("id") String id) {
        return patientService.getPatientOffline(id);
    }
}
