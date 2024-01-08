package util.database;

import java.sql.PreparedStatement;

public interface StatementPreparer {
	public void prepare(PreparedStatement preparedStatement);
}
