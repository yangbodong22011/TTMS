package src.xupt.se.ttms.view.seat;

import java.awt.Color;

import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.util.List;
import java.util.Iterator;

import src.xupt.se.ttms.model.Seat;
import src.xupt.se.ttms.service.SeatSrv;
import src.xupt.se.ttms.view.tmpl.*;

class SeatTableMouseListener extends MouseAdapter {

	private JTable jt;
	private static Seat seat;

	public Seat getSeat() {
		return seat;
	}

	public SeatTableMouseListener(JTable jt, Object[] number, Seat seat) {
		this.seat = seat;
		this.jt = jt;
	}

	// 监听到行号，将所选行的内容依次赋到ddict对象，以便传有值对象到修改面板进行修改
	public void mouseClicked(MouseEvent event) {
		int row = jt.getSelectedRow();
		seat.setId(Integer.parseInt(jt.getValueAt(row, 0).toString()));
		seat.setStudioId(Integer.parseInt(jt.getValueAt(row, 1).toString()));
		seat.setRow(Integer.parseInt(jt.getValueAt(row, 2).toString())); 
		seat.setColumn(Integer.parseInt(jt.getValueAt(row, 3).toString()));
		
		System.out.println(jt.getValueAt(row, 1).toString());
	}
}

class SeatTable {

	private Seat  seat;
	private JTable jt = null;

	public SeatTable(Seat seat) {
		this.seat = seat;
	}

	// 创建JTable
	public void createTable(JScrollPane jp, Object[] columnNames, List<Seat> seatList) {
		try {

			Object data[][] = new Object[seatList.size()][columnNames.length];

			Iterator<Seat> itr =seatList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Seat tmpSeat = itr.next();
				data[i][0] = Integer.toString(tmpSeat.getId());
				data[i][1] = Integer.toString(tmpSeat.getStudioId());
				data[i][2] = Integer.toString(tmpSeat.getRow());
				data[i][3] = Integer.toString(tmpSeat.getColumn());
				i++;
			}

			// 生成JTable
			jt = new JTable(data, columnNames);
			jt.setBounds(0, 0, 700, 450);

			// 添加鼠标监听，监听到所选行
			SeatTableMouseListener tml = new  SeatTableMouseListener(jt, columnNames, seat);
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

public class SeatMgrUI extends MainUITmpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Seat seat=new Seat();
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	private JScrollPane jsc;
	// 查找的提示和输出
	private JLabel hint;
	private JTextField input;

	// 查找，编辑和删除按钮
	private JButton btnAdd, btnEdit, btnDel, btnQuery;

	public SeatMgrUI() {
		initialize();
	}

	// To be override by the detailed business block interface
	
	private void initialize() {
		Rectangle rect = contPan.getBounds();

		ca1 = new JLabel("座位管理", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		contPan.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 90);
		contPan.add(jsc);

		hint = new JLabel("请输入所在影厅ID:", JLabel.RIGHT);
		hint.setBounds(60, rect.height - 45, 150, 30);
		contPan.add(hint);

		input = new JTextField();
		input.setBounds(220, rect.height - 45, 200, 30);
		contPan.add(input);

		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
		btnQuery = new JButton("查找");
		btnQuery.setBounds(440, rect.height - 45, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		contPan.add(btnQuery);

		btnAdd = new JButton("添加");
		btnAdd.setBounds(rect.width - 220, rect.height - 45, 60, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnAddClicked();
			}
		});
		contPan.add(btnAdd);

		btnEdit = new JButton("修改");
		btnEdit.setBounds(rect.width - 150, rect.height - 45, 60, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnModClicked();
			}
		});
		contPan.add(btnEdit);

		btnDel = new JButton("删除");
		btnDel.setBounds(rect.width - 80, rect.height - 45, 60, 30);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDelClicked();
			}
		});
		contPan.add(btnDel);
		contPan.add(ca1);
		showTable();
	}

	private void btnAddClicked() {
		SeatAddUI addStud = new SeatAddUI();
		addStud.toFront();
		addStud.setModal(true);
		addStud.setVisible(true);

		if (addStud.getReturnStatus()) {
			showTable();
		}
	}

	private void btnModClicked() {
			
		SeatEditUI modStu = new SeatEditUI(seat);
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
			SeatSrv seatSrv = new SeatSrv();
			seatSrv.delete(seat.getId());
			showTable();
		}
	}

	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
			SeatTable tms = new SeatTable(seat);
			Object[] in = { "id", "studioId", "row", "column" };
			List<Seat> stuList = new SeatSrv().Fetch("studio_id = " + input.getText());

			tms.createTable(jsc, in, stuList);
			jsc.repaint();

		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
		}
	}

	public void showTable() {
		SeatTable tms = new SeatTable(seat);
		Object[] in = { "id", "studioId", "row", "column" };
		List<Seat> stuList = new SeatSrv().FetchAll();

		tms.createTable(jsc, in, stuList);
		jsc.repaint();
	}

	public static void main(String[] args) {
		SeatMgrUI frmStuMgr = new SeatMgrUI();
		frmStuMgr.setVisible(true);
	}
}
