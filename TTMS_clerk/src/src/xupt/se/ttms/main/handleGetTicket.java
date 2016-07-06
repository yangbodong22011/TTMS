package src.xupt.se.ttms.main;

import net.sf.json.JSONArray;
import src.xupt.se.ttms.dao.TicketDAO;
import src.xupt.se.ttms.model.Ticket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kiosk on 6/22/16.
 */
public class handleGetTicket {
    private static List<Ticket> ticketList = new ArrayList<>();

    public handleGetTicket() {}

    public JSONArray getJsonArray(int studio_id,String sched_time) {
        String date = null;
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.format(df.parse(sched_time));
        } catch(Exception ex) {ex.printStackTrace();}

        ticketList = new TicketDAO().myselect(studio_id,date);
        JSONArray object = JSONArray.fromObject(ticketList);
        return object;
    }
}
