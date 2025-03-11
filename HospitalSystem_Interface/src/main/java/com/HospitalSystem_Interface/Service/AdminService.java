package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;

import java.util.ArrayList;
import java.util.Map;

public interface AdminService {

    /**  管理员登录
     * @param id    登录账号
     * @param password    登录密码
     * @return 查询结果json格式数据
     */
    Map<String, Object> loginHandle(String id, String password);

    /**  管理员分页获取医生列表
     * @param p    页号
     * @param keyword    查询关键词
     * @return 包含科室和医生信息的json格式数据
     */
    Map<String, Object> doctorInfoInterface(String p, String keyword);

    /**  管理员查找某医生
     * @param keyword    查找医生关键字
     * @return 查询结果json格式数据
     */
    ArrayList<Doctor> search(String keyword);

    /**  管理员删除医生
     * @param doctor_id    目标医生职工号
     * @return json格式数据传递消息
     */
    Map<String, Object> deleteDoctor(Integer doctor_id);

    /**  管理员获取医生对象
     * @param doctor_id    医生职工号
     * @return 包含医生对象信息的json格式数据
     */
    Doctor getDoctor(Integer doctor_id);

    Map<String, Object> updateDoctor(Integer doctor_id, String doctor_name, String doctor_sex, Integer dep_no, Integer title_no, String doctor_password,
                                     Integer valid_flag, String doctor_description);


    Map<String, Object> insertDoctor(Integer doctor_id, String doctor_name, String doctor_sex, Integer dep_no, Integer title_no, String doctor_password,
                                     Integer valid_flag, String doctor_description, String create_user);

    /**  管理员获取医院所有科室
     * @return 包含科室对象列表的json格式数据
     */
    ArrayList<Department> getDepartments();

    Map<String, Object> departments(String p);

    /**  管理员获取科室对象
     * @param dep_no    科室编号
     * @return 包含科室对象的json格式数据
     */
    Department getDepartment(Integer dep_no);

    /**  管理员获取某科室的所有医生
     * @param dep_no    科室编号
     * @return 包含某科室下医生列表的json格式数据
     */
    ArrayList<Doctor> getDoctors(Integer dep_no);

    Map<String, Object> insertDepartment(Integer dep_no, String dep_name, String dep_description, Integer valid_flag, String create_user);

    /**  管理员更新某科室信息
     * @param dep_no    科室编号
     * @param dep_name    修改后的科室名称
     * @return json格式数据传递消息
     */
    Map<String, Object> updateDepartment(Integer dep_no, String dep_name, String dep_description, Integer valid_flag);

    /**  管理员迁移一个科室的所有医生到另一科室
     * @param source    源科室
     * @param target    迁移目标科室
     * @return json格式数据传递消息
     */
    Map<String, Object> transfer(Integer source, Integer target);

    /**  管理员获取服务器时间及其排班时间，并获取医院所有科室
     * @return 包含时间、医院科室列表的json格式数据
     */
    Map<String, Object> getSchedule();

    /**  管理员获取某天某科室没有上班的医生
     * @param dep_no    目标日期
     * @param date    目标医生的职工号
     * @return 包含医生信息列表的json格式数据
     */
    ArrayList<Doctor> getDoctorsNoWorkAtDateAndNoon(Integer dep_no, String date, Integer noon_id);

    /**  管理员获取某天某科室的上班医生
     * @param dep_no    目标日期
     * @param date    目标医生的职工号
     * @return 包含医生信息列表的json格式数据
     */
    ArrayList<Doctor> getDoctorsWorkAtDateAndNoon(Integer dep_no, String date, Integer noon_id);

    ArrayList<DoctorScheduleMap> getDoctorsWorkAtDateAndNoon2(Integer dep_no, String date, Integer noon_id);

    /**  管理员安排某医生上班
     * @param work_date    目标日期
     * @param doctor_id    目标医生的职工号
     * @param init_register_count    输入的号源量
     * @return json格式数据传递消息
     */
    Map<String, Object> goToWork(String work_date, Integer doctor_id, Integer noon_id, Integer init_register_count, String submit_user);

    /**  管理员取消某医生的上班安排
     */
    Map<String, Object> cancelSchedule(Integer schedule_id);

    /**  管理员分页获取病人账号列表
     * @param p    页号
     * @param keyword    查找病人时的关键字
     * @return 包含病人信息的json格式数据
     */
    Map<String, Object> patientManager(String p, String keyword);

    /**  管理员重置病人登录密码 默认为123456
     * @param patient_id    目标病人的账号（身份证号码）
     * @return json格式数据传递消息
     */
    Map<String, Object> resetPassword(String patient_id);


    Map<String, Object> titleManager();

    Title getTitle(Integer title_no);

    Map<String, Object> insertTitle(Integer title_no, String title_name, Integer valid_flag);

    Map<String, Object> disableTitle(Integer title_no);

    Map<String, Object> enableTitle(Integer title_no);

    Map<String, Object> updateTitle(Integer title_no, String title_name, Integer valid_flag);


    Map<String, Object> noonManager();

    Noon getNoon(Integer noon_id);

    Map<String, Object> insertNoon(Integer noon_id, String noon_name, Integer begin_time_hour, Integer begin_time_minute,
                                   Integer end_time_hour, Integer end_time_minute, String noon_memo, Integer valid_flag);

    Map<String, Object> disableNoon(Integer noon_id);

    Map<String, Object> enableNoon(Integer noon_id);

    Map<String, Object> updateNoon(Integer noon_id, String noon_name, Integer begin_time_hour, Integer begin_time_minute,
                                   Integer end_time_hour, Integer end_time_minute, String noon_memo, Integer valid_flag);


}