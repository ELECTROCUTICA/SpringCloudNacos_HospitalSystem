package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.Department;
import com.HospitalSystem_Pojo.Entity.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DepartmentMapper {

    void insertDepartment(Department department);

    void updateDepartment(Department department);

    Department getDepartment(@Param("dep_no") int dep_no);

    List<Doctor> getDoctorsByDepartmentNo(@Param("dep_no") int dep_no);

    List<Doctor> getDoctorsByDepartmentName(@Param("dep_name") String dep_name);

    void transferDoctorsToDepartment(@Param("source") int source, @Param("target") int target);

    List<Department> getAllDepartments();
}
