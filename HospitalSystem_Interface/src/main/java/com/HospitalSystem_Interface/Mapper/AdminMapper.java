package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminMapper {

    @Insert("insert into Admin (id, password) values (#{id}, #{password})")
    void insertAdmin(Admin admin);

    @Update("update Admin set password = #{password} where id = #{id}")
    void updateAdmin(Admin admin);

    @Select("select * from Admin where id = #{id}")
    Admin getAdmin(@Param("id") String id);

    @Select("select * from Admin")
    List<Admin> getAllAdmins();
}
