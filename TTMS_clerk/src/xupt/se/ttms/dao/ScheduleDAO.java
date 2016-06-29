package src.xupt.se.ttms.dao;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

import src.xupt.se.ttms.idao.iScheduleDAO;
import src.xupt.se.ttms.model.Schedule;
import src.xupt.se.util.DBUtil;

public class ScheduleDAO implements iScheduleDAO {
	@Override
	public int insert(Schedule stu) {
		try {
			String sql = "insert into schedule(studio_id, play_id, sched_time, sched_ticket_price)"
					+ " values('"
					+ stu.getStudio_id()
					+ "', "
					+ stu.getPlay_id()

					+ ", " + "?"
					+ ", '" + stu.getSched_ticket_price()
					+ "' )";
			DBUtil db = new DBUtil();
			db.openConnection();
			PreparedStatement ps = db.execPrepared(sql);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(stu.getSched_time());
			ps.setString(1,date);
			int count=ps.executeUpdate();
			if (count!=0) {
				stu.setSched_id(count);
			}
			db.close();
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public int update(Schedule stu) {
		int rtn = 0;
		try {/*"update studio set " + " studio_name ='"

					+ stu.getName() + "', " + " studio_row_count = "
					+ stu.getRowCount() + ", " + " studio_col_count = "
					+ stu.getColCount() + ", " + " studio_introduction = '"
     				+ stu.getIntroduction()+ "' ," + " studio_flag = "
					+ stu.getStudioFlag();*/
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(stu.getSched_time());
			String sql = " update schedule set "
					+ " studio_id ="+ stu.getStudio_id() + ", "
					+ " play_id = "+ stu.getPlay_id() + ", "
					+ " sched_time = '"+ date + "', "
					+ " sched_ticket_price = '"+ stu.getSched_ticket_price() + "' " ;

			sql += " where sched_id = " + stu.getSched_id();
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn = db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public int delete(int ID) {
		int rtn = 0;
		try {
			String sql = "delete from  schedule ";
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

	@Override
	public List<Schedule> select(String condt) {
		List<Schedule> stuList = null;
		stuList = new LinkedList<Schedule>();
		try {
			String sql = "select sched_id, studio_id, play_id, sched_time, sched_ticket_price from schedule ";
			condt.trim();
			if (!condt.isEmpty())
				sql += " where " + condt;
			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst != null) {
				while (rst.next()) {
					Schedule stu = new Schedule();
					stu.setSched_id(rst.getInt("sched_id"));
					stu.setStudio_id(rst.getInt("studio_id"));
					stu.setPlay_id(rst.getInt("play_id"));
					stu.setSched_time(rst.getTimestamp("sched_time"));
					stu.setSched_ticket_price(rst.getDouble("sched_ticket_price"));
					stuList.add(stu);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return stuList;
	}
	public List<Schedule> myselect(String condt) {
		List<Schedule> stuList = null;
		stuList = new LinkedList<Schedule>();
		try {
			String sql = "select sched_id, schedule.studio_id, play_id, sched_time, sched_ticket_price,studio_name from schedule,studio" +
					" where schedule.studio_id=studio.studio_id AND ";
			condt.trim();
			if (!condt.isEmpty())
				sql +=  condt + " ORDER BY sched_time";
				//sql += condt;

			System.out.println(sql);

			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst != null) {
				while (rst.next()) {
					Schedule stu = new Schedule();
					stu.setSched_id(rst.getInt("sched_id"));
					stu.setStudio_id(rst.getInt("studio_id"));
					stu.setPlay_id(rst.getInt("play_id"));
					stu.setSched_time(rst.getTimestamp("sched_time"));
					stu.setSched_ticket_price(rst.getDouble("sched_ticket_price"));
					stu.setStudio_name(rst.getString("studio_name"));
					stuList.add(stu);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return stuList;
	}
}
