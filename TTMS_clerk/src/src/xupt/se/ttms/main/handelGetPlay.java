package src.xupt.se.ttms.main;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import src.xupt.se.ttms.dao.PlayDAO;
import src.xupt.se.ttms.model.Play;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiosk on 6/20/16.
 */
public class handelGetPlay {
    private static List<Play> playList = new ArrayList<>();

    public handelGetPlay() {
    }

    public JSONArray getJsonArray() {
        playList = new PlayDAO().selectmyScheduledPlay();
        JSONArray object = JSONArray.fromObject(playList);
        return object;
    }

    /*public static void main(String[] args) {
        playList = new PlayDAO().selectmyScheduledPlay();
        JSONArray object = JSONArray.fromObject(playList);
        System.out.println(object);
    }*/

}
