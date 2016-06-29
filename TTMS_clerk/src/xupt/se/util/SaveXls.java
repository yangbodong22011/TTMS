package src.xupt.se.util;

import java.io.File;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class SaveXls {
	
	private JTable table;

	public SaveXls(JTable table) {
		this.table = table;
	}

	public void saveXls() {
		// 新建文件选择器
		JFileChooser chooser = new JFileChooser();
		
		// 设置为文件和文件夹模式
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		// 获取并判断所选按钮的返回值
		int result = chooser.showSaveDialog(null); 
		if (result == JFileChooser.APPROVE_OPTION) {
			File fi = chooser.getSelectedFile();
			
			// 获取绝对路径
			String file = fi.getAbsolutePath() + ".xls";
			System.out.println(file);
			try {
				//新建字符输出流
				FileWriter out = new FileWriter(file);
				
				// 将table的列名循环写入xls文件 最后换行
				for (int i = 0; i < table.getColumnCount(); i++) {
					out.write(table.getColumnName(i) + "\t");
				}
				out.write("\n");
				
				// 将table的内容逐行写入xls文件
				for (int i = 0; i < table.getRowCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						out.write(table.getValueAt(i, j).toString() + "\t");
					}
					out.write("\n");
				}
				
				// 关闭流
				out.close();
				JOptionPane.showMessageDialog(null, "文件导出成功");
				
				// 打开生成的excel文件
				// Runtime.getRuntime().exec("cmd /c start \"\" \"" + file + "\"");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			// 监听点击取消按钮事件
			// JOptionPane.showMessageDialog(null, "未选择任何路径");
		}
	}

}
