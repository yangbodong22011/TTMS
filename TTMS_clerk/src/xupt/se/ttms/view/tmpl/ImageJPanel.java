package src.xupt.se.ttms.view.tmpl;

import java.awt.*;


import javax.swing.*;

public class ImageJPanel extends JPanel {
	private Image image;

	public ImageJPanel(Image image) { // 构建构造方法.传入的参数是Image的文件路径
		this.image = image;
		Dimension size = new Dimension(image.getWidth(null),image.getHeight(null));
		setSize(size); // 设置JPanel的大小为Image图象的大小
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		//setLocation(0, 35);
		setLayout(null);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null); // 用G 把Image画出来
	}

}