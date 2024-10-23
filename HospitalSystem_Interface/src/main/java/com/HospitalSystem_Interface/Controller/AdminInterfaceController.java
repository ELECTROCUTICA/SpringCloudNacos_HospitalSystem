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
    public AdminDoctorInfoResponse doctorinfoInterface(@RequestParam("p") @Nullable String p, @RequestParam(value = "keyword", required = false) String keyword) {
        return adminService.doctorinfoInterface(p, keyword);
    }

    //医生信息查找
    @GetMapping("/doctorinfo/search")
    public ArrayList<Doctor> search(@RequestParam(value = "keyword", required = false) String keyword) {
        return adminService.search(keyword);
    }

    //医生信息删除
    @PostMapping("/doctorinfo/delete")
    public Map<String, Object> delete(@RequestParam("id") String id) {
        return adminService.deleteDoctor(id);
    }

    //返回Doctor对象
    @GetMapping("/doctorinfo/getDoctor")
    public Doctor getDoctor(@RequestParam("id") String id) {
        return adminService.getDoctor(id);
    }

    //修改医生信息
    @PostMapping("/doctorinfo/update")
    public Map<String, Object> updateDoctor(@RequestParam("id") String id,
                                            @RequestParam("name") String name,
                                            @RequestParam("sex") String sex,
                                            @RequestParam("dep_no") int dep_no,
                                            @RequestParam("title") String title,
                                            @RequestParam("password") String password,
                                            @RequestParam("description") String description) {
        return adminService.updateDoctor(id, name, sex, dep_no, title, password, description);
    }

    //新增医生信息
    @PostMapping("/doctorinfo/insert")
    public Map<String, Object> insertDoctor(@RequestParam("id") String id,
                                            @RequestParam("name") String name,
                                            @RequestParam("sex") String sex,
                                            @RequestParam("dep_no") int dep_no,
                                            @RequestParam("title") String title,
                                            @RequestParam("password") String password,
                                            @RequestParam("description") String description) {
        return adminService.insertDoctor(id, name, sex, dep_no, title, password, description);
    }

    @GetMapping("/getDepartments")
    public ArrayList<Department> getDepartments() {
        return adminService.getDepartments();
    }

    //根据科室编号获取科室
    @GetMapping("/department/getDepartment")
    public Department getDepartment(@RequestParam("dep_no") Integer dep_no) {
        return adminService.getDepartment(dep_no);
    }

    //查看一个科室下的所有医生
    @GetMapping("/department/getDoctors")
    public ArrayList<Doctor> getDoctors(@RequestParam("dep_no") Integer dep_no) {
        return adminService.getDoctors(dep_no);
    }

    //创建一个新科室
    @PostMapping("/department/insert")
    public Map<String, Object> insert(@RequestParam("dep_no") Integer dep_no, @RequestParam("dep_name") String dep_name) {
        return adminService.insertDepartment(dep_no, dep_name);
    }

    //修改科室信息
    @PostMapping("/department/update")
    public Map<String, Object> update(@RequestParam("dep_no") Integer dep_no, @RequestParam("dep_name") String dep_name) {
        return adminService.updateDepartment(dep_no, dep_name);
    }

    //迁移一个科室的所有医生
    @PostMapping("/department/transfer")
    public Map<String, Object> transfer(@RequestParam("source") Integer source, @RequestParam("target") Integer target) {
        return adminService.transfer(source, target);
    }

    @GetMapping("/getSchedule")
    public AdminArrangementResponse getSchedule() {
        return adminService.getSchedule();
    }


    //获取当日某科室的未被安排工作的医生
    @GetMapping("/schedule/getDoctorsNoWorkAtDate")
    public ArrayList<Doctor> getDoctorsNoWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date) {
        return adminService.getDoctorsNoWorkAtDate(dep_no, date);
    }

    //获取当日某科室的工作医生
    @GetMapping("/schedule/getDoctorsWorkAtDate")
    public ArrayList<Doctor> getDoctorsWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date) {
        return adminService.getDoctorsWorkAtDate(dep_no, date);
    }

    //安排医生上班
    @PostMapping("/schedule/goToWork")
    public Map<String, Object> goToWork(@RequestParam("date") String date, @RequestParam("doctor_id") String doctor_id, @RequestParam("remain") Integer remain) {
        return adminService.goToWork(date, doctor_id, remain);
    }

    //取消医生排班
    @PostMapping("/schedule/cancel")
    public Map<String, Object> cancel(@RequestParam("date") String date, @RequestParam("doctor_id") String doctor_id) {
        return adminService.cancelSchedule(date, doctor_id);
    }

    @GetMapping("/patientManager")
    public AdminPatientsDataResponse patientManager(@RequestParam(value = "p", required = false) String p, @RequestParam(value = "keyword", required = false) String keyword) {
        return adminService.patientManager(p, keyword);
    }

    @PostMapping("/patientManager/resetPassword")
    public Map<String, Object> resetPassword(@RequestParam("p_id") String p_id) {
        return adminService.resetPassword(p_id);
    }

}
