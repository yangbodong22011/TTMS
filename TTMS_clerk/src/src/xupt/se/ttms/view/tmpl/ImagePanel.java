package src.xupt.se.ttms.view.tmpl;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;

	public ImagePanel(String imgName) {
		super();
		setOpaque(true);
		image = Toolkit.getDefaultToolkit().getImage(imgName);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		if (image != null) {
			int height = image.getHeight(this);
			int width = image.getWidth(this);
			if (height != -1 && height > getHeight())
				height = getHeight();
			if (width != -1 && width > getWidth())
				width = getWidth();
			int x = (int) (((double) (getWidth() - width)) / 2.0);
			int y = (int) (((double) (getHeight() - height)) / 2.0);
			g.drawImage(image, x, y, width, height, this);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		ImagePanel panel = new ImagePanel("resource/image/header.jpg");
		JButton b = new JButton("按钮");
		panel.add(b);
		frame.add(panel);
		frame.setVisible(true);

	}

}
