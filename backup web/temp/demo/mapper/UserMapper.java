package com.example.fyp.demo.mapper;

import com.example.fyp.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
