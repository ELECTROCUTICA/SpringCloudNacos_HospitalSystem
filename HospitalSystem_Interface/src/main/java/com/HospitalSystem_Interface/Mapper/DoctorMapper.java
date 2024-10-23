package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.Department;
import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Entity.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DoctorMapper {

    void insertDoctor(Doctor doctor);

    void deleteDoctor(Doctor doctor);

    void updateDoctor(Doctor doctor);

    Doctor getDoctor(@Param("id") String id);

    List<Doctor> getDoctorsByDepartment(Department department);

    List<Doctor> getDoctorsByTitle(@Param("title") String title);

    List<Doctor> searchDoctorsByKeyWord(@Param("keyword") String keyword);

    List<Doctor> getDoctorsNoWorkAtDate(@Param("dep_no") int dep_no, @Param("date") String date);

    List<Doctor> getDoctorsWorkAtDate(@Param("dep_no") int dep_no, @Param("date") String date);

    List<Doctor> getAllDoctors();

    List<Doctor> getDoctorsForPagination(Page page);

    List<Doctor> searchDoctorsForPagination(@Param("page") Page page, @Param("keyword") String keyword);

    int getCounts();

}
