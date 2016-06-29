package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by kiosk on 6/23/16.
 */
public class oneTicketUI {

    JButton ticketButton;

    public oneTicketUI() {}

    public JButton init(int seat_id,int ticket_price) {

        ticketButton =  new JButton("座位号:"+seat_id+"               "+"票价:"+ticket_price+
                "                 "+"点击退票");
        ticketButton.setFont(new Font("宋体", Font.BOLD,20));
        ticketButton.setBackground(Color.YELLOW);

        return ticketButton;
    }
}
