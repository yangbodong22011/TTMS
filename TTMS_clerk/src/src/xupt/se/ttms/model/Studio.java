package src.xupt.se.ttms.model;


public class Studio {
	/* add field
	 * studio_flag   smallint comment    '取值含义：
           0：尚未生成座位，可以根据行列信息生成座位
           1：已经根据影厅的座位信息安排了座位，不能再安排座位；
           -1：影厅损坏或者废弃，不能使用’,
	 */
	private int type;
	private int id=0      ; 
	private String name="" ;
	private int rowCount;
	private int colCount;
	private String introduction="";
	private int studioFlag ;
	private int flag;

	public int getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStudioFlag() {
		return studioFlag;
	}

	public void setStudioFlag(int studioFlag) {
		this.studioFlag = studioFlag;
	}

	public void setID(int ID){
		this.id=ID;
	}
	
	public int getID(){
		return id;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}


	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setColCount(int count){
		this.colCount=count;
	}
	
	public int getColCount(){
		return colCount;
	}
	
	public void setIntroduction(String intro){
		this.introduction=intro;
	}
	
	public String getIntroduction(){
		return introduction;
	}

	public String toString() {
		return  rowCount + "   " +colCount;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getFlag(int flag) {
		return flag;
	}
	
}
