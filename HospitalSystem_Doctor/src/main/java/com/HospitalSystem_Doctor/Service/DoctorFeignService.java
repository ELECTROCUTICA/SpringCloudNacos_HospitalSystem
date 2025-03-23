package com.HospitalSystem_Doctor.Service;


import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import com.HospitalSystem_Pojo.Map.RegistrationMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;

@FeignClient("HospitalSystem-Interface")
public interface DoctorFeignService {

    String PREFIX = "/doctor";

    @PostMapping(PREFIX + "/login/loginHandle")
    Map<String, Object> doctorLoginHandle(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("doctor_password") String password);

    @GetMapping(PREFIX + "/getTodaySchedule")
    ArrayList<DoctorScheduleMap> getDoctorSchedule(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("work_date") String work_date);

    @PostMapping(PREFIX + "/addRegisterCount")
    Map<String, Object> addRegisterCount(@RequestParam("schedule_id") Integer schedule_id, @RequestParam("amount") Integer amount);

    @GetMapping(PREFIX +"/searchRegistration")
    ArrayList<RegistrationMap> getRegistrationMapByPatientKeyword(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("keyword") String keyword);

    @GetMapping(PREFIX + "/getPatientRegisterList")
    ArrayList<RegistrationMap> getPatientRegisterList(@SpringQueryMap Doctor doctor);

    @PostMapping(PREFIX + "/changeRegisterStatus")
    Map<String, Object> changeRegisterStatus(@RequestParam("register_id") Integer register_id, @RequestParam("status") Integer status);
}
