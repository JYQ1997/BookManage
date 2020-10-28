package com.bookmanage.service;

import com.bookmanage.dao.UserDao;
import com.bookmanage.dto.UserDto;
import com.bookmanage.util.PageParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserService {

    private static final UserDao userDao = new UserDao();

    /**
     * 登录
     * @param userDto
     * @return
     */
    public UserDto login(UserDto userDto) {
        String sql = "select r.role_id,r.role_name,u.* from sys_user u,sys_role r,sys_user_role ul where user_name = ? and " +
                "password =? and u.user_id=ul.id and ul.role_id=r.role_id and u.status=1";
        List<Object> paramList=new ArrayList<>();
        paramList.add(userDto.getUserName());
        paramList.add(userDto.getPassword());
        List<UserDto> list = userDao.list(sql, paramList);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
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

    /**
     * 修改个人信息
     * @param userDto
     * @return
     */
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

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    public UserDto selectById(Long userId){
        String sql = "select r.role_id,r.role_name,u.* from sys_user u,sys_role r,sys_user_role ul where u.user_id = ? and u.user_id=ul.id and ul.role_id=r.role_id";
        List<Object> paramList=new ArrayList<>();
        paramList.add(userId);
        List<UserDto> list = userDao.list(sql, paramList);
        if (list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据username查询
     * @param userDto
     * @return
     */
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

    /**
     * 分页查询用户信息
     * @param pageParam
     * @param otherParam
     * @return
     */
    public List<UserDto> selectByPage(PageParam pageParam, HashMap<String,Object> otherParam){
        String sql="select r.role_id,r.role_name,u.* from sys_user u,sys_role r,sys_user_role ul where u.user_id=ul.id and ul.role_id=r.role_id ";
        List<Object> paramList=new ArrayList<>();
        if (otherParam!=null){
            if (otherParam.get("status")!=null){
                //根据图书状态
                sql+=" and  u.status= ? ";
                paramList.add(otherParam.get("status"));
            }
            if (otherParam.get("sort")!=null){
                //根据上传用户查找
                sql+=" order by u.gmt_create";
                if (otherParam.get("sort")=="2")
                    sql+=" desc";
            }
        }
        sql+=" limit ? , ?";

        paramList.add(pageParam.getOffset());//获取偏移量
        paramList.add(pageParam.getPageSize());//获取每页条数
        List<UserDto> list = userDao.list(sql, paramList);
        return list;

    }

    /**
     * 分页计数
     * @param otherParam
     * @return
     */
    public Integer selectCountByParam(HashMap<String,Object> otherParam){
        String sql="select count(*) from sys_user u,sys_role r,sys_user_role ul where u.user_id=ul.id and ul.role_id=r.role_id ";
        List<Object> paramList=new ArrayList<>();
        if (otherParam!=null){
            if (otherParam.get("status")!=null){
                //根据图书状态
                sql+=" and  u.status= ? ";
                paramList.add(otherParam.get("status"));
            }
            if (otherParam.get("sort")!=null){
                //根据上传用户查找
                sql+=" order by u.gmt_create";
                if (otherParam.get("sort")=="2")
                    sql+=" desc";
            }
        }
        Integer count = userDao.count(sql, paramList);
        return count;

    }


    public boolean updateStatus(UserDto userDto){
        String sql="update sys_user set status=? where user_id=?";

        List<Object> paramList=new ArrayList<>();
        paramList.add(userDto.getStatus());
        paramList.add(userDto.getUserId());
        int insert = userDao.updateByDto(sql, paramList);
        if (insert>0){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 更改用户角色
     * @param userDto
     * @return
     */
    public boolean updateRole(UserDto userDto){
        String sql="update sys_user_role set role_id=? where user_id=?";

        List<Object> paramList=new ArrayList<>();
        paramList.add(userDto.getRoleId());
        paramList.add(userDto.getUserId());
        int insert = userDao.updateByDto(sql, paramList);
        if (insert>0){
            return true;
        }
        else {
            return false;
        }
    }

}
