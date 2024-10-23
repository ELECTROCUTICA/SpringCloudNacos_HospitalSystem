package com.HospitalSystem_Pojo.Response;

import com.HospitalSystem_Pojo.Entity.Department;
import com.HospitalSystem_Pojo.Entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdminDoctorInfoResponse {
    private ArrayList<Doctor> doctors;
    private ArrayList<Department> departments;
    private int doctors_count;
    private int pages_count;
    private int current;
}
