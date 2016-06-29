/**
 * 
 */
package src.xupt.se.ttms.idao;
import src.xupt.se.ttms.model.Play;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface iPlayDAO {
	public int insert(Play stu);
	public int update(Play stu);
	public int delete(int ID);
	public List<Play> select(String condt); 
	public List<Play> selectScheduledPlay(String condt);
}
