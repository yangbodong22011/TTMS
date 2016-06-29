package src.xupt.se.ttms.view.studio;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;






//import view.studioUI.ImageJPanel;
import src.xupt.se.ttms.model.Studio;
import src.xupt.se.ttms.service.SeatSrv;
import src.xupt.se.ttms.service.StudioSrv;
import src.xupt.se.ttms.view.tmpl.*;

public class StudioAddUI extends JDialog implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblName, lblRow, lblColumn,lblFlag, lblInfo,lblCreateSeat;
	protected JTextField txtName, txtRow, txtColumn, txtInfo, txtFlag ;
	private JCheckBox isCreateBox;
	protected boolean isCreateBoxFlag = false; 
	public StudioAddUI() {
		initContent();
		
	}

//	@Override
	protected void initContent(){
		this.setTitle("添加演出厅");
		this.setBackground(Color.WHITE);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小 
		setBounds((screen.width-800)/2,(screen.height-600)/2,800,600);
		
		lblName = new JLabel("演出厅名称：");
		lblName.setBounds(60, 30, 80, 30);
		this.add(lblName);
		txtName = new JTextField();
		txtName.setBounds(150, 30, 120, 30);
		this.add(txtName);

		lblRow = new JLabel("座位行数：");
		lblRow.setBounds(60, 80, 80, 30);
		this.add(lblRow);
		txtRow = new JTextField();
		txtRow.setBounds(150, 80, 120, 30);
		this.add(txtRow);

		lblColumn = new JLabel("座位列数：");
		lblColumn.setBounds(60, 130, 80, 30);
		this.add(lblColumn);
		txtColumn = new JTextField();
		txtColumn.setBounds(150, 130, 120, 30);
		this.add(txtColumn);
		
		
		lblInfo = new JLabel("演出厅描述：");
		lblInfo.setBounds(60, 180, 80, 30);
		this.add(lblInfo);
		txtInfo = new JTextField();
		txtInfo.setBounds(150, 180, 120, 30);
		this.add(txtInfo);
		
		lblFlag = new JLabel("演出厅状态：");
		lblFlag.setBounds(60, 230, 80, 30);
		this.add(lblFlag);
		txtFlag = new JTextField();
		txtFlag.setBounds(150, 230, 120, 30);
		this.add(txtFlag);
		
		lblCreateSeat = new JLabel("座位：");
		lblCreateSeat.setBounds(60, 280, 80, 30);
		this.add(lblCreateSeat);
		isCreateBox = new JCheckBox("自动初始化");
		isCreateBox.setBounds(150, 280, 120, 30);
		isCreateBox.addItemListener(this);
		this.add(isCreateBox);
		
		btnSave = new JButton("保存");
		btnSave.addActionListener(this);
		btnSave.setBounds(60, 320, 60, 30);
		this.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(180, 320, 60, 30);
		this.add(btnCancel);
		
		JLabel jLabel = new JLabel();
		jLabel.setBounds(360, 160, 100, 100);
	
		this.add(jLabel);
	}
	
	
	public boolean getReturnStatus(){
		   return rst;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			rst=false;
			this.dispose();
			getParent().setVisible(true);

		} else if (e.getSource() == btnSave) {
			rst=true;
			btnSaveClicked();
		}

	}
	
	protected void btnSaveClicked(){
		
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null && txtFlag.getText() != null) {
			StudioSrv stuSrv = new StudioSrv();
			Studio stu=new Studio();
			stu.setName(txtName.getText());
			stu.setRowCount(Integer.parseInt(txtRow.getText()));
			stu.setColCount(Integer.parseInt(txtColumn.getText()));
			stu.setIntroduction(txtInfo.getText());
			stu.setStudioFlag(Integer.parseInt(txtFlag.getText()));
			stuSrv.add(stu);
			
			if(isCreateBoxFlag)
			{
				SeatSrv seatSrv = new SeatSrv();
				seatSrv.CreateSeatsOfStudio(stu.getID(),stu.getRowCount(),stu.getColCount());
				System.out.println("已生成座位");
			}
			
			this.dispose();
			
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(isCreateBox.isSelected()){
			isCreateBoxFlag = true;
		}else{
			isCreateBoxFlag = false;
		}
	}
}
