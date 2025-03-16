package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;
import com.HospitalSystem_Pojo.Utils.*;
import com.HospitalSystem_Pojo.JSON.*;
import com.HospitalSystem_Interface.Mapper.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorServiceImplement implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private RegistrationMapper registrationMapper;


    @Override
    public Map<String, Object> doctorLoginHandle(Integer doctor_id, String doctor_password) {
        Doctor doctor = doctorMapper.getDoctor(doctor_id);
        HashMap<String, Object> map = new HashMap<>();

        if (doctor != null && doctor_password.equals(doctor.getDoctor_password())) {
            if (doctor.getValid_flag() == 0) {
                map.put("status", "fail");
                map.put("message", String.format("%s %s医生账号不可用，请联系管理员",doctor.getDoctor_id(), doctor.getDoctor_name()));
            }
            else {
                String DoctorJSONString = null;

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    DoctorJSONString = objectMapper.writeValueAsString(doctor);
                }
                catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                map.put("status", "ok");
                map.put("doctor", DoctorJSONString);
                map.put("message", String.format("%s医生，登入成功", doctor.getDoctor_name()));
            }
        }
        else {
            map.put("status", "fail");
            map.put("message", "登入失败，请输入正确的职工号和密码");
        }
        return map;
    }

    @Override
    public ArrayList<RegistrationMap> getPatientRegisterList(Doctor doctor) {
        var list = new ArrayList<RegistrationMap>();
        if (doctor != null) {
            list = (ArrayList<RegistrationMap>)registrationMapper.getRegistrationsMapByDoctorID1(doctor.getDoctor_id());
            list.addAll(registrationMapper.getRegistrationsMapByDoctorID2(doctor.getDoctor_id()));
        }
        return list;
    }

    @Override
    public Map<String, Object> changeRegisterStatus(Integer register_id, Integer status) {
        HashMap<String, Object> map = new HashMap<>();
        if (register_id != null) {
            Registration updated = registrationMapper.getRegistration(register_id);
            updated.setRegistration_status(status);
            registrationMapper.updateRegistration(updated);
            if (status == 2) {
                map.put("status", "ok");
                map.put("message", "成功完成就诊");
            }
            else if (status == 0) {
                map.put("state", "ok");
                map.put("message", "成功取消就诊");
            }
            else {
                map.put("status", "fail");
                map.put("message", "提交异常");
            }
            return map;
        }
        else {
            return Map.of(
                    "status", "fail",
                    "message", "无效的就诊信息"
            );
        }
    }


}
