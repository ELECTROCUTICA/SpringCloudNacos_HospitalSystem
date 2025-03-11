package com.HospitalSystem_Admin.Controller;


import com.HospitalSystem_Admin.Service.AdminFeignService;
import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminFeignService adminFeignService;

    @PostMapping("/login/loginHandle")
    public Map<String, Object> loginHandle(@RequestParam("id") String id, @RequestParam("password") String password, HttpServletRequest request) {
        Map<String, Object> map = adminFeignService.loginHandle(id, password);

        if (map.get("status").equals("ok")) {
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(12 * 60 * 60);
            session.setAttribute("Admin", new Admin(id, password));
        }
        return map;
    }

    @GetMapping("/getAdmin")
    public Admin getAdmin(HttpServletRequest request) {
        if (request.getSession(false) != null) return (Admin)request.getSession(false).getAttribute("Admin");
        else return (Admin)request.getSession().getAttribute("Admin");
    }

    @GetMapping("/doctorinfo")
    public Map<String, Object> doctorInfoInterface(@RequestParam(value = "p", required = false) @Nullable String p, @RequestParam(value = "keyword", required = false) String keyword) {
        return adminFeignService.doctorInfoInterface(p, keyword);
    }

    //医生信息查找
    @GetMapping("/doctorinfo/search")
    public ArrayList<Doctor> search(@RequestParam(value = "keyword", required = false) String keyword) {
        return adminFeignService.search(keyword);
    }

    //医生信息删除
    @PostMapping("/doctorinfo/delete")
    public Map<String, Object> delete(@RequestParam("doctor_id") Integer doctor_id) {
        return adminFeignService.delete(doctor_id);
    }

    //返回Doctor对象
    @GetMapping("/doctorinfo/getDoctor")
    public Doctor getDoctor(@RequestParam("doctor_id") Integer doctor_id) {
        return adminFeignService.getDoctor(doctor_id);
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
        System.out.println(doctor_id);
        return adminFeignService.updateDoctor(doctor_id, doctor_name, doctor_sex, dep_no, title_no, doctor_password, valid_flag, doctor_description);
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
                                            HttpServletRequest request) {
        Admin admin = (Admin)request.getSession(false).getAttribute("Admin");
        return adminFeignService.insertDoctor(doctor_id, doctor_name, doctor_sex, dep_no, title_no, doctor_password, valid_flag, doctor_description, admin.getId());
    }

    @GetMapping("/getDepartments")
    public ArrayList<Department> getDepartments() {
        return adminFeignService.getDepartments();
    }

    @GetMapping("/departments")
    public Map<String, Object> departments(@RequestParam(value = "p", required = false) @Nullable String p) {
        return adminFeignService.departments(p);
    }

    //根据科室编号获取科室
    @GetMapping("/departments/getDepartment")
    public Department getDepartment(@RequestParam("dep_no") Integer dep_no) {
        return adminFeignService.getDepartment(dep_no);
    }

    //查看一个科室下的所有医生
    @GetMapping("/departments/getDoctors")
    public ArrayList<Doctor> getDoctors(@RequestParam("dep_no") Integer dep_no) {
        return adminFeignService.getDoctors(dep_no);
    }

    //创建一个新科室
    @PostMapping("/departments/insert")
    public Map<String, Object> insertDepartment(@RequestParam("dep_no") Integer dep_no,
                                      @RequestParam("dep_name") String dep_name,
                                      @RequestParam("dep_description") String dep_description,
                                      @RequestParam("valid_flag") Integer valid_flag,
                                      HttpServletRequest request) {
        Admin admin = (Admin)request.getSession(false).getAttribute("Admin");
        return adminFeignService.insertDepartment(dep_no, dep_name, dep_description, valid_flag, admin.getId());
    }

    //修改科室信息
    @PostMapping("/departments/update")
    public Map<String, Object> updateDepartment(@RequestParam("dep_no") Integer dep_no,
                                                @RequestParam("dep_name") String dep_name,
                                                @RequestParam("dep_description") String dep_description,
                                                @RequestParam("valid_flag") Integer valid_flag) {
        return adminFeignService.updateDepartment(dep_no, dep_name, dep_description, valid_flag);
    }

    //迁移一个科室的所有医生
    @PostMapping("/departments/transfer")
    public Map<String, Object> transfer(@RequestParam("source") Integer source, @RequestParam("target") Integer target) {
        return adminFeignService.transfer(source, target);
    }

    @GetMapping("/getSchedule")
    public Map<String, Object> getSchedule() {
        return adminFeignService.getSchedule();
    }


    //获取当日某科室的未被安排工作的医生
    @GetMapping("/schedule/getDoctorsNoWork")
    public ArrayList<Doctor> getDoctorsNoWork(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id) {
        return adminFeignService.getDoctorsNoWork(dep_no, date, noon_id);
    }

    //获取当日某科室的工作医生
    @GetMapping("/schedule/getDoctorsWork")
    public ArrayList<Doctor> getDoctorsWork(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id) {
        return adminFeignService.getDoctorsWork(dep_no, date, noon_id);
    }


    @GetMapping("/schedule/getDoctorsWork2")
    ArrayList<DoctorScheduleMap> getDoctorsWork2(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date, @RequestParam("noon_id") Integer noon_id) {
        return adminFeignService.getDoctorsWork2(dep_no, date, noon_id);
    }

    //安排医生上班
    @PostMapping("/schedule/goToWork")
    public Map<String, Object> goToWork(@RequestParam("work_date") String work_date,
                                        @RequestParam("doctor_id") Integer doctor_id,
                                        @RequestParam("noon_id") Integer noon_id,
                                        @RequestParam("init_register_count") Integer init_register_count,
                                        HttpServletRequest request) {
        Admin admin = (Admin)request.getSession(false).getAttribute("Admin");
        return adminFeignService.goToWork(work_date, doctor_id, noon_id, init_register_count, admin.getId());
    }

    //取消医生排班
    @PostMapping("/schedule/cancel")
    public Map<String, Object> cancelSchedule(@RequestParam("schedule_id") Integer schedule_id) {
        return adminFeignService.cancelSchedule(schedule_id);
    }

    @GetMapping("/patientManager")
    public Map<String, Object>  patientManager(@RequestParam(value = "p", required = false) String p, @RequestParam(value = "keyword", required = false) String keyword) {
        return adminFeignService.patientManager(p, keyword);
    }

    @PostMapping("/patientManager/resetPassword")
    public Map<String, Object> resetPassword(@RequestParam("patient_id") String patient_id) {
        return adminFeignService.resetPassword(patient_id);
    }

    @GetMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("Admin") != null) {
            session.removeAttribute("Admin");
        }
        var data = new HashMap<String, Object>();
        data.put("state", "ok");
        data.put("message", "登出成功");
        return data;
    }

    @GetMapping("/titleManager")
    Map<String, Object> titleManager() {
        return adminFeignService.titleManager();
    }

    @GetMapping("/titleManager/getTitle")
    Title getTitle(@RequestParam("title_no") Integer title_no) {
        return adminFeignService.getTitle(title_no);
    }

    @PostMapping("/titleManager/insert")
    public Map<String, Object> insertTitleManager(@RequestParam("title_no") Integer title_no, @RequestParam("title_name") String title_name, @RequestParam("valid_flag") Integer valid_flag) {
        return adminFeignService.insertTitle(title_no, title_name, valid_flag);
    }

    @PostMapping("/titleManager/update")
    public Map<String, Object> updateTitleManager(@RequestParam("title_no") Integer title_no, @RequestParam("title_name") String title_name, @RequestParam("valid_flag") Integer valid_flag) {
        return adminFeignService.updateTitle(title_no, title_name, valid_flag);
    }

    @GetMapping("/noonManager")
    public  Map<String, Object> noonManager() {
        return adminFeignService.noonManager();
    }

    @GetMapping("/noonManager/getNoon")
    public Noon getNoon(@RequestParam("noon_id") Integer noon_id) {
        return adminFeignService.getNoon(noon_id);
    }


    @PostMapping("/noonManager/insert")
    Map<String, Object> insertNoon(@RequestParam("noon_id") Integer noon_id, @RequestParam("noon_name") String noon_name,
                                   @RequestParam("begin_time_hour") Integer begin_time_hour, @RequestParam("begin_time_minute") Integer begin_time_minute,
                                   @RequestParam("end_time_hour") Integer end_time_hour, @RequestParam("end_time_minute") Integer end_time_minute,
                                   @RequestParam(value = "noon_memo", required = false) String noon_memo, @RequestParam("valid_flag") Integer valid_flag) {
        return adminFeignService.insertNoon(noon_id, noon_name, begin_time_hour, begin_time_minute, end_time_hour, end_time_minute, noon_memo, valid_flag);
    }

    @PostMapping("/noonManager/update")
    Map<String, Object> updateNoon(@RequestParam("noon_id") Integer noon_id, @RequestParam("noon_name") String noon_name,
                                   @RequestParam("begin_time_hour") Integer begin_time_hour, @RequestParam("begin_time_minute") Integer begin_time_minute,
                                   @RequestParam("end_time_hour") Integer end_time_hour, @RequestParam("end_time_minute") Integer end_time_minute,
                                   @RequestParam(value = "noon_memo", required = false) String noon_memo, @RequestParam("valid_flag") Integer valid_flag) {
        return adminFeignService.updateNoon(noon_id, noon_name, begin_time_hour, begin_time_minute, end_time_hour, end_time_minute, noon_memo, valid_flag);
    }
}
