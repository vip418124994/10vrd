package cn.tedu.dao;

import cn.tedu.entity.User;
import cn.tedu.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    public User login(String username, String password) {
       try (Connection conn = DBUtils.getConn()){
           String sql = "select * from vrduser where username=? and password=?";
           PreparedStatement ps = conn.prepareStatement(sql);
           ps.setString(1,username);
           ps.setString(2,password);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
               int id = rs.getInt(1);
               return new User(id,username,password);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
    }

    public boolean check(String username) {
        try (Connection conn = DBUtils.getConn()){
              String sql = "select id from vrduser where username=?";
              PreparedStatement ps = conn.prepareStatement(sql);
              ps.setString(1,username);
              ResultSet rs = ps.executeQuery();
              return rs.next();//是否查询到的布尔值返回
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
