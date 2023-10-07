package Engine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class Musica {
    private String nomeMusica;
    private String letraMusica;

    private ObservableList<String> items;

    public Musica() {
    }

    public Musica(String nomeMusica, String letraMusica) {
        this.nomeMusica = nomeMusica;
        this.letraMusica = letraMusica;
    }

    public String getNomeMusica() {
        return nomeMusica;
    }

    public String getLetraMusica() {
        return letraMusica;
    }

    public void cadastrarMusica(String nomeMusica, String letraMusica) {
        Persistencia persistencia = new Persistencia();
        persistencia.gravaMusica(nomeMusica, letraMusica);
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


    public Musica retornaNomeLetra(String nomeMusica) {
        Persistencia persistencia = new Persistencia();
        List<Musica> musicas = persistencia.nomes(nomeMusica);
        Musica musicaEncontrada = null;

        for (Musica musica : musicas) {
            musicaEncontrada = musica;
            break;
        }
        return musicaEncontrada;
    }


}
