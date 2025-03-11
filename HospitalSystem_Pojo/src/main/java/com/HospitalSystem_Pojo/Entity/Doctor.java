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
public class Doctor {
    private int doctor_id;
    private String doctor_name;
    private String doctor_spell_code;
    private String doctor_sex;
    private int dep_no;
    private String dep_name;
    private int title_no;
    private String title_name;
    private String doctor_password;
    private int valid_flag;
    private String doctor_description;
    private String create_time;
    private String create_user;
}
