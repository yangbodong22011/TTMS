package src.xupt.se.ttms.dao;

import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

import src.xupt.se.ttms.idao.iStudioDAO;
import src.xupt.se.ttms.model.Seat;
import src.xupt.se.ttms.model.Studio;
import src.xupt.se.util.DBUtil;



public class StudioDAO implements iStudioDAO {
	
	SeatDAO seatDAO = new SeatDAO();
	@Override
	public int insert(Studio stu) {
		try {
			String sql = "insert into studio(studio_name, studio_row_count, studio_col_count, studio_introduction, studio_flag)"
					+ " values('"
					+ stu.getName()
					+ "', "
					+ stu.getRowCount()
					+ ", " + stu.getColCount()
					+ ", '" + stu.getIntroduction()
					+ "',"+ stu.getStudioFlag() +")";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sql);
			if (rst!=null && rst.first()) {
				stu.setID(rst.getInt(1));
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
	public int update(Studio stu) {
		int rtn=0;
		try {
			String sql = "update studio set " + " studio_name ='"
					+ stu.getName() + "', " + " studio_row_count = "
					+ stu.getRowCount() + ", " + " studio_col_count = "
					+ stu.getColCount() + ", " + " studio_introduction = '"
					+ stu.getIntroduction() + "' ," + " studio_flag = " 
					+ stu.getStudioFlag() ;
			
			
			sql += " where studio_id = " + stu.getID();
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
		int rtn=0;		
		try{
			String sql = "delete from  studio ";
			sql += " where studio_id = " + ID;
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn=db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;		
	}
	@Override
	public List<Studio> select(String condt) {
		List<Studio> stuList = null;
		stuList=new LinkedList<Studio>();
		try {
			String sql = "select studio_id, studio_name, studio_row_count, studio_col_count, studio_introduction , studio_flag from studio ";
			condt.trim();
			if(!condt.isEmpty())
				sql+= " where " + condt;
			DBUtil db = new DBUtil();
			if(!db.openConnection()){
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst!=null) {
				while(rst.next()){
					Studio stu=new Studio();
					stu.setID(rst.getInt("studio_id"));
					stu.setName(rst.getString("studio_name"));
					stu.setRowCount(rst.getInt("studio_row_count"));
					stu.setColCount(rst.getInt("studio_col_count"));
					stu.setIntroduction(rst.getString("studio_introduction"));
					stu.setFlag(rst.getInt("studio_flag"));
					stuList.add(stu);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
		}
		
		return stuList;
	}
	public Studio myselect(String condt) {
		Studio stu = new Studio();
		try {
			String sql = "select studio_id, studio_name, studio_row_count, studio_col_count, studio_introduction , studio_flag from studio ";
			condt.trim();
			if(!condt.isEmpty())
				sql+= " where " + condt;
			DBUtil db = new DBUtil();
			if(!db.openConnection()){
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst!=null) {
				while(rst.next()){
					stu.setID(rst.getInt("studio_id"));
					stu.setName(rst.getString("studio_name"));
					stu.setRowCount(Integer.parseInt(rst.getString("studio_row_count")));
					stu.setColCount(Integer.parseInt(rst.getString("studio_col_count")));
					stu.setIntroduction(rst.getString("studio_introduction"));
					stu.setStudioFlag(rst.getInt("studio_flag"));
					//System.out.println(Integer.parseInt(rst.getString("studio_row_count"))+ "  "+rst.getInt("studio_row_count")+ "  "+ stu.getColCount());
					return stu;
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{

		}
		return stu;
	}

	@Override
	public int createSeats(Studio stu) {
		Seat seat = new Seat();
		try{	
			for(int i=1;i<=stu.getRowCount();i++)
				for(int j=1;j<=stu.getColCount();j++)
			{
				seat.setRow(i);
				seat.setColumn(j);
				seat.setSeatStatus(1);
				seatDAO.insert(seat);
			} 
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
}
