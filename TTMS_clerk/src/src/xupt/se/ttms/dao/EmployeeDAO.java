package src.xupt.se.ttms.dao;



import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

import src.xupt.se.ttms.idao.IEmployeeDAO;
import src.xupt.se.ttms.model.Employee;
import src.xupt.se.util.DBUtil;



public class EmployeeDAO implements IEmployeeDAO {



    public int insert(Employee employee) {
        try {

            String sql = "insert into employee(emp_no,emp_name, emp_tel_num, emp_addr, emp_email)"
                    + "values("+employee.getEmployee_workid()
                    + ",'" +employee.getEmployee_name()
                    + "','"+employee.getEmployee_tel()
                    + "','"+employee.getEmployee_address()
                    + "','"+ employee.getEmployee_email()+"')";
            DBUtil db = new DBUtil();
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            if (rst!=null && rst.first()) {
                employee.setId(rst.getInt(1));
            }
            db.close(rst);
            db.close();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public int update(Employee employee) {
        int rtn=0;
        try {
            String sql = "update employee set " + " emp_no ="
                    + employee.getEmployee_workid() + ", " + " emp_name= '"
                    + employee.getEmployee_name()+ "', " + " emp_tel_num = '"
                    + employee.getEmployee_tel() + "', " + " emp_addr = '"
                    + employee.getEmployee_address() + " ',"+"emp_email = '"
                    + employee.getEmployee_email()+" ' ";

            sql += "  where emp_id =  " + employee.getId();
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn =db.execCommand(sql);


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    public int delete(int ID) {
        int rtn=0;
        try{
            String sql = "delete from  employee ";
            sql += " where emp_id = " + ID;
            DBUtil db = new DBUtil();
            db.openConnection();
            rtn=db.execCommand(sql);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }


    public List<Employee> select(String condt) {
        List<Employee> empList = null;
        empList = new LinkedList<Employee>();
        try {
            String sql = "select  * from employee ";
            condt.trim();
            if(!condt.isEmpty())
                sql+= " where emp_name="+"'"+condt+"'";
            DBUtil db = new DBUtil();
            if(!db.openConnection()){
                System.out.print("fail to connect database");
                return null;
            }
            ResultSet rst = db.execQuery(sql);
            if (rst!=null) {
                while(rst.next()){
                    Employee employee=new Employee();
                    employee.setId(rst.getInt("emp_id"));
                    employee.setEmployee_workid(rst.getInt("emp_no"));
                    employee.setEmployee_name(rst.getString("emp_name"));
                    employee.setEmployee_tel(rst.getString("emp_tel_num"));
                    employee.setEmployee_address(rst.getString("emp_addr"));
                    employee.setEmployee_email(rst.getString("emp_email"));

                    empList.add(employee);
                }
            }
            db.close(rst);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{

        }

        return empList;

    }

    public static void main(String[] args) {
        EmployeeDAO d=new EmployeeDAO();
        Employee employee=new Employee();
/*	employee.setEmployee_workid(3);
	employee.setEmployee_tel("123");
	employee.setEmployee_name("123");
	employee.setEmployee_address("jio");
	employee.setEmployee_email("123@qq.com");*/
        List<Employee> v=d. select(" 123");
        for(int i=0;i<v.size();i++){
            System.out.println(v.get(i).getEmployee_name());
            System.out.println(v.get(i).getId());
        }
    }

}

