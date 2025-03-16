package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;

public interface PatientService {

    /**  病人注册
     * @return 包含服务器时间的json格式数据
     */
    Map<String, Object> getServerTime();

    /**  病人获取今日的挂号预约记录
     * @param dateParam  日期参数 yyyy-MM-dd
     * @return json格式数据传递消息
     */
    Map<String, Object> getRegistrationsToday(String dateParam, Patient patient);

    /**  病人登录
     * @param patient_id  登录账号
     * @param patient_password  登录密码
     * @return json格式数据传递消息
     */
    Map<String, Object> loginHandle(String patient_id, String patient_password);

    /**  病人注册
     * @param patient_id  病人身份证号码
     * @param patient_name  病人姓名
     * @param patient_sex  病人性别
     * @param patient_birthdate  病人出生日期
     * @param patient_phone  病人联系电话
     * @param patient_password  病人登录密码
     * @return json格式数据传递消息
     */
    Map<String, Object> registerHandle(String patient_id, String patient_name, String patient_sex, String patient_birthdate, String patient_phone, String patient_password);

    /**  分页获取病人的挂号记录,
     * @param p  页号
     * @param patient  Controller传递的patient对象
     * @return json格式数据传递消息
     */
    Map<String ,Object> getPatientRecords(String p, Patient patient);

    /**  病人取消预约挂号
     * @param register_id  挂号ID
     * @param patient  Controller传递的patient对象
     * @return 包含病人挂号记录的json格式数据
     */
    Map<String, Object> cancelRegistration(Integer register_id, Patient patient);

    /**  病人获取某日某科室上班医生的列表
     * @param patient_id  病人身份证号码（不可更改）
     * @param patient_name  修改后的个人姓名
     * @param patient_birthdate  修改后的出生日期
     * @param patient_password  修改后的登录密码
     * @return json格式数据传递消息
     */
    Map<String, Object> editHandle(String patient_id, String patient_name, String patient_sex, String patient_birthdate, String patient_phone, String patient_password);

    /**  病人获取医院的科室列表
     * @return 包含医院科室列表的json格式数据
     */
    Map<String, Object> getSchedule();

    ArrayList<Doctor> searchDoctor(String keyword);

    ArrayList<DoctorScheduleMap> getDoctorScheduleMapInWeek(Integer doctor_id, String start_date, String end_date);

    /**  病人获取某日某科室上班医生的列表
     * @param dep_no  目标科室
     * @param date  目标时间
     * @return 包含当天当科上班的医生json格式数据
     */
    ArrayList<DoctorScheduleMap> getDoctorsWorkAtDateAndNoon(String date, Integer noon_id, Integer dep_no);


    /**  病人获取指定医师的简介
     * @param doctor_id   目标医师
     * @return 指定医生的简介
     */
    String getDoctorDescription(Integer doctor_id);

    Map<String, Object> submitRegistration(Integer doctor_id, String visit_date, Integer noon_id, Patient patient);

    String getDepartmentsStringList();

    ArrayList<DoctorScheduleMap> getDoctorScheduleRecommendation(ArrayList<String> departments);

    int getCounts();
}