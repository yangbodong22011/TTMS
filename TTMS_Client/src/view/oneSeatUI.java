package view;

import model.Seat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kiosk on 6/22/16.
 */

public class oneSeatUI {

    ImageIcon seatWhite = new ImageIcon("/home/kiosk/Desktop/TTMS_Client/src/source/images/white.png");
    ImageIcon seatGreen = new ImageIcon("/home/kiosk/Desktop/TTMS_Client/src/source/images/green.png");
    ImageIcon seatRed = new ImageIcon("/home/kiosk/Desktop/TTMS_Client/src/source/images/red.JPG");
    ImageIcon seatBlack = new ImageIcon("/home/kiosk/Desktop/TTMS_Client/src/source/images/blank.png");
    private  int row;
    private  int col;
    private  int status;
    private  int seat_id;
    private  int num;

    JButton seatButton = new JButton();

    public oneSeatUI() {}
    public oneSeatUI(int i,Seat seat) {
        this.num = i;
        this.row = seat.getRow();
        this.col = seat.getColumn();
        this.status = seat.getSeatStatus();
        this.seat_id = seat.getId();
    }


    public JButton init(Seat seat) {
        if(seat.getSeatStatus() == 1) {          //正常可选
            seatButton.setIcon(seatWhite);
        }else if(seat.getSeatStatus() == -1 || seat.getSeatStatus() == 0) {   //坏了
            seatButton.setIcon(seatBlack);
        } else if(seat.getSeatStatus() == 2) {   //已经被买
            seatButton.setIcon(seatRed);
        }

        seatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getStatus() == 1) {
                    seatButton.setIcon(seatGreen);
                    setStatus(3);                      //表示选中
                    filmUI.allSchedulePlayStudioSeat.get(num).setSeatStatus(3);
                }else if (getStatus() == 3) {
                    seatButton.setIcon(seatWhite);
                    setStatus(1);
                    filmUI.allSchedulePlayStudioSeat.get(num).setSeatStatus(1);
                }
            }
        });
        return seatButton;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getStatus() {
        return status;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
