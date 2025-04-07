package com.HospitalSystem_Doctor.Controller;


import com.HospitalSystem_Doctor.Service.DoctorFeignService;
import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import com.HospitalSystem_Pojo.Map.RegistrationMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("doctor")
public class DoctorController {

    @Autowired
    private DoctorFeignService doctorFeignService;


    @PostMapping("/login/loginHandle")
    public Map<String, Object> doctorLoginHandle(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("doctor_password") String doctor_password, HttpServletRequest request) {
        Map<String, Object> data = doctorFeignService.doctorLoginHandle(doctor_id, doctor_password);

        if (data.get("status").equals("ok")) {
            String doctorJSONString = data.get("doctor").toString();
            ObjectMapper objectMapper = new ObjectMapper();
            Doctor doctor = null;
            try {
                doctor = objectMapper.readValue(doctorJSONString, Doctor.class);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            data.remove("doctor");
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(12 * 60 * 60);
            session.setAttribute("Doctor", doctor);
        }
        return data;
    }

    @GetMapping("/getDoctor")
    public Doctor getDoctor(HttpServletRequest request) {
        if (request.getSession(false) != null) {
            return (Doctor)request.getSession(false).getAttribute("Doctor");
        }
        return (Doctor)request.getSession(true).getAttribute("Doctor");

    }

    @GetMapping("/getTodaySchedule")
    public ArrayList<DoctorScheduleMap> getTodaySchedule(HttpServletRequest request) {
        LocalDate today = LocalDate.now();
        var doctor = (Doctor)request.getSession(false).getAttribute("Doctor");
        return doctorFeignService.getDoctorSchedule(doctor.getDoctor_id(), today.toString());
    }


    @PostMapping("/addRegisterCount")
    public Map<String, Object> addRegisterCount(@RequestParam("schedule_id") Integer schedule_id, @RequestParam("amount") Integer amount) {
        return doctorFeignService.addRegisterCount(schedule_id, amount);
    }

    @GetMapping("/searchRegistration")
    ArrayList<RegistrationMap> getRegistrationMapByPatientKeyword(@RequestParam("keyword") String keyword, HttpServletRequest request) {
        var doctor = (Doctor)request.getSession(false).getAttribute("Doctor");
        return doctorFeignService.getRegistrationMapByPatientKeyword(doctor.getDoctor_id(), keyword);
    }

    //获取病人就诊列表
    @GetMapping("/getPatientRegisterList")
    public ArrayList<RegistrationMap> getPatientRegisterList(HttpServletRequest request) {
        Doctor doctor = (Doctor)request.getSession(false).getAttribute("Doctor");
        return doctorFeignService.getPatientRegisterList(doctor);
    }

    @PostMapping("/changeRegisterStatus")
    public Map<String, Object> changeRegisterStatus(@RequestParam("register_id") Integer register_id, @RequestParam("status") Integer status) {
        return doctorFeignService.changeRegisterStatus(register_id, status);
    }

    @GetMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("Doctor") != null) {
            session.removeAttribute("Doctor");
        }
        return Map.of(
                "status", "ok",
                "message", "登出成功"
        );
    }

}
