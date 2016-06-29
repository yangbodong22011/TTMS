package view;


import control.clientThread;
import control.main;
import model.Schedule;
import model.Seat;
import model.Ticket;
import model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;
import java.util.List;


public class seatUI {

    public static int studio_row_count;
    public static int studio_col_count;
    public static String studio_introduction;
    public static Schedule schedule;
    public static String str;
    public static float totalprice;

    private Socket s;
    PrintStream ps;

    JButton clickButton;
    public static List<Ticket> ticketlist = new ArrayList<>();    //这个ticketlist中是我的电影票

    JPanel seatUI = new JPanel(new BorderLayout());

    JPanel seatUIPanelUP = new JPanel(new BorderLayout());
    JLabel seatUILabel = new JLabel("选择座位",JLabel.CENTER);

    JPanel seatUIPanelUpmid = new JPanel(new GridLayout(1,2));
    JButton seatUIBackButton = new JButton("返回首页");
    JButton seatUIBuyButton = new JButton("确定购买");


    JLabel seatUIScreenLabel = new JLabel(new ImageIcon("/home/kiosk/Desktop/TTMS_Client/src/source/images/selectsite.png"));


    JPanel seatUIPanel;


    public JPanel init(Socket s) throws IOException{
        this.s = s;
        ps = new PrintStream(s.getOutputStream());

        seatUILabel.setFont(new Font("宋体",Font.BOLD,25));
        seatUIPanelUpmid.add(seatUIBackButton);
        seatUIPanelUpmid.add(seatUIBuyButton);
        seatUIBuyButton.addActionListener(new MyButtonListener());

        seatUIPanelUP.add(seatUILabel,BorderLayout.NORTH);
        seatUIPanelUP.add(seatUIPanelUpmid,BorderLayout.CENTER);
        seatUIPanelUP.add(seatUIScreenLabel,BorderLayout.SOUTH);


        seatUIBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainUI.cardBig.first(mainUI.cardBigPanel);
                for (int i = 0; i <1 ; i++) {
                    mainUI.cardBig.next(mainUI.cardBigPanel);
                }

            }
        });
        seatUI.add(seatUIPanelUP,BorderLayout.NORTH);

        seatUIPanel = new JPanel(new GridLayout(studio_row_count,studio_col_count,5,5));
        for (int i = 0; i < filmUI.allSchedulePlayStudioSeat.size(); i++) {
            Seat seat = filmUI.allSchedulePlayStudioSeat.get(i);

            JButton but = new oneSeatUI(i,seat).init(seat);

            seatUIPanel.add(but);
        }
        seatUI.add(seatUIPanel,BorderLayout.CENTER);

        return seatUI;
    }
    class MyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e ) {
            clickButton = (JButton)e.getSource();
            if(clickButton == seatUIBuyButton) {

                ticketlist.clear();

                str = "恭喜您,购票成功,您的座位信息为:\n";
                for (int i = 0; i < filmUI.allSchedulePlayStudioSeat.size(); i++) {
                    if(filmUI.allSchedulePlayStudioSeat.get(i).getSeatStatus() == 3) {
                        str += filmUI.allSchedulePlayStudioSeat.get(i).getRow()+ " 排 "+
                                filmUI.allSchedulePlayStudioSeat.get(i).getColumn()+ " 列 \n";
                        Ticket ticket = new Ticket();
                        ticket.setSeatId(filmUI.allSchedulePlayStudioSeat.get(i).getId());
                        ticket.setScheduleId(schedule.getSched_id());
                        ticket.setPrice((float)schedule.getSched_ticket_price());
                        ticket.setStatus(9);
                        System.out.println(ticket);
                        ticketlist.add(ticket);
                    }
                }
                totalprice = 0;
                for (int i = 0; i < ticketlist.size(); i++) {
                    totalprice += ticketlist.get(i).getPrice();
                }
                if(totalprice <= main.user.getUserLeftMoney()) {
                    if(ticketlist.size() != 0) {
                        str += "祝您观影愉快!";
                        /*try{
                            new logs(str).showDialog();
                        }catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        main.user.setUserLeftMoney(main.user.getUserLeftMoney()-(int)totalprice);    //本地首先减去金额*/

                    //先发送标志为6 也去更新数据库中钱.
                        User user = main.user;
                        user.setUserLeftMoney(main.user.getUserLeftMoney()-(int)totalprice);
                        user.setType(6);
                        JSONObject object = JSONObject.fromObject(user);
                        ps.println(object);


                        JSONArray array = JSONArray.fromObject(ticketlist);
                        ps.println(array);
                    }else {
                        JOptionPane.showMessageDialog(null,"未选中任何座位","温馨提示",JOptionPane.DEFAULT_OPTION,null);
                    }

                }else {
                    JOptionPane.showMessageDialog(null,"余额不足,购买失败,请充值","温馨提示",JOptionPane.DEFAULT_OPTION,null);
                }
            }
        }
    }

}