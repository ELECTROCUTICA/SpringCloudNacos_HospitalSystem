package com.HospitalSystem_Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Patient implements Serializable {
    @Serial
    private static final long serialVersionUID = 241;

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
