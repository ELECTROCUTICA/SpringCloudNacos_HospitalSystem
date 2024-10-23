package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.Page;
import com.HospitalSystem_Pojo.Entity.Registration;
import com.HospitalSystem_Pojo.Map.RegistrationMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RegistrationMapper {

    void insertRegistration(Registration registration);

    void updateRegistration(Registration registration);

    Registration getRegistration(@Param("id") String id);

    List<Registration> getRegistrationsByPatientID(@Param("patient_id") String patient_id);

    List<Registration> getRegistrationByPatientAtDate(@Param("patient_id") String patient_id, @Param("date") String date);

    List<Registration> getRegistrationsByDoctorID(@Param("doctor_id") String doctor_id);

    List<RegistrationMap> getRegistrationsMapByPatientID(@Param("patient_id") String patient_id);

    List<RegistrationMap> getRegistrationsMapByPatientIDForPagination(@Param("patient_id") String patient_id, @Param("page") Page page);

    List<RegistrationMap> getRegistrationsMapByDoctorID(@Param("doctor_id") String doctor_id);

    List<RegistrationMap> getRegistrationsMapByPatientAtDate(@Param("date") String date, @Param("patient_id") String patient_id);

    long getLastID();

    int getLineUpCount(@Param("date") String date, @Param("doctor_id") String doctor_id);

    List<Registration> getAllRegistration();
}
