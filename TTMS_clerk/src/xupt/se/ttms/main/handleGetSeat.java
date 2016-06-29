package src.xupt.se.ttms.main;

import net.sf.json.JSONArray;
import src.xupt.se.ttms.dao.PlayDAO;
import src.xupt.se.ttms.dao.SeatDAO;
import src.xupt.se.ttms.model.Play;
import src.xupt.se.ttms.model.Seat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiosk on 6/22/16.
 */
public class handleGetSeat {
    private static List<Seat> seatList = new ArrayList<>();

    public handleGetSeat() {
    }

    public JSONArray getJsonArray(int id) {
        seatList = new SeatDAO().select(" studio_id = '"+id+"'");
        JSONArray object = JSONArray.fromObject(seatList);
        return object;
    }
}
