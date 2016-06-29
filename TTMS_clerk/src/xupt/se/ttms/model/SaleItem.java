package src.xupt.se.ttms.model;

public class SaleItem {

	private int id;
	private int ticketId;
	private int saleId;
	private float price;

	public SaleItem() { }

	public SaleItem(int id, int ticketId, int saleId, float price) {
		super();
		this.id = id;
		this.ticketId = ticketId;
		this.saleId = saleId;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
