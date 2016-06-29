package view;

import control.DateJsonValueProcessor;
import model.Schedule;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by kiosk on 6/21/16.
 */
public class oneScheduleUI {
    private Socket s;
    PrintStream ps;

    JButton oneScheduleUIButton;
    String onename;
    int oneschedule_id;
    int onestudio_id;
    double ticket_price;
    Date myDate;

    public oneScheduleUI() {}
    public oneScheduleUI(Socket s) throws IOException{
        this.s = s;
        ps = new PrintStream(s.getOutputStream());
    }

    public JButton init(int schedule_id,int studio_id,String name,String time,Double price) {
        oneschedule_id = schedule_id;
        onestudio_id = studio_id;
        onename = name;
        ticket_price = price;

        //TimeZone.setDefault(TimeZone.getTimeZone("CST"));
        String date = null;
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
            Date d = formatter.parse(time);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.format(d);
            System.out.println(date);
            myDate = df.parse(date);
        } catch(Exception ex) {ex.printStackTrace();}


        oneScheduleUIButton = new JButton("演出厅:"+name+"               "+"时间:"+date+
                "                 "+"价格:"+price);

        oneScheduleUIButton.setFont(new Font("宋体", Font.BOLD,20));
        oneScheduleUIButton.setBackground(Color.YELLOW);



        oneScheduleUIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Schedule schedule = new Schedule();
                schedule.setStudio_id(onestudio_id);
                schedule.setSched_time(myDate);
                schedule.setSched_id(oneschedule_id);
                schedule.setSched_ticket_price(ticket_price);
                schedule.setType(5);

                seatUI.schedule = schedule;

                JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(Date.class,
                        new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
                JSONObject object = JSONObject.fromObject(schedule,jsonConfig);
                System.out.println(object);
                ps.println(object);
                System.out.println(object);
            }
        });

        return  oneScheduleUIButton;
    }
}
