package xupt.se.ttms.view.sale;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import xupt.se.ttms.model.Sale;

class SaleTableMouseListener extends MouseAdapter {

	private JTable jt;
	private static Sale sale;

	public Sale getSale() {
		return sale;
	}

	public SaleTableMouseListener(JTable jt, Object[] number, Sale sale) {
		this.sale = sale;
		this.jt = jt;
	}

	// 监听到行号，将所选行的内容依次赋到 sale对象，以便传有值对象到修改面板进行修改
	public void mouseClicked(MouseEvent event) {
		int row = jt.getSelectedRow();
		sale.setId(Integer.parseInt(jt.getValueAt(row, 0).toString()));
		sale.setEmpId(Integer.parseInt(jt.getValueAt(row, 1).toString()));
		sale.setTime(new Date(jt.getValueAt(row, 2).toString())); 
		sale.setPayment(Integer.parseInt(jt.getValueAt(row, 3).toString()));
		sale.setChange(Integer.parseInt(jt.getValueAt(row, 4).toString()));
		sale.setType(Integer.parseInt(jt.getValueAt(row, 5).toString()));
		sale.setStatus(Integer.parseInt(jt.getValueAt(row, 6).toString()));
	//	System.out.println(jt.getValueAt(row, 1).toString());
	}
}

public class SaleTable {

	private Sale sale;
	private JTable jt = null;

	public SaleTable(Sale sale) {
		this.sale = sale;
	}

	// 创建JTable
	public void createTable(JScrollPane jp, Object[] columnNames, List<Sale> saleList) {
		try {

			Object data[][] = new Object[saleList.size()][columnNames.length];

			Iterator<Sale> itr = saleList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Sale sale = itr.next();
				data[i][0] = Integer.toString(sale.getId());
				data[i][1] = Integer.toString(sale.getEmpId());
				data[i][2] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sale.getTime());  //============//
				data[i][3] = Float.toString(sale.getPayment());
				data[i][4] = Float.toString(sale.getChange());
				data[i][5] = Integer.toString(sale.getType());
				data[i][6] = Integer.toString(sale.getStatus());
			
				i++;
			}

			// 生成JTable
			jt = new JTable(data, columnNames);
			jt.setBounds(0, 0, 700, 450);
			for(int num=0;num<columnNames.length;num++){
		//		jt.getColumn(num).setCellRenderer(new tableJButton());
			}
			// 添加鼠标监听，监听到所选行
			SaleTableMouseListener tml = new SaleTableMouseListener(jt, columnNames, sale);
			jt.addMouseListener(tml);

			// 设置可调整列宽
			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			jp.add(jt);
			jp.setViewportView(jt);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}