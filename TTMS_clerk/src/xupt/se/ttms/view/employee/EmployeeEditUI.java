package xupt.se.ttms.view.employee;

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



import xupt.se.ttms.model.Employee;
//import view.studioUI.ImageJPanel;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.EmployeeSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.studio.StudioAddUI;;

public class EmployeeEditUI extends EmployeeAddUI{
	private int ID;
	public EmployeeEditUI(Employee stu) {
		initData(stu);
	}

	private void initData(Employee stu) {
		this.setTitle("修改员工信息");
		txtName.setText(Integer.toString(stu.getEmployee_workid()));
		txtRow.setText(stu.getEmployee_name());
		txtColumn.setText(stu.getEmployee_tel());
		txtInfo.setText(stu.getEmployee_address());
		txtFlag.setText(stu.getEmployee_email());
		ID = stu.getId();
	}

	@Override
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null && txtFlag.getText() != null) {
			EmployeeSrv stuSrv = new EmployeeSrv();
			Employee stu=new Employee();
			stu.setId(ID);
			stu.setEmployee_workid(Integer.parseInt(txtName.getText()));
			stu.setEmployee_name(txtRow.getText());
			stu.setEmployee_tel(txtColumn.getText());
			stu.setEmployee_address(txtInfo.getText());
			stu.setEmployee_email(txtFlag.getText());
			stuSrv.modify(stu);
			this.setVisible(false);
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}

}