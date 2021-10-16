
import java.io.Serializable;

public class InformazioniPartita implements Serializable{   
    private final  String nomeGiocatore;
    private final  String tempoPartita;
    private final  String[] grigliaSudoku;
        
    public InformazioniPartita(){   //00
        nomeGiocatore = InterfacciaSudoku.getNome();
        tempoPartita = InterfacciaSudoku.getTempo();
        grigliaSudoku = GestorePartita.getStatoPartita();
    }
    
    public String getNomeGiocatore(){
        return nomeGiocatore;
    }
    
    public String getTempoPartita(){
        return tempoPartita;
    }
    
    public String[] getGrigliaSudoku(){
        return grigliaSudoku;
    }
}

/*
(00)
Il costruttore inizializza i campi con le informazioni della partita in modo 
autonomo e trasparente rispetto le altre componenti.
*/
