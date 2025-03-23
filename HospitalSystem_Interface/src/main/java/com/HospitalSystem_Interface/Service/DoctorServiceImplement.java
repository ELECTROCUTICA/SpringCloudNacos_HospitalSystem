package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Utils.*;
import com.HospitalSystem_Pojo.JSON.*;
import com.HospitalSystem_Interface.Mapper.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private DoctorScheduleMapper doctorScheduleMapper;


    @Override
    public Map<String, Object> doctorLoginHandle(Integer doctor_id, String doctor_password) {
        Doctor doctor = doctorMapper.getDoctor(doctor_id);
        HashMap<String, Object> data = new HashMap<>();

        if (doctor != null && doctor_password.equals(doctor.getDoctor_password())) {
            if (doctor.getValid_flag() == 0) {
                data.put("status", "fail");
                data.put("message", String.format("%s %s医生账号不可用，请联系管理员",doctor.getDoctor_id(), doctor.getDoctor_name()));
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

                data.put("status", "ok");
                data.put("doctor", DoctorJSONString);
                data.put("message", String.format("%s医生，登入成功", doctor.getDoctor_name()));
            }
        }
        else {
            data.put("status", "fail");
            data.put("message", "登入失败，请输入正确的职工号和密码");
        }
        return data;
    }

    @Override
    public ArrayList<DoctorScheduleMap> getTodayDoctorSchedule(Integer doctor_id, String work_date) {
        return (ArrayList<DoctorScheduleMap>) doctorScheduleMapper.getTodayDoctorScheduleByDoctor(doctor_id, work_date);
    }


    @Override
    public ArrayList<RegistrationMap> getRegistrationMapByPatientKeyword(Integer doctor_id, String keyword) {
        return (ArrayList<RegistrationMap>)registrationMapper.getRegistrationMapByPatientKeyword(doctor_id, keyword);
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public Map<String, Object> addRegisterCount(Integer schedule_id, Integer amount) {
        var ds = doctorScheduleMapper.getDoctorScheduleForUpdate(schedule_id);
        if (ds == null) return Map.of(
                "status", "fail",
                "message", "找不到该排班信息"
        );
        else if (ds.getValid_flag() == 0) return Map.of(
                "status", "fail",
                "message", "无效的排班信息"
        );

        doctorScheduleMapper.addAppendRegisterCount(ds.getSchedule_id(), amount);
        doctorScheduleMapper.addRemainRegisterCount(ds.getSchedule_id(), amount);

        return Map.of(
                "status", "ok",
                "message", "加号成功"
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public Map<String, Object> changeRegisterStatus(Integer register_id, Integer status) {
        HashMap<String, Object> data = new HashMap<>();
        if (register_id != null) {
            Registration updated = registrationMapper.getRegistration(register_id);
            updated.setRegistration_status(status);
            registrationMapper.updateRegistration(updated);
            if (status == 2) {
                data.put("status", "ok");
                data.put("message", "成功完成就诊");
            }
            else if (status == 0) {
                data.put("state", "ok");
                data.put("message", "成功取消就诊");
            }
            else {
                data.put("status", "fail");
                data.put("message", "提交异常");
            }
            return data;
        }
        else {
            return Map.of(
                    "status", "fail",
                    "message", "无效的就诊信息"
            );
        }
    }


}
