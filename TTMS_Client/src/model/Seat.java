package model;

/**
 * Created by kiosk on 6/15/16.
 */
public class Seat {
    int type;
    private int id;
    private int studioId;
    private int row;
    private int column;
    private int seatStatus;
    /*
     * seat_status   smallint comment    '取值含义：
           0：此处是空位，没有安排座椅
           1：完好的座位，可以使用
           -1：座位损坏，不能安排座位',

     */
    public Seat(){	}

    public Seat(int id, int studioId, int row, int column,int seatStatus) {
        super();
        this.id = id;
        this.studioId = studioId;
        this.row = row;
        this.column = column;
        this.seatStatus = seatStatus;
    }

    public int getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(int seatStatus) {
        this.seatStatus = seatStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudioId() {
        return studioId;
    }

    public void setStudioId(int studioId) {
        this.studioId = studioId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}