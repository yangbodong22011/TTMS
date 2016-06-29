package model;

import java.util.ArrayList;

/**
 * Created by kiosk on 6/15/16.
 */
public class User {
    private int    type;
    private String Username;
    private String UserPasswd;
    private int    UserLeftMoney;
    private int    UserStatus;
    private ArrayList<Play>  UserPlay;
    /*
     * UserStatus :   1:成功登录   0:帐号密码验证不正确,登录失败
     *
     * */

    public User () {}
    public User(int type,String username,String userPasswd) {
        this.type = type;
        this.Username = username;
        this.UserPasswd = userPasswd;
    }
    public User(String username,String userPasswd,int UserLeftMoney,int UserStatus) {
        this.Username = username;
        this.UserPasswd = userPasswd;
        this.UserLeftMoney = UserLeftMoney;
        this.UserStatus = UserStatus;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {

        return type;
    }

    public String getUsername() {
        return Username;
    }

    public String getUserPasswd() {
        return UserPasswd;
    }

    public int getUserLeftMoney() {
        return UserLeftMoney;
    }

    public int getUserStatus() {
        return UserStatus;
    }

    public ArrayList<Play> getUserPlay() {
        return UserPlay;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setUserPasswd(String userPasswd) {
        UserPasswd = userPasswd;
    }

    public void setUserLeftMoney(int userLeftMoney) {
        UserLeftMoney = userLeftMoney;
    }

    public void setUserStatus(int userStatus) {
        UserStatus = userStatus;
    }

    public void setUserPlay(ArrayList<Play> userPlay) {
        UserPlay = userPlay;
    }
}