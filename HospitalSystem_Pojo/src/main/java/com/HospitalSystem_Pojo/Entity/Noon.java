package com.HospitalSystem_Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Noon {
    private int noon_id;
    private String noon_name;
    private int begin_time_hour;
    private int begin_time_minute;
    private int end_time_hour;
    private int end_time_minute;
    private String noon_memo;
    private int valid_flag;
}
