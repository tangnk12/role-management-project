package com.example.fyp.demo.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.fyp.demo.entity.User;
import com.example.fyp.demo.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Component
public class TokenUtil {

    /**
     *
     *
     * build token
     *
     * @return
     */
    private static IUserService staticUserService;
    @Resource
    private IUserService userService;
    @PostConstruct
    public void setUserService(){
        staticUserService=userService;
    }
    public static String genToken(String userId,String sign){
        String x=JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))
                .sign(Algorithm.HMAC256(sign));

        return x;

    }
    public static User getCurrentUser(){

        try{
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token=request.getHeader("token");
            if(StrUtil.isNotBlank(token)) {
                String aud= JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(aud));
            }


        }
        catch(Exception e){
            return null;

        }
        return  null;

    }

}
