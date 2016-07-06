package src.xupt.se.ttms.main;



import src.xupt.se.ttms.model.User;
import src.xupt.se.ttms.view.clerk.LoginFrame;
import src.xupt.se.ttms.view.clerk.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by kiosk on 6/16/16.
 */
public class main {
    private static final int SERVER_PORT = 30000;
    public static List<Socket> socketList = new ArrayList<>();
    public static HashMap<Socket,User> socketUserHashMap = new HashMap<>();

    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(SERVER_PORT);    //创建服务器端的套接字
        if(ss.getLocalSocketAddress() != null) {
            //new logs("服务器启动成功!");
            System.out.println("服务器启动成功!");
        }

        // 初始化服务器端的main界面.
        new Main().main(null);;

        while(true) {
            Socket s = ss.accept();
            socketList.add(s);
            new Thread(new serverThread(s)).start();
        }
    }
}
