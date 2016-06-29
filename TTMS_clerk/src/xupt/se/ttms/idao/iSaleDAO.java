/**
 * 
 */
package src.xupt.se.ttms.idao;
import src.xupt.se.ttms.model.Sale;
import src.xupt.se.ttms.model.SaleItem;
import src.xupt.se.ttms.model.Ticket;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface iSaleDAO {
	public boolean doSale(List<Ticket> tickets);

	public List<Sale> select(String condt);

	public int  update(Sale sale);

	public int delete(int ID);

	public boolean doSale(List<Ticket> tickets, Sale sale);

	List<SaleItem> findSaleItemById(String condt);
}
