package src.xupt.se.ttms.main;

import net.sf.json.JsonConfig;
import src.xupt.se.ttms.dao.PlayDAO;
import src.xupt.se.ttms.dao.ScheduleDAO;
import src.xupt.se.ttms.model.Play;
import src.xupt.se.ttms.model.Schedule;
import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kiosk on 6/21/16.
 */
public class handelGetSchedule {
    private static List<Schedule> scheduleList = new ArrayList<>();

    public handelGetSchedule() {}

    public JSONArray getJsonArray(int play_id) {
        scheduleList = new ScheduleDAO().myselect(" play_id = "+"'"+play_id+"'");
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));

        /*for (int i = 0; i <scheduleList.size() ; i++) {
            System.out.println(scheduleList.get(i).getPlay_id());
        }*/
        JSONArray object = JSONArray.fromObject(scheduleList,jsonConfig);
        return object;
    }
}
