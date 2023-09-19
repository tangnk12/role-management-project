package com.example.fyp.demo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fyp.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tnk
 * @since 2022-08-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Update("update sys_user set password=#{newPassword} where username= #{username} and password=#{password}" )
//    int updatePassword(UserPasswordDto userPasswordDto);

    Page<User> findPage(Page<User> page, @Param("username") String username,@Param("email") String email,@Param("address") String address);

}
