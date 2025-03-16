package com.HospitalSystem_Interface.Controller;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;
import com.HospitalSystem_Pojo.Utils.*;
import com.HospitalSystem_Pojo.JSON.*;
import com.HospitalSystem_Interface.Service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PostExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("doctor")
public class DoctorInterfaceController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/login/loginHandle")
    public Map<String, Object> doctorLoginHandle(@RequestParam("doctor_id") Integer doctor_id, @RequestParam("doctor_password") String doctor_password) {
        return doctorService.doctorLoginHandle(doctor_id, doctor_password);
    }

    //获取病人就诊列表
    @GetMapping("/getPatientRegisterList")
    public ArrayList<RegistrationMap> getPatientRegisterList(@SpringQueryMap Doctor doctor) {
        return doctorService.getPatientRegisterList(doctor);
    }

    @PostMapping("/changeRegisterStatus")
    public Map<String, Object> changeRegisterStatus(@RequestParam("register_id") Integer register_id, @RequestParam("status") Integer status) {
        return doctorService.changeRegisterStatus(register_id, status);
    }
}
