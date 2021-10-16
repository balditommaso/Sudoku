
import javafx.beans.property.*;

public class Giocatore {    //00
    private final SimpleStringProperty nome;
    private final SimpleStringProperty tempo;
    
    public Giocatore(String nome, String tempo){    
        this.nome = new SimpleStringProperty(nome);
        this.tempo = new SimpleStringProperty(tempo);
    }
    
    public String getNome(){
        return nome.get();
    }
    
    public String getTempo(){
        return tempo.get();
    }
}

/*
(00)
La classe Giocatore utilizza beans e viene usata per rappresentare i giocatori
nella classifica.
*/
