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
    public List<Doctor> getDoctorsWorkAtDate(int dep_no, String date) {
        return doctorMapper.getDoctorsWorkAtDate(dep_no, date);
    }

    @Override
    public Map<String, Object> doctorLoginHandle(String id, String password) {
        Doctor doctor = doctorMapper.getDoctor(id);
        HashMap<String, Object> map = new HashMap<>();

        if (doctor != null && password.equals(doctor.getPassword())) {
            map.put("state", "ok");
            String DoctorJSONString = null;

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                DoctorJSONString = objectMapper.writeValueAsString(doctor);
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            map.put("doctor", DoctorJSONString);
            map.put("message", String.format("%s医生，登入成功", doctor.getName()));
        }
        else {
            map.put("state", "fail");
            map.put("message", "登入失败，请输入正确的职工号和密码");
        }
        return map;
    }

    @Override
    public Map<Integer, RegistrationMap> getPatientsList(Doctor doctor) {
        ArrayList<RegistrationMap> list = null;
        HashMap<Integer, RegistrationMap> map = new HashMap<>();

        if (doctor != null) {
            list = (ArrayList<RegistrationMap>)registrationMapper.getRegistrationsMapByDoctorID(doctor.getId());

            for (int i = 0; i < list.size(); i++) {
                RegistrationMap temp = list.get(i);
                temp.setVisit_date(temp.getVisit_date().substring(0, 10));
                map.put(i + 1, temp);
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> changingStatus(String id, int status) {
        HashMap<String, Object> map = new HashMap<>();
        if (id != null) {
            Registration updated = registrationMapper.getRegistration(id);
            updated.setStatus(status);
            registrationMapper.updateRegistration(updated);
            if (status == 2) {
                map.put("state", "ok");
                map.put("message", "成功完成就诊");
                return map;
            }
            else if (status == 0) {
                map.put("state", "ok");
                map.put("message", "成功取消就诊");
                return map;
            }
            else {
                map.put("state", "fail");
                map.put("message", "提交异常");
                return map;
            }
        }
        else {
            map.put("state", "fail");
            map.put("message", "无效的就诊信息");
            return map;
        }
    }


}
