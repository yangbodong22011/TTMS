package src.xupt.se.ttms.dao;

import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

import src.xupt.se.ttms.idao.iPlayDAO;
import src.xupt.se.ttms.model.Play;
import src.xupt.se.ttms.model.Studio;
import src.xupt.se.ttms.service.DataDictSrv;
import src.xupt.se.util.DBUtil;

public class PlayDAO implements iPlayDAO {
	@Override
	public int insert(Play stu) {
		try {
			String sql = "insert into play(play_type_id, play_lang_id, play_name, play_introduction,"
					+ "play_length,play_ticket_price,play_status)"
					+ " values("
					+ stu.getTypeId()+ ", " 
					+ stu.getLangId() + ", '" 
					+ stu.getName()+ "','" 
					+ stu.getIntroduction()+ "',"
//					+ stu.getImage() + ","
					+ stu.getLength() + ","
					+ stu.getTicketPrice()+ ","
					+ stu.getStatus()+")";
			System.out.println(sql);
			DBUtil db = new DBUtil();
			db.openConnection();



			ResultSet rst = db.getInsertObjectIDs(sql);
			if (rst!=null && rst.first()) {
				stu.setId(rst.getInt(1));
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
	public int update(Play stu) {
		int rtn=0;
		try {
			String sql = "update play set " + " play_type_id ="
					+ stu.getTypeId() + ", "  + " play_lang_id = "	
					+ stu.getLangId() + ", " + 	" play_name = '"
					+ stu.getName() + "' , " + " play_introduction = '"
					+ stu.getIntroduction() + "' ," 
//					+ " play_image = " 
//					+ stu.getImage()+ "' ," 
					+ " play_length = " 
					+ stu.getLength()+ " ," + " play_ticket_price = " 
					+ stu.getTicketPrice()+ " ," + " play_status = " 
					+ stu.getStatus();
			
			sql += " where play_id = " + stu.getId();
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn =db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
//		return 0;
	}

	@Override
	public int delete(int ID) {
		int rtn = 0;
		try {
			String sql = "delete from  play ";
			sql += " where play_id = " + ID;
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
	public List<Play> select(String condt) {
		List<Play> listPlay = null;
		listPlay = new LinkedList<Play>();
		try {
			String sql = "select play_id, play_type_id, play_lang_id, play_name, play_introduction,"
					+ "play_length,play_ticket_price,play_status from play";
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
					Play play1=new Play();
					play1.setId(rst.getInt("play_id"));
					play1.setTypeId(rst.getInt("play_type_id"));
					play1.setLangId(rst.getInt("play_lang_id"));
					play1.setName(rst.getString("play_name"));
					play1.setIntroduction(rst.getString("play_introduction"));
//					play1.setImage(rst.getBlob("play_image"));
					play1.setLength(rst.getInt("play_length"));
					play1.setTicketPrice(rst.getFloat("play_ticket_price"));
					play1.setStatus(rst.getInt("play_status"));
					listPlay.add(play1);
			
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
		}
		
		return listPlay;
//		return null;
	}	
	
	@Override
	public List<Play> selectScheduledPlay(String condt) {
		List<Play> stuList = null;
		stuList = new LinkedList<Play>();
		try {
			String sql = "select play.play_id, play_name from play, schedule " +
		       "where play.play_id=schedule.play_id ";
			condt.trim();
			if (!condt.isEmpty())
				sql += " where play_name=" + condt;
			sql += " group by play_name";
			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst != null) {
				while (rst.next()) {
					Play stu = new Play();
					stu.setId(rst.getInt("play_id"));
					stu.setName(rst.getString("play_name"));
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

	public List<Play> selectmyScheduledPlay() {
		 		List<Play> stuList = null;
		 		stuList = new LinkedList<Play>();
		 		try {
		 			String sql = "select *,COUNT(DISTINCT play_name) from play, schedule " +
		 					"where play.play_id=schedule.play_id GROUP BY play_name";
		 			DBUtil db = new DBUtil();
		 			if (!db.openConnection()) {
		 				System.out.print("fail to connect database");
		 				return null;
		 			}
		 			ResultSet rst = db.execQuery(sql);
		 			if (rst != null) {
		 				while (rst.next()) {
		 					Play stu = new Play();
		 					stu.setId(rst.getInt("play_id"));
		 					stu.setSendtype(new DataDictSrv().Fetch(" dict_id = " + rst.getString("play_type_id")).get(0).getName());
		 					stu.setSendLang(new DataDictSrv().Fetch(" dict_id = " + rst.getString("play_lang_id")).get(0).getName());
							stu.setName(rst.getString("play_name"));
		 					stu.setIntroduction(rst.getString("play_introduction"));
		 					stu.setImage(rst.getString("play_image"));
		 					stu.setLength(rst.getInt("play_length"));
		 					stu.setTicketPrice(rst.getFloat("play_ticket_price"));
		 					stu.setStatus(rst.getInt("play_status"));
		  
		 					//System.out.println(stu);
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
