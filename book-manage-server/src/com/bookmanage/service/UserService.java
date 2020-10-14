package com.bookmanage.service;

import com.bookmanage.dao.UserDao;
import com.bookmanage.dto.UserDto;

import java.util.List;

/**
 * @Author YongQiang
 * @Date 2020/10/13 19:19
 * @Version 1.0
 */
public class UserService {

    private static final UserDao userDao=new UserDao();

    public UserDto login(UserDto userDto){
        String sql = "select * from sys_user where user_name = ? and password = ?";
        List<UserDto> list = userDao.list(sql, userDto);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        //System.out.println(list.toString());
        return null;

    }
}
