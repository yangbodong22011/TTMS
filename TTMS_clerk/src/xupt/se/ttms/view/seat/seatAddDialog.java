package src.xupt.se.ttms.view.seat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.xupt.se.ttms.model.Seat;
import src.xupt.se.ttms.service.SeatSrv;
import src.xupt.se.ttms.view.tmpl.ImageJPanel;

public class seatAddDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	

	protected boolean rst=false; 				//操作结果
	protected int txtStudioId, txtRow, txtColumn,txtstatus;
	private JComboBox<Integer> status;
	private int seatStatus = 0;
	public seatAddDialog() {
		//initContent(row,col);
	}
	public seatAddDialog(int row,int col , int studioId) {
		this.txtStudioId = studioId;
		this.txtRow = row;
	    this.txtColumn = col;
		initContent();
	}

	
	protected void initContent(){
		this.setTitle("管理座位");
		this.setBackground(Color.WHITE);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小 
		setBounds((screen.width-300)/2,(screen.height-200)/2,300,200);
		setLayout(new BorderLayout());
		JPanel footerJPanel = new JPanel();
		add(footerJPanel, BorderLayout.SOUTH);
		Integer []statusList = new Integer[]{0,1,-1};	
		status = new JComboBox<Integer>(statusList);
		status.setSize(60, 30);
		add(status, BorderLayout.NORTH);
		
		btnSave = new JButton("保存");
		btnSave.addActionListener(this);
		footerJPanel.add(btnSave);
		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		footerJPanel.add(btnCancel);

//		ImageJPanel imageJP = new ImageJPanel(new ImageIcon(
//				"resource/image/pencil.jpg").getImage());
//		imageJP.setBounds(80, 80, 100, 100);
//		imageJP.setLayout(null);
//		this.add(imageJP);
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
		
			SeatSrv seatSrv = new SeatSrv();
			Seat seat=new Seat();
			seat.setStudioId(txtStudioId);
			seat.setRow(txtRow);
			seat.setColumn(txtColumn);
			seat.setSeatStatus(Integer.parseInt(status.getSelectedItem().toString()));
			seatSrv.add(seat);
			seatStatus = Integer.parseInt(status.getSelectedItem().toString());
			rst = true;
			this.dispose();
			
		
	}
	public int getSeatStatus() {
		return seatStatus;
	}
}
