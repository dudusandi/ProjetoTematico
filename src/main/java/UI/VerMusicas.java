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

}
