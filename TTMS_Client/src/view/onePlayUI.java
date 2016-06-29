package view;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import model.Play;
import net.sf.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by kiosk on 6/21/16.
 */
public class onePlayUI {
    private Socket s;              //传递数据的套接字
    PrintStream ps;

    private int play_id;
    private final static String FilmImageBasePath = "/home/kiosk/Desktop/TTMS_Client/src/source/images/";
    JPanel onePlayUIPanel = new JPanel(new BorderLayout());
    JLabel onePlayUIFilmImage;

    JPanel onePlayUICenterPanel = new JPanel(new BorderLayout());
    JPanel OnePlayUICenterPanelUp = new JPanel(new GridLayout(1,3));
    JLabel OnePlayUICenterPanelUpType;
    JLabel OnePlayUICenterPanelUpLang;
    JLabel OnePlayUICenterPanelUpLength;
    JLabel OnePlayUICenterPanelFilmName;

    JTextArea onePlayUIFilmIntroduction;


    JPanel onePlayUIRightPanel = new JPanel(new BorderLayout());
    JLabel onePlayUIRightPanelPrice;
    JButton onePlayUIRightPanelButton = new JButton("买票");

    public onePlayUI() {}
    public onePlayUI(Socket s) throws IOException{
        this.s = s;
        ps = new PrintStream(s.getOutputStream());
    }

    public JPanel init(int id,String FilmPath,String type,String lang,String name,int length,String introduction,float price) {
        play_id = id;
        onePlayUIFilmImage = new JLabel(new ImageIcon(FilmImageBasePath+FilmPath));
        OnePlayUICenterPanelUpType = new JLabel("类型:"+type);
        OnePlayUICenterPanelUpType.setFont(new Font("宋体",Font.BOLD,15));
        OnePlayUICenterPanelUpLang = new JLabel("语言:"+lang);
        OnePlayUICenterPanelUpLang.setFont(new Font("宋体",Font.BOLD,15));
        OnePlayUICenterPanelUpLength = new JLabel("时长:"+Integer.toString(length)+"分钟");
        OnePlayUICenterPanelUpLength.setFont(new Font("宋体",Font.BOLD,15));
        OnePlayUICenterPanelUp.add(OnePlayUICenterPanelUpType);
        OnePlayUICenterPanelUp.add(OnePlayUICenterPanelUpLang);
        OnePlayUICenterPanelUp.add(OnePlayUICenterPanelUpLength);
        onePlayUICenterPanel.add(OnePlayUICenterPanelUp,BorderLayout.NORTH);
        OnePlayUICenterPanelFilmName = new JLabel(name,JLabel.CENTER);
        OnePlayUICenterPanelFilmName.setFont(new Font("宋体",Font.BOLD,25));
        //OnePlayUICenterPanelFilmName.setBackground(Color.YELLOW);
        OnePlayUICenterPanelFilmName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //onePlayUICenterPanel.add(OnePlayUICenterPanelFilmName,BorderLayout.CENTER);


        onePlayUIFilmIntroduction = new JTextArea(handleIntroduction(introduction));
        onePlayUIFilmIntroduction.setEditable(false);
        JScrollPane scroll = new JScrollPane(onePlayUIFilmIntroduction);
        //onePlayUICenterPanel.add(scroll,BorderLayout.SOUTH);

        JPanel OnePlayUICenterPanelDown = new JPanel(new BorderLayout());
        OnePlayUICenterPanelDown.add(OnePlayUICenterPanelFilmName,BorderLayout.NORTH);
        OnePlayUICenterPanelDown.add(scroll,BorderLayout.CENTER);
        onePlayUICenterPanel.add(OnePlayUICenterPanelDown,BorderLayout.CENTER);


        onePlayUICenterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        onePlayUIRightPanelPrice = new JLabel(Float.toString(price));
        onePlayUIRightPanelPrice.setFont(new Font("宋体",Font.BOLD,20));
        onePlayUIRightPanel.add(onePlayUIRightPanelPrice,BorderLayout.CENTER);
        onePlayUIRightPanelButton.setBackground(Color.YELLOW);
        onePlayUIRightPanel.add(onePlayUIRightPanelButton,BorderLayout.SOUTH);
        onePlayUIRightPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playname = OnePlayUICenterPanelFilmName.getText();
                Play play = new Play();
                play.setId(play_id);
                play.setType(4);
                play.setName(playname);
                JSONObject object = JSONObject.fromObject(play);
                ps.println(object);

            }
        });

        onePlayUIPanel.add(onePlayUIFilmImage,BorderLayout.WEST);
        onePlayUIPanel.add(onePlayUICenterPanel,BorderLayout.CENTER);
        onePlayUIPanel.add(onePlayUIRightPanel,BorderLayout.EAST);
        onePlayUIPanel.setBorder(new MatteBorder(5,5,5,5,Color.LIGHT_GRAY));
        return onePlayUIPanel;
    }
    public String handleIntroduction (String str) {
        int j = 40;
        int sum = str.length()/j;
        String newstr = null;
        int i;
        for (i = 0; i < sum ; i++) {
            newstr += str.substring(i*j,i*j+j);
            newstr += "\n";
        }
        newstr += str.substring(i*j,str.length());
        return newstr;
    }
}
