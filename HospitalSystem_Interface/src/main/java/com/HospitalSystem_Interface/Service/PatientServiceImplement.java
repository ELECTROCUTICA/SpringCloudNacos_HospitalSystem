package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Interface.Utils.DateAndNoon;
import com.HospitalSystem_Interface.Utils.RedisUtils;
import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Interface.Mapper.*;
import com.HospitalSystem_Pojo.JSON.DateJSON;
import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import com.HospitalSystem_Pojo.Map.RegistrationMap;
import com.HospitalSystem_Pojo.Utils.ChineseToPinyinUtils;
import com.HospitalSystem_Pojo.Utils.StringUtils;
import com.HospitalSystem_Pojo.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;
import static com.HospitalSystem_Pojo.Utils.TimeComplement.complement;

@Service
public class PatientServiceImplement implements PatientService {
    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

//    @Autowired
//    private DoctorArrangementMapper doctorArrangementMapper;

    @Autowired
    private DoctorScheduleMapper doctorScheduleMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private NoonMapper noonMapper;
    @Autowired
    private RedisUtils redisUtils;

//    @Autowired
//    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> loginHandle(String patient_id, String patient_password) {        //允许身份证号，手机号登录

        HashMap<String, Object> data = new HashMap<>();
        Patient patient_by_id = patientMapper.getPatient(patient_id);


        if (patient_by_id != null && patient_by_id.getPatient_password().equals(patient_password)) {

            var payLoad = new HashMap<String, Object>();
            payLoad.put("patient_id", patient_by_id.getPatient_id());
            payLoad.put("patient_name", patient_by_id.getPatient_name());
            payLoad.put("patient_spell_code", patient_by_id.getPatient_spell_code());
            payLoad.put("patient_sex", patient_by_id.getPatient_sex());
            payLoad.put("patient_birthdate", patient_by_id.getPatient_birthdate());
            payLoad.put("patient_age", patient_by_id.getPatient_age());
            payLoad.put("patient_phone", patient_by_id.getPatient_phone());
            payLoad.put("patient_password", patient_by_id.getPatient_password());
            payLoad.put("create_time", patient_by_id.getCreate_time());

            String token = JWTUtils.createToken(payLoad);

            data.put("status", "ok");
            data.put("message", "登录成功");
            data.put("patient_token", token);
            return data;
        }

        Patient patient_by_phone = patientMapper.getPatientByPhone(patient_id);
        if (patient_by_phone != null && patient_by_phone.getPatient_password().equals(patient_password)) {

            var payLoad = new HashMap<String, Object>();
            payLoad.put("patient_id", patient_by_phone.getPatient_id());
            payLoad.put("patient_name", patient_by_phone.getPatient_name());
            payLoad.put("patient_spell_code", patient_by_phone.getPatient_spell_code());
            payLoad.put("patient_sex", patient_by_phone.getPatient_sex());
            payLoad.put("patient_birthdate", patient_by_phone.getPatient_birthdate());
            payLoad.put("patient_age", patient_by_phone.getPatient_age());
            payLoad.put("patient_phone", patient_by_phone.getPatient_phone());
            payLoad.put("patient_password", patient_by_phone.getPatient_password());
            payLoad.put("create_time", patient_by_phone.getCreate_time());

            String token = JWTUtils.createToken(payLoad);

            data.put("status", "ok");
            data.put("message", "登录成功");
            data.put("patient_token", token);
            return data;
        }

        data.put("status", "fail");
        data.put("message", "登陆失败，请检查您输入的账号和密码");
        return data;
    }

    @Override
    public Map<String, Object> getServerTime() {
        LocalDateTime current = LocalDateTime.now();

        String dayOfWeek = switch (current.getDayOfWeek()) {
            case MONDAY -> "周一";
            case TUESDAY -> "周二";
            case WEDNESDAY -> "周三";
            case THURSDAY -> "周四";
            case FRIDAY -> "周五";
            case SATURDAY -> "周六";
            case SUNDAY -> "周日";
            default -> "异常";
        };

        String dateStr = String.format("%s %s", current.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日")), dayOfWeek);
        String dateParam = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return Map.of(
                "status", "ok",
                "dateText", dateStr,
                "dateParam", dateParam
        );
    }

    @Override
    public Map<String, Object> getRegistrationsToday(String dateParam, Patient patient) {
        ArrayList<RegistrationMap> registrations = (ArrayList<RegistrationMap>)registrationMapper.getRegistrationsMapByPatientAtDate(dateParam, patient.getPatient_id());

//        var i = 0;
//        for (RegistrationMap item : ) {
//            int queue_up_count = registrationMapper.getLineUpCount(item.getVisit_date(), item.getNoon_id(), item.getDoctor_id());
//            String message;
//            if (queue_up_count == 0) {
//                message = String.format("%s %s %s，已排到您，请尽快到%s %s诊室就诊", item.getDep_name(), item.getDoctor_name(), item.getTitle_name(),
//                        item.getDep_name(), item.getDoctor_name());
//            }
//            else {
//                message = String.format("排队中，您还需等待：%d 人", queue_up_count);
//            }
//            data.put(String.valueOf(i), new PatientRegistrationsOfTodayResponse(message, queue_up_count));
//            i++;
//        }

        return Map.of(
                "registrations", registrations
        );
    }



    @Override
    public Map<String, Object> registerHandle(String patient_id, String patient_name, String patient_sex, String patient_birthdate, String patient_phone, String patient_password) {
        if (!StringUtils.isStringNullOrEmpty(patient_id) && !StringUtils.isStringNullOrEmpty(patient_name) &&
                !StringUtils.isStringNullOrEmpty(patient_sex) && !StringUtils.isStringNullOrEmpty(patient_birthdate) &&
                !StringUtils.isStringNullOrEmpty(patient_phone) && !StringUtils.isStringNullOrEmpty(patient_password)) {

            Patient patient = patientMapper.getPatient(patient_id);
            Patient patient2 = patientMapper.getPatientByPhone(patient_phone);
            HashMap<String, Object> map = new HashMap<>();

            if (patient != null) {
                map.put("status", "duplicate");
                map.put("message", "注册失败，该身份证已经注册过账号");
                return map;
            }
            if (patient2 != null) {
                map.put("status", "duplicate");
                map.put("message", "注册失败，该手机号已经绑定已注册的账号");
                return map;
            }

            if (patient_phone.length() != 11) {
                map.put("status", "fail");
                map.put("message", "注册失败，请输入正确的手机号码");
                return map;
            }

            Patient new_patient = new Patient(patient_id, patient_name, ChineseToPinyinUtils.convertNameToPinYin(patient_name),
                    patient_sex, patient_birthdate, 0, patient_phone, patient_password, null);
            patientMapper.insertPatient(new_patient);
            map.put("status", "ok");
            map.put("message", "注册成功，即将返回登录页");
            return map;
        }
        else {
            return Map.of(
                    "status", "fail",
                    "message", "注册失败，请检查您的输入是否有误或为空"
            );
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Cacheable(cacheNames = "patient_records", keyGenerator = "PatientRecordsGet", cacheManager = "RedisCacheManagerTTL")
    public Map<String ,Object> getPatientRecords(String p, Patient patient) {

        HashMap<String, Object> data = new HashMap<>();

        int pn;
        if (p == null || p.isEmpty()) pn = 1;
        else pn = Integer.parseInt(p);

        int records_count;
        int total_page_count;
        ArrayList<RegistrationMap> registrations;

        records_count = registrationMapper.getRegistrationsMapByPatientID(patient.getPatient_id()).size();
        if (records_count > 0 && records_count % 10 == 0){
            total_page_count = records_count / 10;
        }
        else {
            total_page_count = records_count / 10 + 1;
        }
        registrations = (ArrayList<RegistrationMap>)registrationMapper.getRegistrationsMapByPatientIDForPagination(patient.getPatient_id(), new Page((pn - 1) * 10, 10, pn));

        //registrations.stream().forEach((registration) -> registration.setVisit_date(registration.getVisit_date().substring(0, 10)));


        data.put("current", pn);
        data.put("pages_count", total_page_count);
        data.put("records_count", records_count);
        data.put("registrations", registrations);

        return data;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> cancelRegistration(Integer register_id, Patient patient) {
        redisUtils.deletePatientRecordsCache(patient.getPatient_id());

        HashMap<String, Object> response = new HashMap<>();
        Registration updated = registrationMapper.getRegistration(register_id);
        if (register_id != null && updated.getRegistration_status() == 1) {
            updated.setRegistration_status(-1);
            registrationMapper.updateRegistration(updated);
            response.put("status", "ok");
            response.put("message", "取消预约成功");
        }
        else if (updated.getRegistration_status() != 1) {
            response.put("status", "fail");
            response.put("message", "该挂号状态已经被取消或已经被完成");
        }
        else {
            response.put("status", "fail");
            response.put("message", "无效的挂号信息");
        }
        return response;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> editHandle(String patient_id, String patient_name, String patient_sex, String patient_birthdate, String patient_phone, String patient_password) {
        HashMap<String, Object> map = new HashMap<>();

        if (StringUtils.isStringNullOrEmpty(patient_id)) {
            return Map.of(
                    "status", "fail",
                    "message", "修改失败，无效的登录状态"
            );
        }

        if (!StringUtils.isStringNullOrEmpty(patient_name) && !StringUtils.isStringNullOrEmpty(patient_sex) &&
                !StringUtils.isStringNullOrEmpty(patient_birthdate) && !StringUtils.isStringNullOrEmpty(patient_phone) &&
                !StringUtils.isStringNullOrEmpty(patient_password)) {

            Patient orginal_patient = patientMapper.getPatient(patient_id);
            if (orginal_patient.getPatient_name().equals(patient_name) && orginal_patient.getPatient_sex().equals(patient_sex) &&
                    orginal_patient.getPatient_birthdate().equals(patient_birthdate) && orginal_patient.getPatient_phone().equals(patient_phone) &&
                    orginal_patient.getPatient_password().equals(patient_password)) {
                map.put("status", "no_changes");
                map.put("message", "您没有对个人信息作出任何修改");
                return map;
            }

            if (patient_phone.length() != 11) {
                map.put("status", "fail");
                map.put("message", "修改失败，请输入正确的手机号码");
                return map;
            }

            Patient patient2 = patientMapper.getPatientByPhone(patient_phone);
            if (patient2 != null) {
                map.put("status", "fail");
                map.put("message", "修改失败，手机号已经绑定其他账号");
                return map;
            }

            Patient patient_update = new Patient(patient_id, patient_name, ChineseToPinyinUtils.convertNameToPinYin(patient_name), patient_sex,
                    patient_birthdate, 0, patient_phone, patient_password, null);
            patientMapper.updatePatient(patient_update);

            map.put("status", "ok");
            map.put("message", "修改成功，请重新登录");
        }
        else {
            map.put("status", "fail");
            map.put("message", "修改失败，请检查您的输入是否有空或有误");
        }
        return map;
    }

    @Override
    public Map<String, Object> getSchedule() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalDateTime current = LocalDateTime.now();
        LocalTime current_time = LocalTime.parse(String.format("%s:%s:%s", complement(current.getHour()), complement(current.getMinute()), complement(current.getSecond())), timeFormatter);
        ArrayList<LocalDateTime> times = (ArrayList<LocalDateTime>) IntStream.range(0, 7).mapToObj(i -> current.plusDays(i)).collect(Collectors.toList());

        ArrayList<Noon> all_valid_noons = (ArrayList<Noon>)noonMapper.getAllValidNoon();
        ArrayList<DateAndNoon> dan_list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String dayOfWeek = switch (times.get(i).getDayOfWeek()) {
                case MONDAY -> "周一";
                case TUESDAY -> "周二";
                case WEDNESDAY -> "周三";
                case THURSDAY -> "周四";
                case FRIDAY -> "周五";
                case SATURDAY -> "周六";
                case SUNDAY -> "周日";
                default -> "异常";
            };
            String dateStr = String.format("%s %s", times.get(i).format(dateFormatter), dayOfWeek);
            ArrayList<Noon> valid_noons = new ArrayList<>();
            if (i == 0) {               //当天情况，需要移除掉早于现实时间的午别
                for (Noon noon : all_valid_noons) {
                    String time_str = String.format("%s:%s:%s", complement(noon.getEnd_time_hour()), complement(noon.getEnd_time_minute()), "00");  //取结束时间
                    LocalTime lt = LocalTime.parse(time_str, timeFormatter);
                    if (current_time.isBefore(lt)) {
                        valid_noons.add(noon);
                    }
                }
            }
            else {
                valid_noons = all_valid_noons;
            }
            dan_list.add(new DateAndNoon(new DateJSON(dateStr, (times.get(i).toString()).substring(0, 10)), valid_noons));
        }

        return Map.of(
                "date_and_noons", dan_list,
                "departments", departmentMapper.getAllValidDepartments()
        );
    }

    @Override
    public ArrayList<Doctor> searchDoctor(String keyword) {
        return (ArrayList<Doctor>)doctorMapper.searchDoctorsByPatientKeyWord(keyword);
    }

    @Override
    public ArrayList<DoctorScheduleMap> getDoctorScheduleMapInWeek(Integer doctor_id, String start_date, String end_date) {
        return (ArrayList<DoctorScheduleMap>) doctorScheduleMapper.getDoctorScheduleMapInWeek(doctor_id, start_date, end_date);
    }

    @Override
    public ArrayList<DoctorScheduleMap> getDoctorsWorkAtDateAndNoon(String date, Integer noon_id, Integer dep_no) {
        return (ArrayList<DoctorScheduleMap>) doctorScheduleMapper.getDoctorScheduleMapByDepartmentAtDateAndNoon(date, noon_id, dep_no);
    }


    @Override
    public String getDoctorDescription(Integer doctor_id) {
        String description;
        if (doctor_id != null) {
            description = doctorMapper.getDoctorDescription(doctor_id);
            if (description != null && !description.trim().isEmpty()) {
                return description;
            }
        }
        return "暂无简介";
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> submitRegistration(Integer doctor_id, String visit_date, Integer noon_id, Patient patient) {
        //redisUtils.deletePatientRecordsCache(patient.getId());


        if (patient != null && patient.getPatient_id() != null) {

            ArrayList<Registration> collections = (ArrayList<Registration>)registrationMapper.getRegistrationByPatientAtDateAndNoon(patient.getPatient_id(), visit_date, noon_id);
            for (Registration registration : collections) {
                if (doctor_id.equals(registration.getDoctor_id())  && registration.getRegistration_status() == 1) {
                    return Map.of(
                            "status", "duplicate",
                            "message", "挂号失败，请不要重复预约"
                    );
                }
            }

            DoctorSchedule ds = doctorScheduleMapper.getValidDoctorScheduleForUpdate(visit_date, noon_id, doctor_id);       //锁定
            //DoctorScheduleMap ds = doctorScheduleMapper.getValidDoctorScheduleMap(visit_date, noon_id, doctor_id);       //查视图 不锁定
            if (ds.getRemain_register_count() > 0) {
                Doctor doctor = doctorMapper.getDoctor(doctor_id);

                ds.setRemain_register_count(ds.getRemain_register_count() - 1);

                var registration = new Registration(
                        0,
                        registrationMapper.getSerialIDForInsert(visit_date),
                        noon_id,
                        doctor_id,
                        doctor.getDep_no(),
                        patient.getPatient_id(),
                        patient.getPatient_name(),
                        patient.getPatient_spell_code(),
                        patient.getPatient_sex(),
                        patient.getPatient_birthdate(),
                        patient.getPatient_phone(),
                        1,
                        visit_date,
                        null);
                registrationMapper.insertRegistration(registration);
                doctorScheduleMapper.updateDoctorSchedule(ds);

                return Map.of(
                        "status", "ok",
                        "message", "挂号成功"
                );
            }
            else {
                return Map.of(
                        "status", "no_remain",
                        "message", "挂号失败，该医师已无剩余号源"
                );
            }
        }
        else {
            return Map.of(
                    "status", "fail",
                    "message", "无效的挂号预约信息/身份信息"
            );
        }
    }


    @Override
    public int getCounts() {
        return patientMapper.getCounts();
    }

    @Override
    public String getDepartmentsStringList() {
        StringBuilder text = new StringBuilder(128);
        ArrayList<Department> departments = (ArrayList<Department>)departmentMapper.getAllDepartments();
        for (var i = 0; i < departments.size(); i++) {
            text.append(departments.get(i).getDep_name());
            if (i < departments.size() - 1) {
                text.append(",");
            }
        }
        return new String(text);
    }

    @Override
    public ArrayList<DoctorScheduleMap> getDoctorScheduleRecommendation(ArrayList<String> departments) {
        if (departments == null || departments.isEmpty()) {
            return new ArrayList<>();
        }
        String dep_name = departments.get(0);           //暂时只获取DeepSeek提出的一个科室推荐
        return (ArrayList<DoctorScheduleMap>)doctorScheduleMapper.getRecommendedSchedule(dep_name);

    }
}
