package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by kiosk on 6/20/16.
 */
public class mainUI extends JFrame {
    private Socket s;              //传递数据的套接字
    public static JFrame frame = new JFrame();
    public static CardLayout cardBig = new CardLayout();
    public static JPanel cardBigPanel = new JPanel(cardBig);
    public static CardLayout cardSmall = new CardLayout();
    public static JPanel cardSmallPanel = new JPanel(cardSmall);

    public mainUI() {}
    public mainUI(Socket s) {
        this.s = s;
    }

    public void init() throws IOException{
        frame = this;

        JPanel loginUI = new loginUI(s).init();
        cardBigPanel.add(loginUI);
        frame.add(cardBigPanel);


        frame.setTitle("未来影院");
        frame.setSize(800,650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
