package xupt.se.ttms.view.sellticket;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.print.attribute.standard.JobPriority;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.SaleItem;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.model.Ticket;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.SaleSrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.schedule.ScheduleTable;
import xupt.se.ttms.view.studio.StudioAddUI;
import xupt.se.ttms.view.studio.StudioEditUI;
import xupt.se.ttms.view.tmpl.MainUITmpl;


public class SellTicketUI extends MainUITmpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sale sale=new Sale();
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	private JScrollPane jsc;
	// 查找的提示和输出
	private JLabel hint;
	private JComboBox<String> playComboBox;

	// 查找，编辑和删除按钮
	private JButton btnDoTicket1, btnDoTicket, btnEdit, btnDoSale, btnQuery;
	
	private List<Ticket> ticketList = null;
	
	private Schedule schedule;

	public SellTicketUI() {
		initContent();
	}

	// To be override by the detailed business block interface
//	@Override
	protected void initContent() {
		Rectangle rect = contPan.getBounds();

		ca1 = new JLabel("售票", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		contPan.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 90);
		contPan.add(jsc);
		
		hint = new JLabel("按影片搜索场次:", JLabel.RIGHT);
		hint.setBounds(60, rect.height - 45, 150, 30);
		contPan.add(hint);
		
		List<Play> playList = new PlaySrv().Fetch("");	
		String [] plays = new String[playList.size()];
		for(int i=0; i<playList.size(); i++) 
		{    
			plays[i] = playList.get(i).getName();
		} 
		playComboBox = new JComboBox<String>(plays);
		playComboBox.setBounds(220, rect.height - 45, 200, 30);
		contPan.add(playComboBox);

		
		btnQuery = new JButton("查找");
		btnQuery.setBounds(440, rect.height - 45, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQuerySchedByPaly();
			}
		});
		contPan.add(btnQuery);
		

		btnDoTicket1 = new JButton("自动出票");
		btnDoTicket1.setBounds(rect.width - 270, rect.height - 45, 100, 30);
		btnDoTicket1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDoTicket1Clicked();
			}
		});
		contPan.add(btnDoTicket1);

		btnDoTicket = new JButton("出票");
		btnDoTicket.setBounds(rect.width - 180, rect.height - 45, 80, 30);
		btnDoTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDoTicketClicked();
			}
		});
		contPan.add(btnDoTicket);

		btnDoSale = new JButton("付款");
		btnDoSale.setBounds(rect.width - 100, rect.height - 45, 80, 30);
		btnDoSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDoSleClicked();
			}
		});
		contPan.add(btnDoSale);
		contPan.add(ca1);
		
		schedule = new Schedule();
		showTable();
		
		ticketList = new LinkedList<Ticket>();
		
	//	JPanel sale_item_Panel = new JPanel();
		
	}
	
	private void btnQuerySchedByPaly() {
		ScheduleTable tms = new ScheduleTable(new Schedule());
		Object[] in = { "sched_id", "studio_id", "play_id", "sched_time", "sched_ticket_price" };
		List<Schedule> scheduleList = new ScheduleSrv().Fetch("play_id = " + new PlaySrv().Fetch("play_name = '" + playComboBox.getSelectedItem().toString() + "'").get(0).getId());
		tms.createTable(jsc, in, scheduleList);
		jsc.repaint();
		
	}
	
	private void btnDoTicket1Clicked() {
		//	DoSaleUI doSaleUI = new DoSaleUI();
			
			
			DoTicket1UI doTicket1UI = new DoTicket1UI(schedule);
		//	doTicketUI.setWindowName("出票");
			doTicket1UI.toFront();
			doTicket1UI.setModal(true);
			doTicket1UI.setVisible(true);
			System.out.println(doTicket1UI.getReturnStatus());
			if (doTicket1UI.getReturnStatus()) {
			//	showTable();
				ticketList.add(doTicket1UI.getTicket());
				System.out.println("size:" + ticketList.size());
			}
		}

	private void btnDoTicketClicked() {
	//	DoSaleUI doSaleUI = new DoSaleUI();
		
		
		DoTicketUI doTicketUI = new DoTicketUI();
	//	doTicketUI.setWindowName("出票");
		doTicketUI.toFront();
		doTicketUI.setModal(true);
		doTicketUI.setVisible(true);
		System.out.println(doTicketUI.getReturnStatus());
		if (doTicketUI.getReturnStatus()) {
		//	showTable();
			ticketList.add(doTicketUI.getTicket());
			System.out.println("size:" + ticketList.size());
		}
	}

	private void btnDoSleClicked() {
//			
//		StudioEditUI modStu = new StudioEditUI();
//		modStu.setWindowName("修改演出厅");
//		modStu.toFront();
//		modStu.setModal(true);
//		modStu.setVisible(true);
//		if (modStu.getReturnStatus()) {
//			showTable();
//		}
		DoSaleUI doSaleUI = new DoSaleUI(ticketList);
	//	doSaleUI.setWindowName("生成账单");
		doSaleUI.toFront();
		doSaleUI.setModal(true);
		doSaleUI.setVisible(true);

		if (doSaleUI.getReturnStatus()) {
			showTable();
		}
	}


	public void showTable() {
		ScheduleTable tms = new ScheduleTable(schedule);
		Object[] in = { "sched_id", "studio_id", "play_id", "sched_time", "sched_ticket_price" };
		List<Schedule> scheduleList = new ScheduleSrv().Fetch("");
		tms.createTable(jsc, in, scheduleList);
		jsc.repaint();
	}
//	public void showTable_1() {
//		SaleItem sale_item;
//		SaleItemTable tms = new SaleItemTable(sale_item);
//		Object[] in = { "id", "emp id", "time", "payMent", "payChange","type","status" };
//		List<Sale> saleItemList = new SaleSrv().Fech("");
//		tms.createTable(jsc, in, saleItemList);
//		jsc.repaint();
//	}
	

	public static void main(String[] args) {
		SellTicketUI frmStuMgr = new SellTicketUI();
		frmStuMgr.setVisible(true);
	}

}
