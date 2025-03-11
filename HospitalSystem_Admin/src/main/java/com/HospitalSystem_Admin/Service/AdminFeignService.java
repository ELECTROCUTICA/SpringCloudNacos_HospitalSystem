package com.HospitalSystem_Admin.Service;


import com.HospitalSystem_Pojo.Entity.Department;
import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Entity.Noon;
import com.HospitalSystem_Pojo.Entity.Title;
import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;

@FeignClient("HospitalSystem-Interface")
public interface AdminFeignService {

    String PREFIX = "/admin";

    @PostMapping(PREFIX + "/login/loginHandle")
    Map<String, Object> loginHandle(@RequestParam("id") String id, @RequestParam("password") String password);

    @GetMapping(PREFIX + "/doctorinfo")
    Map<String, Object> doctorInfoInterface(@RequestParam(value = "p", required = false) @Nullable String p, @RequestParam(value = "keyword", required = false) String keyword);

    //医生信息查找
    @GetMapping(PREFIX + "/doctorinfo/search")
    ArrayList<Doctor> search(@RequestParam(value = "keyword", required = false) String keyword);

    //医生信息删除
    @PostMapping(PREFIX + "/doctorinfo/delete")
    Map<String, Object> delete(@RequestParam("doctor_id") Integer doctor_id);

    //返回Doctor对象
    @GetMapping(PREFIX + "/doctorinfo/getDoctor")
    Doctor getDoctor(@RequestParam("doctor_id") Integer doctor_id);

    //修改医生信息
    @PostMapping(PREFIX + "/doctorinfo/update")
    Map<String, Object> updateDoctor(@RequestParam("doctor_id") Integer doctor_id,
                                     @RequestParam("doctor_name") String doctor_name,
                                     @RequestParam("doctor_sex") String doctor_sex,
                                     @RequestParam("dep_no") Integer dep_no,
                                     @RequestParam("title_no") Integer title_no,
                                     @RequestParam("doctor_password") String doctor_password,
                                     @RequestParam("valid_flag") Integer valid_flag,
                                     @RequestParam("doctor_description") String doctor_description);

    //新增医生信息
    @PostMapping(PREFIX + "/doctorinfo/insert")
    Map<String, Object> insertDoctor(@RequestParam("doctor_id") Integer doctor_id,
                                     @RequestParam("doctor_name") String doctor_name,
                                     @RequestParam("doctor_sex") String doctor_sex,
                                     @RequestParam("dep_no") Integer dep_no,
                                     @RequestParam("title_no") Integer title_no,
                                     @RequestParam("doctor_password") String doctor_password,
                                     @RequestParam("valid_flag") Integer valid_flag,
                                     @RequestParam("doctor_description") String doctor_description,
                                     @RequestParam("create_user") String create_user);

    @GetMapping(PREFIX + "/getDepartments")
    ArrayList<Department> getDepartments();

    @GetMapping(PREFIX + "/departments")
    Map<String, Object> departments(@RequestParam(value = "p", required = false) @Nullable String p);

    //根据科室编号获取科室
    @GetMapping(PREFIX + "/departments/getDepartment")
    Department getDepartment(@RequestParam("dep_no") Integer dep_no);

    //查看一个科室下的所有医生
    @GetMapping(PREFIX + "/departments/getDoctors")
    ArrayList<Doctor> getDoctors(@RequestParam("dep_no") Integer dep_no);

    //创建一个新科室
    @PostMapping(PREFIX + "/departments/insert")
    Map<String, Object> insertDepartment(@RequestParam("dep_no") Integer dep_no,
                                         @RequestParam("dep_name") String dep_name,
                                         @RequestParam("dep_description") String dep_description,
                                         @RequestParam("valid_flag") Integer valid_flag,
                                         @RequestParam("create_user") String create_user);

    //修改科室信息
    @PostMapping(PREFIX + "/departments/update")
    Map<String, Object> updateDepartment(@RequestParam("dep_no") Integer dep_no,
                                         @RequestParam("dep_name") String dep_name,
                                         @RequestParam("dep_description") String dep_description,
                                         @RequestParam("valid_flag") Integer valid_flag);

    //迁移一个科室的所有医生
    @PostMapping(PREFIX + "/departments/transfer")
    Map<String, Object> transfer(@RequestParam("source") Integer source, @RequestParam("target") Integer target);

    @GetMapping(PREFIX + "/getSchedule")
    Map<String, Object> getSchedule();


    //获取当日某科室的未被安排工作的医生
    @GetMapping(PREFIX + "/schedule/getDoctorsNoWork")
    ArrayList<Doctor> getDoctorsNoWork(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id);

    //获取当日某科室的工作医生
    @GetMapping(PREFIX + "/schedule/getDoctorsWork")
    ArrayList<Doctor> getDoctorsWork(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id);

    @GetMapping(PREFIX + "/schedule/getDoctorsWork2")
    ArrayList<DoctorScheduleMap> getDoctorsWork2(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id);

    //安排医生上班
    @PostMapping(PREFIX + "/schedule/goToWork")
    Map<String, Object> goToWork(@RequestParam("work_date") String work_date,
                                 @RequestParam("doctor_id") Integer doctor_id,
                                 @RequestParam("noon_id") Integer noon_id,
                                 @RequestParam("init_register_count") Integer init_register_count,
                                 @RequestParam("submit_user") String submit_user);

    //取消医生排班
    @PostMapping(PREFIX + "/schedule/cancel")
    Map<String, Object> cancelSchedule(@RequestParam("schedule_id") Integer schedule_id);

    @GetMapping(PREFIX + "/patientManager")
    Map<String, Object> patientManager(@RequestParam(value = "p", required = false) String p, @RequestParam(value = "keyword", required = false) String keyword);

    @PostMapping(PREFIX + "/patientManager/resetPassword")
    Map<String, Object> resetPassword(@RequestParam("patient_id") String patient_id);

    @GetMapping(PREFIX + "/titleManager")
    Map<String, Object> titleManager();

    @GetMapping(PREFIX + "/titleManager/getTitle")
    Title getTitle(@RequestParam("title_no") Integer title_no);

    @PostMapping(PREFIX + "/titleManager/insert")
    Map<String, Object> insertTitle(@RequestParam("title_no") Integer title_no, @RequestParam("title_name") String title_name, @RequestParam("valid_flag") Integer valid_flag);

    @PostMapping(PREFIX + "/titleManager/update")
    Map<String, Object> updateTitle(@RequestParam("title_no") Integer title_no, @RequestParam("title_name") String title_name, @RequestParam("valid_flag") Integer valid_flag);

    @GetMapping(PREFIX + "/noonManager")
    Map<String, Object> noonManager();

    @GetMapping(PREFIX + "/noonManager/getNoon")
    public Noon getNoon(@RequestParam("noon_id") Integer noon_id);


    @PostMapping(PREFIX + "/noonManager/insert")
    Map<String, Object> insertNoon(@RequestParam("noon_id") Integer noon_id, @RequestParam("noon_name") String noon_name,
                                   @RequestParam("begin_time_hour") Integer begin_time_hour, @RequestParam("begin_time_minute") Integer begin_time_minute,
                                   @RequestParam("end_time_hour") Integer end_time_hour, @RequestParam("end_time_minute") Integer end_time_minute,
                                   @RequestParam(value = "noon_memo", required = false) String noon_memo, @RequestParam("valid_flag") Integer valid_flag);

    @PostMapping(PREFIX + "/noonManager/update")
    Map<String, Object> updateNoon(@RequestParam("noon_id") Integer noon_id, @RequestParam("noon_name") String noon_name,
                                   @RequestParam("begin_time_hour") Integer begin_time_hour, @RequestParam("begin_time_minute") Integer begin_time_minute,
                                   @RequestParam("end_time_hour") Integer end_time_hour, @RequestParam("end_time_minute") Integer end_time_minute,
                                   @RequestParam(value = "noon_memo", required = false) String noon_memo, @RequestParam("valid_flag") Integer valid_flag);

}
