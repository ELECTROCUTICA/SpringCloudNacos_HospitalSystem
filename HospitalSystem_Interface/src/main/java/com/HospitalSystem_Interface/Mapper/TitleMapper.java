package com.HospitalSystem_Interface.Mapper;

import com.HospitalSystem_Pojo.Entity.Title;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TitleMapper {

    @Insert("insert into Title(title_no, title_name, valid_flag) values (#{title_no}, #{title_name}, #{valid_flag})")
    void insertTitle(Title title);

    @Update("update Title set title_name = #{title_name}, valid_flag = #{valid_flag} where title_no = #{title_no}")
    void updateTitle(Title title);

    @Delete("delete from Title where title_no = #{title_no}")
    void deleteTitle(Title title);

    @Select("select * from Title where title_no = #{title_no}")
    Title getTitle(@Param("title_no") int title_no);

    @Select("select * from Title where title_name = #{title_name} and valid_flag = 1")
    Title getValidTitleByName(@Param("title_name") String title_name);

    @Select("select * from Title")
    List<Title> getAllTitles();

    @Select("select * from Title where valid_flag = 1")
    List<Title> getAllValidTitles();
}
