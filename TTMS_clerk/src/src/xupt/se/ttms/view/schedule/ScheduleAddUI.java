package src.xupt.se.ttms.view.schedule;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.xupt.se.ttms.model.Play;
import src.xupt.se.ttms.model.Schedule;
import src.xupt.se.ttms.model.Studio;
//import view.studioUI.ImageJPanel;
import src.xupt.se.ttms.service.PlaySrv;
import src.xupt.se.ttms.service.ScheduleSrv;
import src.xupt.se.ttms.service.StudioSrv;
import src.xupt.se.ttms.view.tmpl.*;

public class ScheduleAddUI extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblStudio_id, lblPlay_id, lblSched_time,lblSched_ticket_price;
	protected JComboBox<String> txtStudio_name = null, txtPlay_name = null;

	protected JTextField txtSched_time,txtSched_ticket_price;
	private StudioSrv studioSrv;
	private PlaySrv playSrv;

	public ScheduleAddUI() {
		initContent();
		
	
	}

	protected void initContent(){
		this.setTitle("添加演出计划");
		this.setBackground(Color.WHITE);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小 
		setBounds((screen.width-800)/2,(screen.height-600)/2,800,600);
		studioSrv = new StudioSrv();
		lblStudio_id = new JLabel("演出厅：");
		lblStudio_id.setBounds(180, 30, 100, 30);
		this.add(lblStudio_id);
		
		List<Studio>listsAllStudio_name = studioSrv.FetchAll();
	    String [] names = new String[listsAllStudio_name.size()];
		Studio studio_name;
		for(int i = 0;i < listsAllStudio_name.size(); i++){
			studio_name = listsAllStudio_name.get(i);
			names[i] = studio_name.getName();
		}
		txtStudio_name = new JComboBox<String>(names);
		txtStudio_name.setBounds(280, 30, 120, 30);
		
		this.add(txtStudio_name);
		
		
		playSrv = new PlaySrv();
		lblPlay_id = new JLabel("剧目：");
		lblPlay_id.setBounds(180, 80, 100, 30);
		this.add(lblPlay_id);
		
		List<Play>listsAllPlay_name = playSrv.FetchAll();
	    String [] plays = new String[listsAllPlay_name.size()];
		Play play_name;
		for(int i = 0;i < listsAllPlay_name.size(); i++){
			play_name = listsAllPlay_name.get(i);
			plays[i] = play_name.getName();
		}
		txtPlay_name = new JComboBox<String>(plays);
		txtPlay_name.setBounds(280, 80, 120, 30);
		
		this.add(txtPlay_name);
		
		lblSched_time = new JLabel("计划演出时间：");
		lblSched_time.setBounds(180, 130, 150, 30);
		this.add(lblSched_time);
		txtSched_time = new JTextField();
		txtSched_time.setBounds(280, 130, 120, 30);
		this.add(txtSched_time);
		
		lblSched_ticket_price = new JLabel("计划票价：");
		lblSched_ticket_price.setBounds(180,180, 90, 30);
		this.add(lblSched_ticket_price);
		txtSched_ticket_price = new JTextField();
		txtSched_ticket_price.setBounds(280,180, 120, 30);
		this.add(txtSched_ticket_price);

		btnSave = new JButton("保存");
		btnSave.addActionListener(this);
		btnSave.setBounds(180,230, 60, 30);
		this.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(280,230,60, 30);
		this.add(btnCancel);
		
		JLabel jLabel = new JLabel();
		jLabel.setBounds(360, 160, 100, 100);
	
		this.add(jLabel);
		
	}
	
	public boolean getReturnStatus(){
		   return rst;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			rst=false;
			this.dispose();
			getParent().setVisible(true);
		} else if (e.getSource() == btnSave) {
		
			btnSaveClicked();
		}
	}
	
	protected void btnSaveClicked(){
		
	
		if (txtStudio_name.getSelectedItem() != null && txtPlay_name.getSelectedItem() != null && txtSched_time.getText() != null && txtSched_ticket_price.getText()!= null) {
			ScheduleSrv scheduleSrv = new ScheduleSrv();
			Schedule schedule=new Schedule();
			java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
			java.util.Date date = null;
			try {
				date = sdf.parse(txtSched_time.getText());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			schedule.setStudio_id(new StudioSrv().Fetch("studio_name = '" + txtStudio_name.getSelectedItem() +"'").get(0).getID());
			schedule.setPlay_id(new PlaySrv().Fetch("play_name = '" + txtPlay_name.getSelectedItem() + "'").get(0).getId());
			schedule.setSched_time(date);
			schedule.setSched_ticket_price(Double.parseDouble(txtSched_ticket_price.getText()));

			scheduleSrv.add(schedule);
			rst=true;
			this.dispose();
			
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
}
