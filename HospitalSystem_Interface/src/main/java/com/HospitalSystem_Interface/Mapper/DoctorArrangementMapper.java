package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Entity.DoctorArrangement;
import com.HospitalSystem_Pojo.Map.DoctorArrangementMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface DoctorArrangementMapper {

    void insertArrangement(DoctorArrangement arrangement);

    void deleteArrangement(DoctorArrangement arrangement);
    
    void subRemain(DoctorArrangement arrangement);

    List<DoctorArrangement> getDoctorArrangementByDate(@Param("date") Date date);

    List<DoctorArrangement> getDoctorArrangementByDoctor(Doctor doctor);

    DoctorArrangement getDoctorArrangement(@Param("date") String date, @Param("doctor_id") String doctor_id);

    DoctorArrangementMap getDoctorArrangementMap(@Param("date") String date, @Param("doctor_id") String doctor_id);

    List<DoctorArrangementMap> getArrangementsByDepartmentAtDate(@Param("dep_no") int dep_no, @Param("date") String date);

    List<DoctorArrangement> getAllDoctorArrangement();
}
