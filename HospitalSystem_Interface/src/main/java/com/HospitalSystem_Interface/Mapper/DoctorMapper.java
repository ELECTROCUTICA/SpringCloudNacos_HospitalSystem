package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DoctorMapper {

    @Insert("insert into Doctor (doctor_id, doctor_name, doctor_spell_code, doctor_sex, dep_no, title_no, doctor_password, valid_flag, doctor_description, create_user) " +
            "values (#{doctor_id}, #{doctor_name}, #{doctor_spell_code}, #{doctor_sex}, #{dep_no}, #{title_no}, #{doctor_password}, #{valid_flag}, #{doctor_description}, #{create_user})")
    void insertDoctor(Doctor doctor);

    @Delete("delete from Doctor where doctor_id = #{doctor_id}")
    void deleteDoctor(Doctor doctor);

    @Update("update Doctor set doctor_name = #{doctor_name}, doctor_spell_code = #{doctor_spell_code}, doctor_sex = #{doctor_sex}, dep_no = #{dep_no}, title_no = #{title_no}, " +
            "doctor_password = #{doctor_password} , valid_flag = #{valid_flag}, doctor_description = #{doctor_description} " +
            "where doctor_id = #{doctor_id}")
    void updateDoctor(Doctor doctor);

    @Update("update Doctor set valid_flag = 0 where doctor_id = #{doctor_id}")
    void disableDoctor(@Param("doctor_id") Integer doctor_id);

    @Select("select * from V_Doctor where doctor_id = #{doctor_id}")
    Doctor getDoctor(@Param("doctor_id") Integer doctor_id);

    @Select("select * from V_Doctor where dep_no = #{dep_no}")
    List<Doctor> getDoctorsByDepartment(Department department);

    @Select("select * from V_Doctor where dep_no = #{dep_no} and valid_flag = 1")
    List<Doctor> getValidDoctorsByDepartment(Department department);

    @Select("select * from V_Doctor where title_no = #{title_no}")
    List<Doctor> getDoctorsByTitle(@Param("title") String title_no);

    @Select("select * from V_Doctor where doctor_id like CONCAT('%', #{keyword}, '%') or doctor_name like CONCAT('%', #{keyword}, '%') or doctor_spell_code like CONCAT('%', #{keyword}, '%')")
    List<Doctor> searchDoctorsByKeyWord(@Param("keyword") String keyword);

    @Select("select * from V_Doctor d where d.dep_no = #{param1} and d.doctor_id not in (select ds.doctor_id from DoctorSchedule ds where ds.work_date = #{param2} and ds.noon_id = #{param3} and ds.valid_flag = 1)")
    List<Doctor> getDoctorsNoWorkAtDateAndNoon(@Param("dep_no") Integer dep_no, @Param("work_date") String work_date, @Param("noon_id") Integer noon_id);

    @Select("select * from V_Doctor d where d.dep_no = #{param1} and d.doctor_id in (select ds.doctor_id from DoctorSchedule ds where ds.work_date = #{param2} and ds.noon_id = #{param3} and ds.valid_flag = 1)")
    List<Doctor> getDoctorsWorkAtDateAndNoon(@Param("dep_no") Integer dep_no, @Param("work_date") String work_date, @Param("noon_id") Integer noon_id);

    @Select("select * from V_Doctor d where d.doctor_name like CONCAT('%', #{keyword}, '%') or d.doctor_spell_code like CONCAT('%', #{keyword}, '%') and valid_flag = 1")
    List<Doctor> searchDoctorsByPatientKeyWord(@Param("keyword") String keyword);

    @Select("select * from V_Doctor")
    List<Doctor> getAllDoctors();

    @Select("select * from V_Doctor limit #{page.start}, #{page.size}")
    List<Doctor> getDoctorsForPagination(@Param("page") Page page);

    @Select("select * from V_Doctor where doctor_id like CONCAT('%', #{keyword}, '%') or doctor_name like CONCAT('%', #{keyword}, '%') or doctor_spell_code like CONCAT('%', #{keyword}, '%') limit #{page.start}, #{page.size}")
    List<Doctor> searchDoctorsForPagination(@Param("page") Page page, @Param("keyword") String keyword);

    @Select("select count(*) from Doctor")
    int getCounts();

    @Select("select doctor_description from V_Doctor where doctor_id = #{doctor_id}")
    String getDoctorDescription(@Param("doctor_id") Integer doctor_id);

}
