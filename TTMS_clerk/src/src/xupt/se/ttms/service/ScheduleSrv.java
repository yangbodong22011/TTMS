package src.xupt.se.ttms.service;

import java.util.List;

import src.xupt.se.ttms.idao.DAOFactory;
import src.xupt.se.ttms.idao.iScheduleDAO;
import src.xupt.se.ttms.model.Schedule;

public class ScheduleSrv {
	private iScheduleDAO schedDAO=DAOFactory.creatScheduleDAO();
	
	public int add(Schedule stu){
		return schedDAO.insert(stu); 		
	}
	
	public int modify(Schedule stu){
		return schedDAO.update(stu); 		
	}
	
	public int delete(int ID){
		return schedDAO.delete(ID); 		
	}
	
	public List<Schedule> Fetch(String condt){
		return schedDAO.select(condt);		
	}
	
	public List<Schedule> FetchAll(){
		return schedDAO.select("");		
	}

	public List<Schedule> FetchPlay(String string) {
		return schedDAO.selectPlay(string);	
	}
}
