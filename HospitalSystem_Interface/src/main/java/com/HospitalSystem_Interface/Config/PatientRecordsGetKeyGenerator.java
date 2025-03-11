//package com.HospitalSystem_Interface.Config;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.stereotype.Component;
//import java.lang.reflect.*;
//import java.util.Arrays;
//
//
//@Component("PatientRecordsGet")
//public class PatientRecordsGetKeyGenerator implements KeyGenerator {
//
//    @Override
//    public Object generate(Object target, Method method, Object... params) {
//        Object[] entity = Arrays.stream(params).toArray();
//        String p = (String)entity[0];
//        Patient patient = (Patient)entity[1];
//        String keyName = String.format("%s-%s", patient.getId(), p);
//        return keyName;
//    }
//}
