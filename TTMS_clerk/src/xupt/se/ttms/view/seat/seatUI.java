package xupt.se.ttms.view.seat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import xupt.se.ttms.model.Seat;
import xupt.se.ttms.service.SeatSrv;

public class seatUI extends JDialog{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public seatUI() {
		
	}
	public seatUI(int m, int n, final int studio_id) {
		initialize(m, n, studio_id);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int m, int n, final int studio_id) {
		
		setTitle("座位管理");
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小 
		setBounds((screen.width-1024)/2,(screen.height-768)/2,1024,768);
		JPanel seatpanel = new JPanel(new BorderLayout());

		JLabel lmainview = new JLabel();

		ImageIcon selectsite = new ImageIcon("resource/image/selectsite.png");
		lmainview.setIcon(selectsite);

		JPanel sites = new JPanel();
		GridLayout gridLayout = new GridLayout(m+1, n+1);
		gridLayout.setHgap(2);
		gridLayout.setVgap(2);
		sites.setLayout(gridLayout);
		sites.setOpaque(false); // 设置背景为透明

	//	sites.setBounds(105, 120, 510, 300);

		final ImageIcon siteimgwhite = new ImageIcon("resource/image/white.png");
		final ImageIcon siteimggreen = new ImageIcon("resource/image/green.png");
		final ImageIcon siteimgred = new ImageIcon("resource/image/red.jpg");

		Action act = new AbstractAction() {
			private static final long serialVersionUID = -144569051730123316L;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JButton site = (JButton) e.getSource();
				String name = site.getName();
				String tmp[] = name.split(",");
				int i = Integer.valueOf(tmp[0]);
				int j = Integer.valueOf(tmp[1]);
				Seat seat = null;
				int status = 0;
				if(Integer.valueOf(tmp[2]) != 0){
					seat = new SeatSrv().Fetch("seat_id = " + Integer.valueOf(tmp[2])).get(0);
					status = seat.getSeatStatus();
				}else{
					seat = new Seat();
					
				}
				
//				if(status == 0){
//						seatAddDialog addStud = new seatAddDialog(i,j,studio_id);	
//						int seatStatus = addStud.getSeatStatus();
//						addStud.toFront();
//						addStud.setModal(true);
//						addStud.setVisible(true);
//						if(addStud.getReturnStatus()){
//							if(seatStatus == -1)
//							    site. setIcon(siteimgred) ; 
//							if(seatStatus == 1)
//								site. setIcon(siteimggreen) ; 
//							if(seatStatus == 0)
//								site. setIcon(siteimgwhite) ;
//						}else{
//							JOptionPane.showMessageDialog(null, "座位更新失败");
//						}
//						
//				}else if(status == 1 || status == -1 || status == 2){//修改座位状态
//					
					seatAddDialog addStud = new seatAddDialog(seat);	
					addStud.toFront();
					addStud.setModal(true);
					addStud.setVisible(true);
					if(addStud.getReturnStatus()){
						int seatStatus = addStud.getSeatStatus();
						if(seatStatus == -1)
						    site. setIcon(siteimgred); 
						else if(seatStatus == 1)
							site. setIcon(siteimggreen); 
						else if(seatStatus == 0)
							site. setIcon(siteimgwhite);
					}else{
						JOptionPane.showMessageDialog(null, "座位更新失败");
					}
			//	}
			}
		};

		
		for (int i = 0; i < m+1; i++) {
			for (int j = 0; j < n+1; j++) {
				
				if(i==0){
					if(j==0)
						sites.add(new JLabel("  "));
					else
						sites.add(new JLabel(j + "座",SwingConstants.CENTER));
				}else if(j==0){
					if(i>0)
						sites.add(new JLabel(i + "排",SwingConstants.CENTER));
				}else{
					int status = -2;
					Seat seat = null;
					if(new SeatSrv().Fetch("studio_id = " + studio_id + " and seat_row = " + i +" and seat_column = " + j).size() > 0){
						
						seat = new SeatSrv().Fetch("studio_id = " + studio_id + " and seat_row = " + i +" and seat_column = " + j).get(0);
			
						status = seat.getSeatStatus();
						
				//		System.out.println(status);
						if (status == -1) {
							JButton site = new JButton(act);
					
								site.setBackground(Color.WHITE);
								site.setIcon(siteimgred);
								site.setName(i + "," + j +","+ seat.getId());
								sites.add(site);
						} else if (status == 0) {
							JButton site = new JButton(act);
					
								site.setBackground(Color.WHITE);
								site.setIcon(siteimgwhite);
								site.setName(i + "," + j +","+ seat.getId());
								sites.add(site);
						} else if (status == 1 || status == 2) {
				
							JButton site = new JButton(act);
				
							site.setBackground(Color.WHITE);
							site.setIcon(siteimggreen);
							site.setName(i + "," + j +","+ seat.getId());
							sites.add(site);
						}
					}
					
					if(status == -2){
						JButton site = new JButton(act);
						site.setBackground(Color.WHITE);
						site.setIcon(siteimgwhite);
						site.setName(i + "," + j +","+ 0);
						sites.add(site);
					}
				}
			}
			
		}
		JPanel top = new JPanel(new FlowLayout());
		top.setBackground(Color.gray);
		JLabel jLabel = new JLabel("银幕",SwingConstants.CENTER);
		jLabel.setFont(new Font("微软雅黑 Light", Font.BOLD, 30));
		jLabel.setForeground(Color.blue);	
		top.add(jLabel);
		seatpanel.add(top, BorderLayout.NORTH);
		seatpanel.add(sites, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(seatpanel);
		this.add(scrollPane);
		seatpanel.setVisible(true);
		this.setVisible(true);

		seatpanel.updateUI();
		
		JPanel footer = new JPanel(new FlowLayout());
		
		JLabel b2 = new JLabel("可用的座位");
		JLabel b1 = new JLabel(siteimggreen);
		b1.setSize( 150, 30);
		b2.setSize( 150, 30);
		footer.add(b1);
		footer.add(b2);
		
		JLabel b3 = new JLabel(siteimgred);
		JLabel b4 = new JLabel("损坏的座位");
		b3.setSize( 150, 30);
		b4.setSize( 150, 30);
		footer.add(b3);
		footer.add(b4);
		JLabel b5 = new JLabel(siteimgwhite);
		JLabel b6 = new JLabel("空缺的座位");
		b3.setSize( 150, 30);
		b4.setSize( 200, 30);
		footer.add(b5);
		footer.add(b6);
		seatpanel.add(footer, BorderLayout.SOUTH);
	}
}
