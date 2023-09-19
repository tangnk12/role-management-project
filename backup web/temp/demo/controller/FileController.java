package com.example.fyp.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.fyp.demo.common.Result;
import com.example.fyp.demo.entity.Files;
import com.example.fyp.demo.entity.User;
import com.example.fyp.demo.mapper.FileMapper;
import com.example.fyp.demo.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * file uload interface
 * @return
 *throw Ioexception
 *
 *
 */

@RestController
@RequestMapping("/file")

public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String oriFileName= file.getOriginalFilename();
        String type= FileUtil.extName(oriFileName);
        long fileSize=file.getSize();

        //create verify code
        String uuid=IdUtil.fastSimpleUUID();
        String fileUuid=uuid+StrUtil.DOT+type;
        File uploadFile= new File(fileUploadPath +fileUuid);

      //save to disk
        //verify if the path on application.yml exist, if not create it
        File parentFile=uploadFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        //then already exist, get md5 code
        //verify same file code md5

        String Url;
        file.transferTo(uploadFile);
        String md5=SecureUtil.md5(uploadFile);;

        Files dbFiles=getFileByMd5(md5);
        if(dbFiles!=null){
            Url=dbFiles.getUrl();
            //file exist in file then delete
            uploadFile.delete();
        }
        else{
            //if file didn't contain repeat file the save
            Url="http://localhost:9090/file/"+fileUuid;
        }




        //get file url



        //save to database


        Files saveFile=new Files();

        saveFile.setName(oriFileName);
        saveFile.setType(type);
        saveFile.setSize(fileSize/1024);
        saveFile.setUrl(Url);
        saveFile.setIsDelete(false);
        saveFile.setEnable(true);
        saveFile.setMd5((md5));

        fileMapper.insert(saveFile);

        return Url;

    }
    /**
     * file download path "http://localhost:9090/file/{fileUuid};
     * @return
     *throw Ioexception
     *
     *
     */
    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response)throws IOException{
        File uploadFile=new File(fileUploadPath+fileUuid);
        ServletOutputStream os= response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(fileUuid,"UTF-8"));
        response.setContentType("application/octet-stream");
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();

    }
    private Files getFileByMd5(String md5){
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> fileList=fileMapper.selectList(queryWrapper);
        return fileList.size()==0? null:fileList.get(0);

    }
    //page flip search
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize ,
                           @RequestParam(defaultValue ="") String name
                           ){
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_Delete",false);
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }

        queryWrapper.orderByDesc("id");



        return  Result.success(fileMapper.selectPage(new Page<>(pageNum,pageSize),queryWrapper));

    }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){//select * fron sys_fiile where id in (id,id,id,)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> files =fileMapper.selectList(queryWrapper);
        for( Files file: files){
            file.setIsDelete(true);
            fileMapper.updateById(file);

        }

        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        Files files= fileMapper.selectById(id);
        files.setIsDelete(true);
        return Result.success(fileMapper.updateById(files));
    }
    @PostMapping("/update")
    public Result save(@RequestBody Files file){
        return  Result.success(fileMapper.updateById(file));
    }
}
