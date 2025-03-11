package com.HospitalSystem_Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSchedule {
    private int schedule_id;
    private String work_date;
    private int noon_id;
    private int doctor_id;
    private int title_no;
    private int dep_no;
    private int init_register_count;
    private int remain_register_count;
    private int append_register_count;
    private int valid_flag;
    private String submit_user;
    private String submit_time;
}
