package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PatientMapper {

    @Insert("insert into Patient (patient_id, patient_name, patient_spell_code, patient_sex, patient_birthdate, patient_phone, patient_password) " +
            "values (#{patient_id}, #{patient_name}, #{patient_spell_code}, #{patient_sex}, #{patient_birthdate}, #{patient_phone}, #{patient_password})")
    void insertPatient(Patient patient);

    @Update("update Patient set patient_name = #{patient_name}, patient_spell_code = #{patient_spell_code}, patient_sex = #{patient_sex}, " +
            "patient_birthdate = #{patient_birthdate}, patient_phone = #{patient_phone}, patient_password = #{patient_password} " +
            "where patient_id = #{patient_id}")
    void updatePatient(Patient patient);

    @Select("select * from V_Patient where patient_id = #{patient_id}")
    Patient getPatient(@Param("patient_id") String patient_id);

    @Select("select * from V_Patient where patient_id like CONCAT('%', #{keyword}, '%') or patient_name like CONCAT('%', #{keyword}, '%') or patient_spell_code like CONCAT('%', #{keyword}, '%')")
    List<Patient> searchPatientsByKeyword(@Param("keyword") String keyword);

    @Select("select * from V_Patient limit #{page.start}, #{page.size}")
    List<Patient> getPatientsForPagination(@Param("page") Page page);

    @Select("select * from V_Patient " +
            "where patient_id like CONCAT('%', #{keyword}, '%') or patient_name like CONCAT('%', #{keyword}, '%') or patient_spell_code like CONCAT('%', #{keyword}, '%') " +
            "limit #{page.start}, #{page.size}")
    List<Patient> searchPatientsForPagination(@Param("page") Page page, @Param("keyword") String keyword);

    @Select("select count(*) from V_Patient")
    int getCounts();

    @Select("select * from V_Patient")
    List<Patient> getAllPatients();
}
