package UI;

import SQL.Sqlite;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.*;

public class CriarMusica extends Application {

    @Override
    public void start(Stage stageCriar) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CriarMusica.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageCriar.setScene(scene);
        stageCriar.setTitle("Criar Música");
        stageCriar.show();
        Sqlite sqlite = new Sqlite();
        sqlite.conectar();
    }


    @FXML
    private TextArea letra;

    @FXML
    private TextField nomeMusica;

    @FXML
    protected void addMusica() throws IOException{
        String nome = nomeMusica.getText();
        String letratmp = letra.getText();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:base.db");
            String sql = "INSERT INTO listaMusicas (name, letra) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, letratmp);
            pstmt.executeUpdate();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM listaMusicas");
            Notifications.create().text("Musica Adicionada") .position(Pos.TOP_CENTER) .hideCloseButton() .showInformation();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nomeMusica = rs.getString("name");
                String letraMusica = rs.getString("letra");
                System.out.println("ID: " + id + ", Nome da Música: " + nomeMusica + ", Letra da Música: " + letraMusica);
                limpar();
            }
        } catch (SQLException error){
            System.out.println(error);
        }
    }

    @FXML
    protected void limpar(){
        letra.setText("");
        nomeMusica.setText("");
    }



}