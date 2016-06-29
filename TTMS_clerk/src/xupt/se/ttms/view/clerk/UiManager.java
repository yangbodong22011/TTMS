package src.xupt.se.ttms.view.clerk;


import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;



import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import src.xupt.se.ttms.view.datadict.DataDictMgrUI;
import src.xupt.se.ttms.view.employee.EmployeeMgrUI;
import src.xupt.se.ttms.view.play.PlayMgrUI;
import src.xupt.se.ttms.view.schedule.ScheduleMgrUI;

import src.xupt.se.ttms.view.studio.StudioMgrUI;
import src.xupt.se.util.TransparentPanel;

import java.awt.Component;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class UiManager extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardLayout card;
	private String cardName[]={"panelScheduleManager","panelRoomManager","panelMovieManager","panelSellTicketManager","panelEmployeeManager","panelDataDictManager","panelWelcome"};

	private JPanel panelScheduleManager,panelRoomManager,panelMovieManager,panelSellTicketManager,panelEmployeeManager,panelDataDictManager;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public UiManager() {
		initialize();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		card = new CardLayout();
		this.setLayout(card);
		this.setOpaque(false);
		
		panelScheduleManager = new ScheduleMgrUI();
		add(cardName[0],panelScheduleManager);			
		
		panelRoomManager = new StudioMgrUI();
		add(cardName[1],panelRoomManager);
	
		
		panelMovieManager = new PlayMgrUI();
		add(cardName[2],panelMovieManager);
		
		panelSellTicketManager = new SaleFrame();
		add(cardName[3],panelSellTicketManager);

	
		panelEmployeeManager = new EmployeeMgrUI();
		add(cardName[4],panelEmployeeManager);
		
		panelDataDictManager = new DataDictMgrUI();
		add(cardName[5],panelDataDictManager);
					
		
		JPanel panelWelcom = new TransparentPanel();
		add(cardName[6],panelWelcom);
		panelWelcom.setLayout(new BorderLayout(0, 0));
		JLabel lblNewLabel = new JLabel(new ImageIcon("resource/image/welcome.jpg"));
		panelWelcom.add(lblNewLabel, BorderLayout.CENTER);				
	}
	public void ToScheduleManager(){
		 card.show(this,cardName[0]);  
	}
	public void ToRoomManager(){
		 card.show(this,cardName[1]);
	}
	public void ToMovieManager(){
		 card.show(this,cardName[2]);  
	}
	public  void ToSellTicketManager(){
		 card.show(this,cardName[3]);  
	}
	public  void ToEmployeeManager(){
		 card.show(this,cardName[4]);  
	}
	public  void ToDataDictManagerr(){
		 card.show(this,cardName[5]);  
	}
	public  void ToWelcome(){
		 card.show(this,cardName[6]);  
		 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	@Override
	public void paintComponent(Graphics g) {
		
        ImageIcon icon = new ImageIcon(("resource//image/bg.jpg"));
        // ͼƬ�洰���С���仯
        g.drawImage(icon.getImage(), 0,0,this.getSize().width,this.getSize().height,this);
        //frame.getSize().width,frame.getSize().height,frame
	}
	
}
