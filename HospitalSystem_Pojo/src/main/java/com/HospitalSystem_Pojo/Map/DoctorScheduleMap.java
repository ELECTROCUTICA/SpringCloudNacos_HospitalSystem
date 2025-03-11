package com.HospitalSystem_Pojo.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleMap {
    private int schedule_id;
    private String work_date;
    private int noon_id;
    private String noon_name;
    private int begin_time_hour;
    private int begin_time_minute;
    private int end_time_hour;
    private int end_time_minute;
    private int doctor_id;
    private String doctor_name;
    private int title_no;
    private String title_name;
    private int dep_no;
    private String dep_name;
    private int init_register_count;
    private int remain_register_count;
    private int append_register_count;
    private int valid_flag;
    private String submit_id;
    private String submit_time;
}
