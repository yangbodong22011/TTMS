package src.xupt.se.ttms.main;


import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import src.xupt.se.ttms.dao.SaleDAO;
import src.xupt.se.ttms.dao.StudioDAO;
import src.xupt.se.ttms.dao.TicketDAO;
import src.xupt.se.ttms.dao.UserDAO;
import src.xupt.se.ttms.model.*;

import javax.jws.soap.SOAPBinding;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;


public class serverThread implements Runnable{
    Socket s = null;
    BufferedReader br = null;
    PrintStream ps;
    //org.json.JSONObject object;
    JSONObject object;

    public serverThread(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
        new logs("accept a new connection"+"  IP:"+s.getInetAddress());
    }
    public void run() {
        try {
            String content;
            while((content = readFromClient()) != null) {
                //object = new org.json.JSONObject(content);
                object = JSONObject.fromObject(content);
                System.out.println(object);
                if (Integer.parseInt(object.get("type").toString()) == 1) {
                    User user;
                    user = new handleConnect(s, object).passwdIsTure();
                    user.setType(1);
                    JSONObject object1 = JSONObject.fromObject(user);
                    //System.out.println(object1);
                    ps.println(object1);
                } else if (Integer.parseInt(object.get("type").toString()) == 2) {
                    //发送标志
                    Play play = new Play();
                    play.setType(2);
                    JSONObject object = JSONObject.fromObject(play);
                    ps.println(object);

                    //发送真正的List<play>
                    JSONArray array = new handelGetPlay().getJsonArray();
                    ps.println(array);
                } else if (Integer.parseInt(object.get("type").toString()) == 3) {
                    User user;
                    user = new handleConnect(s, object).isHaveUser();
                    user.setType(3);
                    JSONObject object1 = JSONObject.fromObject(user);
                    ps.println(object1);
                } else if (Integer.parseInt(object.get("type").toString()) == 4) {

                    JSONArray array = new handelGetSchedule().getJsonArray(Integer.parseInt(object.get("id").toString()));
                    //System.out.println(object.getString("name"));
                    Play play = new Play();
                    play.setType(4);
                    JSONObject object = JSONObject.fromObject(play);
                    ps.println(object);

                    ps.println(array);
                } else if (Integer.parseInt(object.get("type").toString()) == 5) {
                    JSONArray array = new handleGetSeat().getJsonArray(Integer.parseInt(object.get("studio_id").toString()));

                    /*JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setRootClass(Schedule.class);
                    JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}));
                    Schedule schedule = (Schedule)JSONObject.toBean(object,Schedule.class,jsonConfig);*/
                    JSONArray array2 = new handleGetTicket().getJsonArray(Integer.parseInt(object.get("studio_id").toString()),
                            object.get("sched_time").toString());

                    Studio studio = new StudioDAO().myselect(" studio_id = '"+object.get("studio_id").toString()+"'");
                    studio.setType(5);
                    JSONObject object = JSONObject.fromObject(studio);
                    System.out.println(object);
                    ps.println(object);

                    ps.println(array);
                    System.out.println(array2);
                    ps.println(array2);
                }
                else if (Integer.parseInt(object.get("type").toString()) == 6) {

                    String addticketString = readFromClient();

                    System.out.println(addticketString);

                    JSONArray array = JSONArray.fromObject(addticketString);
                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("tickets",Ticket.class);

                    List<Ticket> array3 = JSONArray.toList(array,Ticket.class,classMap);
                    List<Ticket> tempList = new ArrayList<>();

                    boolean ticketlegal = true;
                    for (int i = 0; i < array3.size(); i++) {
                        if(!(new TicketDAO().legalselect(array3.get(i)))) {
                            ticketlegal = false;
                        }
                    }

                    if(ticketlegal) {
                        new handleUpdateMoney().updateMoney(object);
                        main.socketUserHashMap.get(s).setUserLeftMoney(object.getInt("userLeftMoney"));

                        new logs("用户 "+main.socketUserHashMap.get(s).getUsername()+ " 购买了 "+array.size() +" 张票");

                        CouDoSale couDoSale = new CouDoSale();
                        Ticket tick ;
                        for (int i = 0; i < array3.size(); i++) {
                            tick = couDoSale.DoTicketUI(array3.get(i));
                            tempList.add(tick);
                        }
                        couDoSale.DoSale(tempList);
                        User user = new User();
                        user.setType(6);

                        JSONObject object = JSONObject.fromObject(user);
                        ps.println(object);
                    }
                    else {
                        User user = new User();
                        user.setType(7);

                        JSONObject object = JSONObject.fromObject(user);
                        ps.println(object);

                    }
                }
                else if (Integer.parseInt(object.get("type").toString()) == 7) {
                    new handleUpdateMoney().updateMoney(object);
                    new logs("用户 "+main.socketUserHashMap.get(s).getUsername()
                            +"充值 "+(object.getInt("userLeftMoney")-main.socketUserHashMap.get(s).getUserLeftMoney()) + "元钱");
                    main.socketUserHashMap.get(s).setUserLeftMoney(object.getInt("userLeftMoney"));

                }
                else if (Integer.parseInt(object.get("type").toString()) == 8) {
                    new handleUpdateMoney().updateMoney(object);
                    main.socketUserHashMap.get(s).setUserPasswd(object.getString("userPasswd"));
                    new logs("用户 "+main.socketUserHashMap.get(s).getUsername()
                            +"修改了密码为 "+main.socketUserHashMap.get(s).getUserPasswd());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            //每一个用户退出的时候都将用户的状态改为未登录
            if(main.socketUserHashMap.get(s) == null) {
                new logs("IP:" + s.getInetAddress()+" 未登录直接退出!");
            } else {
                User user = main.socketUserHashMap.get(s);
                user.setUserStatus(0);
                new UserDAO().update(user);
                new logs("用户 "+main.socketUserHashMap.get(s).getUsername()
                +" 退出啦!");
                main.socketUserHashMap.remove(s);
            }
        }
    }

    private String readFromClient() {
        try {
            return br.readLine();
        }catch (IOException e) {
            main.socketList.remove(s);
        }
        return null;
    }
}
