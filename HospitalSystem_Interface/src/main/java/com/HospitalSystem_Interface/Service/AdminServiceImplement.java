package com.HospitalSystem_Interface.Service;

import com.HospitalSystem_Pojo.Map.DoctorScheduleMap;
import com.HospitalSystem_Pojo.Utils.ChineseToPinyinUtils;
import com.HospitalSystem_Pojo.Entity.*;
import com.HospitalSystem_Pojo.JSON.*;
import com.HospitalSystem_Interface.Mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;


@Service
public class AdminServiceImplement implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
//    @Autowired
//    private DoctorArrangementMapper doctorArrangementMapper;
    @Autowired
    private DoctorScheduleMapper doctorScheduleMapper;
    @Autowired
    private TitleMapper titleMapper;
    @Autowired
    private NoonMapper noonMapper;
    @Autowired
    private PatientMapper patientMapper;


    @Override
    public Map<String, Object> loginHandle(String id, String password) {
        Admin spec_admin = adminMapper.getAdmin(id);

        HashMap<String, Object> map = new HashMap<>();
        if (spec_admin != null && password.equals(spec_admin.getPassword())) {
            map.put("status", "ok");
            map.put("message", "登陆成功");
        }
        else {
            map.put("status", "fail");
            map.put("message", "登陆失败，请检查输入的账号和密码");
        }
        return map;
    }

    @Override
    public Map<String, Object> doctorInfoInterface(String p, String keyword) {
        HashMap<String, Object> map = new HashMap<>();
        int pn;             //page now
        if (p == null || p.isEmpty()) {
            pn = 1;
        }
        else {
            pn = Integer.parseInt(p);
        }

        int doctors_count;
        int total_page_count;
        ArrayList<Doctor> doctors;
        if (keyword != null && !keyword.isEmpty()) {
            doctors_count = doctorMapper.searchDoctorsByKeyWord(keyword).size();
            if (doctors_count > 0 && doctors_count % 10 == 0) {
                total_page_count = doctors_count / 10;
            }
            else {
                total_page_count = doctors_count / 10 + 1;
            }
            doctors = (ArrayList<Doctor>) doctorMapper.searchDoctorsForPagination(new Page((pn - 1) * 10, 10, pn), keyword);
        }
        else {
            doctors_count = doctorMapper.getCounts();
            if (doctors_count > 0 && doctors_count % 10 == 0) {
                total_page_count = doctors_count / 10;
            }
            else {
                total_page_count = doctors_count / 10 + 1;
            }
            doctors = (ArrayList<Doctor>) doctorMapper.getDoctorsForPagination(new Page((pn - 1) * 10, 10, pn));
        }

        return Map.of(
                "doctors", doctors,
                "valid_departments", departmentMapper.getAllValidDepartments(),
                "departments", departmentMapper.getAllDepartments(),
                "titles", titleMapper.getAllTitles(),
                "valid_titles", titleMapper.getAllValidTitles(),
                "doctors_count", doctors_count,
                "pages_count", total_page_count,
                "current", pn
        );

    }

    @Override
    public ArrayList<Doctor> search(String keyword) {
        return (ArrayList<Doctor>) doctorMapper.searchDoctorsByKeyWord(keyword);
    }

    @Override
    public Map<String, Object> deleteDoctor(Integer doctor_id) {
        Doctor doctor = doctorMapper.getDoctor(doctor_id);
        HashMap<String, Object> map = new HashMap<>();
        if (doctor != null) {
            doctorMapper.deleteDoctor(doctor);
            map.put("status", "ok");
            map.put("message", "删除医生信息成功");
        } else {
            map.put("status", "fail");
            map.put("message", "删除失败");
        }
        return map;
    }

    @Override
    public Doctor getDoctor(Integer doctor_id) {
        return doctorMapper.getDoctor(doctor_id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> updateDoctor(Integer doctor_id, String doctor_name, String doctor_sex, Integer dep_no, Integer title_no, String doctor_password,
                                            Integer valid_flag, String doctor_description) {
        HashMap<String, Object> map = new HashMap<>();
        Doctor doctor = doctorMapper.getDoctor(doctor_id);
        if (doctor != null) {
            doctor.setDoctor_name(doctor_name);
            doctor.setDoctor_spell_code(ChineseToPinyinUtils.convertNameToPinYin(doctor_name));
            doctor.setDoctor_sex(doctor_sex);
            doctor.setDep_no(dep_no);
            doctor.setDep_name(departmentMapper.getDepartment(dep_no).getDep_name());
            doctor.setTitle_no(title_no);
            doctor.setTitle_name(titleMapper.getTitle(title_no).getTitle_name());
            doctor.setDoctor_password(doctor_password);
            doctor.setValid_flag(valid_flag);
            doctor.setDoctor_description(doctor_description);
            doctor.setCreate_time(doctor.getCreate_time());
            doctor.setCreate_user(doctor.getCreate_user());
            doctorMapper.updateDoctor(doctor);
            map.put("status", "ok");
            map.put("message", "修改成功");
        }
        else {
            map.put("status", "fail");
            map.put("message", "修改失败，不存在该医生信息");
        }
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> insertDoctor(Integer doctor_id, String doctor_name, String doctor_sex, Integer dep_no, Integer title_no, String doctor_password,
                                            Integer valid_flag, String doctor_description, String create_user) {
        HashMap<String, Object> map = new HashMap<>();
        if (doctor_name != null && doctor_sex != null && dep_no != null && title_no != null && doctor_password != null && valid_flag != null
                && doctor_description != null && create_user != null) {
            String doctor_spell_code = ChineseToPinyinUtils.convertNameToPinYin(doctor_name);
            Doctor doctor = new Doctor(doctor_id, doctor_name, doctor_spell_code, doctor_sex, dep_no, departmentMapper.getDepartment(dep_no).getDep_name(),
                    title_no,titleMapper.getTitle(title_no).getTitle_name(), doctor_password, valid_flag, doctor_description, null, create_user);
            doctorMapper.insertDoctor(doctor);
            map.put("status", "ok");
            map.put("message", "创建医生信息完成");
        }
        else {
            map.put("status", "fail");
            map.put("message", "新建失败，请检查输入信息是否有效");
        }
        return map;
    }

    @Override
    public ArrayList<Department> getDepartments() {
        return (ArrayList<Department>) departmentMapper.getAllDepartments();
    }

    @Override
    public Map<String, Object> departments(String p) {
        int pn;             //page now
        if (p == null || p.isEmpty()) {
            pn = 1;
        }
        else {
            pn = Integer.parseInt(p);
        }

        var departments_paged = (ArrayList<Department>)departmentMapper.getDepartmentsForPagination(new Page((pn - 1) * 10, 10, pn));
        var all_departments = (ArrayList<Department>)departmentMapper.getAllDepartments();
        int departments_count = departmentMapper.getCount();
        int pages_count;
        if (departments_count > 0 && departments_count % 10 == 0) {
            pages_count  = departments_count / 10;
        }
        else {
            pages_count  = departments_count / 10 + 1;
        }

        return Map.of(
                "all_departments", all_departments,
                "departments", departments_paged,
                "departments_count", departments_count,
                "pages_count", pages_count,
                "current", pn
        );
    }

    @Override
    public Department getDepartment(Integer dep_no) {
        return departmentMapper.getDepartment(dep_no);
    }

    @Override
    public ArrayList<Doctor> getDoctors(Integer dep_no) {
        Department department = departmentMapper.getDepartment(dep_no);
        return (ArrayList<Doctor>) doctorMapper.getValidDoctorsByDepartment(department);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> insertDepartment(Integer dep_no, String dep_name, String dep_description, Integer valid_flag, String create_user) {
        HashMap<String, Object> map = new HashMap<>();
        if (dep_name != null && valid_flag != null && create_user != null) {
            Department department = new Department(0, dep_name, dep_description, valid_flag, null, create_user);
            departmentMapper.insertDepartment(department);
            map.put("status", "ok");
            map.put("message", "创建科室成功");
            return map;
        }
        map.put("status", "fail");
        map.put("message", "创建失败，请完整地填写必要的信息");
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> updateDepartment(Integer dep_no, String dep_name, String dep_description, Integer valid_flag) {
        HashMap<String, Object> map = new HashMap<>();
        if (dep_no > 0 && dep_name != null) {
            var department = departmentMapper.getDepartment(dep_no);
            department.setDep_name(dep_name);
            department.setDep_description(dep_description);
            department.setValid_flag(valid_flag);
            departmentMapper.updateDepartment(department);
            map.put("status", "ok");
            map.put("message", "修改成功");
            return map;
        }
        map.put("status", "fail");
        map.put("message", "修改失败");
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> transfer(Integer source, Integer target) {
        HashMap<String, Object> map = new HashMap<>();
        if (source >= 0 && target >= 0) {
            departmentMapper.transferDoctorsToDepartment(source, target);
            map.put("status", "ok");
            map.put("message", "迁移完成");
        }
        else {
            map.put("status", "fail");
            map.put("message", "迁移失败");
        }
        return map;
    }

    @Override
    public Map<String, Object> getSchedule() {
        LocalDateTime current = LocalDateTime.now();
        ArrayList<LocalDateTime> times = (ArrayList<LocalDateTime>)IntStream.range(0, 7).mapToObj(i -> current.plusDays(i)).collect(Collectors.toList());


        HashMap<String, DateJSON> dates = new HashMap<>();
        String now = null;
        for (int i = 0; i < 7; i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
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
            String dateStr = String.format("%s %s", times.get(i).format(formatter), dayOfWeek);
            if (i == 0) now = dateStr;
            dates.put(String.valueOf(i), new DateJSON(dateStr, (times.get(i).toString()).substring(0, 10)));
        }

        return Map.of(
                "dates", dates,
                "now", current,
                "departments", departmentMapper.getAllValidDepartments(),
                "noons", noonMapper.getAllValidNoon()
        );
    }

    @Override
    public ArrayList<Doctor> getDoctorsNoWorkAtDateAndNoon(Integer dep_no, String work_date, Integer noon_id) {
        return (ArrayList<Doctor>) doctorMapper.getDoctorsNoWorkAtDateAndNoon(dep_no, work_date, noon_id);
    }

    @Override
    public ArrayList<Doctor> getDoctorsWorkAtDateAndNoon(Integer dep_no, String work_date, Integer noon_id) {
        return (ArrayList<Doctor>) doctorMapper.getDoctorsWorkAtDateAndNoon(dep_no, work_date, noon_id);
    }

    @Override
    public ArrayList<DoctorScheduleMap> getDoctorsWorkAtDateAndNoon2(Integer dep_no, String work_date, Integer noon_id) {
        return (ArrayList<DoctorScheduleMap>) doctorScheduleMapper.getDoctorsWorkAtDateAndNoon2(dep_no, work_date, noon_id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> goToWork(String work_date, Integer doctor_id, Integer noon_id, Integer init_register_count, String submit_user) {
        HashMap<String, Object> map = new HashMap<>();

        DoctorSchedule schedule;
        Date real_work_date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            real_work_date = formatter.parse(work_date);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (doctor_id != null && work_date != null && init_register_count > 0) {
            schedule = new DoctorSchedule(0, work_date, noon_id, doctor_id, doctorMapper.getDoctor(doctor_id).getTitle_no(),
                    doctorMapper.getDoctor(doctor_id).getDep_no(), init_register_count, init_register_count, 0, 1, submit_user, null);
            doctorScheduleMapper.insertDoctorSchedule(schedule);
            map.put("status", "ok");
            map.put("message", "排班成功");
        }
        else {
            map.put("status", "fail");
            map.put("message", "排班失败");
        }
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> cancelSchedule(Integer schedule_id) {
        HashMap<String, Object> map = new HashMap<>();
        DoctorSchedule schedule = doctorScheduleMapper.getDoctorSchedule(schedule_id);
        if (schedule != null) {
            schedule.setValid_flag(0);
            doctorScheduleMapper.updateDoctorSchedule(schedule);
            map.put("status", "ok");
            map.put("message", "取消成功");
            return map;
        }
        map.put("status", "fail");
        map.put("message", "取消失败");
        return map;
    }

    @Override
    public Map<String, Object> patientManager(String p, String keyword) {
        int pn;
        if (p == null || p.isEmpty()) {
            pn = 1;
        }
        else {
            pn = Integer.parseInt(p);
        }

        int patients_count;
        int total_page_count;
        ArrayList<Patient> patients;
        if (keyword != null && !keyword.isEmpty()) {
            patients_count = patientMapper.searchPatientsByKeyword(keyword).size();
            if (patients_count > 0 && patients_count % 10 == 0) {
                total_page_count = patients_count / 10;
            }
            else {
                total_page_count = patients_count / 10 + 1;
            }
            patients = (ArrayList<Patient>) patientMapper.searchPatientsForPagination(new Page((pn - 1) * 10, 10, pn), keyword);
        }
        else {
            patients_count = patientMapper.getCounts();
            if (patients_count > 0 && patients_count % 10 == 0) {
                total_page_count = patients_count / 10;
            }
            else {
                total_page_count = patients_count / 10 + 1;
            }
            patients = (ArrayList<Patient>) patientMapper.getPatientsForPagination(new Page((pn - 1) * 10, 10, pn));
        }

        return Map.of(
                "patients", patients,
                "patients_count", patients_count,
                "pages_count", total_page_count,
                "current", pn
        );

    }

    @Override
    public Map<String, Object> resetPassword(String patient_id) {
        HashMap<String, Object> map = new HashMap<>();
        if (patient_id == null || patient_id.isEmpty()) {
            map.put("status", "fail");
            map.put("message", String.format("重置失败，找不到身份证号码为 %s 的病人账户", patient_id));
        }
        else {
            Patient patient = patientMapper.getPatient(patient_id);
            patient.setPatient_password("123456");
            patientMapper.updatePatient(patient);
            map.put("status", "ok");
            map.put("message", String.format("用户 %s %s 的密码已重置为：123456", patient_id, patient.getPatient_name()));
        }
        return map;
    }

    @Override
    public Map<String, Object> titleManager() {
        HashMap<String, Object> map = new HashMap<>();

        ArrayList<Title> titles = (ArrayList<Title>)titleMapper.getAllTitles();

        for (var i = 0; i < titles.size(); i++) map.put(String.valueOf(i + 1), titles.get(i));
        return map;
    }

    @Override
    public Title getTitle(Integer title_no) {
        return titleMapper.getTitle(title_no);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> insertTitle(Integer title_no, String title_name, Integer valid_flag) {
        HashMap<String, Object> map = new HashMap<>();

        if (title_name == null || title_name.isEmpty()) {
            map.put("status", "fail");
            map.put("message", "添加职称失败，职称名称不能为空");
            return map;
        }

        Title temp = titleMapper.getValidTitleByName(title_name);
        if (temp != null) {
            map.put("status", "fail");
            map.put("message", String.format("添加职称失败，已存在同名且有效的职称 %s", temp.getTitle_name()));
            return map;
        }

        Title title = new Title(0, title_name, 1);
        titleMapper.insertTitle(title);
        map.put("status", "ok");
        map.put("message", "职称创建完成");
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> disableTitle(Integer title_no) {
        HashMap<String, Object> map = new HashMap<>();

        Title title = titleMapper.getTitle(title_no);
        if (title == null) {
            map.put("status", "fail");
            map.put("message", String.format("不存在的职称ID %s", title_no));
            return map;
        }
        title.setValid_flag(0);
        titleMapper.updateTitle(title);
        map.put("status", "ok");
        map.put("message", String.format("职称 %s %s 已禁用", title.getTitle_no(), title.getTitle_name()));
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> enableTitle(Integer title_no) {
        HashMap<String, Object> map = new HashMap<>();

        Title title = titleMapper.getTitle(title_no);
        if (title == null) {
            map.put("status", "fail");
            map.put("message", String.format("不存在的职称ID %s", title_no));
            return map;
        }
        title.setValid_flag(0);
        titleMapper.updateTitle(title);
        map.put("status", "ok");
        map.put("message", String.format("职称 %s %s 已启用", title.getTitle_no(), title.getTitle_name()));
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> updateTitle(Integer title_no, String title_name, Integer valid_flag) {
        HashMap<String, Object> map = new HashMap<>();

        Title title = titleMapper.getTitle(title_no);
        if (title == null) {
            map.put("status", "fail");
            map.put("message", String.format("不存在的职称ID %s", title_no));
            return map;
        }
        title.setTitle_name(title_name);
        title.setValid_flag(valid_flag);
        titleMapper.updateTitle(title);
        map.put("status", "ok");
        map.put("message", String.format("职称ID %s 已更新完成", title_no));
        return map;
    }


    @Override
    public Map<String, Object> noonManager() {

        ArrayList<Noon> noons = (ArrayList<Noon>)noonMapper.getAllNoon();
        ArrayList<String> begin_time_str = new ArrayList<>();
        ArrayList<String> end_time_str = new ArrayList<>();
        for (Noon noon : noons) {
            if (noon.getBegin_time_minute() < 10)
                begin_time_str.add(String.format("%s:%s", noon.getBegin_time_hour(), "0" + noon.getBegin_time_minute()));
            else
                begin_time_str.add(String.format("%s:%s", noon.getBegin_time_hour(), noon.getBegin_time_minute()));

            if (noon.getEnd_time_minute() < 10)
                end_time_str.add(String.format("%s:%s", noon.getEnd_time_hour(), "0" + noon.getEnd_time_minute()));
            else
                end_time_str.add(String.format("%s:%s", noon.getEnd_time_hour(), noon.getEnd_time_minute()));
        }

        return Map.of(
                "noons", noons,
                "begin_time_str", begin_time_str,
                "end_time_str", end_time_str
        );
    }

    @Override
    public Noon getNoon(Integer noon_id) {
        return noonMapper.getNoon(noon_id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> insertNoon(Integer noon_id, String noon_name, Integer begin_time_hour, Integer begin_time_minute,
                                          Integer end_time_hour, Integer end_time_minute, String noon_memo, Integer valid_flag) {
        HashMap<String, Object> map = new HashMap<>();

        if (noon_name == null || noon_name.isEmpty()) {
            map.put("status", "fail");
            map.put("message", "添加午别失败，午别名称不能为空");
            return map;
        }

        Noon temp = noonMapper.getNoon(noon_id);
        if (temp != null) {
            map.put("status", "fail");
            map.put("message", String.format("添加午别失败，已存在午别ID %s", noon_id));
            return map;
        }
        Noon noon = new Noon(0, noon_name, begin_time_hour, begin_time_minute, end_time_hour, end_time_minute, noon_memo, valid_flag);
        noonMapper.insertNoon(noon);
        map.put("status", "ok");
        map.put("message", "添加午别成功");
        return map;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> disableNoon(Integer noon_id) {
        HashMap<String, Object> map = new HashMap<>();

        Noon noon = noonMapper.getNoon(noon_id);
        if (noon == null) {
            map.put("status", "fail");
            map.put("message", String.format("不存在的午别ID %s", noon_id));
            return map;
        }
        noon.setValid_flag(0);
        noonMapper.updateNoon(noon);
        map.put("status", "ok");
        map.put("message", String.format("午别 %s %s 已禁用", noon.getNoon_id(), noon.getNoon_name()));
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> enableNoon(Integer noon_id) {
        HashMap<String, Object> map = new HashMap<>();

        Noon noon = noonMapper.getNoon(noon_id);
        if (noon == null) {
            map.put("status", "fail");
            map.put("message", String.format("不存在的午别ID %s", noon_id));
            return map;
        }
        noon.setValid_flag(1);
        noonMapper.updateNoon(noon);
        map.put("status", "ok");
        map.put("message", String.format("午别 %s %s 已启用", noon.getNoon_id(), noon.getNoon_name()));
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> updateNoon(Integer noon_id, String noon_name, Integer begin_time_hour, Integer begin_time_minute,
                                          Integer end_time_hour, Integer end_time_minute, String noon_memo, Integer valid_flag) {
        HashMap<String, Object> map = new HashMap<>();

        Noon noon = noonMapper.getNoon(noon_id);
        if (noon == null) {
            map.put("status", "fail");
            map.put("message", String.format("不存在的午别 %s", noon_id));
            return map;
        }
        noon.setNoon_name(noon_name);
        noon.setBegin_time_hour(begin_time_hour);
        noon.setBegin_time_minute(begin_time_minute);
        noon.setEnd_time_hour(end_time_hour);
        noon.setEnd_time_minute(end_time_minute);
        noon.setNoon_memo(noon_memo);
        noon.setValid_flag(valid_flag);
        noonMapper.updateNoon(noon);
        map.put("status", "ok");
        map.put("message", String.format("午别 %s 更新成功", noon.getNoon_id()));
        return map;
    }

}