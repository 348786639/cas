package com.ding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface  SysUserMapper {
    String findUserName(@Param("username") String username);
}
