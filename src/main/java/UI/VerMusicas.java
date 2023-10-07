package UI;

import Engine.Musica;
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
    public void listar(){
        Musica musica = new Musica();
        musica.listar();
        ObservableList<String> list = musica.getItems();
        lista.setItems(list);

    }

    @FXML
    private void carregarletra(){
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
                    carregarletrafield.setText(letraMusica);
                    break;
                } else {
                    carregarletrafield.setText("");
                }
            }
            connection.close();
        } catch (SQLException error) {
            System.err.println(error.getMessage());
        }

    }

    @FXML
    private void deletarmusica() {
        String selecao = lista.getSelectionModel().getSelectedItem();
        if (selecao != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);
                String sql = "DELETE FROM listaMusicas WHERE name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, selecao);
                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    listar();
                    carregarletrafield.setText("");
                    Notifications.create().text("Musica Deletada") .position(Pos.TOP_CENTER) .hideCloseButton() .showWarning() ;
                    System.out.println("Musica Deletada");
                } else {
                    Notifications.create().text("Nenhuma Musica Deletada") .position(Pos.TOP_CENTER) .hideCloseButton() .showWarning() ;
                    System.out.println("Nenhuma Musica Deletada");
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Nenhum item selecionado na lista.");
        }
    }

}
