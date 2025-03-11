package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.RegistrationMap;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RegistrationMapper {

    @Insert("insert into Registration (register_id, serial_id, noon_id, doctor_id, dep_no, patient_id, patient_name, patient_spell_code, " +
            "patient_sex, patient_birthdate, patient_phone, registration_status, visit_date) values " +
            "(#{register_id}, #{serial_id}, #{noon_id}, #{doctor_id}, #{dep_no}, #{patient_id}, #{patient_name}, #{patient_spell_code}, " +
            "#{patient_sex}, #{patient_birthdate}, #{patient_phone}, #{registration_status}, #{visit_date})")
    void insertRegistration(Registration registration);

    @Update("update Registration set serial_id = #{serial_id}, noon_id = #{noon_id}, doctor_id = #{doctor_id}, dep_no = #{dep_no}, patient_id = #{patient_id}, " +
            "patient_name = #{patient_name}, patient_spell_code = #{patient_spell_code}, patient_sex = #{patient_sex}, patient_birthdate = #{patient_birthdate}, " +
            "patient_phone = #{patient_phone}, registration_status = #{registration_status}, visit_date = #{visit_date} " +
            "where register_id = #{register_id}")
    void updateRegistration(Registration registration);

    @Select("select * from Registration where register_id = #{register_id}")
    Registration getRegistration(@Param("register_id") Integer register_id);

    @Select("select * from Registration where patient_id = #{patient_id}")
    List<Registration> getRegistrationsByPatientID(@Param("patient_id") String patient_id);

    @Select("select * from Registration where patient_id = #{param1} and visit_date = #{param2}")
    List<Registration> getRegistrationByPatientAtDate(@Param("patient_id") String patient_id, @Param("visit_date") String visit_date);

    @Select("select * from Registration where patient_id = #{param1} and visit_date = #{param2} and noon_id = #{noon_id}")
    List<Registration> getRegistrationByPatientAtDateAndNoon(@Param("patient_id") String patient_id, @Param("visit_date") String visit_date, @Param("noon_id") Integer noon_id);

    @Select("select * from registration where doctor_id = #{doctor_id}")
    List<Registration> getRegistrationsByDoctorID(@Param("doctor_id") Integer doctor_id);

    @Select("select * from v_registration where patient_id = #{patient_id} order by register_id desc")
    List<RegistrationMap> getRegistrationsMapByPatientID(@Param("patient_id") String patient_id);

    @Select("select * from v_registration where patient_id = #{patient_id} order by register_id desc limit #{page.start}, #{page.size}")
    List<RegistrationMap> getRegistrationsMapByPatientIDForPagination(@Param("patient_id") String patient_id, @Param("page") Page page);

    @Select("select * from v_registration where doctor_id = #{doctor_id} and visit_date between DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() " +
            "order by case when registration_status = 1 then 0 end, case when registration_status != 1 then register_id end desc")
    List<RegistrationMap> getRegistrationsMapByDoctorID(@Param("doctor_id") Integer doctor_id);

    @Select("select * from v_registration where visit_date = #{param1} and patient_id = #{param2} and registration_status = 1")
    List<RegistrationMap> getRegistrationsMapByPatientAtDate(@Param("visit_date") String visit_date, @Param("patient_id") String patient_id);

    @Select("select * from v_registration where visit_date = #{param1} and patient_id = #{param2} and noon_id = #{param3} and registration_status = 1")
    List<RegistrationMap> getRegistrationsMapByPatientAtDateAndNoon(@Param("visit_date") String visit_date, @Param("patient_id") String patient_id, @Param("noon_id") Integer noon_id);

    @Select("select register_id from Registration order by register_id desc limit 1")
    long getLastID();

    @Select("select count(*) - 1 as queueup from v_registration where visit_date = #{param1} and noon_id = #{param2} and doctor_id = #{param3} and registration_status = 1")
    int getLineUpCount(@Param("work_date") String work_date, @Param("noon_id") Integer noon_id, @Param("doctor_id") Integer doctor_id);

    @Select("select * from registration")
    List<Registration> getAllRegistration();

    @Select("select count(*) + 1 from registration where visit_date = #{visit_date}")
    int getSerialIDForInsert(@Param("visit_date") String visit_date);

    @Select("select * from Registration where register_id = #{register_id} for update")
    List<Registration> getRegistrationForUpdate(@Param("register_id") Integer register_id);
}
