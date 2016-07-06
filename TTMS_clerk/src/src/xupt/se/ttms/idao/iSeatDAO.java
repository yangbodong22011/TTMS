/**
 * 
 */
package src.xupt.se.ttms.idao;
import src.xupt.se.ttms.model.Seat;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface iSeatDAO {
	public int insert(Seat stu);
	public int update(Seat stu);
	public int delete(int ID);
	public List<Seat> select(String condt);
	public int getSeatStatus(Seat seat);
}
