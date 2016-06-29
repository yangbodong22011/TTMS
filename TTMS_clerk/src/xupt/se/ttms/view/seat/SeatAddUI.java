package src.xupt.se.ttms.view.seat;

import javax.swing.JDialog;






import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;




//import view.studioUI.ImageJPanel;
import src.xupt.se.ttms.model.Seat;
import src.xupt.se.ttms.service.SeatSrv;
import src.xupt.se.ttms.view.tmpl.*;

public class SeatAddUI extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblStudioId, lblRow, lblColumn,lblstatus;
	protected JTextField txtStudioId, txtRow, txtColumn,txtstatus;
	public SeatAddUI() {
		//initContent(row,col);
	}
	public SeatAddUI(int row,int col , int studioId) {
		initContent(row,col,studioId);
		setVisible(true);
	}

	
	protected void initContent(int row,int col, int studioId){
		this.setTitle("添加座位");
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小 
		setBounds((screen.width-800)/2,(screen.height-600)/2,800,600);
		
		lblStudioId = new JLabel("演出厅ID：");
		lblStudioId.setBounds(60, 30, 80, 30);
		this.add(lblStudioId);
		txtStudioId = new JTextField(String.valueOf(studioId));
		txtStudioId.setBounds(150, 30, 120, 30);
		this.add(txtStudioId);

		lblRow = new JLabel("行号：");
		lblRow.setBounds(60, 80, 50, 30);
		this.add(lblRow);
		txtRow = new JTextField(String.valueOf(row));
		txtRow.setBounds(150, 80, 120, 30);
		this.add(txtRow);

		lblColumn = new JLabel("列号：");
		lblColumn.setBounds(60, 130, 90, 30);
		this.add(lblColumn);
		txtColumn = new JTextField(String.valueOf(col));
		txtColumn.setBounds(150, 130, 120, 30);
		this.add(txtColumn);
		
		lblstatus = new JLabel("状态：");
		lblstatus.setBounds(60, 170, 90, 30);
		this.add(lblstatus);
		txtstatus = new JTextField(1);
		txtstatus.setBounds(150, 170, 120, 30);
		this.add(txtstatus);

		btnSave = new JButton("保存");

		btnSave.addActionListener(this);
		btnSave.setBounds(60, 220, 60, 30);
		this.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(180, 220, 60, 30);
		this.add(btnCancel);

		ImageJPanel imageJP = new ImageJPanel(new ImageIcon(
				"resource/image/pencil.jpg").getImage());
		imageJP.setBounds(360, 160, 100, 100);
		imageJP.setLayout(null);
		this.add(imageJP);
	}
	
	
	public boolean getReturnStatus(){
		   return rst;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			rst=false;
			this.dispose();
			getParent().setVisible(true);

		} else if (e.getSource() == btnSave) {
			btnSaveClicked();
		}

	}
	
	protected void btnSaveClicked(){
		if (txtStudioId.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null&&txtstatus.getText()!=null) {
			SeatSrv seatSrv = new SeatSrv();
			Seat seat=new Seat();
			seat.setStudioId(Integer.parseInt(txtStudioId.getText()));
			seat.setRow(Integer.parseInt(txtRow.getText()));
			seat.setColumn(Integer.parseInt(txtColumn.getText()));
			seat.setSeatStatus(Integer.parseInt(txtstatus.getText()));
			seatSrv.add(seat);
			this.setVisible(false);
			rst=true;
			//getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null,"数据不完整");
		}		
	}
	
	public int getSeatStatus() {
		return Integer.parseInt(txtstatus.getText());
	}
}
