package src.xupt.se.ttms.idao;

import java.util.List;

import src.xupt.se.ttms.model.DataDict;

public interface IDataDictDAO {
	public int insert(DataDict ddict);
	public int update(DataDict ddict);
	public int delete(int ID);
	public List<DataDict> select(String condt);
	public List<DataDict> findByID(int id) ;
	public void findAllSonByID(List<DataDict> list, int id);
	public boolean hasChildren(int id) ;
	public List<DataDict> findSelfByID(int id);
	public DataDict findSelfByName(String condt);
}
