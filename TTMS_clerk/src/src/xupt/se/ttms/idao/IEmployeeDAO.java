package src.xupt.se.ttms.idao;

import java.util.List;

import src.xupt.se.ttms.model.Employee;
import src.xupt.se.ttms.model.Studio;

public interface IEmployeeDAO {
    public int insert(Employee stu);
    public int update(Employee stu);
    public int delete(int ID);
    public List<Employee> select(String condt);

}


