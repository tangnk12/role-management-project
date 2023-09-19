package com.example.fyp.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fyp.demo.common.Constants;
import com.example.fyp.demo.common.Result;
import com.example.fyp.demo.entity.Dict;
import com.example.fyp.demo.mapper.DictMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.example.fyp.demo.service.IMenuService;
import com.example.fyp.demo.entity.Menu;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tnk
 * @since 2022-09-04
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

@Resource
private IMenuService menuService;

@Resource
private DictMapper dictMapper;


    @PostMapping
    public Result save(@RequestBody Menu  menu){
        return  Result.success(menuService.saveOrUpdate( menu));
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(menuService.removeById(id));
    }
    @GetMapping
    public Result findAll(@RequestParam(defaultValue= "") String name){


        return  Result.success(menuService.findMenus(name));
    }
    @GetMapping("{/id}")
    public Result findOne(@PathVariable Integer id){

        return Result.success(menuService.getById(id));
    }


    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue="") String name,@RequestParam Integer pageNum,@RequestParam Integer pageSize){

        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);

       return  Result.success(menuService.page(new Page<>(pageNum,pageSize),queryWrapper));

    }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(menuService.removeBatchByIds(ids));
    }
    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constants.Dict_Type_Icon);
        return Result.success(dictMapper.selectList(null));
    }
}


