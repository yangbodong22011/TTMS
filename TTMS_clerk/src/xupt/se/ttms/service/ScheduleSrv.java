package src.xupt.se.ttms.service;

import java.util.List;

import src.xupt.se.ttms.idao.DAOFactory;
import src.xupt.se.ttms.idao.iScheduleDAO;
import src.xupt.se.ttms.model.Schedule;

public class ScheduleSrv {
	private iScheduleDAO stuDAO=DAOFactory.creatScheduleDAO();
	
	public int add(Schedule stu){
		return stuDAO.insert(stu); 		
	}
	
	public int modify(Schedule stu){
		return stuDAO.update(stu); 		
	}
	
	public int delete(int ID){
		return stuDAO.delete(ID); 		
	}
	
	public List<Schedule> Fetch(String condt){
		return stuDAO.select(condt);		
	}
	
	public List<Schedule> FetchAll(){
		return stuDAO.select("");		
	}
}
