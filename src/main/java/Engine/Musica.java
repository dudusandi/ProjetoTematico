package Engine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Musica {
    private ObservableList<String> items;


    public void cadastrarMusica(String nomeMusica, String letraMusica){
        Persistencia persistencia = new Persistencia();
        persistencia.gravaMusica(nomeMusica,letraMusica);
    }


    public void listar() {
        items = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db")) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("SELECT name FROM listaMusicas");
            while (rs.next()) {
                String nomeMusica = rs.getString("name");
                items.add(nomeMusica);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public ObservableList<String> getItems() {
            return items;
    }
}
