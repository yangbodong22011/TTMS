package src.xupt.se.ttms.view.schedule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import src.xupt.se.ttms.model.Schedule;
import src.xupt.se.ttms.model.Studio;
import src.xupt.se.ttms.service.ScheduleSrv;
import src.xupt.se.ttms.service.StudioSrv;
import src.xupt.se.ttms.view.tmpl.*;
import src.xupt.se.util.NewClass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Iterator;


public class ScheduleMgrUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private Schedule schedule=new Schedule();
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	private JScrollPane jsc;
	// 查找的提示和输出
	private JLabel hint;
	private JTextField input;

	// 查找，编辑和删除按钮
	private JButton btnAdd, btnEdit, btnDel, btnQuery;

	public ScheduleMgrUI() {
		initContent();
		this.setVisible(true);
	}
	
	protected void initContent() {
		Rectangle rect = this.getBounds();
		this.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel(new FlowLayout());
		JPanel centerJPanel = new JPanel(new BorderLayout());
		
	
		centerJPanel.add(topPanel,BorderLayout.NORTH);
		add(centerJPanel,BorderLayout.CENTER);
		
		
		
		ca1 = new JLabel("演出计划管理", JLabel.CENTER);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		topPanel.add(ca1);
		
		hint = new JLabel("请输入演出计划名称:", JLabel.RIGHT);
		hint.setBounds(60, rect.height - 45, 150, 30);
		topPanel.add(hint);

		input = new JTextField(10);
		input.setBounds(220, rect.height - 45, 200, 30);
		topPanel.add(input);

		btnQuery = new JButton("查找");
		btnQuery.setBounds(440, rect.height - 45, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		topPanel.add(btnQuery);

		btnAdd = new JButton("添加");
		btnAdd.setBounds(rect.width - 220, rect.height - 45, 60, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnAddClicked();
			}
		});
		topPanel.add(btnAdd);

		btnEdit = new JButton("修改");
		btnEdit.setBounds(rect.width - 150, rect.height - 45, 60, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnModClicked();
			}
		});
		topPanel.add(btnEdit);

		btnDel = new JButton("删除");
		btnDel.setBounds(rect.width - 80, rect.height - 45, 60, 30);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDelClicked();
			}
		});
		topPanel.add(btnDel);
		
		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 90);
		centerJPanel.add(jsc, BorderLayout.CENTER); 
		
		showTable();
	}

	private void btnAddClicked() {
		ScheduleAddUI addSchedule = new ScheduleAddUI();
	//	addSchedule.setWindowName("添加演出计划");
		addSchedule.toFront();
		addSchedule.setModal(true);
		addSchedule.setVisible(true);
		if (addSchedule.getReturnStatus()) {
			showTable();
		}
		
	}

	private void btnModClicked() {	
	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sched_time = schedule.getSched_time();
		System.out.println("----------- "+sched_time);
		ScheduleEditUI modSchedule = new ScheduleEditUI(schedule);
//		modSchedule.setWindowName("修改演出计划");
		modSchedule.toFront();
		modSchedule.setModal(true);
		modSchedule.setVisible(true);
		if (modSchedule.getReturnStatus()) {
			showTable();
		}
	}

	private void btnDelClicked() {
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			ScheduleSrv scheduleSrv = new ScheduleSrv();
			scheduleSrv.delete(schedule.getSched_id());
			showTable();
		}
	}

	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
			ScheduleTable tms = new ScheduleTable(schedule);
			Object[] in = { "sched_id","studio_id", "play_id" , "sched_time", "sched_ticket_price" };
			List<Schedule> stuList = new ScheduleSrv().Fetch("sched_id = " + input.getText());
			tms.createTable(jsc, in, stuList);
			jsc.repaint();

		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
		}
	}

	public void showTable() {
		ScheduleTable tms = new ScheduleTable(schedule);
		Object[] in = { "sched_id","studio_id", "play_id" , "sched_time", "sched_ticket_price" };
		List<Schedule> stuList = new ScheduleSrv().FetchAll();

		tms.createTable(jsc, in, stuList);
		jsc.repaint();
	}

	public static void main(String[] args) {
		ScheduleMgrUI frmStuMgr = new ScheduleMgrUI();
		frmStuMgr.setVisible(true);
	}
}
