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
public class Department {
    private int dep_no;
    private String dep_name;
    private String dep_description;
    private int valid_flag;
    private String create_time;
    private String create_user;
}
