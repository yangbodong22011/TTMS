package src.xupt.se.ttms.model;

import java.text.DateFormat;
import java.util.Date;

public class Schedule {
	int type;
	private int sched_id;
	private int studio_id;
	private String studio_name;
	private int play_id;
	private Date sched_time;
	private double sched_ticket_price;
	
	public String toString(){
		if(sched_time==null)
			return "0:00:00";
		else
			return DateFormat.getTimeInstance().format(sched_time);
	}

	public String getStudio_name() {
		return studio_name;
	}

	public void setStudio_name(String studio_name) {
		this.studio_name = studio_name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSched_id() {
		return sched_id;
	}
	public void setSched_id(int sched_id) {
		this.sched_id = sched_id;
	}
	public int getStudio_id() {
		return studio_id;
	}
	public void setStudio_id(int studio_id) {
		this.studio_id = studio_id;
	}
	public int getPlay_id() {
		return play_id;
	}
	public void setPlay_id(int play_id) {
		this.play_id = play_id;
	}
	public Date getSched_time() {
		return sched_time;
	}
	public void setSched_time(Date sched_time) {
		this.sched_time = sched_time;
	}
	public double getSched_ticket_price() {
		return sched_ticket_price;
	}
	public void setSched_ticket_price(double sched_ticket_price) {
		this.sched_ticket_price = sched_ticket_price;
	}

}
