package src.xupt.se.ttms.main;

import net.sf.json.JSONObject;
import src.xupt.se.ttms.dao.UserDAO;
import src.xupt.se.ttms.model.User;

/**
 * Created by kiosk on 6/22/16.
 */
public class handleUpdateMoney {

    public handleUpdateMoney() {}

    public void updateMoney(JSONObject object) {
        User user = new User(object.getString("username"),
                object.getString("userPasswd"),
                object.getInt("userLeftMoney"),
                object.getInt("userStatus"));
        new UserDAO().update(user);

    }
}
