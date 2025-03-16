package com.HospitalSystem_Doctor.Controller;


import com.HospitalSystem_Doctor.Service.DoctorFeignService;
import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Map.RegistrationMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("doctor")
public class DoctorController {
//
//    @Autowired
//    private DoctorHttpExchangeService doctorHttpExchangeService;

    @Autowired
    private DoctorFeignService doctorFeignService;


    @PostMapping("/login/loginHandle")
    public Map<String, Object> doctorLoginHandle(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("doctor_password") String doctor_password, HttpServletRequest request) {
        Map<String, Object> map = doctorFeignService.doctorLoginHandle(doctor_id, doctor_password);

        if (map.get("status").equals("ok")) {
            String doctorJSONString = map.get("doctor").toString();
            ObjectMapper objectMapper = new ObjectMapper();
            Doctor doctor = null;
            try {
                doctor = objectMapper.readValue(doctorJSONString, Doctor.class);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            map.remove("doctor");
            request.getSession(true).setAttribute("Doctor", doctor);
        }
        return map;
    }

    @GetMapping("/getDoctor")
    public Doctor getDoctor(HttpServletRequest request) {
        if (request.getSession(false) != null) {
            return (Doctor)request.getSession(false).getAttribute("Doctor");
        }
        return (Doctor)request.getSession(true).getAttribute("Doctor");

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

    @GetMapping("logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Doctor") != null) {
            session.removeAttribute("Doctor");
        }
        return Map.of(
                "status", "ok",
                "message", "登出成功"
        );
    }

}
