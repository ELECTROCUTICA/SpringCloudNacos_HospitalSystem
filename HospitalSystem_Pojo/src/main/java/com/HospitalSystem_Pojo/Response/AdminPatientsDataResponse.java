package com.HospitalSystem_Pojo.Response;

import com.HospitalSystem_Pojo.Entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdminPatientsDataResponse {
    private ArrayList<Patient> patients;
    private int patients_count;
    private int pages_count;
    private int current;
}
