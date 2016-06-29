package control;

import model.Schedule;
import model.Seat;
import model.User;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import view.*;
import model.Play;
import model.Ticket;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kiosk on 6/16/16.
 */

public class clientThread implements Runnable{
    PrintStream ps;
    private Socket s;
    private int logintimes = 0;
    public static filmUI mainSpecialPanel;

    BufferedReader br = null;
    public clientThread(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
    }
    public void run() {
        try {
            String content;
            while((content = readFromServer()) != null) {
                org.json.JSONObject object = new org.json.JSONObject(content);
                System.out.println(content);

                /*****************************
                * 有关登录的逻辑判断
                *****************************/
                if(Integer.parseInt(object.get("type").toString()) == 1) {
                    main.user = new User(object.get("username").toString(),
                            object.get("userPasswd").toString(),
                            Integer.parseInt(object.get("userLeftMoney").toString()),
                            Integer.parseInt(object.get("userStatus").toString()) );
                    System.out.println("UserStatus: "+main.user.getUserStatus());
                    if(main.user.getUserStatus() == 1) {
                        new logs("登录成功");

                        //如果登录成功,应该发送信息去获取服务器端的电影信息
                        //发送的信息使用一个play,标志为2
                        Play play = new Play();
                        play.setType(2);
                        JSONObject objectfilm = JSONObject.fromObject(play);
                        ps.println(objectfilm);
                    }
                    if(main.user.getUserStatus() == 0) {
                        /*new logs("用户名或密码错误,请查证后输入 \n密码提示:"+
                                main.user.getUserPasswd().substring(0,2)).showDialog();*/
                        new logs("用户名或密码错误,请查证后输入").showDialog();
                    }
                }
                /*****************************
                 * 获取了电影的信息
                 *****************************/
                 else if(Integer.parseInt(object.get("type").toString()) == 2) {
                    String playString = readFromServer();
                    JSONArray array = JSONArray.fromObject(playString);
                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("Plays",Play.class);
                    List<Play> array1 = JSONArray.toList(array,Play.class,classMap);
                    filmUI.allSchedulePlay = array1;

                    //初始化数据,友情提醒
                    //new my_dialog("欢迎您: "+main.user.getUsername()+" 正在初始化数据...").setVisible(true);
                    //Thread.sleep(3000);

                    JPanel panel = new filmUI(s).init();
                    mainUI.cardBigPanel.add(panel);
                    mainUI.cardBig.first(mainUI.cardBigPanel);
                    for (int i = 0; i < 1; i++) {
                        mainUI.cardBig.next(mainUI.cardBigPanel);
                    }
                }
                /*****************************
                 * 有关注册的判断
                 *****************************/
                 else if(Integer.parseInt(object.get("type").toString()) == 3) {
                    main.user = new User(object.get("username").toString(),
                            object.get("userPasswd").toString(),
                            Integer.parseInt(object.get("userLeftMoney").toString()),
                            Integer.parseInt(object.get("userStatus").toString()) );

                    /*如果没有注册*/
                    if(main.user.getUserStatus() == 2) {
                        new logs("恭喜您: "+main.user.getUsername()+" 注册成功  密码: "
                                +main.user.getUserPasswd()+" \n请登录......").showDialog();
                    }
                    /*如果已经注册*/
                    if(main.user.getUserStatus() == 3) {
                        new logs("此用户已经注册,请登录").showDialog();
                    }
                }
                else if(Integer.parseInt(object.get("type").toString()) == 4) {
                    //System.out.println(object.getString("name"));
                    String scheduleString = readFromServer();

                    JSONArray array = JSONArray.fromObject(scheduleString);
                    System.out.println(scheduleString);
                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("Schedule",Schedule.class);
                    //JsonConfig jsonConfig = new JsonConfig();
                    //jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.setRootClass(Schedule.class);
                    JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}));
                    List<Schedule> array1 = JSONArray.toList(array,Schedule.class,classMap);
                    filmUI.allSchedulePlayStudio = array1;
                    /*for (int i = 0; i < filmUI.allSchedulePlayStudio.size(); i++) {
                        System.out.println(filmUI.allSchedulePlayStudio.get(i).getStudio_id());
                        System.out.println(filmUI.allSchedulePlayStudio.get(i).getStudio_name());
                        System.out.println(filmUI.allSchedulePlayStudio.get(i).getPlay_id());
                        System.out.println(filmUI.allSchedulePlayStudio.get(i).getSched_time());

                    }*/
                    JScrollPane panel = new scheduleUI().init(s);
                    mainUI.cardBigPanel.add(panel);
                    mainUI.cardBig.last(mainUI.cardBigPanel);
                    /*mainUI.cardBig.first(mainUI.cardBigPanel);
                    for (int i = 0; i < 2; i++) {
                        mainUI.cardBig.next(mainUI.cardBigPanel);
                    }*/
                }
                else if(Integer.parseInt(object.get("type").toString()) == 5) {
                    seatUI.studio_row_count = object.getInt("rowCount");
                    seatUI.studio_col_count = object.getInt("colCount");
                    seatUI.studio_introduction = object.getString("introduction");

                    String seatString = readFromServer();
                    JSONArray array = JSONArray.fromObject(seatString);

                    Map<String, Class> classMap = new HashMap<String, Class>();
                    classMap.put("seats",Play.class);
                    List<Seat> array1 = JSONArray.toList(array,Seat.class,classMap);
                    filmUI.allSchedulePlayStudioSeat.clear();
                    filmUI.allSchedulePlayStudioSeat = array1;

                    System.out.println(">>>>>>>>>>>");
                    for (int i = 0; i < filmUI.allSchedulePlayStudioSeat.size() ; i++) {
                        System.out.println(filmUI.allSchedulePlayStudioSeat.get(i).getId());
                    }
                    System.out.println(">>>>>>>>>>>>>");



                    String ticketString = readFromServer();
                    JSONArray array2 = JSONArray.fromObject(ticketString);
                    Map<String, Class> classMap1 = new HashMap<String, Class>();
                    classMap1.put("seats",Ticket.class);

                    List<Ticket> array3 = JSONArray.toList(array2,Ticket.class,classMap);
                    filmUI.allSchedulePlayStudioSeatTicket.clear();
                    filmUI.allSchedulePlayStudioSeatTicket = array3;


                    for (int i = 0; i < filmUI.allSchedulePlayStudioSeatTicket.size(); i++) {
                        for (int j = 0; j <filmUI.allSchedulePlayStudioSeat.size() ; j++) {
                            if(filmUI.allSchedulePlayStudioSeatTicket.get(i).getSeatId() == filmUI.allSchedulePlayStudioSeat.get(j).getId()) {
                                filmUI.allSchedulePlayStudioSeat.get(j).setSeatStatus(2);  //设置票的状态为卖出
                            }
                        }
                    }
                    System.out.println(">>>>>>>>>>>");
                    for (int i = 0; i < filmUI.allSchedulePlayStudioSeatTicket.size() ; i++) {
                        System.out.println(filmUI.allSchedulePlayStudioSeatTicket.get(i).getSeatId());
                    }
                    System.out.println(">>>>>>>>>>>>>");

                    JPanel panel = new seatUI().init(s);
                    mainUI.cardBigPanel.add(panel);
                    mainUI.cardBig.last(mainUI.cardBigPanel);
                }else if(Integer.parseInt(object.get("type").toString()) == 6) {
                    try{
                        new logs(seatUI.str).showDialog();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    main.user.setUserLeftMoney(main.user.getUserLeftMoney()-(int)seatUI.totalprice);

                }else if(Integer.parseInt(object.get("type").toString()) == 7) {
                    try{
                        new logs("您的手速有点慢啊,可能票被别人抢走了\n试试别的座位吧,或者看看别的场次").showDialog();
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String readFromServer() {
        try {
            return br.readLine();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
