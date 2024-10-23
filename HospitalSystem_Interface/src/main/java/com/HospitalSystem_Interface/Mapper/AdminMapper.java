package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper {

    void insertAdmin(Admin admin);

    void updateAdmin(Admin admin);

    Admin getAdmin(@Param("id") String id);

    List<Admin> getAllAdmins();
}
