package com.HospitalSystem_Pojo.Utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeComplement {

    public static String complement(int value) {
        return value < 10 ? String.format("0%s", value) : String.valueOf(value);
    }
}
