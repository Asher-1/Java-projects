package iado;
import java.sql.SQLException;
import java.util.Collection;

public interface IMyEbay {
	
	public Collection selectGoodsByUid(int uid) throws SQLException;
	
	public void deleteGoodByID(int id) throws SQLException;
	
	public void deleteAllByUid(int uid) throws SQLException;
	
	public void close() throws SQLException;
}
