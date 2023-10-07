package UI;

import Engine.Exercicio;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CriarExercicio extends Application {



    @Override
    public void start(Stage stage) throws IOException {
    }

    @FXML
    private ListView<String> lista;

    @FXML
    public ObservableList<String> listar() {
            ObservableList<String> items = FXCollections.observableArrayList();
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
            lista.setItems(items);
            return items;
        }

    @FXML
    private void criarex() {
        String selecao = lista.getSelectionModel().getSelectedItem();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("SELECT name, letra FROM listaMusicas");

            while (rs.next()) {
                String nomeMusica = rs.getString("name");
                String letraMusica = rs.getString("letra");

                if (selecao.equals(nomeMusica)) {
                    String[] paragrafos = letraMusica.split("\n");

                    StringBuilder textoComUnderline = new StringBuilder();

                    for (String paragrafo : paragrafos) {
                        String[] palavras = paragrafo.split("\\s+");
                        Random random = new Random();
                        int numPalavrasASeparar = 1;

                        for (int i = 0; i < palavras.length; i++) {
                            if (numPalavrasASeparar > 0 && random.nextBoolean()) {
                                textoComUnderline.append("________");
                                numPalavrasASeparar--;
                            } else {
                                textoComUnderline.append(palavras[i]);
                            }

                            if (i < palavras.length - 1) {
                                textoComUnderline.append(" ");
                            }
                        }

                        textoComUnderline.append("\n");
                    }

                    String sql = "INSERT INTO listaMusicas (name, letra) VALUES (?, ?)";

                    PreparedStatement pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, nomeMusica + " (Exercício) ");
                    pstmt.setString(2, textoComUnderline.toString());

                    pstmt.executeUpdate();

                    System.out.println("Letra com palavras substituídas por underline e mantendo a formatação original salva no banco de dados.");
                }
            }
            connection.close();
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }
    }

}