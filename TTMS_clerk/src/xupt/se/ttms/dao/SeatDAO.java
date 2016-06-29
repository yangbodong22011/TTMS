package src.xupt.se.ttms.dao;

import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

import src.xupt.se.ttms.idao.iSeatDAO;
import src.xupt.se.ttms.model.Seat;
import src.xupt.se.util.DBUtil;

public class SeatDAO implements iSeatDAO {
	@Override
	public int insert(Seat seat) {

		// TODO Auto-generated method stub
				try {
					String sql = "insert into seat ( studio_id ,seat_row , seat_column,seat_status)"
							+ " values(" + seat.getStudioId()
							+ ", " +  seat.getRow()
							+ ", " +  seat.getColumn()
							+ ", " +  seat.getSeatStatus()
							+ " )";
					DBUtil db = new DBUtil();
					db.openConnection();
					ResultSet rst = db.getInsertObjectIDs(sql);
					if (rst!=null && rst.first()) {
						seat.setId(rst.getInt(1));
					}
					db.close(rst);
					db.close();
					return 1;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return 0;
	}

	@Override
	public int update(Seat seat) {

		// TODO Auto-generated method stub
		int rtn=0;
		try {
			String sql = "update seat set "
					+ " studio_id =" + seat.getStudioId() + ", "
					+ " seat_row = " +  seat.getRow() + ", "
					+ " seat_column = " + seat.getColumn()  + ", "
					+ " seat_status = " + seat.getSeatStatus()
					;

			sql += " where seat_id = " + seat.getId();
			System.out.println(sql);
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn =db.execCommand(sql);
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
			String sql = "delete from seat ";
			sql += " where seat_id = " + ID;
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
	public List<Seat> select(String condt) {
		List<Seat> stuList = null;
		stuList = new LinkedList<Seat>();
		try {
			String sql = "select * from seat ";
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
					Seat stu = new Seat();
					stu.setId(rst.getInt("seat_id"));
					stu.setStudioId(rst.getInt("studio_id"));
					stu.setRow(rst.getInt("seat_row"));
					stu.setColumn(rst.getInt("seat_column"));
					stu.setSeatStatus(rst.getInt("seat_status"));
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

	public int getSeatStatus(Seat seat){
		int status = -2;
		try {
			String sql = "select seat_status from seat where seat_id = " + seat.getId();

			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				System.out.print("fail to connect database");
				return -2;
			}
			ResultSet rst = db.execQuery(sql);
			status = rst.getInt("seat_status");
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return status;

	}
}
