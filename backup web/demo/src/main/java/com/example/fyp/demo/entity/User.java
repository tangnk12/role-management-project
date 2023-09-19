package com.example.fyp.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author tnk
 * @since 2022-08-30
 */
@Getter
@Setter
@TableName("sys_user")
@ToString
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("username")
      private String username;

      @ApiModelProperty("password")
      private String password;

      @ApiModelProperty("nick name")
      private String nickname;

      @ApiModelProperty("email")
      private String email;

      @ApiModelProperty("pgone no")
      private String phone;

      @ApiModelProperty("address")
      private String address;

      @ApiModelProperty("create time")
      private Date createTime;

      @ApiModelProperty("avatarUrl")
      private String avatarUrl;

      @ApiModelProperty("role")
      private String role;

      @TableField(exist=false)
      private List<Course> courses;

      @TableField(exist=false)
      private List<Course> stuCourses;


}
