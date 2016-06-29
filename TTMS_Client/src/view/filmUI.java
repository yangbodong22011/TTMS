package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import control.main;
import model.Play;
import model.Schedule;
import model.Seat;
import model.Ticket;

/**
 * Created by kiosk on 6/20/16.
 */

//登录之后整体的第一个界面

public class filmUI extends  JPanel{
    private Socket s;              //传递数据的套接字
    JButton clickButton;           //点击按钮
    PrintStream ps;
    public static List<Play> allSchedulePlay = new ArrayList<>();
    public static List<Schedule> allSchedulePlayStudio = new ArrayList<>();
    public static List<Seat> allSchedulePlayStudioSeat = new ArrayList<>();
    public static List<Ticket> allSchedulePlayStudioSeatTicket = new ArrayList<>();

    JPanel filmUIPanel = new JPanel(new BorderLayout());
    JPanel filmUIPanelUP = new JPanel(new GridLayout(1,7,5,5));

    JLabel filmUIPanelUserHeadImage = new JLabel(new ImageIcon("/home/kiosk/Desktop/TTMS_Client/src/source/images/touxiang.png"));
    JLabel filmUIPanelUserName = new JLabel(main.user.getUsername());
    JLabel filmUIPanelLeftMoney = new JLabel("$" + main.user.getUserLeftMoney());
    JButton filmUIPanelViewFilmButton = new JButton("正在上映");
    JButton filmUIPanelRechargeButton = new JButton("我要充值");
    JButton filmUIPanelChangePasswdButton = new JButton("修改密码");
    JButton filmUIPanelViewChargedButton = new JButton("我的影票");

    public filmUI() {}
    public filmUI(Socket s) throws IOException{
        this.s = s;
        ps = new PrintStream(s.getOutputStream());
    }

    public JPanel init() throws IOException{
        filmUIPanel.add(filmUIPanelUP,BorderLayout.NORTH);

        filmUIPanelUP.add(filmUIPanelUserHeadImage);
        filmUIPanelUP.add(filmUIPanelUserName);
        filmUIPanelUserName.setFont(new Font("宋体",Font.BOLD,20));
        filmUIPanelUP.add(filmUIPanelLeftMoney);
        filmUIPanelLeftMoney.setFont(new Font("宋体",Font.BOLD,15));
        filmUIPanelUP.add(filmUIPanelViewFilmButton);
        filmUIPanelViewFilmButton.setBackground(Color.YELLOW);
        filmUIPanelViewFilmButton.setFont(new Font("宋体",Font.BOLD,15));
        filmUIPanelViewFilmButton.addActionListener(new MyButtonActionListener());
        filmUIPanelUP.add(filmUIPanelRechargeButton);
        filmUIPanelRechargeButton.setBackground(Color.ORANGE);
        filmUIPanelRechargeButton.setFont(new Font("宋体",Font.BOLD,15));
        filmUIPanelRechargeButton.addActionListener(new MyButtonActionListener());
        filmUIPanelUP.add(filmUIPanelChangePasswdButton);
        filmUIPanelChangePasswdButton.addActionListener(new MyButtonActionListener());
        filmUIPanelChangePasswdButton.setBackground(Color.PINK);
        filmUIPanelChangePasswdButton.setFont(new Font("宋体",Font.BOLD,15));
        filmUIPanelUP.add(filmUIPanelViewChargedButton);
        filmUIPanelViewChargedButton.setBackground(Color.GREEN);
        filmUIPanelViewChargedButton.setFont(new Font("宋体",Font.BOLD,15));
        filmUIPanelViewChargedButton.addActionListener(new MyButtonActionListener());

        filmUIPanel.add(new JScrollPane(mainUI.cardSmallPanel));
      //  this.add(new JScrollPane(mainUI.cardSmallPanel));



        JPanel cardSmallPanelFilm = new playUI().init(s);
        mainUI.cardSmallPanel.add(cardSmallPanelFilm);


        JPanel cardSmallPanelchargeMoney = new chargeMoneyUI(s).init();
        mainUI.cardSmallPanel.add(cardSmallPanelchargeMoney);

        JPanel cardSmallPanelchangePasswd = new changePasswdUI(s).init();
        mainUI.cardSmallPanel.add(cardSmallPanelchangePasswd);




        return filmUIPanel;
        //this.add(new ScrollPane().add(filmUIPanel));
        //return this;
    }
    class MyButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clickButton = (JButton)e.getSource();

            if(clickButton == filmUIPanelViewFilmButton) {
                mainUI.cardSmall.first(mainUI.cardSmallPanel);

            }
            if(clickButton == filmUIPanelRechargeButton) {
                mainUI.cardSmall.first(mainUI.cardSmallPanel);
                for (int i = 0; i <1 ; i++) {
                    mainUI.cardSmall.next(mainUI.cardSmallPanel);
                }
            }
            if(clickButton == filmUIPanelChangePasswdButton) {
                mainUI.cardSmall.first(mainUI.cardSmallPanel);
                for (int i = 0; i <2 ; i++) {
                    mainUI.cardSmall.next(mainUI.cardSmallPanel);
                }
            }
            if(clickButton == filmUIPanelViewChargedButton) {
                try {
                    JScrollPane panel = new ticketUI().init();
                    mainUI.cardBigPanel.add(panel);
                    mainUI.cardBig.last(mainUI.cardBigPanel);

                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
