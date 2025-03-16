package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Entity.DoctorSchedule;
import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface DoctorScheduleMapper {

    @Insert("insert into DoctorSchedule (schedule_id, work_date, noon_id, doctor_id, title_no, dep_no, init_register_count, remain_register_count, append_register_count, valid_flag, submit_user) values " +
            "(#{schedule_id}, #{work_date}, #{noon_id}, #{doctor_id}, #{title_no}, #{dep_no}, #{init_register_count}, #{remain_register_count}, #{append_register_count}, #{valid_flag}, #{submit_user})")
    void insertDoctorSchedule(DoctorSchedule doctorSchedule);

    @Delete("delete from DoctorSchedule where schedule_id = #{schedule_id}")
    void deleteDoctorSchedule(DoctorSchedule doctorSchedule);

    @Select("select * from doctorschedule where schedule_id = #{schedule_id}")
    DoctorSchedule getDoctorSchedule(@Param("schedule_id") int schedule_id);

    @Update("update DoctorSchedule set work_date = #{work_date}, noon_id = #{noon_id}, doctor_id = #{doctor_id}, dep_no = #{dep_no}, init_register_count = #{init_register_count}, " +
            "remain_register_count = #{remain_register_count}, append_register_count = #{append_register_count}, valid_flag = #{valid_flag}, submit_user = #{submit_user} " +
            "where schedule_id = #{schedule_id}")
    void updateDoctorSchedule(DoctorSchedule doctorSchedule);

    @Select("select * from DoctorSchedule where work_date = #{work_date}")
    List<DoctorSchedule> getDoctorScheduleByDate(@Param("work_date") Date work_date);

    @Select("select * from DoctorSchedule where work_date = #{work_date} and noon_id = #{noon_id}")
    List<DoctorSchedule> getDoctorScheduleByDateAndNoon(@Param("work_date") Date work_date, @Param("noon_id") Integer noon_id);

    @Select("select * from DoctorSchedule where work_date = #{work_date} and noon_id = #{noon_id} and valid_flag = 1")
    List<DoctorSchedule> getValidDoctorScheduleByDateAndNoon(@Param("work_date") Date work_date, @Param("noon_id") Integer noon_id);

    @Select("select * from DoctorSchedule where doctor_id = #{doctor_id}")
    List<DoctorSchedule> getDoctorScheduleByByDoctor(Doctor doctor);

    @Select("select * from DoctorSchedule where work_date = #{work_date} and noon_id = #{noon_id} and doctor_id = #{doctor_id} and valid_flag = 1")
    DoctorSchedule getValidDoctorSchedule(@Param("work_date") String work_date, @Param("noon_id") Integer noon_id, @Param("doctor_id") Integer doctor_id);

    @Select("select * from DoctorSchedule where work_date = #{work_date} and noon_id = #{noon_id} and doctor_id = #{doctor_id} and valid_flag = 1 for update")
    DoctorSchedule getValidDoctorScheduleForUpdate(@Param("work_date") String work_date, @Param("noon_id") Integer noon_id, @Param("doctor_id") Integer doctor_id);

    @Select("select * from v_doctorschedule where work_date = #{work_date} and noon_id = #{noon_id} and doctor_id = #{doctor_id} and valid_flag = 1")
    DoctorScheduleMap getValidDoctorScheduleMap(@Param("work_date") String work_date, @Param("noon_id") Integer noon_id, @Param("doctor_id") Integer doctor_id);

    @Select("select * from V_DoctorSchedule where work_date = #{work_date} and noon_id = #{noon_id} and dep_no = #{dep_no} and valid_flag = 1")
    List<DoctorScheduleMap> getDoctorScheduleMapByDepartmentAtDateAndNoon(@Param("work_date") String work_date, @Param("noon_id") Integer noon_id, @Param("dep_no") Integer dep_no);

    @Select("select * from v_doctorschedule ds " +
            "where ds.doctor_id = #{doctor_id} " +
            "  and ds.work_date = #{start_date} " +
            "  and ds.end_time_hour >= DATE_FORMAT(current_time, '%H') " +
            "  and ds.end_time_minute > DATE_FORMAT(current_time, '%i') " +
            "  and ds.valid_flag = 1 " +
            "union all " +
            "select * from v_doctorschedule ds " +
            "where ds.doctor_id = #{doctor_id} " +
            "  and ds.work_date > #{start_date} " +
            "  and ds.work_date < #{end_date} " +
            "  and ds.valid_flag = 1")
    List<DoctorScheduleMap> getDoctorScheduleMapInWeek(@Param("doctor_id") Integer doctor_id, @Param("start_date") String start_date, @Param("end_date") String end_date);

    @Select("select * from v_doctorschedule ds where ds.work_date >= #{start_date} and ds.work_date <= #{end_date} and ds.dep_name like CONCAT('%', #{dep_name}, '%') and ds.valid_flag = 1")
    List<DoctorScheduleMap> getDoctorScheduleMapByDepartmentName(@Param("dep_name") String dep_name, @Param("start_date") String start_date, @Param("end_date") String end_date);

    @Select("select * from v_doctorschedule ds where ds.dep_no = #{dep_no} and ds.work_date = #{date} and noon_id = #{noon_id} and valid_flag = 1")
    List<DoctorScheduleMap> getDoctorsWorkAtDateAndNoon2(@Param("dep_no") Integer dep_no, @Param("date") String date, @Param("noon_id") Integer noon_id);

    @Select("(select * from v_doctorschedule ds " +
            " where ds.work_date = DATE_FORMAT(NOW(), '%Y-%m-%d') " +
            "  and ds.end_time_hour > DATE_FORMAT(NOW(), '%H') " +
            "  and ds.end_time_minute > DATE_FORMAT(NOW(), '%m') " +
            "  and ds.dep_name = #{dep_name} " +
            "  and ds.remain_register_count > 0 " +
            "  and ds.valid_flag = 1 " +
            "order by ds.remain_register_count desc limit 1) " +
            "union all " +
            "(select * from v_doctorschedule ds " +
            "where ds.work_date > DATE_FORMAT(NOW(), '%Y-%m-%d') " +
            "  and ds.work_date < DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 7 DAY), '%Y-%m-%d') " +
            "  and ds.dep_name = #{dep_name} " +
            "  and ds.remain_register_count > 0 " +
            "  and ds.valid_flag = 1 " +
            "order by ds.remain_register_count desc limit 1);")
    List<DoctorScheduleMap> getRecommendedSchedule(String dep_name);

    @Select("select * from DoctorSchedule")
    List<DoctorSchedule> getAllDoctorSchedule();
}
