package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DepartmentMapper {

    @Insert("insert into Department (dep_no, dep_name, dep_description, valid_flag, create_user) " +
            "values (#{dep_no}, #{dep_name}, #{dep_description}, #{valid_flag}, #{create_user})")
    void insertDepartment(Department department);

    @Update("update Department set dep_name = #{dep_name}, dep_description = #{dep_description}, valid_flag = #{valid_flag} where dep_no = #{dep_no}")
    void updateDepartment(Department department);

    @Select("select * from Department where dep_no = #{dep_no}")
    Department getDepartment(@Param("dep_no") int dep_no);

    @Select("select * from Doctor join Department on Doctor.dep_no = Department.dep_no where department.dep_no = #{dep_no}")
    List<Doctor> getDoctorsByDepartmentNo(@Param("dep_no") int dep_no);

    @Select("select * from Doctor join Department on Doctor.dep_no = Department.dep_no where department.dep_name = #{dep_name}")
    List<Doctor> getDoctorsByDepartmentName(@Param("dep_name") String dep_name);

    @Update("update Doctor set dep_no = #{param2} where dep_no = #{param1}")
    void transferDoctorsToDepartment(@Param("source") int source, @Param("target") int target);

    @Select("select * from Department limit #{page.start}, #{page.size}")
    List<Department> getDepartmentsForPagination(@Param("page") Page page);

    @Select("select * from Department where valid_flag = 1")
    List<Department> getAllValidDepartments();

    @Select("select * from Department where dep_no = #{dep_no} or valid_flag = 1")
    Department getDepartmentsForUpdate(@Param("dep_no") int dep_no);

    @Select("select * from Department")
    List<Department> getAllDepartments();

    @Select("select count(*) from Department")
    int getCount();

}
