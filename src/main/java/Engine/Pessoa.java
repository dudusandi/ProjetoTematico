package Engine;

public class Pessoa {
    String nome;
    String lingua;

    public Pessoa(String nome, String lingua) {
        this.nome = nome;
        this.lingua = lingua;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }
}
