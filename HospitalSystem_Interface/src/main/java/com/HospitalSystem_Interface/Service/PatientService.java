package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.Map.*;
import com.HospitalSystem_Pojo.Response.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
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
     * @param id  登录账号
     * @param password  登录密码
     * @return json格式数据传递消息
     */
    Map<String, Object> loginHandle(String id, String password);

    /**  病人注册
     * @param id  病人身份证号码
     * @param name  病人姓名
     * @param birthdate  病人出生日期
     * @param password  病人登录密码
     * @return json格式数据传递消息
     */
    Map<String, Object> registerHandle(String id, String name, String sex, String birthdate, String password);

    /**  分页获取病人的挂号记录,
     * @param p  页号
     * @param patient  Controller传递的patient对象
     * @return json格式数据传递消息
     */
    PatientRecordsResponse getPatientRecords(String p, Patient patient);

    /**  病人取消预约挂号
     * @param reg_id  挂号编号
     * @param patient  Controller传递的patient对象
     * @return 包含病人挂号记录的json格式数据
     */
    Map<String, Object> cancelRegistration(String reg_id, Patient patient);

    /**  病人获取某日某科室上班医生的列表
     * @param id  病人身份证号码（不可更改）
     * @param name  修改后的个人姓名
     * @param birthdate  修改后的出生日期
     * @param password  修改后的登录密码
     * @return json格式数据传递消息
     */
    Map<String, Object> editHandle(String id, String name, String sex, String birthdate, String password, Patient patient);

    /**  病人获取医院的科室列表
     * @return 包含医院科室列表的json格式数据
     */
    PatientArrangementResponse getArrangement();

    /**  病人获取某日某科室上班医生的列表
     * @param dep_no  目标科室
     * @param date  目标时间
     * @return 包含当天当科上班的医生json格式数据
     */
    ArrayList<DoctorArrangementMap> getDoctorsWorkAtDate(Integer dep_no, String date);

    /**  病人获取指定医师的简介
     * @param doctor_id   目标医师
     * @param date  目标时间
     * @return 指定医生的简介
     */
    String getDoctorDescription(String doctor_id, String date);

    /**  病人提交挂号申请
     * @param doctor_id    挂号的目标医师
     * @param date  预约就医日期
     * @return json格式数据传递消息
     */
    Map<String, Object> registrationSubmit(String doctor_id, String date, Patient patient);

    int getCounts();

    Patient getPatientOffline(String id);
}