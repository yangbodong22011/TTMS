package src.xupt.se.ttms.dao;

import src.xupt.se.util.DBUtil;

import java.sql.ResultSet;

/**
 * Created by kiosk on 6/16/16.
 */
public class LogsDAO {
    public int insert (String time,String str) {
        try {
            String sql = "insert into mylog(log_time,log_content)"
                    + " values('"
                    + time
                    + "', "
                    + " '" + str
                    + "' )";
            DBUtil db = new DBUtil();
            db.openConnection();
            ResultSet rst = db.getInsertObjectIDs(sql);
            db.close(rst);
            db.close();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
