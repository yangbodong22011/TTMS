package src.xupt.se.ttms.view.clerk;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;



public class LoginFrame extends JFrame implements ActionListener {

		
		private static final long serialVersionUID = 1L;
		private static JFrame frame;
		private CardLayout card;
		private String nameStr, pwdStr;
		private JPanel panel_Login;
		private MannagerUI panel_Main;
		private JLabel label;
		private JTextField textField;
		private JLabel label_1;
		private JPasswordField passwordField;
		private JButton login_bt;
		private JButton resgit_bt;
		private JLabel label_2;
		private JPanel panel_1;
		private JPanel panel_3;
		private int frmWidth=1024;
		private int frmHeight=700;
		/*
		 * Create the application.
		 */
		public LoginFrame() {
			
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						initialize();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frame = this;
			
			Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小   
			this.setTitle("未来影院");
		//	this.setBounds((int) (screen.getWidth() - 900) / 2,
	     //           (int) (screen.getHeight() - 600) / 2 ,900, 600);
			/*this.setBounds(0,0,(int) (screen.getWidth()),
	                (int) (screen.getHeight()));*/
			setSize(frmWidth, frmHeight);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			card = new CardLayout();
			getContentPane().setLayout(card);
			
		
			panel_Login = new JPanel() {
		           
			   	    private static final long serialVersionUID = 1L;
			   	    
					public void paintComponent(Graphics g) {
						
		                ImageIcon icon = new ImageIcon("resource/image/login1.jpg");
		                // 图片随窗体大小而变化
		                g.drawImage(icon.getImage(), 0,0,frame.getSize().width,frame.getSize().height,frame);
		                //frame.getSize().width,frame.getSize().height,frame
					}
		    };
		    panel_Login.setOpaque(false);
			panel_Login.setLayout(new BorderLayout());
			
			
			JPanel panel_2 = new JPanel()/* {
		           
			   	    private static final long serialVersionUID = 1L;
			   	    
					public void paintComponent(Graphics g) {
						
		                ImageIcon icon = new ImageIcon((getClass().getResource("/image/login.jpg")));
		                // 图片随窗体大小而变化
		                g.drawImage(icon.getImage(), 0,0,frame.getSize().width,frame.getSize().height,frame);
		                //frame.getSize().width,frame.getSize().height,frame
					}
		    }*/;
			panel_Login.add(panel_2);
			
			 panel_2.setLayout(new BorderLayout());
			 // panel.setBounds((int) (screen.getWidth()-400)/2,(int) (screen.getHeight()-200)/2, 400, 200);
	        // panel必须设置成透明
			 panel_2.setOpaque(false);
			 GridBagLayout gbl_panel_2 = new GridBagLayout();
			 gbl_panel_2.columnWidths = new int[] {80, 160, 80, 80};
			 gbl_panel_2.rowHeights = new int[] {40, 40, 40, 40, 40};
			 gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
			 gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0};
			 panel_2.setLayout(gbl_panel_2);
			 
			 label = new JLabel("\u7528\u6237\u540D");
			 label.setFont(new Font("微软雅黑", Font.BOLD, 12));
			 label.setBackground(Color.WHITE);
			 label.setHorizontalAlignment(SwingConstants.RIGHT);
			 GridBagConstraints gbc_label = new GridBagConstraints();
			 gbc_label.fill = GridBagConstraints.BOTH;
			 gbc_label.insets = new Insets(0, 0, 5, 5);
			 gbc_label.gridx = 0;
			 gbc_label.gridy = 1;
			 panel_2.add(label, gbc_label);
			 
			 resgit_bt = new JButton("\u6CE8\u518C");
			 resgit_bt.setFont(new Font("微软雅黑", Font.BOLD, 12));
			 GridBagConstraints gbc_resgit_bt = new GridBagConstraints();
			 gbc_resgit_bt.insets = new Insets(0, 0, 5, 5);
			 gbc_resgit_bt.fill = GridBagConstraints.BOTH;
			 gbc_resgit_bt.gridx = 2;
			 gbc_resgit_bt.gridy = 1;
			 resgit_bt.addActionListener(this);
			 resgit_bt.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
			 
			 panel_2.add(resgit_bt, gbc_resgit_bt);
			 
			 textField = new JTextField(1);
			 GridBagConstraints gbc_textField = new GridBagConstraints();
			 gbc_textField.fill = GridBagConstraints.BOTH;
			 gbc_textField.insets = new Insets(0, 0, 5, 5);
			 gbc_textField.gridx = 1;
			 gbc_textField.gridy = 1;
			 panel_2.add(textField, gbc_textField);
			 
			 
			 
			 label_1 = new JLabel("\u5BC6\u7801");
			 label_1.setFont(new Font("微软雅黑", Font.BOLD, 12));
			 label_1.setBackground(Color.WHITE);
			 label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			 GridBagConstraints gbc_label_1 = new GridBagConstraints();
			 gbc_label_1.fill = GridBagConstraints.BOTH;
			 gbc_label_1.insets = new Insets(0, 0, 5, 5);
			 gbc_label_1.gridx = 0;
			 gbc_label_1.gridy = 2;
			 panel_2.add(label_1, gbc_label_1);
			 
			 login_bt = new JButton("\u767B\u5F55");
			 login_bt.setFont(new Font("微软雅黑", Font.BOLD, 12));
			 GridBagConstraints gbc_login_bt = new GridBagConstraints();
			 gbc_login_bt.insets = new Insets(0, 0, 5, 5);
			 gbc_login_bt.fill = GridBagConstraints.BOTH;
			 gbc_login_bt.gridx = 2;
			 gbc_login_bt.gridy = 2;
			 login_bt.addActionListener(this);
			 login_bt.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
			 panel_2.add(login_bt, gbc_login_bt);
			 
			 passwordField = new JPasswordField(1);
			 GridBagConstraints gbc_passwordField = new GridBagConstraints();
			 gbc_passwordField.fill = GridBagConstraints.BOTH;
			 gbc_passwordField.insets = new Insets(0, 0, 5, 5);
			 gbc_passwordField.gridx = 1;
			 gbc_passwordField.gridy = 2;
			 panel_2.add(passwordField, gbc_passwordField);
			
//			panel_3 = new JPanel();
//			panel_3.setOpaque(false);
//			label_2 = new JLabel("剧院管理系统");
//			label_2.setFont(new Font("微软雅黑 Light", Font.BOLD, 30));
//			panel_3.add(label_2);
//			panel_Login.add(panel_3, BorderLayout.NORTH);
//			
			getContentPane().add("panel_Login",panel_Login);
			panel_Main = new MannagerUI(card,(JPanel) getContentPane());
			getContentPane().add("panel_Main",panel_Main);
			card.show(getContentPane(),"panel_Login");

		}

		

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == resgit_bt){
			//	this.setVisible(false);
				
			}else if (e.getSource() == login_bt) {
				card.show(getContentPane(),"panel_Main");

			}
		}
		public void this_windowLostFocus(WindowEvent e) {
		        this.requestFocus();
		        this.setLocation(0,0);
		}
		public void this_windowIconified(WindowEvent e) {
		        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		public static JFrame getFrame(){
			return frame;
		}
		
	}

