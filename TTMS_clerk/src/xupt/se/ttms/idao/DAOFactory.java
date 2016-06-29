package src.xupt.se.ttms.idao;
import src.xupt.se.ttms.dao.*;

public class DAOFactory {
	public static iStudioDAO creatStudioDAO(){
		return new StudioDAO();
	}

	public static IDataDictDAO creatDataDictDAO(){
		return new DataDictDAO();
	}

	public static iScheduleDAO creatScheduleDAO(){
		return new ScheduleDAO();
	}

	public static iPlayDAO creatPlayDAO(){
		return new PlayDAO();
	}

	public static iSeatDAO creatSeatDAO(){
		return new SeatDAO();
	}

	public static iTicketDAO creatTicketDAO(){
		return new TicketDAO();
	}

	public static iSaleDAO creatSaleDAO(){
		return new SaleDAO();
	}

	public static IEmployeeDAO creatEmployeeDAO() {
		return new EmployeeDAO();
	}
}
