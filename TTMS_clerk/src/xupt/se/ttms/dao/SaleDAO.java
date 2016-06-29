package src.xupt.se.ttms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import src.xupt.se.ttms.idao.iSaleDAO;
import src.xupt.se.ttms.model.Sale;
import src.xupt.se.ttms.model.SaleItem;
import src.xupt.se.ttms.model.Seat;
import src.xupt.se.ttms.model.Studio;
import src.xupt.se.ttms.model.Ticket;
import src.xupt.se.ttms.service.PlaySrv;
import src.xupt.se.ttms.service.ScheduleSrv;
import src.xupt.se.util.DBUtil;


public class SaleDAO implements iSaleDAO {
	DBUtil db;
	Connection con;

	@Override
	public boolean doSale(List<Ticket> tickets, Sale sale) {
		try {
			int id = -1;
			db = new DBUtil();
			db.openConnection();
			con = db.getConn();
			con.setAutoCommit(false);

			String sqlDoSale = "insert into sale(emp_id, sale_time, sale_payment, sale_change, sale_type, sale_status) VALUES(?,?,?,?,1,1)";
			PreparedStatement prep = con.prepareStatement(sqlDoSale, PreparedStatement.RETURN_GENERATED_KEYS);  ;
			prep.setInt(1, sale.getEmpId());
			prep.setTimestamp(2, new Timestamp(new Date().getTime()));
			prep.setFloat(3, sale.getPayment());
			prep.setFloat(4, sale.getChange());
			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();

			//      System.out.println(">1");

			if (rs.next()) {
				id = rs.getInt(1);
			}
			if(id>0){
				for(Ticket t : tickets){
					double price = new ScheduleSrv().Fetch("sched_id = " + t.getScheduleId()).get(0).getSched_ticket_price();
					String sqlDoSaleItem = "insert into sale_item(ticket_id, sale_ID, sale_item_price) VALUES(" +
							t.getId() + ", " + id + ", " + price + ")";

					int flag = db.execCommand(sqlDoSaleItem);

					if(flag==1){

						//     		System.out.println(">2");
						Date ticket_locked_time = t.getLocked_time();
						//     		new Date(t.getLocked_time().getTime() +
						String sqlUpDataTic = "update ticket set ticket_status = 9, ticket_locked_time = '" + new Timestamp(new PlaySrv().Fetch("play_id = " + new ScheduleSrv().Fetch("sched_id = "+t.getScheduleId()).get(0).getPlay_id()).get(0).getLength() * 60000) + "' where ticket_id = " + t.getId();
						int flag2 = db.execCommand(sqlUpDataTic);
						if(flag2!=1){
							return false;
						}
						//        	System.out.println(">3");
					}else
						return false;
				}
			}
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		} finally {
			try {
				con.setAutoCommit(true);
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	public boolean mydoSale(List<Ticket> tickets, Sale sale) {
        try {
            String sql = "insert into sale(emp_id, sale_time, sale_payment, sale_change, sale_type, sale_status)"
                    + " values('"
                    + sale.getEmpId()
                    + "', '"
                    + new Timestamp(new Date().getTime())
                    + "', " + sale.getPayment()
                    + ", '" + sale.getChange()
                    +"' , '1','1'"
                    + " )";
            System.out.println(sql);
            DBUtil db = new DBUtil();
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);

            int id = -1;
            if (rst.next()) {
                id = rst.getInt(1);
            }

            for (Ticket t : tickets) {
                double price = new ScheduleSrv().Fetch("sched_id = " + t.getScheduleId()).get(0).getSched_ticket_price();
                String sqlDoSaleItem = "insert into sale_item(ticket_id, sale_ID, sale_item_price) VALUES(" +
                        t.getId() + ", " + id + ", " + price + ")";
                db.execCommand(sqlDoSaleItem);
            }

            db.close(rst);
            db.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
		/*try {
			int id = -1;
			db = new DBUtil();
			db.openConnection();
			con = db.getConn();
			con.setAutoCommit(false);

			String sqlDoSale = "insert into sale(emp_id, sale_time, sale_payment, sale_change, sale_type, sale_status) VALUES(?,?,?,?,1,1)";
			PreparedStatement prep = con.prepareStatement(sqlDoSale, PreparedStatement.RETURN_GENERATED_KEYS);

			prep.setInt(1, sale.getEmpId());
			prep.setTimestamp(2, new Timestamp(new Date().getTime()));
			prep.setFloat(3, sale.getPayment());
			prep.setFloat(4, sale.getChange());


            System.out.println(sale.getEmpId()+"  "+sale.getPayment()+ "  "+sale.getChange()+ " "+
            new Timestamp(new Date().getTime()));

			prep.executeUpdate();


			ResultSet rs = prep.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getInt(1);
			}
			if (id > 0) {
				for (Ticket t : tickets) {
					double price = new ScheduleSrv().Fetch("sched_id = " + t.getScheduleId()).get(0).getSched_ticket_price();
					String sqlDoSaleItem = "insert into sale_item(ticket_id, sale_ID, sale_item_price) VALUES(" +
							t.getId() + ", " + id + ", " + price + ")";
					System.out.println("aaaaaaaaaaaaa"+sqlDoSaleItem);
					int flag = db.execCommand(sqlDoSaleItem);

					if (flag == 1) {

						Date ticket_locked_time = t.getLocked_time();
						String sqlUpDataTic = "update ticket set ticket_status = 9, ticket_locked_time = '" + new Timestamp(new PlaySrv().Fetch("play_id = " + new ScheduleSrv().Fetch("sched_id = " + t.getScheduleId()).get(0).getPlay_id()).get(0).getLength() * 60000) + "' where ticket_id = " + t.getId();
						int flag2 = db.execCommand(sqlUpDataTic);
						if (flag2 != 1) {
							return false;
						}
					} else
						return false;
				}
			}
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		} finally {
			try {
				con.setAutoCommit(true);
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;*/
    }


	public int update(Sale sale) {
		int rtn = 0;
		try {
			String sql = "update sale set " + " emp_id ='"
					+ sale.getEmpId() + "', " + " sale_payment = '"
					+ sale.getPayment() + "', " + " sale_change = '"
					+ sale.getChange() + "', " + " sale_type = '"
					+ sale.getType() + "' ," + "sale_time = '"
					+ sale.getTime() + "'," + "sale_status = '"
					+ sale.getStatus() + "'";

			sql += " where sale_id = " + sale.getId();
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
			String sql = "delete from sale ";
			sql += " where sale_id = " + ID;
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn = db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}


	public List<Sale> select(String condt) {
		List<Sale> saleList = null;
		saleList = new LinkedList<Sale>();
		try {
			String sql = "select * from sale ";
			condt.trim();
			if (!condt.isEmpty())
				sql += " where sale_id = " + condt;
			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst != null) {
				while (rst.next()) {
					Sale sale = new Sale();
					sale.setId(rst.getInt("sale_id"));
					sale.setEmpId(rst.getInt("emp_id"));
					sale.setTime(rst.getTimestamp("sale_time"));
					sale.setPayment(rst.getFloat("sale_payment"));
					sale.setChange(rst.getFloat("sale_change"));
					sale.setStatus(rst.getInt("sale_status"));
					sale.setType(rst.getInt("sale_type"));
					saleList.add(sale);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return saleList;
	}


	public boolean doSale(List<Ticket> tickets) {
		return false;
	}


	public List<SaleItem> findSaleItemById(String condt) {
		List<SaleItem> saleItemList = null;
		saleItemList = new LinkedList<SaleItem>();
		try {
			String sql = "select * from sale_item ";
			condt.trim();
			if (!condt.isEmpty())
				sql += " where sale_ID = " + condt;
			System.out.println(sql);
			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst != null) {
				while (rst.next()) {
					SaleItem saleItem = new SaleItem();
					saleItem.setId(rst.getInt("sale_item_id"));
					saleItem.setTicketId(rst.getInt("ticket_id"));
					saleItem.setSaleId(rst.getInt("sale_ID"));
					saleItem.setPrice(rst.getFloat("sale_item_price"));

					saleItemList.add(saleItem);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return saleItemList;
	}

}
