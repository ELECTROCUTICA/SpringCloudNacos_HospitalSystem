package com.HospitalSystem_Admin.Controller;


import com.HospitalSystem_Admin.Service.AdminFeignService;
import com.HospitalSystem_Pojo.Entity.Admin;
import com.HospitalSystem_Pojo.Entity.Department;
import com.HospitalSystem_Pojo.Entity.Doctor;
import com.HospitalSystem_Pojo.Response.AdminArrangementResponse;
import com.HospitalSystem_Pojo.Response.AdminDoctorInfoResponse;
import com.HospitalSystem_Pojo.Response.AdminPatientsDataResponse;
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

//    @Autowired
//    private AdminHttpExchangeService adminHttpExchangeService;

    @Autowired
    private AdminFeignService adminFeignService;

    @PostMapping("/login/loginHandle")
    public Map<String, Object> loginHandle(@RequestParam("id") String id, @RequestParam("password") String password, HttpServletRequest request) {
        Map<String, Object> map = adminFeignService.loginHandle(id, password);

        if (map.get("state").equals("ok")) {
            request.getSession(true).setAttribute("Admin", new Admin(id, password));
        }
        return map;
    }

    @GetMapping("/getAdmin")
    public Admin getAdmin(HttpServletRequest request) {
        return (Admin)request.getSession(false).getAttribute("Admin");
    }

    @GetMapping("/doctorinfo")
    public AdminDoctorInfoResponse doctorinfoInterface(@RequestParam("p") @Nullable String p, @RequestParam(value = "keyword", required = false) String keyword) {
        return adminFeignService.doctorinfoInterface(p, keyword);
    }

    //医生信息查找
    @GetMapping("/doctorinfo/search")
    public ArrayList<Doctor> search(@RequestParam(value = "keyword", required = false) String keyword) {
        return adminFeignService.search(keyword);
    }

    //医生信息删除
    @PostMapping("/doctorinfo/delete")
    public Map<String, Object> delete(@RequestParam("id") String id) {
        return adminFeignService.delete(id);
    }

    //返回Doctor对象
    @GetMapping("/doctorinfo/getDoctor")
    public Doctor getDoctor(@RequestParam("id") String id) {
        return adminFeignService.getDoctor(id);
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
        return adminFeignService.updateDoctor(id, name, sex, dep_no, title, password, description);
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
        return adminFeignService.insertDoctor(id, name, sex, dep_no, title, password, description);
    }

    @GetMapping("/getDepartments")
    public ArrayList<Department> getDepartments() {
        return adminFeignService.getDepartments();
    }

    //根据科室编号获取科室
    @GetMapping("/department/getDepartment")
    public Department getDepartment(@RequestParam("dep_no") Integer dep_no) {
        return adminFeignService.getDepartment(dep_no);
    }

    //查看一个科室下的所有医生
    @GetMapping("/department/getDoctors")
    public ArrayList<Doctor> getDoctors(@RequestParam("dep_no") Integer dep_no) {
        return adminFeignService.getDoctors(dep_no);
    }

    //创建一个新科室
    @PostMapping("/department/insert")
    public Map<String, Object> insert(@RequestParam("dep_no") Integer dep_no, @RequestParam("dep_name") String dep_name) {
        return adminFeignService.insert(dep_no, dep_name);
    }

    //修改科室信息
    @PostMapping("/department/update")
    public Map<String, Object> update(@RequestParam("dep_no") Integer dep_no, @RequestParam("dep_name") String dep_name) {
        return adminFeignService.update(dep_no, dep_name);
    }

    //迁移一个科室的所有医生
    @PostMapping("/department/transfer")
    public Map<String, Object> transfer(@RequestParam("source") Integer source, @RequestParam("target") Integer target) {
        return adminFeignService.transfer(source, target);
    }

    @GetMapping("/getSchedule")
    public AdminArrangementResponse getSchedule() {
        return adminFeignService.getSchedule();
    }


    //获取当日某科室的未被安排工作的医生
    @GetMapping("/schedule/getDoctorsNoWorkAtDate")
    public ArrayList<Doctor> getDoctorsNoWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date) {
        return adminFeignService.getDoctorsNoWorkAtDate(dep_no, date);
    }

    //获取当日某科室的工作医生
    @GetMapping("/schedule/getDoctorsWorkAtDate")
    public ArrayList<Doctor> getDoctorsWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date) {
        return adminFeignService.getDoctorsWorkAtDate(dep_no, date);
    }

    //安排医生上班
    @PostMapping("/schedule/goToWork")
    public Map<String, Object> goToWork(@RequestParam("date") String date, @RequestParam("doctor_id") String doctor_id, @RequestParam("remain") Integer remain) {
        return adminFeignService.goToWork(date, doctor_id, remain);
    }

    //取消医生排班
    @PostMapping("/schedule/cancel")
    public Map<String, Object> cancel(@RequestParam("date") String date, @RequestParam("doctor_id") String doctor_id) {
        return adminFeignService.cancel(date, doctor_id);
    }

    @GetMapping("/patientManager")
    public AdminPatientsDataResponse patientManager(@RequestParam(value = "p", required = false) String p, @RequestParam(value = "keyword", required = false) String keyword) {
        return adminFeignService.patientManager(p, keyword);
    }

    @PostMapping("/patientManager/resetPassword")
    public Map<String, Object> resetPassword(@RequestParam("p_id") String p_id) {
        return adminFeignService.resetPassword(p_id);
    }

    @GetMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Admin") != null) {
            session.removeAttribute("Admin");
        }
        var data = new HashMap<String, Object>();
        data.put("state", "ok");
        data.put("message", "登出成功");
        return data;

    }

}
