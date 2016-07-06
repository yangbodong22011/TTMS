package src.xupt.se.ttms.model;

import java.util.Date;

public class Ticket {

	private int id;
	private int seatId;
	private int scheduleId;
	private double price;
	private int status;  //0：待销售  1：锁定    9：卖出
	private Date locked_time;
	
	private String playName;
	private Schedule schedule;
	private Seat seat;
	private Date current_locked_time;
	
	public Ticket() {}
	
	public Ticket(int id, int seatId, int scheduleId, float price, int status) {
		super();
		this.id = id;
		this.seatId = seatId;
		this.scheduleId = scheduleId;
		this.price = price;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double d) {
		this.price = d;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLocked_time() {
		return locked_time;
	}

	public void setLocked_time(Date locked_time) {
		this.locked_time = locked_time;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Date getCurrent_locked_time() {
		return current_locked_time;
	}

	public void setCurrent_locked_time(Date current_locked_time) {
		this.current_locked_time = current_locked_time;
	}
	
}
