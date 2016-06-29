package view;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

import model.*;

/**
 * Created by kiosk on 6/21/16.
 */
public class playUI extends JFrame {
    Socket s;
    JPanel playUIPanel = new JPanel(new GridLayout(4, 1, 0, 10));

    public JPanel init(Socket s) throws IOException{
        this.s = s;
        //System.out.println(filmUI.allSchedulePlay.size());
        for (int i = 0; i < filmUI.allSchedulePlay.size(); i++) {
            Play play = filmUI.allSchedulePlay.get(i);
            System.out.println(play);
            JPanel panel = new onePlayUI(s).init(play.getId(),play.getImage(),
                    play.getSendtype(), play.getSendLang(),play.getName(),
                    play.getLength(), play.getIntroduction(), play.getTicketPrice());
            playUIPanel.add(panel);
        }
        return playUIPanel;
    }
    /*public void init() {
        JPanel panel = new onePlayUI().init("X战警.png",
                "武打","汉语","X战警",
                123,"“天启”是漫威X战警世界里，能力最强大并且是史上首位变种人，自人类文明开始以来，便为世人当作天神来膜拜，他汲取多位变种人的超能力，演变成一位永生不死且无人能敌的超级变种人，历经数千年后，他再度觉醒，集结了多位强大的变种" +
                        "人意欲毁灭一切，重建世界秩序，当中包含失志的万磁王（迈克尔·法斯宾德 Michael Fassbender 饰）。当地球面临未知的巨大危机之际，瑞雯（詹妮弗·劳伦斯 Jennifer Lawrence 饰）在X教授（詹姆斯·麦卡沃伊 James McAvoy 饰）的协助之下，必须领导年轻的X战警们力抗这史上最强大的敌人，并扭转人类命运。",22);
        playUIPanel.add(panel);
        JPanel panel2 = new onePlayUI().init("X战警.png",
                "武打","汉语","X战警",
                123,"“天启”是漫威X战警世界里，能力最强大并且是史上首位变种人，自人类文明开始以来，便为世人当作天神来膜拜，他汲取多位变种人的超能力，演变成一位永生不死且无人能敌的超级变种人，历经数千年后，他再度觉醒，集结了多位强大的变种" +
                        "人意欲毁灭一切，重建世界秩序，当中包含失志的万磁王（迈克尔·法斯宾德 Michael Fassbender 饰）。当地球面临未知的巨大危机之际，瑞雯（詹妮弗·劳伦斯 Jennifer Lawrence 饰）在X教授（詹姆斯·麦卡沃伊 James McAvoy 饰）的协助之下，必须领导年轻的X战警们力抗这史上最强大的敌人，并扭转人类命运。",22);
        playUIPanel.add(panel2);

        this.add(playUIPanel);
        this.setSize(800,650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        new playUI().init();
    }*/

}
