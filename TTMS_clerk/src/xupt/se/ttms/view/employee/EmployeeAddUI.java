package xupt.se.ttms.view.employee;

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








import xupt.se.ttms.model.Employee;
//import view.studioUI.ImageJPanel;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.EmployeeSrv;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.tmpl.*;

public class EmployeeAddUI extends JDialog implements ActionListener{

	
	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblName, lblRow, lblColumn,lblFlag, lblInfo;
	protected JTextField txtName, txtRow, txtColumn, txtInfo, txtFlag;
	private JCheckBox isCreateBox;
	private boolean isCreateBoxFlag = false; 
	public EmployeeAddUI() {
		initContent();
	}

	
	protected void initContent(){
		this.setTitle("添加员工信息");
		this.setBackground(Color.WHITE);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小 
		setBounds((screen.width-800)/2,(screen.height-600)/2,800,600);
		lblName = new JLabel("员工工号：");
		lblName.setBounds(60, 30, 80, 30);
		this.add(lblName);
		txtName = new JTextField();
		txtName.setBounds(150, 30, 120, 30);
		this.add(txtName);

		lblRow = new JLabel("员工名：");
		lblRow.setBounds(60, 80, 80, 30);
		this.add(lblRow);
		txtRow = new JTextField();
		txtRow.setBounds(150, 80, 120, 30);
		this.add(txtRow);

		lblColumn = new JLabel("员工电话：");
		lblColumn.setBounds(60, 130, 80, 30);
		this.add(lblColumn);
		txtColumn = new JTextField();
		txtColumn.setBounds(150, 130, 120, 30);
		this.add(txtColumn);
		
		
		lblInfo = new JLabel("员工住址：");
		lblInfo.setBounds(60, 180, 80, 30);
		this.add(lblInfo);
		txtInfo = new JTextField();
		txtInfo.setBounds(150, 180, 120, 30);
		this.add(txtInfo);
		
		lblFlag = new JLabel("员工邮箱：");
		lblFlag.setBounds(60, 230, 80, 30);
		this.add(lblFlag);
		txtFlag = new JTextField();
		txtFlag.setBounds(150, 230, 120, 30);
		this.add(txtFlag);
		
		
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
			btnSaveClicked();
		}

	}
	
	protected void btnSaveClicked(){
		
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null && txtFlag.getText() != null) {
			EmployeeSrv stuSrv = new EmployeeSrv();
			Employee stu=new Employee();
			stu.setEmployee_workid(Integer.parseInt(txtName.getText()));
			stu.setEmployee_name(txtRow.getText());
			/*txtName, txtRow, txtColumn, txtInfo, txtFlag*/
			stu.setEmployee_tel(txtColumn.getText());
			stu.setEmployee_address(txtInfo.getText());
			stu.setEmployee_email(txtFlag.getText());
			stuSrv.add(stu);
			
			
				this.setVisible(false);
			this.dispose();
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	
	
}
