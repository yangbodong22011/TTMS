package src.xupt.se.ttms.view.sellticket;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ObjectInputStream.GetField;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.fabric.xmlrpc.base.Data;

import src.xupt.se.ttms.model.*;
import src.xupt.se.ttms.service.*;

import src.xupt.se.ttms.view.tmpl.ImageJPanel;
import src.xupt.se.ttms.view.tmpl.PopUITmpl;


public class DoTicketUI extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕
	
	private int frmWidth=800;
	private int frmHeight=600;

	protected boolean rst=false; 				//操作结果
	private JLabel lblMovieName, lblSchedule, lblStudio,lblPrice, lblSeat, txtPrice, lblPriceSymbol;
	protected JComboBox<String> txtMovieName = null, txtSchedule = null,txtStudio = null,txtSeat = null;
	private ScheduleSrv scheduleSrv;
	private SeatSrv seatSrv;
	private PlaySrv playSrv ;
	private StudioSrv studioSrv;
	private TicketSrv ticketSrv;
	private Ticket tickets;
	private int scheduleId, playId, studioId, seatId;
	private List<Schedule> listsSchedulesOfSelectedMovieAndTime;
	
	public DoTicketUI() {
		initContent();
	}

//	@Override
	protected void initContent(){

		this.setTitle("选座出票");
		
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小  
		
		this.setBounds((screen.width - frmWidth)/2, (screen.height - frmHeight)/2,frmWidth, frmHeight);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		
		scheduleSrv = new ScheduleSrv();
		seatSrv = new SeatSrv();
		playSrv = new PlaySrv();
		studioSrv = new StudioSrv();
		ticketSrv = new TicketSrv();
	
	    List<Schedule> listsAllSchedules = scheduleSrv.FetchAll();
		String [] movies = new String[listsAllSchedules.size()];
		Schedule schedule;
		for(int i=0; i<listsAllSchedules.size(); i++) 
		{    
			schedule = listsAllSchedules.get(i);
			movies[i] = playSrv.Fetch("play_id = " + schedule.getPlay_id()).get(0).getName();
		} 
		
	//	System.out.println("Tag1");
		
		lblMovieName = new JLabel("电影：");
		lblMovieName.setBounds(60, 30, 80, 30);
		this.add(lblMovieName);
		txtMovieName = new JComboBox<String>(movies);
		txtMovieName.setBounds(150, 30, 120, 30);
		this.add(txtMovieName);
		JButton ok1 = new JButton("选定");
		ok1.setBounds(270, 30, 60, 30);
		ok1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				List<Schedule> listsSchedulesOfSelectedMovie = scheduleSrv.Fetch("play_id = " + playSrv.Fetch("play_name = '" + txtMovieName.getSelectedItem().toString() + "'").get(0).getId());
				String [] times = new String[listsSchedulesOfSelectedMovie.size()];
				txtSchedule.removeAllItems();
				for(int i=0; i<listsSchedulesOfSelectedMovie.size(); i++) 
				{    
					times[i] = listsSchedulesOfSelectedMovie.get(i).getSched_time().toString();
					txtSchedule.addItem(times[i]);
				} 
				
		//		System.out.println("Tag2"); 
				
			}
		});
		this.add(ok1);

		lblSchedule = new JLabel("场次：");
		lblSchedule.setBounds(60, 80, 80, 30);
		this.add(lblSchedule);
		txtSchedule = new JComboBox<String>();
		txtSchedule.setBounds(150, 80, 120, 30);
		this.add(txtSchedule);
		
		JButton ok2 = new JButton("选定");
		ok2.setBounds(270, 80, 60, 30);
		ok2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				listsSchedulesOfSelectedMovieAndTime = scheduleSrv.Fetch("sched_time = '" + txtSchedule.getSelectedItem().toString() +"'"
						+ " and play_id = " + playSrv.Fetch("play_name = '" + txtMovieName.getSelectedItem().toString() + "'").get(0).getId());

				String [] studios = new String[listsSchedulesOfSelectedMovieAndTime.size()];
				txtStudio.removeAllItems();
				for(int i=0; i<listsSchedulesOfSelectedMovieAndTime.size(); i++) 
				{    
					studios[i] = studioSrv.Fetch("studio_id = "+listsSchedulesOfSelectedMovieAndTime.get(i).getStudio_id()).get(i).getName();
					txtStudio.addItem(studios[i]);
				} 
				
				scheduleId = listsSchedulesOfSelectedMovieAndTime.get(0).getSched_id();
				
		//		System.out.println("Tag3");
				
			}
		});
		this.add(ok2);

		lblStudio = new JLabel("所在厅：");
		lblStudio.setBounds(60, 130, 80, 30);
		this.add(lblStudio);
		txtStudio = new JComboBox<String>();
		txtStudio.setBounds(150, 130, 120, 30);
		this.add(txtStudio);
		JButton ok4 = new JButton("选定");
		ok4.setBounds(270, 130, 60, 30);
		ok4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				List<Seat> listSeat = seatSrv.Fetch("studio_id = " + studioSrv.Fetch("studio_name = '" + txtStudio.getSelectedItem().toString() + "'").get(0).getID());	
				String [] seats = new String[listSeat.size()];
				txtSeat.removeAllItems();
				for(int i=0; i<listSeat.size(); i++) 
				{    
					seats[i] = String.valueOf(listSeat.get(i).getId());
					txtSeat.addItem(seats[i]);
				} 
				
	  //	    System.out.println("Tag4");
				
			}
		});
		this.add(ok4);
		
		
		lblSeat = new JLabel("座位：");
		lblSeat.setBounds(60, 180, 80, 30);
		this.add(lblSeat);
		txtSeat = new JComboBox<String>();
		txtSeat.setBounds(150, 180, 120, 30);
		this.add(txtSeat);
		JButton ok5 = new JButton("选定");
		ok5.setBounds(270, 180, 60, 30);
		ok5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txtPrice.setText(String.valueOf(listsSchedulesOfSelectedMovieAndTime.get(0).getSched_ticket_price()));
	
			}
		});
		this.add(ok5);
		
		
		lblPrice = new JLabel("金额(￥):");
		lblPrice.setBounds(60, 230, 80, 30);
		this.add(lblPrice);
		txtPrice = new JLabel();
		txtPrice.setBounds(150, 230, 120, 30);
		this.add(txtPrice); 

		btnSave = new JButton("出票");
		btnSave.addActionListener(this);
		btnSave.setBounds(60, 280, 60, 30);
		this.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(180, 280, 60, 30);
		this.add(btnCancel);

		ImageJPanel imageJP = new ImageJPanel(new ImageIcon(
				"files/imgs/pencil.jpg").getImage());
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
		    tickets = new Ticket();
			tickets.setPlayName(txtMovieName.getSelectedItem().toString());
			tickets.setScheduleId(scheduleId);
			tickets.setSeatId(Integer.parseInt(txtSeat.getSelectedItem().toString()));
			tickets.setPrice(Float.parseFloat(txtPrice.getText()));
//			tickets.setLocked_time(new Date(System.currentTimeMillis() + 300000));
			if(ticketSrv.add(tickets) > 0){
				rst=true;
		//		System.out.println(DateFormat.getDateTimeInstance().format((System.currentTimeMillis() + 300000)));
				ticketSrv.lockTicket(tickets .getId(), DateFormat.getDateTimeInstance().format((System.currentTimeMillis() + 300000)));
				JOptionPane.showMessageDialog(null, "出票成功,请在5分钟内支付。过期失效！");
			
			}
			
			//	this.setVisible(false);
			this.dispose();
			
			getParent().setVisible(true);
			
	}
	public Ticket getTicket(){
		return tickets;
		
	}
	public static void main(String[] args) {
		DoTicketUI frmDoTicketUI = new DoTicketUI();
		frmDoTicketUI.setVisible(true);
	}
	
}
