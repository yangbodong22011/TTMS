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
public class changePasswdUI {
    private Socket s;
    PrintStream ps;

    public JPanel chargeMoneyUI = new JPanel(new BorderLayout());

    JButton clickbutton;
    JLabel ThirdPanelOldPasswd = new JLabel("请输入原始密码  ",JLabel.CENTER);
    JLabel ThirdPanelNewPasswd = new JLabel("请输入新的密码  ",JLabel.CENTER);
    JLabel ThirdPanelConfirmPasswd = new JLabel("请再次输入密码  ",JLabel.CENTER);
    JPasswordField ThirdPanelOldPasswdField = new JPasswordField(10);
    JPasswordField ThirdPanelNewPasswdField = new JPasswordField(10);
    JPasswordField ThirdPanelConfirmPasswdField = new JPasswordField(10);
    JButton ThirdPanelConfirmPasswdButton = new JButton("确认");
    JButton ThirdPanelCanelChangePasswdButton = new JButton("取消");

    public changePasswdUI(Socket s) throws IOException {
        this.s = s;
        ps = new PrintStream(s.getOutputStream());
    }

    public JPanel init() {

        GridBagLayout gb3 = new GridBagLayout();
        GridBagConstraints gbc3 = new GridBagConstraints();
        JPanel ThirdPanelDown = new JPanel(gb3);
        ThirdPanelDown.setBorder(new MatteBorder(20,5,10,30,Color.LIGHT_GRAY));
        ThirdPanelDown.setBackground(Color.PINK);
        chargeMoneyUI.add(ThirdPanelDown,BorderLayout.CENTER);

        gbc3.gridx = 0;
        gbc3.gridy = 0;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(ThirdPanelOldPasswd,gbc3);


        gbc3.gridx = 1;
        gbc3.gridy = 0;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(ThirdPanelOldPasswdField,gbc3);


        gbc3.gridx = 0;
        gbc3.gridy = 1;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(new JLabel(" "),gbc3);


        gbc3.gridx = 0;
        gbc3.gridy = 2;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(ThirdPanelNewPasswd,gbc3);


        gbc3.gridx = 1;
        gbc3.gridy = 2;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(ThirdPanelNewPasswdField,gbc3);


        gbc3.gridx = 0;
        gbc3.gridy = 3;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(new JLabel(" "),gbc3);


        gbc3.gridx = 0;
        gbc3.gridy = 4;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(ThirdPanelConfirmPasswd,gbc3);


        gbc3.gridx = 1;
        gbc3.gridy = 4;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(ThirdPanelConfirmPasswdField,gbc3);

        gbc3.gridx = 0;
        gbc3.gridy = 5;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(new JLabel(" "),gbc3);


        gbc3.gridx = 0;
        gbc3.gridy = 6;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(ThirdPanelConfirmPasswdButton,gbc3);
        ThirdPanelConfirmPasswdButton.addActionListener(new MyButtonActionListener());

        gbc3.gridx = 1;
        gbc3.gridy = 6;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        ThirdPanelDown.add(ThirdPanelCanelChangePasswdButton,gbc3);
        ThirdPanelCanelChangePasswdButton.addActionListener(new MyButtonActionListener());

        return chargeMoneyUI;
    }
    class MyButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clickbutton =  (JButton)e.getSource();
            if(clickbutton == ThirdPanelConfirmPasswdButton) {
                if(!ThirdPanelOldPasswdField.getText().equals(main.user.getUserPasswd())) {
                    //JOptionPane.showMessageDialog(null,"原始密码错误,修改密码失败","提示",JOptionPane.DEFAULT_OPTION,null);
                    try{
                        new logs("原始密码错误,修改密码失败" ).showDialog();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ThirdPanelOldPasswdField.setText("");
                }else if (!ThirdPanelNewPasswdField.getText().equals(ThirdPanelConfirmPasswdField.getText())) {
                    //JOptionPane.showMessageDialog(null,"两次密码输入不一致,请重新输入","提示",JOptionPane.DEFAULT_OPTION,null);
                    try{
                        new logs("两次密码输入不一致,请重新输入" ).showDialog();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ThirdPanelNewPasswdField.setText("");
                    ThirdPanelConfirmPasswdField.setText("");
                } else {
                    main.user.setUserPasswd(ThirdPanelConfirmPasswdField.getText());
                    User user = main.user;
                    user.setType(8);

                    JSONObject object = JSONObject.fromObject(user);
                    ps.println(object);
                    ThirdPanelOldPasswdField.setText("");
                    ThirdPanelNewPasswdField.setText("");
                    ThirdPanelConfirmPasswdField.setText("");
                    //OptionPane.showMessageDialog(null,"恭喜您,密码修改成功\n新密码: "+main.user.getUserPasswd()+"\n" +
                    //        "请妥善保管","提示",JOptionPane.DEFAULT_OPTION,null);
                    try{
                        new logs("恭喜您,密码修改成功\n新密码: "+main.user.getUserPasswd()+"\n请妥善保管").showDialog();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if(clickbutton == ThirdPanelCanelChangePasswdButton) {
                mainUI.cardSmall.first(mainUI.cardSmallPanel);
            }
        }
    }
}
