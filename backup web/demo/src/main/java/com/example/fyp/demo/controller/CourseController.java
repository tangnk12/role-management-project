package com.example.fyp.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fyp.demo.common.Result;
import com.example.fyp.demo.entity.User;
import com.example.fyp.demo.service.IUserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.example.fyp.demo.service.ICourseService;
import com.example.fyp.demo.entity.Course;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tnk
 * @since 2022-09-08
 */
@RestController
@RequestMapping("/course")
public class CourseController {

@Resource
private ICourseService courseService;

@Resource
private IUserService userService;


    @PostMapping
    public Result save(@RequestBody Course  course){
        return  Result.success(courseService.saveOrUpdate( course));
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(courseService.removeById(id));
    }
    @GetMapping
    public Result findAll(){

        return  Result.success(courseService.list());
    }
    @GetMapping("{/id}")
    public Result findOne(@PathVariable Integer id){

        return Result.success(courseService.getById(id));
    }



    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String name){

//        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
//        Page<Course> page =courseService.page(new Page<>(pageNum,pageSize));
//        List<Course> records=page.getRecords();
//        for(Course record:records){
//            User user =userService.getById(record.getTeacherId());
//            if(user!=null){
//                record.setTeacher(user.getNickname());
//
//            }
//
//        }
        Page<Course> page =courseService.findPage(new Page<>(pageNum,pageSize),name);

        return  Result.success(page);

    }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(courseService.removeBatchByIds(ids));
    }
    @PostMapping("/studentCourse/{courseId}/{studentId}")
    public Result studentCourse(@PathVariable Integer  courseId,@PathVariable Integer studentId){

        courseService.setStudentCourse(courseId,studentId);
        return  Result.success();
    }
}


