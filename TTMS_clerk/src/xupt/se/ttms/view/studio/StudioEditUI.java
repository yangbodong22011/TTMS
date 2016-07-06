package xupt.se.ttms.view.studio;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


//import view.studioUI.ImageJPanel;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.studio.StudioAddUI;;

public class StudioEditUI extends StudioAddUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	public StudioEditUI(Studio stu) {
		initData(stu);
	}

	private void initData(Studio stu) {
		this.setTitle("修改演出厅");
		txtName.setText(stu.getName());
		txtRow.setText(Integer.toString(stu.getRowCount()));
		txtColumn.setText(Integer.toString(stu.getColCount()));
		txtFlag.setText(Integer.toString(stu.getStudioFlag()));
		txtInfo.setText(stu.getIntroduction());
		ID = stu.getID();
	}

	@Override
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null && txtFlag.getText() != null) {
			StudioSrv stuSrv = new StudioSrv();
			Studio stu=new Studio();
			stu.setID(ID);
			stu.setName(txtName.getText());
			stu.setRowCount(Integer.parseInt(txtRow.getText()));
			stu.setColCount(Integer.parseInt(txtColumn.getText()));
			stu.setIntroduction(txtInfo.getText());
			stu.setStudioFlag(Integer.parseInt(txtFlag.getText()));
			stuSrv.modify(stu);
			
			if(isCreateBoxFlag)
			{
				SeatSrv seatSrv = new SeatSrv();
				seatSrv.CreateSeatsOfStudio(stu.getID(),stu.getRowCount(),stu.getColCount());
				
			}
			
			this.setVisible(false);
			
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}

}