package com.HospitalSystem_Pojo.Utils;

import com.HospitalSystem_Pojo.Entity.Patient;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JWTUtils {
    public static final long keepTime = 24L * 60L * 60L * 1000L;

    private static final String secretString = "Zd+kZozTI5OgURtbegh8E6KTPghNNe/tEFwuLxd2UNw=";
    //private static final SecretKey KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));


    public static String createToken(Map<String, Object> claims) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        long expMillis = nowMillis + keepTime;

        Date expiredDate = new Date(expMillis);

        String token = Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setSubject("auth")
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretString)
                .compact();
        return token;
    }

    //jjwt-0.9.1方法：
    public static Patient getPatientFromToken(String token){
        Patient patient = new Patient(
                (String)Jwts.parser().setSigningKey(secretString).parseClaimsJws(token).getBody().get("id"),
                (String)Jwts.parser().setSigningKey(secretString).parseClaimsJws(token).getBody().get("name"),
                (String)Jwts.parser().setSigningKey(secretString).parseClaimsJws(token).getBody().get("sex"),
                (int)Jwts.parser().setSigningKey(secretString).parseClaimsJws(token).getBody().get("age"),
                new java.sql.Date((long)Jwts.parser().setSigningKey(secretString).parseClaimsJws(token).getBody().get("birthdate")),
                ""
        );
        return patient;
    }

}
