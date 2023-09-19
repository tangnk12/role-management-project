package com.example.fyp.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fyp.demo.entity.Menu;
import com.example.fyp.demo.mapper.MenuMapper;
import com.example.fyp.demo.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tnk
 * @since 2022-09-04
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);

        }
        //search all data
        List<Menu> list=list(queryWrapper);
        //find lv1 menu
        List<Menu> parentNode = list.stream().filter(menu->menu.getPid()==null).collect(Collectors.toList());
        //find parent menu's child menu
        for(Menu menu:parentNode){
            menu.setChildren(list.stream().filter(m->menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return  parentNode;
    }
}
