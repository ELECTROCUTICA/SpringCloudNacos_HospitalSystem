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
public class Patient {
    private String patient_id;
    private String patient_name;
    private String patient_spell_code;
    private String patient_sex;
    private String patient_birthdate;
    private int patient_age;
    private String patient_phone;
    private String patient_password;
    private String create_time;
}
