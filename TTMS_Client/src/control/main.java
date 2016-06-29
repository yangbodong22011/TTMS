package control;

import model.User;
import view.loginUI;
import view.logs;
import view.mainUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by kiosk on 6/16/16.
 */
public class main {
    private static Socket s;
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 30000;
    public static User user = new User();
    private static String[] DEFAULT_FONT  = new String[]{
            "Table.font"
            ,"TableHeader.font"
            ,"CheckBox.font"
            ,"Tree.font"
            ,"Viewport.font"
            ,"ProgressBar.font"
            ,"RadioButtonMenuItem.font"
            ,"ToolBar.font"
            ,"ColorChooser.font"
            ,"ToggleButton.font"
            ,"Panel.font"
            ,"TextArea.font"
            ,"Menu.font"
            ,"TableHeader.font"
            // ,"TextField.font"
            ,"OptionPane.font"
            ,"MenuBar.font"
            ,"Button.font"
            ,"Label.font"
            ,"PasswordField.font"
            ,"ScrollPane.font"
            ,"MenuItem.font"
            ,"ToolTip.font"
            ,"List.font"
            ,"EditorPane.font"
            ,"Table.font"
            ,"TabbedPane.font"
            ,"RadioButton.font"
            ,"CheckBoxMenuItem.font"
            ,"TextPane.font"
            ,"PopupMenu.font"
            ,"TitledBorder.font"
            ,"ComboBox.font"
    };

    public static void main(String[] args) throws IOException {
        try {
            s = new Socket(SERVER_IP,SERVER_PORT);
            new logs("连接服务器成功");
        }catch (IOException e) {
            new logs("连接服务器出错").showDialog();
        }

        /*try
        {


            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);

            for (int i = 0; i < DEFAULT_FONT.length; i++)
                UIManager.put(DEFAULT_FONT[i],new Font("Î¢ÈíÑÅºÚ", Font.PLAIN,14));

        }
        catch(Exception e)
        {
            //
        }*/

        new mainUI(s).init();                           //new 出loginUI
        new Thread(new clientThread(s)).start();         //新线程去处理连接

    }

}
