package com.HospitalSystem_Pojo.Response;
import com.HospitalSystem_Pojo.Entity.Department;
import com.HospitalSystem_Pojo.JSON.DateJSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientArrangementResponse {
    private Map<Integer, DateJSON> dates;
    private String now;
    private ArrayList<Department> departments;
}
