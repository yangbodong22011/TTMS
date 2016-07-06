package src.xupt.se.ttms.view.sellticket;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import src.xupt.se.ttms.model.Schedule;
import src.xupt.se.ttms.model.Ticket;
import src.xupt.se.ttms.service.ScheduleSrv;
import src.xupt.se.ttms.service.TicketSrv;

public class DoTicket1UI extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕
	
	private int frmWidth=800;
	private int frmHeight=600;

	protected boolean rst=false; 				//操作结果
	private JLabel lblMovieName, lblSchedule, lblStudio,lblPrice, lblSeat, txtPrice, lblPriceSymbol;
	protected JLabel txtMovieName, txtSchedule,txtStudio;
	protected JComboBox<String> txtSeat = null;
	private TicketSrv ticketSrv;
	private Ticket tickets;
	private int scheduleId, playId, studioId, seatId;
	private Schedule schedule;
	
	public DoTicket1UI(Schedule sched) {
		initContent();
		initData(sched);
		this.schedule = sched;
	}

	private void initData(Schedule sched){
		txtMovieName.setText(Integer.toString(sched.getPlay_id()));
		txtSchedule.setText(String.valueOf(sched.getSched_id()));
		scheduleId = new ScheduleSrv().Fetch("sched_id = " + sched.getSched_id()).get(0).getStudio_id();
		txtStudio.setText(String.valueOf(scheduleId));
	}
//	@Override
	protected void initContent(){

		this.setTitle("选座出票");
		
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小  
		
		this.setBounds((screen.width - frmWidth)/2, (screen.height - frmHeight)/2,frmWidth, frmHeight);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		
		
		
	//	System.out.println("Tag1");
		
		lblMovieName = new JLabel("电影：");
		lblMovieName.setBounds(60, 30, 80, 30);
		this.add(lblMovieName);
		txtMovieName = new JLabel();
		txtMovieName.setBounds(150, 30, 120, 30);
		this.add(txtMovieName);

		lblSchedule = new JLabel("场次：");
		lblSchedule.setBounds(60, 80, 80, 30);
		this.add(lblSchedule);
		txtSchedule = new JLabel();
		txtSchedule.setBounds(150, 80, 120, 30);
		this.add(txtSchedule);
	

		lblStudio = new JLabel("所在厅：");
		lblStudio.setBounds(60, 130, 80, 30);
		this.add(lblStudio);
		txtStudio = new JLabel();
		txtStudio.setBounds(150, 130, 120, 30);
		this.add(txtStudio);
		
		
		lblSeat = new JLabel("座位：");
		lblSeat.setBounds(60, 180, 80, 30);
		this.add(lblSeat);
		
		JButton ok5 = new JButton(">>选座>>");
		ok5.setBounds(150, 180, 100, 30);
		ok5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txtPrice.setText(String.valueOf(schedule.getSched_ticket_price()));
	
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
			tickets.setPlayName(txtMovieName.getText());
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
