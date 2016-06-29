package view;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kiosk on 6/16/16.
 */
public class logs {
    String message ;
    private final static String logPath = "/home/kiosk/Desktop/TTMS_Client/src/journal/log";

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String date = df.format(new Date());

    public logs(String message) throws IOException {
        this.message = message;

        FileWriter writer = new FileWriter(logPath,true);
        writer.write(date+"          "+message+"\n");
        writer.close();

    }
    public void showDialog() {
        JOptionPane.showMessageDialog(null,date+"\n\n"+message,"提示",JOptionPane.DEFAULT_OPTION,null);
    }
}
