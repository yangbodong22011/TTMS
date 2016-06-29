package src.xupt.se.ttms.service;

import java.util.List;

import src.xupt.se.ttms.idao.DAOFactory;
import src.xupt.se.ttms.idao.IEmployeeDAO;
import src.xupt.se.ttms.idao.iStudioDAO;
import src.xupt.se.ttms.model.Employee;
import src.xupt.se.ttms.model.Studio;

public class EmployeeSrv {
    private IEmployeeDAO employeeDAO=DAOFactory.creatEmployeeDAO();

    public int add(Employee stu){
        return employeeDAO.insert(stu);
    }

    public int modify(Employee stu){
        return employeeDAO.update(stu);
    }

    public int delete(int ID){
        return employeeDAO.delete(ID);
    }

    public List<Employee> Fetch(String condt){
        return employeeDAO.select(condt);
    }

    public List<Employee> FetchAll(){
        return employeeDAO.select("");
    }

}
