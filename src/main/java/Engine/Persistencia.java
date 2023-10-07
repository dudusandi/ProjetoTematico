package Engine;

import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


	public void gravaMusica(String nomeMusica, String letraMusica) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
			String sql = "INSERT INTO listaMusicas (name, letra) VALUES (?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, nomeMusica);
			pstmt.setString(2, letraMusica);
			pstmt.executeUpdate();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM listaMusicas");
			Notifications.create().text("Musica Adicionada").position(Pos.TOP_CENTER).hideCloseButton().showInformation();

			while (rs.next()) {
				int id = rs.getInt("id");
				nomeMusica = rs.getString("name");
				letraMusica = rs.getString("letra");
				System.out.println("ID: " + id + ", Nome da Música: " + nomeMusica + ", Letra da Música: " + letraMusica);
			}
		} catch (SQLException error) {
			System.out.println(error);
		}
	}


	public List<String> returnName() {
		List<String> names = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db")) {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("SELECT name FROM listaMusicas");
			while (rs.next()) {
				String nomeMusica = rs.getString("name");
				names.add(nomeMusica);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return names;
	}

	public List<Musica> nomes(String nomeMusica) {
		List<Musica> musicas = new ArrayList<>();

		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);

			String query = "SELECT name, letra FROM listaMusicas WHERE name = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, nomeMusica);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String nome = rs.getString("name");
				String letra = rs.getString("letra");
				Musica musica = new Musica(nome, letra);
				musicas.add(musica);
			}

			connection.close();
		} catch (SQLException error) {
			System.err.println(error.getMessage());
		}

		return musicas;
	}


	public boolean deletarMusica(String nomeMusica) {
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db")) {
			String sql = "DELETE FROM listaMusicas WHERE name = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, nomeMusica);
			int rowsDeleted = preparedStatement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


}
