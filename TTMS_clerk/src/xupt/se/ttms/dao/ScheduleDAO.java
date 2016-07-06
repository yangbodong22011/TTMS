package xupt.se.ttms.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import xupt.se.ttms.idao.iScheduleDAO;
import xupt.se.ttms.model.Schedule;
import xupt.se.util.DBUtil;

public class ScheduleDAO implements iScheduleDAO {
	public int insert(Schedule sched) {
		try {
			String sql = "insert into Schedule(studio_id, play_id, sched_time, sched_ticket_price)"
					+ " values('"
					+ sched.getStudio_id()
					+ "', "
					+ sched.getPlay_id()
					
					+ ", " + "?"
					+ ", '" + sched.getSched_ticket_price()
					+ "' )";
			DBUtil db = new DBUtil();
			db.openConnection();
			PreparedStatement ps = db.execPrepared(sql);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(sched.getSched_time());
			ps.setString(1,date);
			int count=ps.executeUpdate();
			if (count!=0) {
				sched.setSched_id(count);
			}
			db.close();
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public int update(Schedule sched) {
		int rtn = 0;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(sched.getSched_time());
			String sql = " update Schedule set "
					+ " studio_id ="+ sched.getStudio_id() + ", " 
					+ " play_id = "+ sched.getPlay_id() + ", " 
					+ " sched_time = '"+ date + "', " 
					+ " sched_ticket_price = '"+ sched.getSched_ticket_price() + "' " ;

			sql += " where sched_id = " + sched.getSched_id();
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn = db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	public int delete(int ID) {
		int rtn = 0;
		try {
			String sql = "delete from  Schedule ";
			sql += " where sched_id = " + ID;
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn = db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	public List<Schedule> select(String condt) {
		List<Schedule> schedList = null;
		schedList = new LinkedList<Schedule>();
		try {
			String sql = "select sched_id, studio_id, play_id, sched_time, sched_ticket_price from Schedule ";
			condt.trim();
			if (!condt.isEmpty())
				sql += " where " + condt;
	//		System.out.println(sql);
			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst != null) {
				while (rst.next()) {
					Schedule sched = new Schedule();
					sched.setSched_id(rst.getInt("sched_id"));
					sched.setStudio_id(rst.getInt("studio_id"));
					sched.setPlay_id(rst.getInt("play_id"));
					sched.setSched_time(rst.getTimestamp("sched_time"));
					sched.setSched_ticket_price(rst.getDouble("sched_ticket_price"));
					schedList.add(sched);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return schedList;
	}


	@Override
	public List<Schedule> selectPlay(String condt) {
		List<Schedule> schedList = null;
		schedList = new LinkedList<Schedule>();
		try {
			String sql = "select distinct play_id from Schedule ";
			condt.trim();
			if (!condt.isEmpty())
				sql += " where " + condt;
		//	System.out.println(sql);
			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst != null) {
				while (rst.next()) {
					Schedule sched = new Schedule();
					sched.setPlay_id(rst.getInt("play_id"));
					schedList.add(sched);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		
		return schedList;
	}
}
