package src.xupt.se.ttms.main;

/**
 * Created by kiosk on 6/23/16.
 */
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import src.xupt.se.ttms.dao.SaleDAO;
import src.xupt.se.ttms.dao.TicketDAO;
import src.xupt.se.ttms.model.Sale;
import src.xupt.se.ttms.model.Ticket;
import src.xupt.se.ttms.service.SaleSrv;
import src.xupt.se.ttms.service.TicketSrv;

public class CouDoSale {

    public CouDoSale(){

    }
    public Ticket DoTicketUI(Ticket tickets){

        TicketSrv ticketSrv = new TicketSrv();
        if(ticketSrv.add(tickets) > 0){
            tickets = new TicketDAO().myselectoneticket(tickets.getSeatId(),tickets.getScheduleId());
            //ticketSrv.lockTicket(tickets .getId(), DateFormat.getDateTimeInstance().format((System.currentTimeMillis() + 300000)));
        }
        return tickets;
    }
    public void DoSale(List<Ticket> ticketList){
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
            System.out.println("已生成销售单");
            //JOptionPane.showMessageDialog(null, "已生成销售单");
        }else{
            TicketSrv ticketSrv = new TicketSrv();
            for(int i = 0;i<ticketList.size();i++){
                ticketSrv.unlockTicket(ticketList.get(i).getId());
            }
        }
    }
}
