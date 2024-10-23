package com.HospitalSystem_Pojo.Response;

import com.HospitalSystem_Pojo.Map.RegistrationMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientRecordsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<RegistrationMap> registrations;
    private int records_count;
    private int pages_count;
    private int current;
}
