package xupt.se.ttms.view.sale;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import xupt.se.ttms.model.Sale;
import xupt.se.ttms.model.SaleItem;
import xupt.se.ttms.model.Ticket;
import xupt.se.ttms.service.SaleSrv;
import xupt.se.ttms.view.sellticket.DoSaleUI;
import xupt.se.ttms.view.sellticket.DoTicketUI;
import xupt.se.ttms.view.tmpl.MainUITmpl;

public class SaleMgrUI extends MainUITmpl {
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
	private JTextField input;

	// 查找，编辑和删除按钮
	private JButton btnAdd, btnEdit, btnDel, btnQuery, btnBack;
	
	private List<Ticket> ticketList = null;
	
	private Rectangle rect;

	public SaleMgrUI() {
		initContent();
	}

	// To be override by the detailed business block interface
//	@Override
	protected void initContent() {
		rect = contPan.getBounds();

		ca1 = new JLabel("销售记录", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		contPan.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 90);
		contPan.add(jsc);

		hint = new JLabel("请输入sale Id:", JLabel.RIGHT);
		hint.setBounds(60, rect.height - 45, 150, 30);
		contPan.add(hint);

		input = new JTextField();
		input.setBounds(220, rect.height - 45, 200, 30);
		contPan.add(input);

		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
		btnQuery = new JButton("查找");
		btnQuery.setBounds(440, rect.height - 45, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		contPan.add(btnQuery);

		btnAdd = new JButton("明细");
		btnAdd.setBounds(rect.width - 250, rect.height - 45, 80, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryItemClicked();
			}
		});
		contPan.add(btnAdd);

		btnDel = new JButton("删除");
		btnDel.setBounds(rect.width - 180, rect.height - 45, 80, 30);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDelClicked();
			}
		});
		contPan.add(btnDel);

//		btnBack = new JButton("返回");
//		btnBack.setBounds(rect.width - 100, rect.height - 45, 80, 30);
//		btnBack.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnDelClicked();
//			}
//		});
//		contPan.add(btnBack);
	
		showTable();
		
		ticketList = new LinkedList<Ticket>();
		
	//	JPanel sale_item_Panel = new JPanel();
		
	}

	

	

	private void btnDelClicked() {
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			SaleSrv saleSrv = new SaleSrv();
			saleSrv.delete(sale.getId());
			showTable();
		}
	}

	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
			SaleSrv SaleSrv = new SaleSrv();
			List<Sale> saleList = SaleSrv.Fech(input.getText());;
			SaleTable tms = new SaleTable(sale);
			Object[] in = { "id", "emp id", "time", "payMent", "payChange","type","status" };
			tms.createTable(jsc, in, saleList);
			jsc.repaint();

		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
		}
	}
	
	private void btnQueryItemClicked() {
		
			SaleSrv saleSrv = new SaleSrv();		
			List<SaleItem> saleItemList = saleSrv.FechSaleItems(String.valueOf(sale.getId()));
			System.out.println(saleItemList.size());
			SaleItemTable tms = new SaleItemTable(new SaleItem());
			Object[] in = { "sale_item_id", "ticket_id", "sale_ID", "sale_item_price"};
			tms.createTable(jsc, in, saleItemList);
			jsc.repaint();
			
			ca1.setText("销售明细");
			btnBack = new JButton("返回");
			btnBack.setBounds(rect.width - 100, rect.height - 45, 80, 30);
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent Event) {
					ca1.setText("销售记录");
					showTable();
					contPan.remove(btnBack);
					contPan.repaint();
				}
			});
			contPan.add(btnBack);
			contPan.repaint();

		
	}
	public void showTable() {
		SaleTable tms = new SaleTable(sale);
		Object[] in = { "id", "emp id", "time", "payMent", "payChange","type","status" };
		List<Sale> saleList = new SaleSrv().Fech("");

		tms.createTable(jsc, in, saleList);
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
		SaleMgrUI frmSaleMgr = new SaleMgrUI();
		frmSaleMgr.setVisible(true);
	}

}

