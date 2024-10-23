package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PatientMapper {

    void insertPatient(Patient patient);

    void updatePatient(Patient patient);

    Patient getPatient(@Param("id") String id);

    List<Patient> searchPatientsByKeyword(@Param("keyword") String keyword);

    List<Patient> getPatientsForPagination(Page page);

    List<Patient> searchPatientsForPagination(@Param("page") Page page, @Param("keyword") String keyword);

    int getCounts();

    List<Patient> getAllPatients();
}
