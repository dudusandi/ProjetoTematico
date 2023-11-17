package UI;

import Engine.Musica;
import Engine.Persistencia;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import java.io.IOException;
import java.sql.*;
import java.util.Random;


public class VerMusicas extends Application {


    @FXML
    private ListView<String> lista;

    @FXML
    private TextArea carregarletrafield;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VerMusicas.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        VerMusicas controller = fxmlLoader.getController();
        controller.listar();
        stage.setTitle("Visualizar Músicas");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void listar() {
        Musica musica = new Musica();
        musica.listar();
        ObservableList<String> list = musica.getItems();
        lista.setItems(list);
    }

    @FXML
    private void carregarletra() {
        String selecao = lista.getSelectionModel().getSelectedItem();

        if (selecao != null) {
            Musica musica = new Musica();
            Musica musicaSelecionada = musica.retornaNomeLetra(selecao);
            if (musicaSelecionada != null) {
                String letraMusica = musicaSelecionada.getLetraMusica();
                carregarletrafield.setText(letraMusica);
            } else {
                carregarletrafield.setText("");
            }
        }
    }

    @FXML
    private void deletarmusica() {
        String selecao = lista.getSelectionModel().getSelectedItem();
        Persistencia persistencia = new Persistencia();
        if (selecao != null) {
            boolean musicaDeletada = persistencia.deletarMusica(selecao);
            if (musicaDeletada) {
                listar();
                carregarletrafield.setText("");
                Notifications.create().text("Musica Deletada").position(Pos.TOP_CENTER).hideCloseButton().showWarning();
                System.out.println("Musica Deletada");
            } else {
                Notifications.create().text("Nenhuma Musica Deletada").position(Pos.TOP_CENTER).hideCloseButton().showWarning();
                System.out.println("Nenhuma Musica Deletada");
            }
        }
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

                    Notifications.create().text("Exercicio Criado com Sucesso").position(Pos.TOP_CENTER).hideCloseButton().showWarning();

                }
            }
            connection.close();
            listar();
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }
    }

}
