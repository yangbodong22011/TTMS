package xupt.se.ttms.view.sale;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import xupt.se.ttms.model.SaleItem;


class SaleItemTableMouseListener extends MouseAdapter {

	private JTable jt;
	private static SaleItem saleItem;

	public SaleItem getSaleItem() {
		return saleItem;
	}

	public SaleItemTableMouseListener(JTable jt, Object[] number, SaleItem saleItem) {
		this.saleItem = saleItem;
		this.jt = jt;
	}

	// 监听到行号，将所选行的内容依次赋到 sale对象，以便传有值对象到修改面板进行修改
	public void mouseClicked(MouseEvent event) {
		int row = jt.getSelectedRow();
		saleItem.setId(Integer.parseInt(jt.getValueAt(row, 0).toString()));
		saleItem.setId(Integer.parseInt(jt.getValueAt(row, 1).toString()));
		saleItem.setTicketId(Integer.parseInt(jt.getValueAt(row, 2).toString())); 
		saleItem.setPrice(Float.parseFloat(jt.getValueAt(row, 3).toString()));
	
	//	System.out.println(jt.getValueAt(row, 1).toString());
	}
}

public class SaleItemTable {

	private SaleItem saleItem;
	private JTable jt = null;

	public SaleItemTable(SaleItem saleItem) {
		this.saleItem = saleItem;
	}

	// 创建JTable
	public void createTable(JScrollPane jp, Object[] columnNames, List<SaleItem> saleItemList) {
		try {

			Object data[][] = new Object[saleItemList.size()][columnNames.length];

			Iterator<SaleItem> itr = saleItemList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				SaleItem saleItem = itr.next();
				data[i][0] = Integer.toString(saleItem.getId());
				data[i][1] = Integer.toString(saleItem.getTicketId());
				data[i][2] = Integer.toString(saleItem.getSaleId());
				data[i][3] = Float.toString(saleItem.getPrice());

				i++;
			}

			// 生成JTable
			jt = new JTable(data, columnNames);
			jt.setBounds(0, 0, 700, 450);
			for(int num=0;num<columnNames.length;num++){
		//		jt.getColumn(num).setCellRenderer(new tableJButton());
			}
			// 添加鼠标监听，监听到所选行
			SaleItemTableMouseListener tml = new SaleItemTableMouseListener(jt, columnNames, saleItem);
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