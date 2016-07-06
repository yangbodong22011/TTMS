package src.xupt.se.ttms.view.studio;

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

import src.xupt.se.ttms.model.Studio;
import src.xupt.se.ttms.service.StudioSrv;
import src.xupt.se.ttms.view.tmpl.*;
import src.xupt.se.util.tableJButton;

import java.util.List;
import java.util.Iterator;


class StudioTableMouseListener extends MouseAdapter {

	private JTable jt;
	private static Studio stud;

	public Studio getStudio() {
		return stud;
	}

	public StudioTableMouseListener(JTable jt, Object[] number, Studio stud) {
		this.stud = stud;
		this.jt = jt;
	}

	// 监听到行号，将所选行的内容依次赋到 stud对象，以便传有值对象到修改面板进行修改
	public void mouseClicked(MouseEvent event) {
		int row = jt.getSelectedRow();
		stud.setID(Integer.parseInt(jt.getValueAt(row, 0).toString()));
		stud.setName(jt.getValueAt(row, 1).toString());
		stud.setRowCount(Integer.parseInt(jt.getValueAt(row, 2).toString())); // 0
		stud.setColCount(Integer.parseInt(jt.getValueAt(row, 3).toString()));
		stud.setStudioFlag(Integer.parseInt(jt.getValueAt(row, 5).toString()));
		if (jt.getValueAt(row, 4) != null)
			stud.setIntroduction(jt.getValueAt(row, 4).toString());
		else
			stud.setIntroduction("");
	//	System.out.println(jt.getValueAt(row, 1).toString());
	}
}

public class StudioTable {

	private Studio stud;
	private JTable jt = null;

	public StudioTable(Studio stud) {
		this.stud = stud;
	}

	// 创建JTable
	public void createTable(JScrollPane jp, Object[] columnNames, List<Studio> stuList) {
		try {

			Object data[][] = new Object[stuList.size()][columnNames.length];

			Iterator<Studio> itr = stuList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Studio stu = itr.next();
				data[i][0] = Integer.toString(stu.getID());
				data[i][1] = stu.getName();
				data[i][2] = Integer.toString(stu.getRowCount());
				data[i][3] = Integer.toString(stu.getColCount());
				data[i][4] = stu.getIntroduction();
				data[i][5] = Integer.toString(stu.getStudioFlag());
				
				i++;
			}

			// 生成JTable
			jt = new JTable(data, columnNames);
			jt.setBounds(0, 0, 700, 450);
			for(int num=0;num<columnNames.length;num++){
		//		jt.getColumn(num).setCellRenderer(new tableJButton());
			}
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

