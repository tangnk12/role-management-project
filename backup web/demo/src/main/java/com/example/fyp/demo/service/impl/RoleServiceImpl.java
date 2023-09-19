package com.example.fyp.demo.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fyp.demo.entity.Menu;
import com.example.fyp.demo.entity.Role;
import com.example.fyp.demo.entity.RoleMenu;
import com.example.fyp.demo.mapper.RoleMapper;
import com.example.fyp.demo.mapper.RoleMenuMapper;
import com.example.fyp.demo.service.IMenuService;
import com.example.fyp.demo.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tnk
 * @since 2022-09-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;



    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
//        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("roleId",menuIds);
//        roleMenuMapper.delete(queryWrapper);
        //delete the id covered to be renew avoid stack
        roleMenuMapper.deleteByRoleId(roleId);

        List<Integer> menuIdsCopy= CollUtil.newArrayList(menuIds);
        //get the id and renew new covered id
        for(Integer menuId:menuIds){

            Menu menu =menuService.getById(menuId);

            if(menu.getPid()!=null&&!menuIdsCopy.contains(menu.getPid())){//secondary menu and menuids didnt have parent id
                //then give parent id
                RoleMenu roleMenu=new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());

            }

            RoleMenu roleMenu=new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }

    }

    @Override
    public List<Integer> getRoleId(Integer roleId) {
        return roleMenuMapper.selectByRowId(roleId);
    }

}
