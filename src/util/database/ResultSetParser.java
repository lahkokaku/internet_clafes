package util.database;

import java.sql.ResultSet;
import java.util.Vector;

public interface ResultSetParser<out> {
	public Vector<out> parse(ResultSet resultSet);
}
