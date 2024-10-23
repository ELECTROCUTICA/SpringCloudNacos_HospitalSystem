package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface DoctorService {

    List<Doctor> getDoctorsWorkAtDate(int dep_no, String date);

    Map<String, Object> doctorLoginHandle(String id, String password);

    Map<Integer, RegistrationMap> getPatientsList(Doctor doctor);

    Map<String, Object> changingStatus(String id, int status);
}
