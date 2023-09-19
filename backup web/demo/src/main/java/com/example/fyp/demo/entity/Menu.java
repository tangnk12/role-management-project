package com.example.fyp.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

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
  @TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("character name")
      private String name;

      @ApiModelProperty("path")
      private String path;

      @ApiModelProperty("avatar")
      private String icon;

      @ApiModelProperty("decription")
      private String description;

      @TableField(exist=false)
      private List<Menu> children;

      @ApiModelProperty("pid")
      private Integer pid;

      private String pagePath;


}
