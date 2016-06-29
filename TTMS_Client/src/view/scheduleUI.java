package view;

import model.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by kiosk on 6/21/16.
 */
public class scheduleUI extends JFrame{
    private Socket s ;
    JPanel scheduleUIPanel = new JPanel(new GridLayout(10,1,5,5));
    JButton scheduleUIBackButton = new JButton("返回");
    JLabel scheduleUILabel  = new JLabel("选择演出厅",JLabel.CENTER);

    public JScrollPane init(Socket s) throws IOException{
        this.s = s;
        scheduleUIPanel.add(scheduleUILabel);
        scheduleUILabel.setFont(new Font("宋体",Font.BOLD,25));

        scheduleUIPanel.add(scheduleUIBackButton);
        scheduleUIBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //mainUI.cardBig.removeLayoutComponent(mainUI.cardBigPanel);
                mainUI.cardBig.first(mainUI.cardBigPanel);
                for (int i = 0; i <1 ; i++) {
                    mainUI.cardBig.next(mainUI.cardBigPanel);
                }
            }
        });

        for (int i = 0; i <filmUI.allSchedulePlayStudio.size() ; i++) {
            Schedule schedule = filmUI.allSchedulePlayStudio.get(i);
            System.out.println(schedule);

            JButton studioButton = new oneScheduleUI(s).init(schedule.getSched_id(),schedule.getStudio_id(),
                   schedule.getStudio_name(),schedule.getSched_time().toString(),
                    schedule.getSched_ticket_price());
            scheduleUIPanel.add(studioButton);
        }
        JScrollPane scroll = new JScrollPane(scheduleUIPanel);

        return scroll;
    }
}
