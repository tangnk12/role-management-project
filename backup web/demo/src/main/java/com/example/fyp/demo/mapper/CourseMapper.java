package com.example.fyp.demo.mapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fyp.demo.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tnk
 * @since 2022-09-08
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    Page<Course> findPage(Page<Course> page,@Param("name") String name);

    void setStudentCourse(@Param("courseId") Integer courseId,@Param("studentId") Integer studentId);

    void deleteStudentCourse(@Param("courseId")Integer courseId, @Param("studentId")Integer studentId);
}
