package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Interface.Utils.DateAndNoon;
import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Interface.Mapper.*;
import com.HospitalSystem_Pojo.JSON.DateJSON;
import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import com.HospitalSystem_Pojo.Map.RegistrationMap;
import com.HospitalSystem_Pojo.Utils.ChineseToPinyinUtils;
import com.HospitalSystem_Pojo.Utils.JWTUtils;
import com.HospitalSystem_Pojo.Utils.TimeComplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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

//    @Autowired
//    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> loginHandle(String patient_id, String patient_password) {

        Patient patient = patientMapper.getPatient(patient_id);

        HashMap<String, Object> map = new HashMap<>();

        if (patient != null && patient.getPatient_password().equals(patient_password)) {

            var payLoad = new HashMap<String, Object>();
            payLoad.put("patient_id", patient.getPatient_id());
            payLoad.put("patient_name", patient.getPatient_name());
            payLoad.put("patient_spell_code", patient.getPatient_spell_code());
            payLoad.put("patient_sex", patient.getPatient_sex());
            payLoad.put("patient_birthdate", patient.getPatient_birthdate());
            payLoad.put("patient_age", patient.getPatient_age());
            payLoad.put("patient_phone", patient.getPatient_phone());
            payLoad.put("patient_password", patient.getPatient_password());
            payLoad.put("create_time", patient.getCreate_time());

            String token = JWTUtils.createToken(payLoad);

            map.put("status", "ok");
            map.put("message", "登录成功");
            map.put("patient_token", token);
        }
        else {
            map.put("status", "fail");
            map.put("message", "登陆失败，请检查您输入的账号和密码");
        }
        return map;
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
        if (registrations == null || registrations.isEmpty()) {
            return Map.of(
                    "registrations", new ArrayList<RegistrationMap>()
            );
        }

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

        Patient patient = patientMapper.getPatient(patient_id);
        HashMap<String, Object> map = new HashMap<>();

        if (patient != null) {
            map.put("status", "duplicate");
            map.put("message", "注册失败，该身份账号已存在");
            return map;
        }

        if (patient_id != null && !patient_id.isEmpty() && patient_name != null && !patient_name.isEmpty()
                && patient_sex != null && !patient_sex.isEmpty() && patient_birthdate != null && !patient_birthdate.isEmpty() &&
                patient_password != null && !patient_password.isEmpty()) {
            patient = new Patient(patient_id, patient_name, ChineseToPinyinUtils.convertNameToPinYin(patient_name),
                    patient_sex, patient_birthdate, 0, patient_phone, patient_password, null);
            patientMapper.insertPatient(patient);
            map.put("status", "ok");
            map.put("message", "注册成功，即将返回登录页");
            return map;
        }
        else {
            map.put("status", "fail");
            map.put("message", "注册失败，请检查您的输入是否有误");
            return map;
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    //@Cacheable(value = "PatientRecords", keyGenerator = "PatientRecordsGet", cacheManager = "RedisCacheManagerTTL")
    public Map<String ,Object> getPatientRecords(String p, Patient patient) {

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

        return Map.of(
                "current", pn,
                "pages_count", total_page_count,
                "records_count", records_count,
                "registrations", registrations
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> cancelRegistration(Integer register_id, Patient patient) {
        //redisUtils.deletePatientRecordsCache(patient.getId());

        HashMap<String, Object> map = new HashMap<>();
        Registration updated = registrationMapper.getRegistration(register_id);
        if (register_id != null && updated.getRegistration_status() == 1) {
            updated.setRegistration_status(0);
            registrationMapper.updateRegistration(updated);
            map.put("status", "ok");
            map.put("message", "取消预约成功");
        }
        else if (updated.getRegistration_status() != 1) {
            map.put("status", "fail");
            map.put("message", "该挂号状态已经被取消或已经被完成");
        }
        else {
            map.put("status", "fail");
            map.put("message", "无效的挂号信息");
        }
        return map;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> editHandle(String patient_id, String patient_name, String patient_sex, String patient_birthdate, String patient_phone, String patient_password) {
        HashMap<String, Object> map = new HashMap<>();

        if (patient_id == null || patient_id.isEmpty()) {
            return Map.of(
                    "status", "fail",
                    "message", "修改失败，无效的登录状态"
            );
        }

        if (patient_name != null && !patient_name.isEmpty()
                && patient_sex != null && !patient_sex.isEmpty() && patient_birthdate != null && !patient_birthdate.isEmpty() &&
                patient_password != null && !patient_password.isEmpty()) {

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

        HashMap<String, Object> map = new HashMap<>();

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
        StringBuilder text = new StringBuilder(64);
        ArrayList<Department> departments = (ArrayList<Department>)departmentMapper.getAllDepartments();
        for (var i = 0; i < departments.size(); i++) {
            text.append(departments.get(i).getDep_name());
            if (i < departments.size() - 1) {
                text.append(",");
            }
        }
        return new String(text);
    }
}
