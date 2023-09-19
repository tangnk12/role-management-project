package com.example.fyp.demo.service;

import com.example.fyp.demo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tnk
 * @since 2022-09-04
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
