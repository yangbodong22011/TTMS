package xupt.se.ttms.view.schedule;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.ComboBoxUI;

import xupt.se.ttms.model.Schedule;
//import view.studioUI.ImageJPanel;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.studio.StudioAddUI;;

public class ScheduleEditUI extends ScheduleAddUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	public ScheduleEditUI(Schedule stud){
		initData(stud);
		ID = stud.getSched_id();
	}

	private void initData(Schedule stud){
		this.setTitle("修改演出计划");
		txtStudio_name.setSelectedItem(new StudioSrv().Fetch("studio_id = "+stud.getStudio_id()).get(0).getName());
		txtPlay_name.setSelectedItem(new PlaySrv().Fetch("play_id = "+stud.getPlay_id()).get(0).getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		String str = sdf.format(stud.getSched_time());  
		txtSched_time.setText(str);
		txtSched_ticket_price.setText(String.valueOf(stud.getSched_ticket_price()));
		btnSaveClicked();
	}

	protected void btnSaveClicked(){
		if (txtStudio_name.getSelectedItem() != null && txtPlay_name.getSelectedItem()!= null && txtSched_time.getText() != null &&  txtSched_ticket_price.getText() != null) {
			ScheduleSrv stuSrv = new ScheduleSrv();
			Schedule stu=new Schedule();
			stu.setSched_id(ID);
			stu.setStudio_id(new StudioSrv().Fetch("studio_name = '" + txtStudio_name.getSelectedItem() +"'").get(0).getID());
			stu.setPlay_id(new PlaySrv().Fetch("play_name = '" + txtPlay_name.getSelectedItem() + "'").get(0).getId());
			SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟   
			Date date = null;
			try {
				date = sdf.parse(txtSched_time.getText());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			stu.setSched_time(date);
			stu.setSched_ticket_price(Double.parseDouble(txtSched_ticket_price.getText()));

			stuSrv.modify(stu);
			this.setVisible(false);
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}

}
