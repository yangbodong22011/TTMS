package src.xupt.se.ttms.view.schedule;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import src.xupt.se.ttms.model.Schedule;
import src.xupt.se.ttms.service.PlaySrv;
import src.xupt.se.ttms.service.ScheduleSrv;
import src.xupt.se.ttms.service.StudioSrv;

class ScheduleTableMouseListener extends MouseAdapter {

	private JTable jt;
	private static Schedule schedule;

	public Schedule getSchedule() {
		return schedule;
	}

	public ScheduleTableMouseListener(JTable jt, Object[] number, Schedule schedule) {
		this.schedule = schedule;
		this.jt = jt;
	}
	
			
	// 监听到行号，将所选行的内容依次赋到 schedule对象，以便传有值对象到修改面板进行修改
	public void mouseClicked(MouseEvent event) {
		int row = jt.getSelectedRow();
		schedule.setSched_id(Integer.parseInt(jt.getValueAt(row, 0).toString()));
		schedule.setStudio_id(new StudioSrv().Fetch("studio_name = '"+jt.getValueAt(row, 1).toString() +"'").get(0).getId());
		schedule.setPlay_id(new PlaySrv().Fetch("play_name = '"+jt.getValueAt(row, 2).toString()+"'").get(0).getId()); // 0
		java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟   
		java.util.Date date = null;
		try {
			
			date = sdf.parse(jt.getValueAt(row, 3).toString());
		//	date = new Timestamp(jt.getValueAt(row, 3).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		schedule.setSched_time(date);
		schedule.setSched_ticket_price(Float.parseFloat(jt.getValueAt(row, 4).toString())); // 0
	}
}

public class ScheduleTable {

	private Schedule schedule;
	private JTable jt = null;
	private ScheduleSrv scheduleSrv = new ScheduleSrv();
	private PlaySrv playSrv = new PlaySrv();
	private StudioSrv studioSrv = new StudioSrv();
	public ScheduleTable(Schedule schedule) {
		this.schedule = schedule;
	}

	// 创建JTable
	public void createTable(JScrollPane jp, Object[] columnNames, List<Schedule> scheduleList) {
		try {

			Object data[][] = new Object[scheduleList.size()][columnNames.length];

			Iterator<Schedule> itr = scheduleList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Schedule schedule = itr.next();
			    data[i][0] = schedule.getSched_id();
				data[i][1] = studioSrv.Fetch("studio_id = " + schedule.getStudio_id()).get(0).getName();
				data[i][2] = playSrv.Fetch("play_id = " + schedule.getPlay_id()).get(0).getName();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = df.format(schedule.getSched_time());
				data[i][3] = date;
				data[i][4] = schedule.getSched_ticket_price();
				i++;
			}

			// 生成JTable
			jt = new JTable(data, columnNames);
			jt.setBounds(0, 0, 700, 450);

			// 添加鼠标监听，监听到所选行
			ScheduleTableMouseListener tml = new ScheduleTableMouseListener(jt, columnNames, schedule);
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
