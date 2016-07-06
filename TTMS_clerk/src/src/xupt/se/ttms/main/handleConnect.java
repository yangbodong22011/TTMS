package src.xupt.se.ttms.main;

import net.sf.json.*;
import src.xupt.se.ttms.dao.UserDAO;
import src.xupt.se.ttms.model.User;

import java.net.Socket;

/**
 * Created by kiosk on 6/16/16.
 */
public class handleConnect {
    Socket s = null;
    //org.json.JSONObject object;
    JSONObject object;
    String name;
    String passwd;


    public handleConnect() {}
    /*public handleConnect(Socket s, org.json.JSONObject object) {
        this.s = s;
        this.object = object;
        name = object.get("username").toString();
        passwd = object.get("userPasswd").toString();
    }*/
    public handleConnect(Socket s, JSONObject object) {
        this.s = s;
        this.object = object;
        name = object.get("username").toString();
        passwd = object.get("userPasswd").toString();
    }

    public User passwdIsTure (){

        User user = new UserDAO().select(name);

        //System.out.println(user.getUserPasswd());
        if(user.getUserPasswd() == null || !user.getUserPasswd().equals(passwd)) {
            return user;
        }else if(user.getUserPasswd().equals(passwd)) {
            user.setUserStatus(1);                //设置用户的登录状态为1
            new UserDAO().update(user);
            main.socketUserHashMap.put(s,user);
            new logs("用户 "+main.socketUserHashMap.get(s).getUsername()
                    +" 登录啦!");
        }
        return user;
    }
    public User isHaveUser () {
        System.out.println(name+" "+passwd);
        User user = new UserDAO().select(name);


        if(user.getUsername() == null) {
            User user1 = new User(name,passwd,0,1);
            new UserDAO().insert(user1);
            main.socketUserHashMap.put(s,user1);
            new logs("用户 "+main.socketUserHashMap.get(s).getUsername()
                    +" 注册成功!");
            new logs("用户 "+main.socketUserHashMap.get(s).getUsername()
                    +" 登录啦!");

            user1.setUserStatus(2);           //Status设置为2表示注册
            return user1;
        }else {
            user.setUserStatus(3);            //表示用户已经存在
            return user;
        }
    }
}
