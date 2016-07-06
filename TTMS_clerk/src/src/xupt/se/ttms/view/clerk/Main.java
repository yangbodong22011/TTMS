package src.xupt.se.ttms.view.clerk;

import java.awt.Font;

import javax.swing.UIManager;


public class Main {
	
	/** UIManager中UI字体相关的key */
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
	public static void main(String[] args) {
		
		
		
		try
	    {
		//    设置本属性将改变窗口边框样式定义
	    //    BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
	       
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
			
			// 调整默认字体
			for (int i = 0; i < DEFAULT_FONT.length; i++)
			    UIManager.put(DEFAULT_FONT[i],new Font("微软雅黑", Font.PLAIN,14));
			
	    }
	    catch(Exception e)
	    {
	        //TODO exception
	    }
		
		new LoginFrame();

	}
}

