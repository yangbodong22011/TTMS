package src.xupt.se.ttms.view.play;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.xupt.se.ttms.model.DataDict;
import src.xupt.se.ttms.model.Play;
//import view.studioUI.ImageJPanel;
import src.xupt.se.ttms.service.DataDictSrv;
import src.xupt.se.ttms.service.PlaySrv;
import src.xupt.se.ttms.view.play.PlayAddUI;

public class PlayEditUI extends PlayAddUI{
	private int ID;

	public PlayEditUI(Play play) {
		initData(play);
	}

	private void initData(Play play) {
		this.setTitle("修改影片");
		DataDictSrv dataDictSrv = new DataDictSrv();
		System.out.println(play.toString());
		txtName.setText(play.getName());
		txtType.setSelectedItem(dataDictSrv.findSelfByID(play.getTypeId()).get(0).getName());
		txtLang.setSelectedItem(dataDictSrv.findSelfByID(play.getLangId()).get(0).getName());
	    txtLength.setText(Integer.toString(play.getLength()));
		txtPrice.setText(Float.toString(play.getTicketPrice()));
		txtStatus.setText(Integer.toString(play.getStatus()));
		txtIntrodution.setText(play.getIntroduction());
		ID = play.getId();
	}
   
	@Override
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtIntrodution.getText() != null && txtLength.getText() != null 
				&& txtPrice.getText() != null && txtStatus.getText() != null){
			PlaySrv playSrv = new PlaySrv();
			Play p=new Play();
			
			p.setId(ID);
			p.setTypeId(new DataDictSrv().findSelfByName(txtType.getSelectedItem().toString()).getId());
			p.setLangId(new DataDictSrv().findSelfByName(txtLang.getSelectedItem().toString()).getId());
			p.setName(txtName.getText());
			p.setLength(Integer.parseInt(txtLength.getText()));
			p.setTicketPrice(Float.parseFloat(txtPrice.getText()));
			p.setStatus(Integer.parseInt(txtStatus.getText()));
			p.setIntroduction(txtIntrodution.getText());

			playSrv.modify(p);
			this.setVisible(false);
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}

}
