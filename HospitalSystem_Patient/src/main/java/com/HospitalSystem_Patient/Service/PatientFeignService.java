package com.HospitalSystem_Patient.Service;


import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Entity.Patient;
import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import com.HospitalSystem_Pojo.Response.PatientArrangementResponse;
import com.HospitalSystem_Pojo.Response.PatientRecordsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.lang.Nullable;
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
    Map<String, Object> loginHandle(@RequestParam("patient_id") String patient_id, @RequestParam("patient_password") String patient_password);


    @PostMapping(PREFIX + "/register/registerHandle")
    Map<String, Object> registerHandle(@RequestParam("patient_id") String patient_id,
                                       @RequestParam("patient_name") String patient_name,
                                       @RequestParam("patient_sex") String patient_sex,
                                       @RequestParam("patient_birthdate") String patient_birthdate,
                                       @RequestParam("patient_phone") String patient_phone,
                                       @RequestParam("patient_password") String patient_password);

    @PostMapping(PREFIX + "/edit/editHandle")
    Map<String, Object> editHandle(@RequestParam("patient_id") String patient_id,
                                   @RequestParam("patient_name") String patient_name,
                                   @RequestParam("patient_sex") String patient_sex,
                                   @RequestParam("patient_birthdate") String patient_birthdate,
                                   @RequestParam("patient_phone") String patient_phone,
                                   @RequestParam("patient_password") String patient_password);


    @GetMapping(PREFIX + "/getRecords")
    Map<String, Object>  getRecords(@RequestParam(value = "p", required = false) String p, @SpringQueryMap Patient patient);


    @PostMapping(PREFIX + "/cancelRegistration")
    Map<String, Object> cancelRegistration(@RequestParam("register_id") Integer register_id, @SpringQueryMap Patient patient);

    @GetMapping( PREFIX + "/getSchedule")
    Map<String, Object> getSchedule();

    @GetMapping(PREFIX + "/searchDoctor/search")
    ArrayList<Doctor> searchDoctor(@RequestParam("keyword") String keyword);

    @GetMapping(PREFIX + "/searchDoctor/getDoctorSchedule")
    ArrayList<DoctorScheduleMap> getDoctorSchedule(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("start_date") String start_date, @RequestParam("end_date")  String end_date);

    @GetMapping(PREFIX + "/registration/getDoctorsWorkAtDateAndNoon")
    ArrayList<DoctorScheduleMap> getDoctorsWorkAtDateAndNoon(@RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id, @RequestParam("dep_no") Integer dep_no);

    @GetMapping(PREFIX + "/registration/getDescription")
    String getDoctorDescription(@RequestParam("doctor_id") Integer doctor_id);


    @PostMapping(PREFIX + "/registration/submit")
    Map<String, Object> submitRegistration(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id, @SpringQueryMap Patient patient);

    @PostMapping(PREFIX + "/requestAI")
    Map<String, Object> requestAI(@RequestParam("message") String message);


}
