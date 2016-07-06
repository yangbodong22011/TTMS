package src.xupt.se.ttms.view.sellticket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import src.xupt.se.ttms.model.Employee;
import src.xupt.se.ttms.model.Sale;
import src.xupt.se.ttms.model.Ticket;
import src.xupt.se.ttms.service.EmployeeSrv;
import src.xupt.se.ttms.service.SaleSrv;
import src.xupt.se.ttms.service.TicketSrv;

public class DoSaleUI extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int frmWidth=800;
	private int frmHeight=600;
	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblEmpId, lblShed, lblPay, lblPayMent,lblPayChange;
	protected JTextField txtPayMent;
	protected JComboBox<Integer> txtEmpId;
	private JLabel txtShed, lblTicNum, txtTicNumn, txtPayChange, txtPay;
	private EmployeeSrv employeeSrv;

	private List<Ticket> ticketList = null;

	public DoSaleUI() {
	//	initContent();
	}

	public DoSaleUI(List<Ticket> ticketList) {
	
			this.ticketList = new LinkedList<Ticket>();
			this.ticketList  = ticketList;
	//		System.out.println("已出票：ticket_id:" + ticketList.get(0).getId());
			initContent();
		
	
	}

	//	@Override
	protected void initContent(){
		this.setTitle("生成账单");
		this.setBackground(Color.WHITE);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小  
		
		this.setBounds((screen.width - frmWidth)/2, (screen.height - frmHeight)/2,frmWidth, frmHeight);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		
		lblEmpId = new JLabel("操作员 ID：");
		lblEmpId.setBounds(60, 30, 80, 30);
		this.add(lblEmpId);
		
		txtEmpId = new JComboBox<Integer>();
		employeeSrv = new EmployeeSrv();
		List<Employee> listsAllEmployee = employeeSrv.FetchAll();
		Integer [] empId = new Integer[listsAllEmployee.size()];
		for(int i=0; i<listsAllEmployee.size(); i++) 
		{    
			empId[i] = listsAllEmployee.get(i).getId();
			txtEmpId.addItem(empId[i]);
		} 
		txtEmpId.setBounds(150, 30, 120, 30);
		this.add(txtEmpId);

		lblShed = new JLabel("剧目场次：");
		lblShed.setBounds(60, 80, 80, 30);
		this.add(lblShed);
		
		String sched = new String();
		for(int i = 0;i<ticketList.size(); i++){
			sched += "[" + String.valueOf(ticketList.get(i).getScheduleId()) + "]";
		}
		txtShed = new JLabel(sched);
		txtShed.setBounds(150, 80, 120, 30);
		this.add(txtShed);
		
		lblTicNum = new JLabel("票数量：");
		lblTicNum.setBounds(60, 130, 80, 30);
		this.add(lblTicNum);
		txtTicNumn = new JLabel(ticketList.size() + " 张");
		txtTicNumn.setBounds(150, 130, 120, 30);
		this.add(txtTicNumn);

		lblPay = new JLabel("应收金额(￥):");
		lblPay.setBounds(60, 180, 80, 30);
		this.add(lblPay);
		Double payNum =  (double) 0;
		for(Ticket ticket : ticketList){
			payNum += ticket.getPrice();
		}
		txtPay = new JLabel(payNum + "");
		txtPay.setBounds(150, 180, 120, 30);
		this.add(txtPay);
		
		lblPayMent = new JLabel("实收金额(￥):");
		lblPayMent.setBounds(60, 230, 80, 30);
		this.add(lblPayMent);
		txtPayMent = new JTextField();
		txtPayMent.setBounds(150, 230, 120, 30);
		this.add(txtPayMent);
			
		lblPayChange = new JLabel("找零金额(￥):");
		lblPayChange.setBounds(60, 280, 80, 30);
		this.add(lblPayChange);
		txtPayChange = new JLabel("待刷新>>");
		txtPayChange.setBounds(150, 280, 80, 30);
		this.add(txtPayChange);
		JButton toChangeBt = new JButton("刷新");
		toChangeBt.setBounds(210, 280, 60, 30);
		toChangeBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			   txtPayChange.setText((Float.parseFloat(txtPayMent.getText())-Float.parseFloat(txtPay.getText()))+"");
				
			}
		});
		this.add(toChangeBt);
		
		btnSave = new JButton("结算");
		btnSave.addActionListener(this);
		btnSave.setBounds(60, 320, 60, 30);
		this.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(210, 320, 60, 30);
		this.add(btnCancel);

		JLabel jLabel = new JLabel();
		jLabel.setBounds(360, 160, 100, 100);
		this.add(jLabel);

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
		
		
	//	List<Ticket> titcks = new TicketSrv().Fetch("sched_id = 1");

		if (txtEmpId.getSelectedItem() != null && txtShed.getText() != null
				&& txtTicNumn.getText() != null && txtPay.getText() != null 
				&& txtPayMent.getText() != null ) {
			SaleSrv stuSrv = new SaleSrv();
			Sale sale =new Sale();
			sale.setEmpId(Integer.parseInt(txtEmpId.getSelectedItem().toString()));
			sale.setPayment(Float.parseFloat(txtPayMent.getText()));
			sale.setChange(Float.parseFloat(txtPayChange.getText()));
			sale.setType(1);
			sale.setStatus(0);
			if(stuSrv.add(ticketList,sale)){
				JOptionPane.showMessageDialog(null, "已生成销售单");
			}else{
				TicketSrv ticketSrv = new TicketSrv();
				for(int i = 0;i<ticketList.size();i++){
					ticketSrv.unlockTicket(ticketList.get(i).getId());
				}
				
			}
			
		
			this.dispose();
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	

}
