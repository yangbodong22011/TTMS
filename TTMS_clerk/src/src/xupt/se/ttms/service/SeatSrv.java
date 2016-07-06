package src.xupt.se.ttms.service;

import java.util.LinkedList;
import java.util.List;

import src.xupt.se.ttms.idao.DAOFactory;
import src.xupt.se.ttms.idao.iSeatDAO;
import src.xupt.se.ttms.model.Schedule;
import src.xupt.se.ttms.model.Seat;
import src.xupt.se.ttms.model.Ticket;

public class SeatSrv {
	private iSeatDAO seatDAO=DAOFactory.creatSeatDAO();

	public int add(Seat stu){
		return seatDAO.insert(stu);
	}

	public int modify(Seat stu){
		return seatDAO.update(stu);
	}

	public int delete(int ID){
		return seatDAO.delete(ID);
	}

	public List<Seat> Fetch(String condt){
		return seatDAO.select(condt);
	}

	public List<Seat> FetchAll(){
		return seatDAO.select("");
	}
	public int CreateSeatsOfStudio(int studioId, int studioRow, int studioCloumn){

		for(int rows =1 ;rows<=studioRow; rows++){
			for(int cloumn = 1;cloumn<=studioCloumn; cloumn++){
				SeatSrv seatSrv = new SeatSrv();
				Seat seat=new Seat();
				seat.setStudioId(studioId);
				seat.setRow(rows);
				seat.setColumn(cloumn);
				seat.setSeatStatus(1);
				seatSrv.add(seat);
			}
		}
		return 1;

	}
	public int getSeatStatus(Seat seat){
		return seatDAO.getSeatStatus(seat);
	}

	public List<Integer> isSellOfSeat(Schedule schedule) {
		TicketSrv ticketSrv = new TicketSrv();
		List<Ticket> ticketList = new LinkedList<Ticket>();
		ticketList = ticketSrv.Fetch("schedule_id = " + schedule.getSched_id());
		List<Integer> seatList = new LinkedList<Integer>();
		for(Ticket ticket : ticketList){
			seatList.add(ticket.getSeatId());
		}
		return seatList;

	}
}
