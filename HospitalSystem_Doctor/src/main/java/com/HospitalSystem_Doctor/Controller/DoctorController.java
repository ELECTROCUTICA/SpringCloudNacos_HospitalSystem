package com.HospitalSystem_Doctor.Controller;


import com.HospitalSystem_Doctor.Service.DoctorFeignService;
import com.HospitalSystem_Pojo.Entity.Admin;
import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Map.RegistrationMap;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public Map<String, Object> doctorLoginHandle(@RequestParam("id") String id, @RequestParam("password") String password, HttpServletRequest request) {
        Map<String, Object> map = doctorFeignService.doctorLoginHandle(id, password);


        if (map.get("state").equals("ok")) {
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
        return (Doctor)request.getSession(false).getAttribute("Doctor");
    }

    //获取病人就诊列表
    @GetMapping("/getPatientsList")
    public Map<Integer, RegistrationMap> getPatientsList(HttpServletRequest request) {
        Doctor doctor = (Doctor)request.getSession(false).getAttribute("Doctor");
        return doctorFeignService.getPatientsList(doctor);
    }

    @PostMapping("/changingStatus")
    public Map<String, Object> changingStatus(@RequestParam("id") String id, @RequestParam("status") int status) {
        return doctorFeignService.changingStatus(id, status);
    }

    @GetMapping("logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Doctor") != null) {
            session.removeAttribute("Doctor");
        }
        var data = new HashMap<String, Object>();
        data.put("state", "ok");
        data.put("message", "登出成功");
        return data;
    }

}
