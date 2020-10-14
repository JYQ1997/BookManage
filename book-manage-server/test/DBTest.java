import com.bookmanage.dto.UserDto;
import com.bookmanage.service.UserService;
import com.bookmanage.util.DBUtil;

import java.sql.*;
import java.util.List;

/**
 * @Author YongQiang
 * @Date 2020/10/13 13:52
 * @Version 1.0
 */
public class DBTest {

    private static UserService userService=new UserService();
    public static void main(String[] args) {
        String username = "test";
        String password = "123456";
        boolean b = login(username, password);
        if (b) {
            System.out.println("登陆成功!");
        } else {
            System.out.println("登陆失败!");
        }
    }

    public static boolean login(String username,String password) {
        if (username == null || password == null) {
            return false;
        }

        UserDto userDto=new UserDto();
        userDto.setUserName("test");
        userDto.setPassword("123456");
        String sql = "select * from sys_user where user_name = ? and password = ?";
        UserDto login = userService.login(userDto);
        System.out.println(login);
        return false;
    }
}
