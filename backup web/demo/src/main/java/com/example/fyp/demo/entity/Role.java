package com.example.fyp.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2022-09-04
 */
@Getter
@Setter
@TableName("sys_role")
@ApiModel(value = "Role对象", description = "")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("description")
    private String description;

    @ApiModelProperty("note")
    private String flag;


}
