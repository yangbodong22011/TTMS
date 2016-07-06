package src.xupt.se.ttms.view.play;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Blob;
import java.awt.Label;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import src.xupt.se.ttms.model.Play;
import src.xupt.se.ttms.service.PlaySrv;
import src.xupt.se.ttms.view.tmpl.*;


public class PlayMgrUI extends JPanel{
	private static final long serialVersionUID = 1L;
	private Play play=new Play();
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	private JScrollPane jsc;
	// 查找的提示和输出
	private JLabel hint;
	private JTextField input;

	// 查找，编辑和删除按钮
	private JButton btnAdd, btnEdit, btnDel, btnQuery;

	public PlayMgrUI() {
		initContent();
		this.setVisible(true);
	}

	// To be override by the detailed business block interface
	private void initContent() {
		Rectangle rect = this.getBounds();
		setLayout(new BorderLayout());
		JPanel topJPanel = new JPanel(new FlowLayout());
		add(topJPanel, BorderLayout.NORTH);
		ca1 = new JLabel("影片管理", JLabel.CENTER);
		ca1.setFont(new Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		topJPanel.add(ca1);
	
		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 90);
		add(jsc, BorderLayout.CENTER);

		hint = new JLabel("请输入影片名称:", JLabel.RIGHT);
		hint.setBounds(60, rect.height - 45, 150, 30);
		topJPanel.add(hint);

		input = new JTextField(10);
		input.setBounds(220, rect.height - 45, 200, 30);
		topJPanel.add(input);

		btnQuery = new JButton("查找");
		btnQuery.setBounds(440, rect.height - 45, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		topJPanel.add(btnQuery);

		btnAdd = new JButton("添加");
		btnAdd.setBounds(rect.width - 220, rect.height - 45, 60, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnAddClicked();
			}
		});
		topJPanel.add(btnAdd);

		btnEdit = new JButton("修改");
		btnEdit.setBounds(rect.width - 150, rect.height - 45, 60, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnModClicked();
			}
		});
		topJPanel.add(btnEdit);

		btnDel = new JButton("删除");
		btnDel.setBounds(rect.width - 80, rect.height - 45, 60, 30);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDelClicked();
			}
		});
		topJPanel.add(btnDel);
		showTable();
	}
    
	private void btnAddClicked() {
		PlayAddUI addPlay = new PlayAddUI();
//		addPlay.setWindowName("添加影片");
		addPlay.toFront();
		addPlay.setModal(true);
		addPlay.setVisible(true);
		System.out.println(addPlay.getReturnStatus());
		if (addPlay.getReturnStatus()) {
			showTable();
		}
	}

	private void btnModClicked() {
			
		PlayEditUI modStu = new PlayEditUI(play);
//		modStu.setWindowName("修改影片");
		modStu.toFront();
		modStu.setModal(true);
		modStu.setVisible(true);
		if (modStu.getReturnStatus()) {
			showTable();
		}
	}

	private void btnDelClicked() {
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			PlaySrv playSrv = new PlaySrv();
			playSrv.delete(play.getId());
			showTable();
		}
	}

	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
			showTable_1(input.getText());
			
		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
		}
	}
   
	public void showTable_1(String name){
    	PlayTable tms = new PlayTable(play);
		Object[] in = { "play_id", "play_type", "play_lang", "play_name", "play_introduction ",  
				"play_length", "play_ticket_price","play_status" };
		List<Play> playList = new PlaySrv().Fetch("play_name = '" + name + "'");
		tms.createTable(jsc, in, playList);
		jsc.repaint();
    }
	
	public void showTable() {
		PlayTable tms = new PlayTable(play);
		Object[] in = { "play_id", "play_type", "play_lang", "play_name", "play_introduction ",   
				"play_length", "play_ticket_price","play_status" };
		List<Play> play = new PlaySrv().FetchAll();

		tms.createTable(jsc, in, play);
		jsc.repaint();
	}

//	public static void main(String[] args) {
//		PlayMgrUI frmStuMgr = new PlayMgrUI();
//		frmStuMgr.setVisible(true);
//	}
}
