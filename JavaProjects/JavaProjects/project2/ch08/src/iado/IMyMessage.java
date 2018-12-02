package iado;
import java.sql.SQLException;
import java.util.Collection;

public interface IMyMessage {
	public Collection selectMsgByUid(int uid) throws SQLException;
	public void deleteMsgByID(int id) throws SQLException;
	public void deleteAllByUid(int uid) throws SQLException;
}
