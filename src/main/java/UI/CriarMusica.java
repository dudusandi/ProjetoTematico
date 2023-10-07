package UI;

import Engine.Musica;
import Engine.Persistencia;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CriarMusica extends Application {

    String nome;
    String letratmp;

    @Override
    public void start(Stage stageCriar) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CriarMusica.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stageCriar.setScene(scene);
        stageCriar.setTitle("Criar Música");
        stageCriar.show();
        Persistencia persistencia = new Persistencia();
        persistencia.conectaBanco();
    }


    @FXML
    private TextArea letra;

    @FXML
    private TextField nomeMusica;

    @FXML
    protected void addMusica() throws IOException{
        Musica musica = new Musica();
        nome = nomeMusica.getText();
        letratmp = letra.getText();
        musica.cadastrarMusica(nome,letratmp);
        limpar();
    }

    @FXML
    protected void limpar(){
        letra.setText("");
        nomeMusica.setText("");
    }
}