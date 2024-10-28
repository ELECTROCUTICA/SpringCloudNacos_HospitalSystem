package com.HospitalSystem_Interface.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void deletePatientRecordsCache(String prefix) {
        ArrayList<String> keys = new ArrayList<>();
        ScanOptions options = ScanOptions.scanOptions().match("PatientRecords::" + prefix + "*").count(100).build();

        redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(options)) {
                while (cursor.hasNext()) {
                    String key = new String(cursor.next());
                    keys.add(key);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });


        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }



}
