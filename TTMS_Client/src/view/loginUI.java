package view;

import model.User;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by kiosk on 6/20/16.
 */
public class loginUI {
    private Socket s;              //传递数据的套接字
    JButton clickButton;           //点击按钮
    PrintStream ps;

    JPanel loginUIPanel = new JPanel(new BorderLayout());
    JLabel loginUILabelImage = new JLabel(new ImageIcon("/home/kiosk/Desktop/TTMS_Client/src/source/images/yingyuan.png"));
    JLabel loginUILabelName = new JLabel("帐号  ");
    JLabel loginUILabelPasswd = new JLabel("密码  ");
    JTextField loginUIFieldName = new JTextField(15);
    JPasswordField loginUIFieldPasswd = new JPasswordField(15);

    JButton loginUILoginButton = new JButton("登录");
    JButton loginUIRegisterButton = new JButton("注册");

    public loginUI() {}
    public loginUI(Socket s) throws IOException {
        this.s = s;
        ps = new PrintStream(s.getOutputStream());
    }
    public JPanel init() {

        loginUIPanel.add(loginUILabelImage,BorderLayout.NORTH);
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel FirstPanelDown = new JPanel(gb);

        loginUIPanel.add(FirstPanelDown,BorderLayout.CENTER);
        FirstPanelDown.setBackground(Color.GRAY);
        loginUILabelName.setFont(new Font("宋体",Font.BOLD,20));
        loginUILabelPasswd.setFont(new Font("宋体",Font.BOLD,20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        FirstPanelDown.add(loginUILabelName,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        FirstPanelDown.add(loginUIFieldName,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        FirstPanelDown.add(new JLabel(" "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        FirstPanelDown.add(loginUILabelPasswd,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        FirstPanelDown.add(loginUIFieldPasswd,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        FirstPanelDown.add(new JLabel(" "),gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        FirstPanelDown.add(loginUILoginButton,gbc);
        loginUILoginButton.addActionListener(new mybutActionListener());

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        FirstPanelDown.add(loginUIRegisterButton,gbc);
        loginUIRegisterButton.addActionListener(new mybutActionListener());
        return loginUIPanel;

    }
    class mybutActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clickButton = (JButton)e.getSource();
            if(clickButton == loginUILoginButton) {
                User user = new User(1,loginUIFieldName.getText(),loginUIFieldPasswd.getText());
                JSONObject object = JSONObject.fromObject(user);
                System.out.println(object);
                ps.println(object);
                loginUIFieldName.setText("");
                loginUIFieldPasswd.setText("");
            }
            if(clickButton == loginUIRegisterButton) {
                User user = new User(3,loginUIFieldName.getText(),loginUIFieldPasswd.getText());
                System.out.println(user.getUsername());
                if(!user.getUsername().equals("")) {
                    JSONObject object = JSONObject.fromObject(user);
                    ps.println(object);
                    loginUIFieldName.setText("");
                    loginUIFieldPasswd.setText("");
                }else {
                    JOptionPane.showMessageDialog(null,"用户名不能为空,注册失败","",JOptionPane.DEFAULT_OPTION,null);
                }
            }
        }
    }
}
