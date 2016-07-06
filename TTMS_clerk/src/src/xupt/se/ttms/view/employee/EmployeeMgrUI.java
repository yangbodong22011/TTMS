package src.xupt.se.ttms.view.employee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.Book;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import src.xupt.se.ttms.model.DataDict;
import src.xupt.se.ttms.model.Employee;
import src.xupt.se.ttms.model.Studio;
import src.xupt.se.ttms.service.DataDictSrv;
import src.xupt.se.ttms.service.EmployeeSrv;
import src.xupt.se.ttms.service.StudioSrv;
import src.xupt.se.ttms.view.tmpl.*;
import src.xupt.se.util.NewClass;

import java.util.List;
import java.util.Iterator;

class StudioTableMouseListener extends MouseAdapter {

	private JTable jt;
	private static Employee stud;

	public Employee getStudio() {
		return stud;
	}

	public StudioTableMouseListener(JTable jt, Object[] number, Employee stud) {
		this.stud = stud;
		this.jt = jt;
	}

	// 监听到行号，将所选行的内容依次赋到 stud对象，以便传有值对象到修改面板进行修改
	public void mouseClicked(MouseEvent event) {
		int row = jt.getSelectedRow();
		stud.setId(Integer.parseInt(jt.getValueAt(row, 0).toString()));
		stud.setEmployee_workid(Integer.parseInt(jt.getValueAt(row, 1).toString()));
		stud.setEmployee_name(jt.getValueAt(row, 2).toString());
		stud.setEmployee_tel(jt.getValueAt(row, 3).toString()); // 0
		stud.setEmployee_address(jt.getValueAt(row, 4).toString());
		stud.setEmployee_email(jt.getValueAt(row, 5).toString());
		System.out.println(jt.getValueAt(row, 1).toString());
	}
}

class StudioTable {

	private Employee stud;
	private JTable jt = null;

	public StudioTable(Employee stud) {
		this.stud = stud;
	}

	// 创建JTable
	public void createTable(JScrollPane jp, Object[] columnNames, List<Employee> stuList) {
		try {

			Object data[][] = new Object[stuList.size()][columnNames.length];

			Iterator<Employee> itr = stuList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Employee stu = itr.next();
				data[i][0] = Integer.toString(stu.getId());
				data[i][1] = Integer.toString(stu.getEmployee_workid());
				data[i][2] = stu.getEmployee_name();
				data[i][3] = stu.getEmployee_tel();
				data[i][4] = stu.getEmployee_address();
				data[i][5] = stu.getEmployee_email();
				i++;
			}

			// 生成JTable
			jt = new JTable(data, columnNames);
			jt.setBounds(0, 0, 700, 450);

			// 添加鼠标监听，监听到所选行
			StudioTableMouseListener tml = new StudioTableMouseListener(jt, columnNames, stud);
			jt.addMouseListener(tml);

			// 设置可调整列宽
			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			jp.add(jt);
			jp.setViewportView(jt);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class EmployeeMgrUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Employee stud=new Employee();
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	private JScrollPane jsc;
	// 查找的提示和输出
	
	

	// 查找，编辑和删除按钮
	private JButton btnAdd, btnEdit, btnDel, btnQuery;

	public EmployeeMgrUI() {
		initContent();
		this.setVisible(true);
	}

	// To be override by the detailed business block interface
//	@Override
	public void initContent() {
		Rectangle rect = this.getBounds();
		
		setLayout(new BorderLayout());
		JPanel topJPanel = new JPanel();
		add(topJPanel, BorderLayout.NORTH);
		ca1 = new JLabel("员工管理", JLabel.CENTER);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		topJPanel.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 80, rect.width-30, rect.height - 290);
		add(jsc, BorderLayout.CENTER);

		btnQuery = new JButton("查找");
		btnQuery.setBounds(100, rect.height - 160, 60, 30);

		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		topJPanel.add(btnQuery);

		btnAdd = new JButton("添加");
		btnAdd.setBounds(rect.width - 660, rect.height - 160, 60, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnAddClicked();
			}
		});
		topJPanel.add(btnAdd);

		btnEdit = new JButton("修改");
		btnEdit.setBounds(rect.width - 400, rect.height - 160, 60, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnModClicked();
			}
		});
		topJPanel.add(btnEdit);

		btnDel = new JButton("删除");
		btnDel.setBounds(rect.width - 150, rect.height - 160, 60, 30);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDelClicked();
			}
		});
		topJPanel.add(btnDel);
		showTable();
	}

	private void btnAddClicked() {
		EmployeeAddUI addStud = new EmployeeAddUI();
//		addStud.setWindowName("员工注册");
		addStud.toFront();
		addStud.setModal(true);
		addStud.setVisible(true);

		if (addStud.getReturnStatus()) {
			showTable();
		}
	}

	private void btnModClicked() {
			
		EmployeeEditUI modStu = new EmployeeEditUI(stud);
	//	modStu.setWindowName("修改员工信息");
		modStu.toFront();
		modStu.setModal(true);
		modStu.setVisible(true);
		if (modStu.getReturnStatus()) {
			showTable();
		}
	}

	private void btnDelClicked() {
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			EmployeeSrv stuSrv = new EmployeeSrv();
			stuSrv.delete(stud.getId());
			showTable();
		}
	}

	private void btnQueryClicked() {
		String s=JOptionPane.showInputDialog("请输入要查找员工的名称:");
		
		
		
		if (s.equals("")) {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
			

		} else {
			                     
			
			EmployeeSrv stuSrv = new EmployeeSrv();
			List<Employee> stuList = stuSrv.Fetch(s);;
			StudioTable tms = new StudioTable(stud);
			Object[] in = { "id", "emp_no", "name", "tel_num", "address", "Email"};
			tms.createTable(jsc, in, stuList);
			jsc.repaint();
			
			
		}
	}

	public void showTable() {
		StudioTable tms = new StudioTable(stud);
		Object[] in = { "id", "emp_no", "name", "tel_num", "address", "Email"};
		List<Employee> stuList = new EmployeeSrv().FetchAll();

		tms.createTable(jsc, in, stuList);
		jsc.repaint();
	}

//	public static void main(String[] args) {
//		EmployeeMgrUI frmStuMgr = new EmployeeMgrUI();
//		frmStuMgr.setVisible(true);
//	}
}
