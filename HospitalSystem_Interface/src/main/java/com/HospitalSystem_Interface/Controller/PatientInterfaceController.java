package com.HospitalSystem_Interface.Controller;

import com.HospitalSystem_Interface.Mapper.DoctorScheduleMapper;
import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Entity.Patient;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;
import com.HospitalSystem_Pojo.Utils.*;
import com.HospitalSystem_Interface.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("patient")
public class PatientInterfaceController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorScheduleMapper doctorScheduleMapper;

    @GetMapping("/getServerTime")
    public Map<String, Object> getServerTime() {
        return patientService.getServerTime();
    }

    @GetMapping("/getRegistrationsToday")
    public Map<String, Object> getRegistrationsToday(@RequestParam("dateParam") String dateParam, @SpringQueryMap Patient patient) {
        return patientService.getRegistrationsToday(dateParam, patient);
    }

    @PostMapping("/login/loginHandle")
    public Map<String, Object> loginHandle(@RequestParam("patient_id") String patient_id, @RequestParam("patient_password") String patient_password) {
        return patientService.loginHandle(patient_id, patient_password);
    }


    @PostMapping("/register/registerHandle")
    public Map<String, Object> registerHandle(@RequestParam("patient_id") String patient_id,
                                              @RequestParam("patient_name") String patient_name,
                                              @RequestParam("patient_sex") String patient_sex,
                                              @RequestParam("patient_birthdate") String patient_birthdate,
                                              @RequestParam("patient_phone") String patient_phone,
                                              @RequestParam("patient_password") String patient_password) {
        return patientService.registerHandle(patient_id, patient_name, patient_sex, patient_birthdate, patient_phone, patient_password);
    }

    @PostMapping("/edit/editHandle")
    public Map<String, Object> editHandle(@RequestParam("patient_id") String patient_id,
                                          @RequestParam("patient_name") String patient_name,
                                          @RequestParam("patient_sex") String patient_sex,
                                          @RequestParam("patient_birthdate") String patient_birthdate,
                                          @RequestParam("patient_phone") String patient_phone,
                                          @RequestParam("patient_password") String patient_password) {
        return patientService.editHandle(patient_id, patient_name, patient_sex, patient_birthdate, patient_phone, patient_password);
    }


    @GetMapping("/getRecords")
    public Map<String, Object> getRecords(@RequestParam(value = "p", required = false) String p, @SpringQueryMap Patient patient) {
        return patientService.getPatientRecords(p, patient);
    }


    @PostMapping("/cancelRegistration")
    public Map<String, Object> cancelRegistration(@RequestParam("register_id") Integer register_id, @SpringQueryMap Patient patient) {
        return patientService.cancelRegistration(register_id, patient);
    }


    @GetMapping( "/getSchedule")
    public Map<String, Object> getSchedule() {
        return patientService.getSchedule();
    }

    @GetMapping("/searchDoctor/search")
    public ArrayList<Doctor> searchDoctor(@RequestParam("keyword") String keyword) {
        return patientService.searchDoctor(keyword);
    }

    @GetMapping("/searchDoctor/getDoctorSchedule")
    public ArrayList<DoctorScheduleMap> getDoctorSchedule(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("start_date") String start_date, @RequestParam("end_date")  String end_date) {
        return patientService.getDoctorScheduleMapInWeek(doctor_id, start_date, end_date);
    }

    @GetMapping("/registration/getDoctorsWorkAtDateAndNoon")
    public ArrayList<DoctorScheduleMap> getDoctorsWorkAtDateAndNoon(@RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id, @RequestParam("dep_no") Integer dep_no) {
        return patientService.getDoctorsWorkAtDateAndNoon(date, noon_id, dep_no);
    }

    @GetMapping("/registration/getDescription")
    public String getDoctorDescription(@RequestParam("doctor_id") Integer doctor_id) {
        return patientService.getDoctorDescription(doctor_id);
    }

    //病人提交挂号预约
    @PostMapping("/registration/submit")
    public Map<String, Object> submitRegistration(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id, @SpringQueryMap Patient patient) {
        return patientService.submitRegistration(doctor_id, date,noon_id, patient);
    }

    @PostMapping("/requestAI")
    public Map<String, Object> requestAI(@RequestParam("message") String message) {
        HashMap<String, Object> data = DeepSeekAPI.sendRequestToDeepSeek(message, patientService.getDepartmentsStringList());
        ArrayList<DoctorScheduleMap> rec = patientService.getDoctorScheduleRecommendation((ArrayList<String>) data.get("departments"));

        HashMap<String, Object> response = new HashMap<>();
        response.put("message", data.get("message"));
        if (rec.isEmpty()) {
            response.put("data_count", 0);
        }
        else {
            response.put("recommendation", rec.get(0));         //暂时只获取第一个查询到的号源查询结果
            response.put("data_count", rec.size());
        }
        return response;
    }


}
