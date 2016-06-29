package src.xupt.se.ttms.view.studio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import src.xupt.se.ttms.model.Studio;
import src.xupt.se.ttms.service.SeatSrv;
import src.xupt.se.ttms.service.StudioSrv;
import src.xupt.se.ttms.view.seat.seatUI;



public class StudioMgrUI extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container jPanel;
	private JScrollPane jsc;   // 用来放表格的滚动控件
	private JPanel topPanel,centerPanel,footerPanel;
	private JButton addStuBt,delStuBt,editStuBt,queryStuBt,seatStuBt;
	private JTextField inputTextField;
	private JLabel queryJLabel;
	private Studio stud=new Studio();
	public StudioMgrUI(){
		initialize();
		this.setVisible(true);
	}
	
	public void initialize(){
		jPanel = this;
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小  
	//	this.getContentPane().setForeground(Color.WHITE);
	//	this.setSize(screen.width,screen.height);
	//	this.setTitle("TTMS");
	//	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	getContentPane().setLayout(new BorderLayout(0, 0));
	//	setLocationRelativeTo(null);
		
		jPanel.setLayout(new BorderLayout());
		
		/*
		 *  topPanle
		 * */
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);
		topPanel = new JPanel();
		topPanel.setLayout(flowLayout);
	//	topPanel.setSize(500,screen.width);
	//	topPanel.setBackground(Color.gray);
		JLabel ca1 = new JLabel("演出厅管理", JLabel.CENTER);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		topPanel.add(ca1);
		
		addStuBt = new JButton("添加演出厅");
		addStuBt.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		addStuBt.addActionListener(this);
		topPanel.add(addStuBt);
		editStuBt = new JButton("修改演出厅");
		editStuBt.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		editStuBt.addActionListener(this);
		topPanel.add(editStuBt);
		delStuBt = new JButton("删除演出厅");
		delStuBt.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		delStuBt.addActionListener(this);
		topPanel.add(delStuBt);
		queryJLabel = new JLabel("请输入演出厅名称：");
		queryJLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		topPanel.add(queryJLabel);
		inputTextField = new JTextField(10);
	//	inputTextField.setBounds(220, screen.height - 45, 200, 30);
		topPanel.add(inputTextField);
		queryStuBt = new JButton("查找演出厅");
		queryStuBt.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		queryStuBt.addActionListener(this);
		topPanel.add(queryStuBt);
		
		seatStuBt = new JButton("座位管理");
		seatStuBt.setFont(new Font("微软雅黑 Light", Font.BOLD, 16));
		seatStuBt.addActionListener(this);
		topPanel.add(seatStuBt);
		
		jPanel.add(topPanel,BorderLayout.NORTH);
		
		/*
		 *  centerPanel
		 * */
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.lightGray);
		JTable stuJTable = new JTable();
		centerPanel.add(stuJTable);
		jsc = new JScrollPane();
		jsc.setBounds(0, 40, screen.width, screen.height - 90);
		centerPanel.add(jsc);
		jPanel.add(centerPanel,BorderLayout.CENTER);
		showTable();
		/**
		 * footerPanel
		 */
		footerPanel = new JPanel();
		footerPanel.setLayout(flowLayout);
		footerPanel.setBackground(Color.gray);
		JLabel infoJLabel = new JLabel("com.xupt.se.ttms.View.studio.studioMgrUI1");
		infoJLabel.setFont(new Font("微软雅黑 Light", Font.PLAIN, 16));
		footerPanel.add(infoJLabel);
		jPanel.add(footerPanel,BorderLayout.SOUTH);
		
		
	}
//	public static void main(String[] args){
//		StudioMgrUI1 frmStudioMgr = new StudioMgrUI1();
//		frmStudioMgr.setVisible(true);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addStuBt){
			btnAddClicked();
		}else if(e.getSource() == delStuBt){
			btnDelClicked();
		}else if(e.getSource() == editStuBt){
			btnModClicked();
		}else if(e.getSource() == queryStuBt){
			btnQueryClicked();
		}else if(e.getSource() == seatStuBt){
			btnSeatClicked();
		}
	}
	private void btnSeatClicked() {
		new seatUI(stud.getRowCount(), stud.getColCount(), stud.getID()).setVisible(true);
	}

	private void btnAddClicked() {
		StudioAddUI addStud = new StudioAddUI();
//		addStud.setWindowName("添加演出厅");
		addStud.toFront();
		addStud.setModal(true);
		addStud.setVisible(true);
		showTable();
		
	}

	private void btnModClicked() {
			
		StudioEditUI modStu = new StudioEditUI(stud);
	//	modStu.setWindowName("修改演出厅");
		modStu.toFront();
		modStu.setModal(true);
		modStu.setVisible(true);
		showTable();
		
		
		
	}

	private void btnDelClicked() {
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			StudioSrv stuSrv = new StudioSrv();
			stuSrv.delete(stud.getID());
			showTable();
		}
	}
	
	private void btnQueryClicked() {
		if (inputTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
			

		} else {
		
			StudioSrv stuSrv = new StudioSrv();
			List<Studio> stuList = stuSrv.Fetch("studio_name='" + inputTextField.getText() + "'");;
			StudioTable tms = new StudioTable(stud);
			Object[] in = { "id", "name", "row", "column", "studio desciption", "studio flag"};
			tms.createTable(jsc, in, stuList);
			jsc.repaint();
			
		}
	}

	public void showTable() {
		StudioTable tms = new StudioTable(stud);
		Object[] in = { "id", "name", "row", "column", "studio desciption", "studio flag"};
		List<Studio> stuList = new StudioSrv().FetchAll();

		tms.createTable(jsc, in, stuList);
		jsc.repaint();
	}

}
