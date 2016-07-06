package xupt.se.ttms.view.play;

import xupt.se.ttms.model.Play;
import xupt.se.ttms.service.DataDictSrv;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

class PlayTableMouseListener extends MouseAdapter {

	private JTable jt;
	private static Play play;

	public Play getPlay() {
		return play;
	}

	public PlayTableMouseListener(JTable jt, Object[] number, Play play) {
		this.play = play;
		this.jt = jt;
	}

	// �������кţ�����ѡ�е��������θ��� stud�����Ա㴫��ֵ�����޸��������޸�
	public void mouseClicked(MouseEvent event) {
		int row = jt.getSelectedRow();
		DataDictSrv dataDictSrv = new DataDictSrv();
		play.setId(Integer.parseInt(jt.getValueAt(row, 0).toString()));
		play.setTypeId(dataDictSrv.findSelfByName(jt.getValueAt(row, 1).toString()).getId());
		play.setLangId(dataDictSrv.findSelfByName(jt.getValueAt(row, 2).toString()).getId());
		play.setName(jt.getValueAt(row, 3).toString());

//		play.setImage((Blob) jt.getValueAt(row, 5));
		play.setLength(Integer.parseInt(jt.getValueAt(row, 5).toString()));
		play.setTicketPrice(Float.parseFloat(jt.getValueAt(row, 6).toString()));
		play.setStatus(Integer.parseInt(jt.getValueAt(row, 7).toString()));

		if (jt.getValueAt(row, 4) != null)
			play.setIntroduction(jt.getValueAt(row, 4).toString());
		else
			play.setIntroduction("");
	}
}

public class PlayTable {

	private Play play;
	private JTable jt = null;

	public PlayTable(Play play) {
		this.play = play;
	}

	// ����JTable
	public void createTable(JScrollPane jp, Object[] columnNames, List<Play> playList) {
		try {

			Object data[][] = new Object[playList.size()][columnNames.length];

			Iterator<Play> itr = playList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				Play p = itr.next();
				DataDictSrv findSelfByName = new DataDictSrv();
				data[i][0] = Integer.toString(p.getId());
				data[i][1] = findSelfByName.findSelfByID(p.getTypeId()).get(0).getName();
				data[i][2] = findSelfByName.findSelfByID(p.getLangId()).get(0).getName();
				data[i][3] = p.getName();
				data[i][4] = p.getIntroduction();
//				data[i][5] = p.getImage();
				data[i][5] = Integer.toString(p.getLength());
				data[i][6] = p.getTicketPrice();
				data[i][7] = Integer.toString(p.getStatus());
				i++;
			}

			// ����JTable
			jt = new JTable(data, columnNames);
			jt.setBounds(0, 0, 700, 450);

			// �������������������ѡ��
			PlayTableMouseListener tml = new PlayTableMouseListener(jt, columnNames, play);
			jt.addMouseListener(tml);

			// ���ÿɵ����п�
			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			jp.add(jt);
			jp.setViewportView(jt);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}