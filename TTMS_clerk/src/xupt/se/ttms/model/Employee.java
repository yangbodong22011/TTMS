package src.xupt.se.ttms.model;

import java.io.Serializable;

public class Employee implements Serializable {
	private int id;
	private int employee_workid;//员工工号
	private String employee_name;
	private String employee_tel;
	private String employee_address;
	private String employee_email;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEmployee_workid() {
		return employee_workid;
	}
	public void setEmployee_workid(int employee_workid) {
		this.employee_workid = employee_workid;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getEmployee_tel() {
		return employee_tel;
	}
	public void setEmployee_tel(String employee_tel) {
		this.employee_tel = employee_tel;
	}
	public String getEmployee_address() {
		return employee_address;
	}
	public void setEmployee_address(String employee_address) {
		this.employee_address = employee_address;
	}
	public String getEmployee_email() {
		return employee_email;
	}
	public void setEmployee_email(String employee_email) {
		this.employee_email = employee_email;
	}
}
