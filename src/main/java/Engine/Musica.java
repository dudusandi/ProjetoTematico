package Engine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class Musica {
    private ObservableList<String> items;


    public void cadastrarMusica(String nomeMusica, String letraMusica){
        Persistencia persistencia = new Persistencia();
        persistencia.gravaMusica(nomeMusica,letraMusica);
    }


    public void listar() {
        Persistencia persistencia = new Persistencia();
        items = FXCollections.observableArrayList();
        List<String> nomesDasMusicas = persistencia.returnName();
        items.addAll(nomesDasMusicas);
    }
        public ObservableList<String> getItems() {
            return items;
    }
}
