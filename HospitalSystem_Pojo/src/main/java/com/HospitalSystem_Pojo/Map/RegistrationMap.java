package com.HospitalSystem_Pojo.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationMap implements Serializable {

    @Serial
    private static final long serialVersionUID = 242;

    private int register_id;
    private int serial_id;
    private int noon_id;
    private String noon_name;
    private int begin_time_hour;
    private int begin_time_minute;
    private int end_time_hour;
    private int end_time_minute;
    private int doctor_id;
    private String doctor_name;
    private int dep_no;
    private String dep_name;
    private int title_no;
    private String title_name;
    private String patient_id;
    private String patient_name;
    private String patient_spell_code;
    private String patient_sex;
    private int patient_age;
    private String patient_birthdate;
    private String patient_phone;
    private int registration_status;
    private String visit_date;
    private String register_time;
}
