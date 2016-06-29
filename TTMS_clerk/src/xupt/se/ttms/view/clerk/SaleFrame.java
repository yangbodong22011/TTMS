package src.xupt.se.ttms.view.clerk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.time.temporal.JulianFields;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.management.loading.PrivateClassLoader;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import src.xupt.se.ttms.model.Play;
import src.xupt.se.ttms.model.Schedule;
import src.xupt.se.ttms.model.Seat;
import src.xupt.se.ttms.model.Studio;
import src.xupt.se.ttms.model.Ticket;
import src.xupt.se.ttms.service.PlaySrv;
import src.xupt.se.ttms.service.ScheduleSrv;
import src.xupt.se.ttms.service.SeatSrv;
import src.xupt.se.ttms.service.SellTicketHandler;
import src.xupt.se.ttms.service.StudioSrv;
import src.xupt.se.ttms.service.TicketSrv;
import src.xupt.se.ttms.view.tmpl.MainUITmpl;
import src.xupt.se.util.NewClass;

public class SaleFrame extends JPanel {

	private static final long serialVersionUID = -8069838656058091382L;
	private JPanel salePanel;
	private JPanel leftPanel;
	private JPanel middlePanel;
	private JPanel rightPanel;
	private SellTicketHandler handler;
	private Play curPlay;
	private Schedule curSchedule;
	private DefaultMutableTreeNode curNode;
	private List<Play> scheduledPlay;
	private JTree tree;
	private JTextArea detail;
	private JLabel ca1;
	private Ticket[][] ticketArray;
	private Schedule schedule;
	private ImageIcon siteimgwhite,siteimggreen,siteimgred;
	private Action act;
	private List<JButton> selectedSeatList;
//	@Override
	public SaleFrame(){
		initContent();
		this.setVisible(true);
		
	}
	protected void initContent() {
//		tabPane = new JTabbedPane();
//		tabPane.setBounds(0, 0, 1024, 590);
//		setLayout(new BorderLayout());
		Rectangle rect = this.getBounds();
		this.setLayout(new BorderLayout());
		salePanel = new JPanel();
		salePanel.setLayout(new BorderLayout());
		add(salePanel,BorderLayout.CENTER);
		handler = new SellTicketHandler();
		handler.makeNewSale();
		

		setLeftPanel();
		setRightPanel();

//		tabPane.addTab("正在上映", salePanel);
//		tabPane.addTab("即将上映", new JLabel());
//		tabPane.addTab("全部电影", new JLabel());
//		this.add(tabPane);
//		this.validate();
		
		
		siteimgwhite = new ImageIcon("resource/image/white.png");
		siteimggreen = new ImageIcon("resource/image/green.png");
		siteimgred = new ImageIcon("resource/image/red.jpg");
		
		selectedSeatList = new LinkedList<JButton>();
		act = new AbstractAction() {
			private static final long serialVersionUID = -144569051730123316L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton site = (JButton) e.getSource();
				String name = site.getName();
				String tmp[] = name.split(",");
				int i = Integer.valueOf(tmp[0]);
				int j = Integer.valueOf(tmp[1]);
	//			System.out.println(i+"|"+j);
				Seat seat = new SeatSrv().Fetch("seat_id = " + Integer.valueOf(tmp[2])).get(0);
		
				Ticket ticket = new Ticket();
				ticket.setPlayName(new PlaySrv().Fetch("play_id = " + schedule.getPlay_id()).get(0).getName());
				ticket.setScheduleId(schedule.getSched_id());
				ticket.setSeatId(seat.getId());
				ticket.setSchedule(schedule);
				ticket.setSeat(seat);
				ticket.setPrice((float)schedule.getSched_ticket_price());
				
				if (site.getIcon() == siteimgwhite) {
					
					selectedSeatList.add(site);
					handler.addTicket(ticket);
					site.setIcon(siteimggreen);
					detail.setText(handler.getInfo());
				} else if (site.getIcon() == siteimggreen) {
					detail.setText("");
					selectedSeatList.remove(site);
					handler.removeTicket(ticket);
					site.setIcon(siteimgwhite);
					detail.setText(handler.getInfo());
				
				}
					
		
				
			}
		};

	}

	private void setLeftPanel() {
		if(leftPanel==null)
			leftPanel = new JPanel(new BorderLayout());
		else
			leftPanel.removeAll();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("影片");
		ScheduleSrv service = new ScheduleSrv();
		List<Schedule> listAll = service.Fetch("");
		for(int i = 0;i<listAll.size();i++){
			DefaultMutableTreeNode parent = new DefaultMutableTreeNode(new PlaySrv().Fetch("play_id="+listAll.get(i).getPlay_id()).get(0).getName());
			root.add(parent);
			List<Schedule> list = service.Fetch("play_id="+listAll.get(i).getPlay_id());
			if (list.size() > 0) {
				List<String> dates = new ArrayList<String>();
				for (Schedule sh : list) {
					String s = DateFormat.getDateInstance().format(sh.getSched_time());
					if(!dates.contains(s)){
						dates.add(s);
					}
				}
				for(String s:dates){
					DefaultMutableTreeNode child = new DefaultMutableTreeNode(s);
					parent.add(child);									
				}
				for (Schedule sh : list) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode)parent.getFirstChild();
					for(int j=0; j<dates.size(); j++){
						if(node.getUserObject().toString().equals(DateFormat.getDateInstance().format(sh.getSched_time()))){
							node.add(new DefaultMutableTreeNode(sh));
							break;
						}else
							node = node.getNextSibling();
					}
				}
			}
		}
		tree = new JTree(root);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			
		    @Override  
		    public void valueChanged(TreeSelectionEvent e) {		    	
		        DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();  
		        curNode = selectedNode;
		        getStudioInfo(selectedNode);
		    }  
		});  
		
		ca1 = new JLabel("售票管理", JLabel.CENTER);
		ca1.setFont(new Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		
		leftPanel.add(ca1, BorderLayout.NORTH);
		leftPanel.add(tree, BorderLayout.CENTER);
		salePanel.add(leftPanel, BorderLayout.WEST);
		leftPanel.updateUI();
		
	}
	
	private List<Seat> getTickets(){
        	System.out.println("schedule:"+schedule.getSched_id());
        	TicketSrv ticketSrv = new TicketSrv();
        	SeatSrv seatSrv = new SeatSrv();
        	List<Seat> seats = new LinkedList<Seat>();
        	List<Ticket> tickets = ticketSrv.Fetch("sched_id = "+ schedule.getSched_id());
        	for(Ticket t : tickets){
        		if(t.getStatus() != 0){
        			seats.add(seatSrv.Fetch("seat_id = "+t.getSeatId()).get(0));
        		}
        	}
        	return seats;
  
	}
	private void getStudioInfo(DefaultMutableTreeNode node){
		if(node!=null && node.isLeaf()){
			schedule = (Schedule)node.getUserObject();
			System.out.println("stu:"+schedule.getStudio_id());
			Studio studio = new StudioSrv().Fetch("studio_id = " + (schedule.getStudio_id())).get(0);
			int row = studio.getRowCount();
			int col = studio.getColCount();
			setMiddlePanel(row,col,studio);
		}
		
	}
	private void setMiddlePanel(int m, int n, Studio studio) {
		
	// 	System.out.println(m+"|"+n+"//"+studio.getName());
		if(middlePanel == null){
			middlePanel = new JPanel(new BorderLayout());
			JScrollPane scrollPane = new JScrollPane(middlePanel);
			salePanel.add(scrollPane, BorderLayout.CENTER);
		}else{
			middlePanel.removeAll();
		}
		JPanel topJPanel = new JPanel(new BorderLayout());
		JLabel stuName = new JLabel(new StudioSrv().Fetch("studio_id = " + schedule.getStudio_id()).get(0).getName(),JLabel.CENTER);
		stuName.setFont(new Font("微软雅黑 Light", Font.BOLD, 30));
		stuName.setForeground(Color.blue);	
		topJPanel.add(stuName, BorderLayout.NORTH);
		
		JLabel lmainview = new JLabel(new ImageIcon("resource/image/selectsite.png"), JLabel.CENTER);
		topJPanel.add(lmainview, BorderLayout.CENTER);
		
		middlePanel.add(topJPanel, BorderLayout.NORTH);
		
		JPanel sites = new JPanel();
		GridLayout gridLayout = new GridLayout(m+1, n+1);
		gridLayout.setHgap(2);
		gridLayout.setVgap(2);
		sites.setLayout(gridLayout);
		sites.setOpaque(false); // 设置背景为透明


		

		// 座位标示   -1:无座, 0:待销售   1:锁定   2:已选   9:卖出
		
		JButton[][] site = new JButton[m+1][n+1];
		for (int i = 0; i < m+1; i++) {
			for (int j = 0; j < n+1; j++) {
				
				if(i==0){
					if(j==0)
						sites.add(new JLabel("  "));
					else
						sites.add(new JLabel("  " + j + "座",SwingConstants.CENTER));
				}else if(j==0){
					if(i>0)
						sites.add(new JLabel(i + "排",SwingConstants.CENTER));
				}else{
					int status = -2;  
					Seat seat = null;
					if(new SeatSrv().Fetch("studio_id = " + studio.getID() + " and seat_row = " + i +" and seat_column = " + j).size() > 0){
						
						seat = new SeatSrv().Fetch("studio_id = " + studio.getID() + " and seat_row = " + i +" and seat_column = " + j).get(0);
						status = seat.getSeatStatus();
					
					}
					if (status == 1 || status == 2) {
							
							site[i][j] = new JButton(act);
							site[i][j].setBackground(Color.WHITE);
							site[i][j].setIcon(siteimgwhite);
							site[i][j].setName(i + "," + j +","+ seat.getId());
							sites.add(site[i][j]);
					} else {
						sites.add(new JLabel("  "));
					}
					
				}
			}
			
		}

		List<Seat> seats2 = getTickets();
		for(Seat seat : seats2){
			if(seat.getSeatStatus() == 1 || seat.getSeatStatus() == 2){
				site[seat.getRow()][seat.getColumn()].setIcon(siteimgred);
		
			}
		}
	
		middlePanel.add(sites, BorderLayout.CENTER);
		middlePanel.updateUI();
	}

	private void setRightPanel() {
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		detail = new JTextArea("");
		JScrollPane scroll = new JScrollPane(detail);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightPanel.add(scroll, BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		JButton sale = new JButton("出票");
		sale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(handler.doSale()){
					detail.setText("");
					
					for(JButton s : selectedSeatList){
						s.setIcon(siteimgred);
						
					}
					JOptionPane.showMessageDialog(null, "出票成功。");
					
				}else{
					JOptionPane.showMessageDialog(null, "出票失败");					
				}
			}
		});
		JButton clear = new JButton("清除");
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handler.clearSale();
				detail.setText("");
			//	getTickets(curNode);
			}
		});
		buttons.add(sale);
		buttons.add(clear);
		rightPanel.add(buttons, BorderLayout.SOUTH);
		salePanel.add(rightPanel, BorderLayout.EAST);
	}
}
