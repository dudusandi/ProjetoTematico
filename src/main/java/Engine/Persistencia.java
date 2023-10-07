package Engine;

import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

import java.sql.*;

public class Persistencia {

	public void conectaBanco() {

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


	public void gravaMusica(String nomeMusica, String letraMusica){
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
			String sql = "INSERT INTO listaMusicas (name, letra) VALUES (?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, nomeMusica);
			pstmt.setString(2, letraMusica);
			pstmt.executeUpdate();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM listaMusicas");
			Notifications.create().text("Musica Adicionada") .position(Pos.TOP_CENTER) .hideCloseButton() .showInformation();

			while (rs.next()) {
				int id = rs.getInt("id");
				nomeMusica = rs.getString("name");
				letraMusica = rs.getString("letra");
				System.out.println("ID: " + id + ", Nome da Música: " + nomeMusica + ", Letra da Música: " + letraMusica);
			}
		} catch (SQLException error){
			System.out.println(error);
		}
	}

}
