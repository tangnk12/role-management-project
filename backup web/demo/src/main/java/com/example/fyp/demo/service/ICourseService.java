package com.example.fyp.demo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fyp.demo.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tnk
 * @since 2022-09-08
 */
public interface ICourseService extends IService<Course> {
    Page<Course> findPage(Page<Course> page, String name);

    void setStudentCourse(Integer courseId, Integer studentId);
}
