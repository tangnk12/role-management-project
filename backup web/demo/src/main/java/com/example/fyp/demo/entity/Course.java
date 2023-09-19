package com.example.fyp.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author tnk
 * @since 2022-09-08
 */
@Getter
@Setter
  @ApiModel(value = "Course对象", description = "")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("student id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("course")
      private String name;

      @ApiModelProperty("score")
      private Integer score;

    private String times;

      @ApiModelProperty("disable course")
      private Boolean state;

    @ApiModelProperty("teacherId")
    private Integer teacherId;

    @TableField(exist=false)
    private String teacher;


}
