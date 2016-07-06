package src.xupt.se.ttms.dao;

import src.xupt.se.ttms.model.User;
import src.xupt.se.util.DBUtil;

import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by kiosk on 6/16/16.
 */
public class UserDAO {
    public User select(String name) {
        User user = new User();
        try {
            String sql = "select * from user where user_name = "
                    + " '" + name +"'";
            DBUtil db = new DBUtil();
            //System.out.println(sql);
            db.openConnection();
            ResultSet rst = db.execQuery(sql);
            if(rst != null) {
                if(rst.next()) {
                    user.setUsername(rst.getString("user_name"));
                    user.setUserPasswd(rst.getString("user_passwd"));
                    user.setUserLeftMoney(rst.getInt("user_leftmoney"));
                    user.setUserStatus(rst.getInt("user_status"));
                }
            }
            db.close(rst);
            db.close();
            return user;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int update(User user) {
        int rtn=0;
        try {
            String sql = "update user set " + " user_name ='"
                    + user.getUsername() + "', " + " user_passwd = '"
                    + user.getUserPasswd() + "', " + " user_leftmoney = '"
                    + user.getUserLeftMoney() + "', " + " user_status = '"
                    + user.getUserStatus() + "'" ;


            sql += " where user_name = " + "'"+user.getUsername()+"'";

            System.out.println();
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn =db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }
    public int insert(User user) {
        int rtn=0;
        try {
            String sql = "insert user(user_name,user_passwd,user_leftmoney,user_status) "
                    +" values('"
                    +user.getUsername()+ "',"
                    +"'"+user.getUserPasswd()+"',"
                    +"'"+user.getUserLeftMoney()+"',"
                    +"'"+user.getUserStatus()+"')";
            System.out.println(sql);
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn =db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }
}
