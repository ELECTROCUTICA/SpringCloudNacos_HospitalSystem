package com.HospitalSystem_Interface.Controller;


import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;
import com.HospitalSystem_Pojo.Utils.*;
import com.HospitalSystem_Pojo.JSON.*;
import com.HospitalSystem_Interface.Service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminInterfaceController {

    @Autowired
    private AdminService adminService;

    //登录处理
    @PostMapping("/login/loginHandle")
    public Map<String, Object> loginHandle(@RequestParam("id") String id, @RequestParam("password") String password) {
        return adminService.loginHandle(id, password);
    }



    @GetMapping("/doctorinfo")
    public Map<String, Object> doctorInfoInterface(@RequestParam(value = "p", required = false) @Nullable String p, @RequestParam(value = "keyword", required = false) String keyword) {
        return adminService.doctorInfoInterface(p, keyword);
    }

    //医生信息查找
    @GetMapping("/doctorinfo/search")
    public ArrayList<Doctor> search(@RequestParam(value = "keyword", required = false) String keyword) {
        return adminService.search(keyword);
    }

    //医生信息删除
    @PostMapping("/doctorinfo/delete")
    public Map<String, Object> delete(@RequestParam("doctor_id") Integer doctor_id) {
        return adminService.deleteDoctor(doctor_id);
    }

    //返回Doctor对象
    @GetMapping("/doctorinfo/getDoctor")
    public Doctor getDoctor(@RequestParam("doctor_id") Integer doctor_id) {
        return adminService.getDoctor(doctor_id);
    }

    //修改医生信息
    @PostMapping("/doctorinfo/update")
    public Map<String, Object> updateDoctor(@RequestParam("doctor_id") Integer doctor_id,
                                            @RequestParam("doctor_name") String doctor_name,
                                            @RequestParam("doctor_sex") String doctor_sex,
                                            @RequestParam("dep_no") Integer dep_no,
                                            @RequestParam("title_no") Integer title_no,
                                            @RequestParam("doctor_password") String doctor_password,
                                            @RequestParam("valid_flag") Integer valid_flag,
                                            @RequestParam("doctor_description") String doctor_description) {
        return adminService.updateDoctor(doctor_id, doctor_name, doctor_sex, dep_no, title_no, doctor_password, valid_flag, doctor_description);
    }

    //新增医生信息
    @PostMapping("/doctorinfo/insert")
    public Map<String, Object> insertDoctor(@RequestParam("doctor_id") Integer doctor_id,
                                            @RequestParam("doctor_name") String doctor_name,
                                            @RequestParam("doctor_sex") String doctor_sex,
                                            @RequestParam("dep_no") Integer dep_no,
                                            @RequestParam("title_no") Integer title_no,
                                            @RequestParam("doctor_password") String doctor_password,
                                            @RequestParam("valid_flag") Integer valid_flag,
                                            @RequestParam("doctor_description") String doctor_description,
                                            @RequestParam("create_user") String create_user) {
        return adminService.insertDoctor(doctor_id, doctor_name, doctor_sex, dep_no, title_no, doctor_password, valid_flag,
                doctor_description, create_user);
    }

    @GetMapping("/getDepartments")
    public ArrayList<Department> getDepartments() {
        return adminService.getDepartments();
    }

    @GetMapping("/departments")
    public Map<String, Object> departments(@RequestParam(value = "p", required = false) @Nullable String p) {
        return adminService.departments(p);
    }

    //根据科室编号获取科室
    @GetMapping("/departments/getDepartment")
    public Department getDepartment(@RequestParam("dep_no") Integer dep_no) {
        return adminService.getDepartment(dep_no);
    }

    //查看一个科室下的所有医生
    @GetMapping("/departments/getDoctors")
    public ArrayList<Doctor> getDoctors(@RequestParam("dep_no") Integer dep_no) {
        return adminService.getDoctors(dep_no);
    }

    //创建一个新科室
    @PostMapping("/departments/insert")
    public Map<String, Object> insertDepartment(@RequestParam("dep_no") Integer dep_no,
                                      @RequestParam("dep_name") String dep_name,
                                      @RequestParam("dep_description") String dep_description,
                                      @RequestParam("valid_flag") Integer valid_flag,
                                      @RequestParam("create_user") String create_user) {
        return adminService.insertDepartment(dep_no, dep_name, dep_description, valid_flag, create_user);
    }

    //修改科室信息
    @PostMapping("/departments/update")
    public Map<String, Object> updateDepartment(@RequestParam("dep_no") Integer dep_no,
                                      @RequestParam("dep_name") String dep_name,
                                      @RequestParam("dep_description") String dep_description,
                                      @RequestParam("valid_flag") Integer valid_flag) {
        return adminService.updateDepartment(dep_no, dep_name, dep_description, valid_flag);
    }

    //迁移一个科室的所有医生
    @PostMapping("/departments/transfer")
    public Map<String, Object> transfer(@RequestParam("source") Integer source, @RequestParam("target") Integer target) {
        return adminService.transfer(source, target);
    }

    @GetMapping("/getSchedule")
    public Map<String, Object> getSchedule() {
        return adminService.getSchedule();
    }

    //获取当日某科室的未被安排工作的医生
    @GetMapping("/schedule/getDoctorsNoWork")
    public ArrayList<Doctor> getDoctorsNoWork(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id) {
        return adminService.getDoctorsNoWorkAtDateAndNoon(dep_no, date, noon_id);
    }

    //获取当日某科室的工作医生
    @GetMapping("/schedule/getDoctorsWork")
    public ArrayList<Doctor> getDoctorsWork(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id) {
        return adminService.getDoctorsWorkAtDateAndNoon(dep_no, date, noon_id);
    }

    @GetMapping("/schedule/getDoctorsWork2")
    public ArrayList<DoctorScheduleMap> getDoctorsWork2(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id) {
        return adminService.getDoctorsWorkAtDateAndNoon2(dep_no, date, noon_id);
    }

    //安排医生上班
    @PostMapping("/schedule/goToWork")
    public Map<String, Object> goToWork(@RequestParam("work_date") String work_date,
                                        @RequestParam("doctor_id") Integer doctor_id,
                                        @RequestParam("noon_id") Integer noon_id,
                                        @RequestParam("init_register_count") Integer init_register_count,
                                        @RequestParam("submit_user") String submit_user) {
        return adminService.goToWork(work_date, doctor_id, noon_id, init_register_count, submit_user);
    }

    //取消医生排班
    @PostMapping("/schedule/cancel")
    public Map<String, Object> cancelSchedule(@RequestParam("schedule_id") Integer schedule_id) {
        return adminService.cancelSchedule(schedule_id);
    }

    @GetMapping("/patientManager")
    public Map<String, Object> patientManager(@RequestParam(value = "p", required = false) String p, @RequestParam(value = "keyword", required = false) String keyword) {
        return adminService.patientManager(p, keyword);
    }

    @PostMapping("/patientManager/resetPassword")
    public Map<String, Object> resetPassword(@RequestParam("patient_id") String patient_id) {
        return adminService.resetPassword(patient_id);
    }

    @GetMapping("/titleManager")
    public Map<String, Object> titleManager() {
        return adminService.titleManager();
    }

    @GetMapping("/titleManager/getTitle")
    public Title getTitle(@RequestParam("title_no") Integer title_no) {
        return adminService.getTitle(title_no);
    }

    @PostMapping("/titleManager/insert")
    public Map<String, Object> insertTitle(@RequestParam("title_no") Integer title_no, @RequestParam("title_name") String title_name, @RequestParam("valid_flag") Integer valid_flag) {
        return adminService.insertTitle(title_no, title_name, valid_flag);
    }

    @PostMapping("/titleManager/disableTitle")
    public Map<String, Object> disableTitle(@RequestParam("title_no") Integer title_no) {
        return adminService.disableTitle(title_no);
    }

    @PostMapping("/titleManager/enableTitle")
    public Map<String, Object> enableTitle(@RequestParam("title_no") Integer title_no) {
        return adminService.enableTitle(title_no);
    }

    @PostMapping("/titleManager/update")
    public Map<String, Object> updateTitle(@RequestParam("title_no") Integer title_no, @RequestParam("title_name") String title_name, @RequestParam("valid_flag")  Integer valid_flag) {
        return adminService.updateTitle(title_no, title_name, valid_flag);
    }

    @GetMapping("/noonManager")
    public Map<String, Object> noonManager() {
        return adminService.noonManager();
    }

    @GetMapping("/noonManager/getNoon")
    public Noon getNoon(@RequestParam("noon_id") Integer noon_id) {
        return adminService.getNoon(noon_id);
    }

    @PostMapping("/noonManager/insert")
    public Map<String, Object> insertNoon(@RequestParam("noon_id") Integer noon_id, @RequestParam("noon_name") String noon_name,
                                          @RequestParam("begin_time_hour") Integer begin_time_hour, @RequestParam("begin_time_minute") Integer begin_time_minute,
                                          @RequestParam("end_time_hour") Integer end_time_hour, @RequestParam("end_time_minute") Integer end_time_minute,
                                          @RequestParam(value = "noon_memo", required = false) String noon_memo, @RequestParam("valid_flag") Integer valid_flag) {
        return adminService.insertNoon(noon_id, noon_name, begin_time_hour, begin_time_minute, end_time_hour, end_time_minute, noon_memo, valid_flag);
    }

    @PostMapping("/noonManager/disableNoon")
    public Map<String, Object> disableNoon(@RequestParam("noon_id") Integer noon_id) {
        return adminService.disableNoon(noon_id);
    }

    @PostMapping("/noonManager/enableNoon")
    public Map<String, Object> enableNoon(@RequestParam("noon_id") Integer noon_id) {
        return adminService.enableNoon(noon_id);
    }

    @PostMapping("/noonManager/update")
    public Map<String, Object> updateNoon(@RequestParam("noon_id") Integer noon_id, @RequestParam("noon_name") String noon_name,
                                          @RequestParam("begin_time_hour") Integer begin_time_hour, @RequestParam("begin_time_minute") Integer begin_time_minute,
                                          @RequestParam("end_time_hour") Integer end_time_hour, @RequestParam("end_time_minute") Integer end_time_minute,
                                          @RequestParam(value = "noon_memo", required = false) String noon_memo, @RequestParam("valid_flag") Integer valid_flag) {
        return adminService.updateNoon(noon_id, noon_name, begin_time_hour, begin_time_minute, end_time_hour, end_time_minute, noon_memo, valid_flag);
    }


}
