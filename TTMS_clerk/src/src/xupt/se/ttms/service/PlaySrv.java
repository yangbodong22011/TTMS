package src.xupt.se.ttms.service;

import java.util.List;

import src.xupt.se.ttms.idao.DAOFactory;
import src.xupt.se.ttms.idao.iPlayDAO;
import src.xupt.se.ttms.model.Play;

public class PlaySrv {
	private iPlayDAO stuDAO=DAOFactory.creatPlayDAO();
	
	public int add(Play stu){
		return stuDAO.insert(stu); 		
	}
	
	public int modify(Play stu){
		return stuDAO.update(stu); 		
	}
	
	public int delete(int ID){
		return stuDAO.delete(ID); 		
	}
	
	public List<Play> Fetch(String condt){
		return stuDAO.select(condt);		
	}
	
	public List<Play> FetchAll(){
		return stuDAO.select("");		
	}
	
	public List<Play> selectScheduledPlay(String condt){
		return stuDAO.selectScheduledPlay("");
	}
}
