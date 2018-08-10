package com.raja.smr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.raja.smr.entity.User;

@Mapper
public interface UserMapper {

	@Select("select * from sys_user")
	public List<User> selectAllUser();
	
	@Select("select * from sys_user where username=#{username}")
	public User selectUserByName(String username);
	
}
