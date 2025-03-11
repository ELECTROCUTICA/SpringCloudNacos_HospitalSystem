//package com.HospitalSystem_Interface.Controller;
//
//import com.HospitalSystem_Pojo.Entity.*;
//import com.HospitalSystem_Pojo.Map.*;
//import com.HospitalSystem_Pojo.Response.*;
//import com.HospitalSystem_Pojo.Utils.*;
//import com.HospitalSystem_Pojo.JSON.*;
//import com.HospitalSystem_Interface.Service.DoctorService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.openfeign.SpringQueryMap;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.service.annotation.PostExchange;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("doctor")
//public class DoctorInterfaceController {
//
//    @Autowired
//    private DoctorService doctorService;
//
//    @PostMapping("/login/loginHandle")
//    public Map<String, Object> doctorLoginHandle(@RequestParam("id") String id, @RequestParam("password") String password) {
//        return doctorService.doctorLoginHandle(id, password);
//    }
//
//    //获取病人就诊列表
//    @GetMapping("/getPatientsList")
//    public Map<Integer, RegistrationMap> getPatientsList(@SpringQueryMap Doctor doctor) {
//        return doctorService.getPatientsList(doctor);
//    }
//
//    @PostMapping("/changingStatus")
//    public Map<String, Object> changingStatus(@RequestParam("id") String id, @RequestParam("status") int status) {
//        return doctorService.changingStatus(id, status);
//    }
//}
