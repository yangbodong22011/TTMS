package src.xupt.se.ttms.dao;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import src.xupt.se.ttms.idao.IDataDictDAO;
import src.xupt.se.ttms.model.DataDict;
import src.xupt.se.ttms.model.Studio;
import src.xupt.se.util.DBUtil;

public class DataDictDAO implements IDataDictDAO {

	/*
	 create table data_dict
	   (
	   dict_id              int not null auto_increment,
	   dict_parent_id       int,
	   dict_index           int,
	   dict_name            varchar(200),
	   dict_value           varchar(100) not null,
	   primary key (dict_id)
	   );

		
		
		private int id;
		private int superId;
		private int index;
		private String name;	
		private String value;
		
		==============================================================*/
	
	@Override
	public int insert(DataDict ddict) {
		// TODO Auto-generated method stub
		
		try {
			String sql = "insert into data_dict( dict_parent_id,  dict_index , dict_name , dict_value )"
					+ " values(" + ddict.getSuperId()
					+ ", " +  ddict.getIndex()
					+ ", '" + ddict.getName() 
					+ "' , '" +ddict.getValue()
					+ "' )";
			DBUtil db = new DBUtil();
			db.openConnection();
			ResultSet rst = db.getInsertObjectIDs(sql);
			if (rst!=null && rst.first()) {
				ddict.setId(rst.getInt(1));
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
	public int update(DataDict ddict) {
		// TODO Auto-generated method stub
		int rtn=0;
		try {
			String sql = "update data_dict set " 
		            + " dict_parent_id =" + ddict.getSuperId() + ", " 
					+ "  dict_index = " +  ddict.getIndex() + ", " 
					+ " dict_name = '" + ddict.getName() + "', "
					+ " dict_value = '"+ ddict.getValue() + "' ";

			sql += " where dict_id = " + ddict.getId();
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
		// TODO Auto-generated method stub
		int rtn=0;		
		try{
			String sql = "delete from  data_dict ";
			sql += " where dict_id = " + ID;
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
	public List<DataDict> select(String condt) {
		// TODO Auto-generated method stub
		List<DataDict> ddictList = null;
		ddictList=new LinkedList<DataDict>();
		try {
			String sql = "select dict_id, dict_parent_id,  dict_index , dict_name , dict_value from data_dict ";
			condt.trim();
			if(!condt.isEmpty())
				sql+= " where " + condt;

			//System.out.println(sql);
			DBUtil db = new DBUtil();
			if(!db.openConnection()){
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			
			/*
			 * ddict.getSuperId()
					+ "', " +  ddict.getIndex()
					+ ", " + ddict.getName() 
					+ ", '" +ddict.getValue()
			 * 
			 * 
			 * */
			if (rst!=null) {
				while(rst.next()){
					DataDict ddict=new DataDict();
					ddict.setId(rst.getInt("dict_id"));
					ddict.setSuperId(rst.getInt("dict_parent_id"));
					ddict.setIndex(rst.getInt("dict_index"));
					ddict.setName(rst.getString("dict_name"));
					ddict.setValue(rst.getString("dict_value"));
					ddictList.add(ddict);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
		}
		
		return ddictList;
	}
		 
	public List<DataDict> findByID( int id) {
		return select(" dict_parent_id =" +id);
	
	}
	public List<DataDict> findSelfByID(int id) {
//		System.out.print(select(" dict_parent_id =" +id).get(3).getName());
		return select(" dict_id =" +id);

	}
	
	public void findAllSonByID(List<DataDict> list, int id)
	{
		List<DataDict> childList = findByID(id);
		for(int i=0;i<childList.size();i++){
			if(!hasChildren(childList.get(i).getId()))
				list.add(childList.get(i));
			else 
			{
				 findAllSonByID(list, childList.get(i).getId());
			}
		}
	}
	
	// 给定的节点是否有孩子节点
	public boolean hasChildren(int id) {
		List<DataDict> list = select(" dict_parent_id =" +id);
		return list.size()>0 ? true : false;
	}
	public DataDict findSelfByName(String condt){

		return select(" dict_name = '"+ condt +"'").get(0);
	}
	
	
	public static void main(String[] args)
	{
		DataDictDAO dictDAO = new DataDictDAO();
		List<DataDict> list = dictDAO.findByID(1);

		//for(int i=0;i<list.size();i++) System.out.println(list.get(i).getName());
		System.out.println(dictDAO.hasChildren(2));

		List<DataDict> list1 = new ArrayList<DataDict>();

		dictDAO.findAllSonByID(list1,0);
		for(int i=0;i<list1.size();i++) System.out.println("tt" +list1.get(i).getId());
	}

}
