package Engine;

import java.util.List;

public interface PersistenciaInterface {
    void conectaBanco();

    void gravaMusica(String nomeMusica, String letraMusica);

    List<String> returnName();

    List<Musica> nomes(String nomeMusica);

    boolean deletarMusica(String nomeMusica);
}
