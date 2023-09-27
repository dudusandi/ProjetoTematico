package SQL;

import java.sql.*;

public class Sqlite {

	public void conectar() {

		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet tableExists = statement
					.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='listaMusicas'");
			if (!tableExists.next()) {
				statement.executeUpdate(
						"CREATE TABLE listaMusicas (id INTEGER PRIMARY KEY AUTOINCREMENT, name STRING, letra STRING)");
			}
			System.out.println("CONECTADO");
			connection.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

}
