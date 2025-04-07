package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DoctorService {

    Map<String, Object> doctorLoginHandle(Integer doctor_id, String doctor_password);

    ArrayList<DoctorScheduleMap> getTodayDoctorSchedule(Integer doctor_id, String work_date);

    Map<String, Object> addRegisterCount(Integer schedule_id, Integer amount);

    ArrayList<RegistrationMap> getRegistrationMapByPatientKeyword(Integer doctor_id, String keyword);

    ArrayList<RegistrationMap> getPatientRegisterList(Doctor doctor);

    Map<String, Object> changeRegisterStatus(Integer register_id, Integer status);
}
