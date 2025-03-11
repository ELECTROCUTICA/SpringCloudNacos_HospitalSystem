package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.Noon;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface NoonMapper {

    //new
    @Insert("insert into Noon (noon_id, noon_name, begin_time_hour, begin_time_minute, end_time_hour, end_time_minute, noon_memo, valid_flag) values " +
            "(#{noon_id}, #{noon_name}, #{begin_time_hour}, #{begin_time_minute}, #{end_time_hour}, #{end_time_minute}, #{noon_memo}, #{valid_flag})")
    void insertNoon(Noon noon);

    @Delete("delete from Noon where noon_id = #{noon_id}")
    void deleteNoon(Noon noon);

    @Update("update Noon set valid_flag = 0 where noon_id = #{noon_id}")
    void disableNoon(Noon noon);

    @Update("update Noon set valid_flag = 1 where noon_id = #{noon_id}")
    void enableNoon(Noon noon);

    @Update("update Noon set noon_name = #{noon_name}, begin_time_hour = #{begin_time_hour}, begin_time_minute = #{begin_time_minute}, " +
            "end_time_hour = #{end_time_hour}, end_time_minute = #{end_time_minute}, noon_memo = #{noon_memo}, valid_flag = #{valid_flag} " +
            "where noon_id = #{noon_id}")
    void updateNoon(Noon noon);

    @Select("select * from Noon where noon_id = #{noon_id}")
    Noon getNoon(int noon_id);

    @Select("select * from Noon")
    List<Noon> getAllNoon();

    @Select("select * from Noon where valid_flag = 1")
    List<Noon> getAllValidNoon();

}
