package com.HospitalSystem_Doctor.Service;


import com.HospitalSystem_Pojo.Map.RegistrationMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("HospitalSystem-Interface")
public interface DoctorFeignService {

    String PREFIX = "/doctor";

    @PostMapping(PREFIX + "/login/loginHandle")
    Map<String, Object> doctorLoginHandle(@RequestParam("id") String id, @RequestParam("password") String password);

    @GetMapping(PREFIX + "/getPatientsList")
    Map<Integer, RegistrationMap> getPatientsList(@SpringQueryMap Doctor doctor);

    @PostMapping(PREFIX + "/changingStatus")
    Map<String, Object> changingStatus(@RequestParam("id") String id, @RequestParam("status") int status);
}
