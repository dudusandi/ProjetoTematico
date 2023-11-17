package UI;

import Engine.Musica;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class CriarPDF extends Application {


    @Override
    public void start(Stage primaryStage) {

    }

    public static final String pdfFile = "PDF/Trabalho.pdf";

    @FXML
    private ListView<String> lista;

    @FXML
    private TextField nomeProfessor;

    @FXML
    private TextField conteudo;

    @FXML
    private TextField vocabulario;



    @FXML
    public void listar() {
        Musica musica = new Musica();
        musica.listar();
        ObservableList<String> list = musica.getItems();
        lista.setItems(list);
    }

    @FXML
    public void criarPDF() throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(pdfFile));
        Document document = new Document(pdf);
        String selecao = lista.getSelectionModel().getSelectedItem();
        if (selecao != null) {
            Musica musica = new Musica();
            Musica musicaSelecionada = musica.retornaNomeLetra(selecao);
            if (musicaSelecionada != null) {
                String letraMusica = musicaSelecionada.getLetraMusica();
                String nomeMusica = musicaSelecionada.getNomeMusica();
                document.add(new Paragraph(nomeMusica));
                document.add(new Paragraph("Nome do Professor: " + nomeProfessor.getText()));
                document.add(new Paragraph("Conteúdo Gramatical: " + conteudo.getText()));
                document.add(new Paragraph("Vocabulário: " + vocabulario.getText()));
                document.add(new Paragraph(letraMusica));
                document.close();
                Notifications.create().text("PDF Criado com Sucesso").position(Pos.TOP_CENTER).hideCloseButton().showWarning();
            }
        }
    }

}
