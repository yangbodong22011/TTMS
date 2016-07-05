package xupt.se.ttms.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

import javax.swing.border.TitledBorder;

import xupt.se.ttms.idao.iTicketDAO;
import xupt.se.ttms.model.Ticket;
import xupt.se.util.DBUtil;

public class TicketDAO implements iTicketDAO {
	@Override
	public int insert(Ticket ticket) {
		try {
			String sql = "insert into ticket (seat_id, sched_id ,ticket_price ,ticket_status )"
					+ " values(" + ticket.getSeatId()
					+ ", " +  ticket.getScheduleId()
					+ ", " +  ticket.getPrice()
					+ ", " +  1
					+" )";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sql);
			if (rst!=null && rst.first()) {
				ticket.setId(rst.getInt(1));
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
	public int update(Ticket ticket) {
		int rtn=0;
		try {
			String sql = "";
			if(ticket.getLocked_time() != null) {
				sql += "update ticket set " 
		            + " seat_id =" + ticket.getSeatId() + ", " 
					+ " sched_id = " +   ticket.getScheduleId() + ", " 
					+ " ticket_price = " + ticket.getPrice()  + ", "
					+ " ticket_status = " + ticket.getStatus() +","
					+ " ticket_locked_time = '" + ticket.getLocked_time() +"'";
			}else {
				sql += "update ticket set " 
			            + " seat_id =" + ticket.getSeatId() + ", " 
						+ " sched_id = " +   ticket.getScheduleId() + ", " 
						+ " ticket_price = " + ticket.getPrice()  + ", "
						+ " ticket_status = " + ticket.getStatus() +","
						+ " ticket_locked_time = null ";
			}
			sql += " where ticket_id = " + ticket.getId();
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
			String sql = "delete from ticket ";
			sql += " where ticket_id = " + ID;
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
	public List<Ticket> select(String condt) {
		List<Ticket> ticketList = null;
		ticketList = new LinkedList<Ticket>();
		try {
			String sql = "select * from ticket ";
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
					
					Ticket ticket = new Ticket();
					ticket.setId(rst.getInt("ticket_id"));
					ticket.setSeatId(rst.getInt("seat_id"));
					ticket.setScheduleId(rst.getInt("sched_id"));
					ticket.setPrice(rst.getFloat("ticket_price"));
					ticket.setStatus(rst.getInt("ticket_status"));
					ticket.setLocked_time(rst.getTimestamp("ticket_locked_time"));
					ticketList.add(ticket);
					
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	
		return ticketList;
	}

	@Override
	public int lockTicket(int ID, String time) {
		int rtn=0;
		try {
			String sql = "update ticket set ticket_status=1, ticket_locked_time='" + time + "'";
			sql += " where ticket_id = " + ID;
		
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
	public int unlockTicket(int ID) {
		int rtn=0;
		try {
			String sql = "update ticket set ticket_status=0";
			sql += " where ticket_id = " + ID;
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn =db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}
}
