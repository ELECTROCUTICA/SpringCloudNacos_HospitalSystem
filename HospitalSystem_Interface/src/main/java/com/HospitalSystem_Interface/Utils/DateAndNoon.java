package com.HospitalSystem_Interface.Utils;

import com.HospitalSystem_Pojo.Entity.Noon;
import com.HospitalSystem_Pojo.JSON.DateJSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateAndNoon {          //一天对应多个午别
    DateJSON date;
    ArrayList<Noon> valid_noons;
}
