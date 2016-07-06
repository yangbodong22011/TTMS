package src.xupt.se.ttms.view.sellticket;

import java.text.DateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import src.xupt.se.ttms.model.Sale;
import src.xupt.se.ttms.model.Ticket;
import src.xupt.se.ttms.service.SaleSrv;
import src.xupt.se.ttms.service.TicketSrv;

public class CouDoSale {
	private List<Ticket>  ticketList= null;
	public CouDoSale(){
		
	}
	public void DoTicketUI(Ticket tickets){
		ticketList = new LinkedList<Ticket>();
		ticketList.add(tickets);
		 	TicketSrv ticketSrv = new TicketSrv();
			if(ticketSrv.add(tickets) > 0){
				ticketSrv.lockTicket(tickets .getId(), DateFormat.getDateTimeInstance().format((System.currentTimeMillis() + 300000)));
				JOptionPane.showMessageDialog(null, "出票成功");
			
			}
			
	}
	public void DoSale(){
		SaleSrv saleSrv = new SaleSrv();
		float payNum =  (float) 0;
		for(Ticket ticket : ticketList){
			payNum += ticket.getPrice();
		}
		Sale sale =new Sale();
		sale.setEmpId(1);   //此处职员ID应为网络售票端标识
		sale.setPayment(payNum);
		sale.setChange(payNum);
		sale.setType(1);
		sale.setStatus(0);
		if(saleSrv.add(ticketList,sale)){
			JOptionPane.showMessageDialog(null, "已生成销售单");
		}else{
			TicketSrv ticketSrv = new TicketSrv();
			for(int i = 0;i<ticketList.size();i++){
				ticketSrv.unlockTicket(ticketList.get(i).getId());
			}
			
		}
	}
}
