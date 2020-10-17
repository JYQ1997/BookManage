package com.bookmanage.service;

import com.bookmanage.dao.UserDao;
import com.bookmanage.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YongQiang
 * @Date 2020/10/13 19:19
 * @Version 1.0
 */
public class UserService {

    private static final UserDao userDao = new UserDao();

    /**
     * 登录
     * @param userDto
     * @return
     */
    public UserDto login(UserDto userDto) {
        String sql = "select r.role_id,r.role_name,u.* from sys_user u,sys_role r,sys_user_role ul where user_name = ? and " +
                "password =? and u.user_id=ul.id and ul.role_id=r.role_id";
        List<Object> paramList=new ArrayList<>();
        paramList.add(userDto.getUserName());
        paramList.add(userDto.getPassword());
        List<UserDto> list = userDao.list(sql, paramList);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        //System.out.println(list.toString());
        return null;

    }

    /**
     * 修改密码
     * @param userDto
     * @return
     */
    public boolean updatePassword(UserDto userDto) {
        String sql = "update sys_user set password= ? where user_id= ? ";
        List<Object> paramList=new ArrayList<>();
        paramList.add(userDto.getPassword());
        paramList.add(userDto.getUserId());

        int rows = userDao.updateByDto(sql, paramList);
        if (rows>0){
            return true;
        }
        return false;
    }

    public boolean updateBasicMesg(UserDto userDto) {
        String sql = "update sys_user set name= ? , sex = ? , birth= ? , mobile = ? , email = ? , province = ? , city = ? , district= ? , live_address = ? where user_id= ? ";
        List<Object> paramList=new ArrayList<>();
        paramList.add(userDto.getUserName());
        paramList.add(userDto.getSex());
        paramList.add(userDto.getBirth());
        paramList.add(userDto.getMobile());
        paramList.add(userDto.getEmail());
        paramList.add(userDto.getProvince());
        paramList.add(userDto.getCity());
        paramList.add(userDto.getDistrict());
        paramList.add(userDto.getLiveAddress());
        paramList.add(userDto.getUserId());

        int rows = userDao.updateByDto(sql, paramList);
        if (rows>0){
            return true;
        }
        return false;
    }

    public UserDto selectById(Long userId){
        String sql = "select * from  sys_user where user_id = ? ";
        List<Object> paramList=new ArrayList<>();
        paramList.add(userId);
        List<UserDto> list = userDao.list(sql, paramList);
        if (list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public UserDto select(UserDto userDto){
        String sql = "select * from  sys_user where password=? and  user_name= ? ";
        List<Object> paramList=new ArrayList<>();
        paramList.add(userDto.getPassword());
        paramList.add(userDto.getUserName());
        List<UserDto> list = userDao.list(sql, paramList);
        if (list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
