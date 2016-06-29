package src.xupt.se.ttms.service;

import java.util.List;


import src.xupt.se.ttms.idao.DAOFactory;
import src.xupt.se.ttms.idao.iStudioDAO;
import src.xupt.se.ttms.model.Studio;

public class StudioSrv {
	private iStudioDAO stuDAO=DAOFactory.creatStudioDAO();
	
	public int add(Studio stu){
		return stuDAO.insert(stu); 		
	}
	
	public int modify(Studio stu){
		return stuDAO.update(stu); 		
	}
	
	public int delete(int ID){
		return stuDAO.delete(ID); 		
	}
	
	public List<Studio> Fetch(String condt){
		return stuDAO.select(condt);		
	}
	
	public List<Studio> FetchAll(){
		return stuDAO.select("");		
	}
	
	public int createSeats(Studio stu){
		if(stu.getStudioFlag()==0){
			return stuDAO.createSeats(stu);
        }
		return -1;
	}
}
