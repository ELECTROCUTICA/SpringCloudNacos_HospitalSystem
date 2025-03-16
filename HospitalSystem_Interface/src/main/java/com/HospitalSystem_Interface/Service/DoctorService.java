package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DoctorService {


    Map<String, Object> doctorLoginHandle(Integer doctor_id, String doctor_password);

    ArrayList<RegistrationMap> getPatientRegisterList(Doctor doctor);

    Map<String, Object> changeRegisterStatus(Integer register_id, Integer status);
}
