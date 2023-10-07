package UI;

import Engine.Persistencia;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Home extends Application {

    public static void main(String[] args) {
        launch(args);}

    @FXML
    private StackPane sp;

    @FXML
    private Button btn;

    @Override
    public void start(Stage stage)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        Persistencia persistencia = new Persistencia();
        persistencia.conectaBanco();



    }

    @FXML
    protected void CriarMusica()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CriarMusica.fxml"));
        Parent root = loader.load();
        sp.getChildren().clear();
        sp.getChildren().add(root);

    }

    @FXML
    protected void VerMusica()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VerMusicas.fxml"));
        Parent root = loader.load();
        VerMusicas controller = loader.getController();
        controller.listar();
        sp.getChildren().clear();
        sp.getChildren().add(root);
    }

    @FXML
    protected void CriarExcercicio()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NovoExercicio.fxml"));
        Parent root = loader.load();
        CriarExercicio criarExercicio = loader.getController();
        criarExercicio.listar();
        sp.getChildren().clear();
        sp.getChildren().add(root);
    }

    @FXML
    protected void CriarPDF()throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CriarPDF.fxml"));
        Parent root = loader.load();
        sp.getChildren().clear();
        sp.getChildren().add(root);
    }


}
