package view;

import model.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by kiosk on 6/23/16.
 */
public class ticketUI {
    
    private Socket s ;
    JPanel ticketUIPanel = new JPanel(new GridLayout(10,1,5,5));
    JButton ticketUIBackButton = new JButton("返回");
    JLabel ticketUILabel  = new JLabel("我的电影票",JLabel.CENTER);
    
    public JScrollPane init() throws IOException {
        ticketUIPanel.add(ticketUILabel);
        ticketUILabel.setFont(new Font("宋体",Font.BOLD,25));

        ticketUIPanel.add(ticketUIBackButton);
        ticketUIBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainUI.cardBig.first(mainUI.cardBigPanel);
                for (int i = 0; i <1 ; i++) {
                    mainUI.cardBig.next(mainUI.cardBigPanel);
                }
            }
        });

        for (int i = 0; i < seatUI.ticketlist.size() ; i++) {
            JButton but = new oneTicketUI().init(seatUI.ticketlist.get(i).getSeatId(),(int)seatUI.ticketlist.get(i).getPrice());

            ticketUIPanel.add(but);
        }
        JScrollPane scroll = new JScrollPane(ticketUIPanel);

        return scroll;

    }
}
