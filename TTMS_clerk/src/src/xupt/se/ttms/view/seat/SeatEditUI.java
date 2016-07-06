package src.xupt.se.ttms.view.seat;



import javax.swing.JOptionPane;




//import view.studioUI.ImageJPanel;


import src.xupt.se.ttms.model.Seat;
import src.xupt.se.ttms.service.SeatSrv;


public class SeatEditUI extends SeatAddUI{

	public SeatEditUI(Seat seat) {
		initData(seat);
	}

	private void initData(Seat seat) {
		txtStudioId.setText(Integer.toString(seat.getStudioId()));
		txtRow.setText(Integer.toString(seat.getRow()));
		txtColumn.setText(Integer.toString(seat.getColumn()));
		//txtstatus.setText(Integer.parseInt(txtstatus.getText()));
	}

	@Override
	protected void btnSaveClicked(){
		if (txtStudioId.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null&&txtstatus.getText()!=null) {
			SeatSrv seatSrv = new SeatSrv();
			Seat seat=new Seat();
			seat.setStudioId(Integer.parseInt(txtstatus.getText())); // 暂时填成0 ，后面要进行修改
			seat.setRow(Integer.parseInt(txtRow.getText()));// 待修改
			seat.setColumn(Integer.parseInt(txtColumn.getText()));  // 待修改

			seatSrv.modify(seat);
			this.setVisible(false);
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}

}
