package view;

import control.main;
import model.User;
import net.sf.json.JSONObject;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by kiosk on 6/23/16.
 */
public class chargeMoneyUI {
    private Socket s;
    PrintStream ps;
    JButton clickbutton;

    public JPanel chargeMoneyUI = new JPanel(new BorderLayout());

    JLabel FourthPanelChargeMoneyLabel = new JLabel("请输入充值金额  ",JLabel.CENTER);
    JTextField FourthPanelChargeMoneyField = new JTextField(10);
    JLabel FourthPanelChargePasswdLabel = new JLabel("请输入支付密码  ",JLabel.CENTER);
    JPasswordField FourthPanelChargePasswdField  = new JPasswordField(10);
    JButton FourthPanelConfirmChargeMoneyButton = new JButton("确认");
    JButton FourthPanelCanelChargeMoneyButton = new JButton("取消");

    public chargeMoneyUI(Socket s) throws IOException{
        this.s = s;
        ps = new PrintStream(s.getOutputStream());
    }

    public JPanel init() {
        GridBagLayout gb4 = new GridBagLayout();
        GridBagConstraints gbc4 = new GridBagConstraints();
        JPanel FourthPanelDown = new JPanel(gb4);
        FourthPanelDown.setBorder(new MatteBorder(20,5,10,30,Color.LIGHT_GRAY));
        FourthPanelDown.setBackground(Color.ORANGE);
        chargeMoneyUI.add(FourthPanelDown,BorderLayout.CENTER);

        gbc4.gridx = 0;
        gbc4.gridy = 0;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        FourthPanelDown.add(FourthPanelChargeMoneyLabel,gbc4);


        gbc4.gridx = 1;
        gbc4.gridy = 0;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        FourthPanelDown.add(FourthPanelChargeMoneyField,gbc4);


        gbc4.gridx = 0;
        gbc4.gridy = 1;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        FourthPanelDown.add(new JLabel(" "),gbc4);


        gbc4.gridx = 0;
        gbc4.gridy = 2;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        FourthPanelDown.add(FourthPanelChargePasswdLabel,gbc4);


        gbc4.gridx = 1;
        gbc4.gridy = 2;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        FourthPanelDown.add(FourthPanelChargePasswdField,gbc4);


        gbc4.gridx = 0;
        gbc4.gridy = 3;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        FourthPanelDown.add(new JLabel(" "),gbc4);


        gbc4.gridx = 0;
        gbc4.gridy = 4;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        FourthPanelDown.add(FourthPanelConfirmChargeMoneyButton,gbc4);
        FourthPanelConfirmChargeMoneyButton.addActionListener(new MyButtonActionListener());


        gbc4.gridx = 1;
        gbc4.gridy = 4;
        gbc4.gridwidth = 1;
        gbc4.gridheight = 1;
        FourthPanelDown.add(FourthPanelCanelChargeMoneyButton,gbc4);
        FourthPanelCanelChargeMoneyButton.addActionListener(new MyButtonActionListener());

        return chargeMoneyUI;

    }
    class MyButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           clickbutton =  (JButton)e.getSource();
            if(clickbutton == FourthPanelConfirmChargeMoneyButton) {
                if(!FourthPanelChargePasswdField.getText().equals(main.user.getUserPasswd())) {
                    try{
                        new logs("支付密码错误!" ).showDialog();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    FourthPanelChargePasswdField.setText("");
                }else {
                    int money = Integer.parseInt(FourthPanelChargeMoneyField.getText());
                    main.user.setUserLeftMoney(main.user.getUserLeftMoney()+money);
                    User user = main.user;
                    user.setType(7);

                    JSONObject object = JSONObject.fromObject(user);
                    ps.println(object);
                    FourthPanelChargeMoneyField.setText("");
                    FourthPanelChargePasswdField.setText("");
                    //JOptionPane.showMessageDialog(null,"恭喜您,充值成功\n新余额: "+main.user.getUserLeftMoney()+"\n快去看看有什么新电影吧!" ,"提示",JOptionPane.DEFAULT_OPTION,null);
                    try{
                        new logs("恭喜您,充值成功\n新余额: "+main.user.getUserLeftMoney()+"\n快去看看有什么新电影吧!" ).showDialog();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            if(clickbutton == FourthPanelCanelChargeMoneyButton) {
                mainUI.cardSmall.first(mainUI.cardSmallPanel);

            }

        }
    }


}
