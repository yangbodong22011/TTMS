package src.xupt.se.ttms.service;

import java.util.List;

import src.xupt.se.ttms.idao.DAOFactory;
import src.xupt.se.ttms.idao.IDataDictDAO;
import src.xupt.se.ttms.model.DataDict;

public class DataDictSrv {

	private IDataDictDAO dictDAO=DAOFactory.creatDataDictDAO();

	public int add(DataDict ddict){
		return dictDAO.insert(ddict);
	}

	public int modify(DataDict ddict){
		return dictDAO.update(ddict);
	}

	public int delete(int ID){
		return dictDAO.delete(ID);
	}

	public List<DataDict> Fetch(String condt){
		return dictDAO.select(condt);
	}

	public List<DataDict> FetchAll(){
		return dictDAO.select("");
	}

	public List<DataDict> findByID(int id) {
		return dictDAO.findByID(id);
	}

	public void findAllSonByID(List<DataDict> list, int id){
		dictDAO.findAllSonByID(list, id);
	}

	public boolean hasChildren(int id){
		return dictDAO.hasChildren(id);
	}

	public List<DataDict> findSelfByID(int id) {
		return dictDAO.findSelfByID(id);
	}
	public DataDict findSelfByName(String condt) {
		return dictDAO.findSelfByName(condt);
	}

}
