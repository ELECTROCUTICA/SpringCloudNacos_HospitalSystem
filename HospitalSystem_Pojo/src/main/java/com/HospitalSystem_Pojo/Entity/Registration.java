package com.HospitalSystem_Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Registration implements Serializable {

    @Serial
    private static final long serialVersionUID = 240;

    private int register_id;
    private int serial_id;
    private int noon_id;
    private int doctor_id;
    private int dep_no;
    private String patient_id;
    private String patient_name;
    private String patient_spell_code;
    private String patient_sex;
    private String patient_birthdate;
    private String patient_phone;
    private int registration_status;
    private String visit_date;
    private String register_time;
}
