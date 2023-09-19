package com.example.fyp.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fyp.demo.common.Constants;
import com.example.fyp.demo.common.Result;
import com.example.fyp.demo.controller.userDto.UserDTO;
import com.example.fyp.demo.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import com.example.fyp.demo.service.IUserService;
import com.example.fyp.demo.entity.User;






import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tnk
 * @since 2022-08-30
 */
@RestController
@RequestMapping("/user")
public class UserController {
//    @Value("${files.upload.path}")
//    private String fileUploadPath;

@Resource
private IUserService userService;


    @PostMapping
    public Result save(@RequestBody User user){
        return  Result.success(userService.saveOrUpdate(user));
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(userService.removeById(id));
    }
    @GetMapping
    public Result findAll(){

        return  Result.success(userService.list());
    }
    @GetMapping("/role/{role}")
    public Result findUsersByRole(@PathVariable String role){
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("role",role);
        List<User> list=userService.list(queryWrapper);

        return  Result.success(list);
    }
    @GetMapping("{/id}")
    public Result findOne(@PathVariable Integer id){

        return Result.success(userService.getById(id));
    }
    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username){
        QueryWrapper<User> user =new QueryWrapper<>();
        user.eq("username",username);
        return Result.success(userService.getOne(user));
    }


    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize ,
                               @RequestParam(defaultValue ="") String username,
                               @RequestParam(defaultValue ="") String email,
                               @RequestParam(defaultValue ="") String address){
//        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
//        if(!"".equals(username)){
//            queryWrapper.like("username",username);
//        }
//        if(!"".equals(email)){
//            queryWrapper.like("email",email);
//        }
//        if(!"".equals(address)){
//            queryWrapper.like("address",address);
//        }
//        queryWrapper.orderByDesc("id");
//        User currentUser=TokenUtil.getCurrentUser();
//        System.out.println(currentUser.getNickname());

//        return  Result.success(userService.pagePage(new Page<>(pageNum,pageSize),queryWrapper));
        return  Result.success(userService.findPage(new Page<>(pageNum,pageSize),username,email,address));

    }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.removeBatchByIds(ids));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception{
        List<User> list =userService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("username","Username");
        writer.addHeaderAlias("password","Password");
        writer.addHeaderAlias("nickname","Nickname");
        writer.addHeaderAlias("email","Email");
        writer.addHeaderAlias("phone","Phone");
        writer.addHeaderAlias("address","Address");
        writer.addHeaderAlias("createTime","Create time");
        writer.addHeaderAlias("avatarUrl","Avatar");

        writer.write(list,true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("user information","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out= response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();


    }
    @PostMapping("/import")
    public Result imp(MultipartFile file)throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //below method name and database name must be same  and cannot chinese
       // List<User> list = reader.readAll(User.class);

        List<List<Object>> list= reader.read(1);
        ArrayList<User> users=CollUtil.newArrayList();

        //ignore header, hence can read data different database name and can be another launguage
        for(List<Object> row:list){
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());
            users.add(user);

        }
        userService.saveBatch(users);
        return Result.success(true);
    }
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDto){
        String username= userDto.getUsername();
        String password = userDto.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"detail input error");

        }
        UserDTO dto=userService.login(userDto);
        return Result.success(dto);

    }
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDto){
        String username= userDto.getUsername();
        String password = userDto.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"detail input error");

        }
        return Result.success(userService.register(userDto));
    }

}


