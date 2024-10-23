//package com.HospitalSystem_Admin.Service;
//
//
//import com.HospitalSystem_Pojo.Entity.Admin;
//import com.HospitalSystem_Pojo.Entity.Department;
//import com.HospitalSystem_Pojo.Entity.Doctor;
//import com.HospitalSystem_Pojo.Response.AdminArrangementResponse;
//import com.HospitalSystem_Pojo.Response.AdminDoctorInfoResponse;
//import com.HospitalSystem_Pojo.Response.AdminPatientsDataResponse;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.service.annotation.GetExchange;
//import org.springframework.web.service.annotation.HttpExchange;
//import org.springframework.web.service.annotation.PostExchange;
//
//import java.util.ArrayList;
//import java.util.Map;
//
//@Component
//@HttpExchange("admin")
//public interface AdminHttpExchangeService {
//
//    @PostExchange("/login/loginHandle")
//    Map<String, Object> loginHandle(@RequestParam("id") String id, @RequestParam("password") String password);
//
//
//    @GetExchange("/doctorinfo")
//    AdminDoctorInfoResponse doctorinfoInterface(@RequestParam("p") @Nullable String p, @RequestParam(value = "keyword", required = false) String keyword);
//
//    //医生信息查找
//    @GetExchange("/doctorinfo/search")
//    ArrayList<Doctor> search(@RequestParam(value = "keyword", required = false) String keyword);
//
//    //医生信息删除
//    @PostExchange("/doctorinfo/delete")
//    Map<String, Object> delete(@RequestParam("id") String id);
//
//    //返回Doctor对象
//    @GetExchange("/doctorinfo/getDoctor")
//    Doctor getDoctor(@RequestParam("id") String id);
//
//    //修改医生信息
//    @PostExchange("/doctorinfo/update")
//    Map<String, Object> updateDoctor(@RequestParam("id") String id,
//                                            @RequestParam("name") String name,
//                                            @RequestParam("sex") String sex,
//                                            @RequestParam("dep_no") int dep_no,
//                                            @RequestParam("title") String title,
//                                            @RequestParam("password") String password,
//                                            @RequestParam("description") String description);
//
//    //新增医生信息
//    @PostExchange("/doctorinfo/insert")
//    Map<String, Object> insertDoctor(@RequestParam("id") String id,
//                                            @RequestParam("name") String name,
//                                            @RequestParam("sex") String sex,
//                                            @RequestParam("dep_no") int dep_no,
//                                            @RequestParam("title") String title,
//                                            @RequestParam("password") String password,
//                                            @RequestParam("description") String description);
//
//    @GetExchange("/getDepartments")
//    ArrayList<Department> getDepartments();
//
//    //根据科室编号获取科室
//    @GetExchange("/department/getDepartment")
//    Department getDepartment(@RequestParam("dep_no") Integer dep_no);
//
//    //查看一个科室下的所有医生
//    @GetExchange("/department/getDoctors")
//    ArrayList<Doctor> getDoctors(@RequestParam("dep_no") Integer dep_no);
//
//    //创建一个新科室
//    @PostExchange("/department/insert")
//    Map<String, Object> insert(@RequestParam("dep_no") Integer dep_no, @RequestParam("dep_name") String dep_name);
//
//    //修改科室信息
//    @PostExchange("/department/update")
//    Map<String, Object> update(@RequestParam("dep_no") Integer dep_no, @RequestParam("dep_name") String dep_name);
//
//    //迁移一个科室的所有医生
//    @PostExchange("/department/transfer")
//    Map<String, Object> transfer(@RequestParam("source") Integer source, @RequestParam("target") Integer target);
//
//    @GetExchange("/getSchedule")
//    public AdminArrangementResponse getSchedule();
//
//
//    //获取当日某科室的未被安排工作的医生
//    @GetExchange("/schedule/getDoctorsNoWorkAtDate")
//    ArrayList<Doctor> getDoctorsNoWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date);
//
//    //获取当日某科室的工作医生
//    @GetExchange("/schedule/getDoctorsWorkAtDate")
//    ArrayList<Doctor> getDoctorsWorkAtDate(@RequestParam("dep_no") Integer dep_no, @RequestParam("date") String date);
//
//    //安排医生上班
//    @PostExchange("/schedule/goToWork")
//    Map<String, Object> goToWork(@RequestParam("date") String date, @RequestParam("doctor_id") String doctor_id, @RequestParam("remain") Integer remain);
//
//    //取消医生排班
//    @PostExchange("/schedule/cancel")
//    Map<String, Object> cancel(@RequestParam("date") String date, @RequestParam("doctor_id") String doctor_id);
//
//    @GetExchange("/patientManager")
//    AdminPatientsDataResponse patientManager(@RequestParam(value = "p", required = false) String p, @RequestParam(value = "keyword", required = false) String keyword);
//
//    @PostExchange("/patientManager/resetPassword")
//    Map<String, Object> resetPassword(@RequestParam("p_id") String p_id);
//}
