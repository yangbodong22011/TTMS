package src.xupt.se.ttms.view.clerk;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import src.xupt.se.util.RoundBorder;


public class MannagerUI extends JPanel implements ActionListener, ItemListener {

	
	private static final long serialVersionUID = 1L;
	private CardLayout card;
	private JPanel panel;
	private JButton play_bt, room_bt, analysis_bt, movie_bt, button_1, button;
	private UiManager uiManager;
	private JComboBox<String> comboBox;
	 /**
	  *  Create the application.
	  **/
	
	public MannagerUI(CardLayout card, JPanel panel) {
		
		initialize();
		this.card = card;
		this.panel = panel;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		

		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小  
	//	setSize(screen);
		setSize(1024, 500);
		
	//	this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel() {
	           
	   	    private static final long serialVersionUID = 1L;
	   	    
			public void paintComponent(Graphics g) {
				
                ImageIcon icon = new ImageIcon("resource/image/bg.jpg");
                // 图片随窗体大小而变化
                g.drawImage(icon.getImage(), 0,0,this.getSize().width,this.getSize().height,this);
                //frame.getSize().width,frame.getSize().height,frame
			}
		};
		
		this.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 600, 200};
		gbl_panel.rowHeights = new int[] {100};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{6.5};
		panel.setLayout(gbl_panel);
		panel.setOpaque(true);

			JPanel panel_1 = new JPanel();
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.gridwidth = 2;
			gbc_panel_1.fill = GridBagConstraints.BOTH;
			gbc_panel_1.insets = new Insets(0, 0, 0, 5);
			gbc_panel_1.gridx = 1;
			gbc_panel_1.gridy = 0;
			panel.add(panel_1, gbc_panel_1);
			panel_1.setOpaque(false);
			panel_1.setLayout(new GridLayout(0, 6, 5, 0));

				play_bt = new JButton("\u4E0A\u6620\u7BA1\u7406");
				play_bt.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
				panel_1.add(play_bt);
				play_bt.setToolTipText("");
		//		play_bt.setBorder((Border) new RoundBorder());
				play_bt.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
				play_bt.addActionListener(this);

				room_bt = new JButton("\u5F71\u5385\u7BA1\u7406");
				room_bt.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
				panel_1.add(room_bt);
		//		room_bt.setBorder((Border) new RoundBorder());
				room_bt.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
				room_bt.addActionListener(this);

				movie_bt = new JButton("\u5F71\u7247\u7BA1\u7406");
				movie_bt.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
				panel_1.add(movie_bt);
		//		movie_bt.setBorder((Border) new RoundBorder());
				movie_bt.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
				movie_bt.addActionListener(this);

				analysis_bt = new JButton("售票管理");
				analysis_bt.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
				panel_1.add(analysis_bt);
		//		analysis_bt.setBorder((Border) new RoundBorder());
				analysis_bt.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
				analysis_bt.addActionListener(this);
				
				button_1 = new JButton("员工管理");
				panel_1.add(button_1);
				button_1.setToolTipText("");
				button_1.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		//		button_1.setBorder((Border) new RoundBorder());
				button_1.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
				button_1.addActionListener(this);
				
				button = new JButton("字典管理");
				panel_1.add(button);
				button.setToolTipText("");
				button.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		//		button.setBorder((Border) new RoundBorder());
				button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
				button.addActionListener(this);
																									
					JPanel panel_3 = new JPanel();
					GridBagConstraints gbc_panel_3 = new GridBagConstraints();
					gbc_panel_3.anchor = GridBagConstraints.EAST;
					gbc_panel_3.fill = GridBagConstraints.VERTICAL;
					gbc_panel_3.gridx = 4;
					gbc_panel_3.gridy = 0;
					panel.add(panel_3, gbc_panel_3);
					panel_3.setOpaque(false);
										panel_3.setLayout(new GridLayout(2, 2, 0, 0));
										
															JLabel label_1 = new JLabel("\u7528\u6237\uFF1A");
															label_1.setFont(new Font("微软雅黑", Font.BOLD, 12));
															label_1.setHorizontalAlignment(SwingConstants.LEFT);
															panel_3.add(label_1);
										
															JLabel user_jl = new JLabel();
															panel_3.add(user_jl);
															user_jl.setText("admin");
										
										JLabel label_2 = new JLabel("");
										panel_3.add(label_2);
										
															JLabel label = new JLabel("\u7528\u6237\u7BA1\u7406");
															label.setFont(new Font("微软雅黑", Font.BOLD, 12));
															label.setHorizontalAlignment(SwingConstants.LEFT);
															panel_3.add(label);
										
															comboBox = new JComboBox<String>();
															panel_3.add(comboBox);
															comboBox.addItem("更改密码");
															comboBox.addItem("退出登录");
															comboBox.addItemListener(this);
										
										JLabel label_3 = new JLabel("");
										panel_3.add(label_3);

		uiManager = new UiManager();
		this.add(uiManager, BorderLayout.CENTER);
		uiManager.ToWelcome();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == play_bt) {
			uiManager.ToScheduleManager();

		}
		if (e.getSource() == room_bt) {
			uiManager.ToRoomManager();

		}
		if (e.getSource() == movie_bt) {
			uiManager.ToMovieManager();

		}
		if (e.getSource() == analysis_bt) {
			uiManager.ToSellTicketManager();

		}
		if (e.getSource() == button_1) {
			uiManager.ToEmployeeManager();

		}
		if (e.getSource() == button) {
			uiManager.ToDataDictManagerr();

		}
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		 if(e.getStateChange() == ItemEvent.SELECTED)
         {
             String s=(String) comboBox.getSelectedItem();
             if(s.equals("退出登录")){
            	
     			 card.show(panel, "panel_Login");
             }else if(s.equals("更改密码")){
            //	 dispose(); 
     			 
             }
         }
	}
}