package src.xupt.se.ttms.main;

import src.xupt.se.ttms.dao.LogsDAO;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kiosk on 6/16/16.
 */
public class logs {

    public logs() {}
    public logs(String str) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        System.out.println(date+"   " +str);
        new LogsDAO().insert(date,str);
    }


}
