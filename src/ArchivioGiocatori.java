import java.sql.*;
import java.util.*;

public class ArchivioGiocatori {
    private final short portaDB;
    private final String usernameDB;
    private final String passwordDB;
    private final int numeroRecord;
    
    
    public ArchivioGiocatori(ParametriConfigurazione config){   //00
        portaDB = config.portaDB;
        usernameDB = config.usernameDB;
        passwordDB = config.passwordDB;
        numeroRecord = config.numeroRigheClassifica;
    }
    
    public List<Giocatore> caricaGiocatori(){   //01
        List<Giocatore> elenco = new ArrayList<>();
        try(Connection connessione = DriverManager.getConnection("jdbc:mysql://localhost:"+portaDB+"/sudoku", usernameDB, passwordDB);
                PreparedStatement ps = connessione.prepareStatement("SELECT * FROM classifica ORDER BY tempo ASC LIMIT ?");){
            ps.setInt(1, numeroRecord);
            ResultSet risultato = ps.executeQuery();
            while(risultato.next()){
                elenco.add(new Giocatore(risultato.getString("nome"), risultato.getString("tempo")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return elenco;
    }
    
    public List<Giocatore> cercaGiocatore(String nome, String tempo){   //02
        List<Giocatore> elenco = new ArrayList<>();
        try(Connection connessione = DriverManager.getConnection("jdbc:mysql://localhost:"+portaDB+"/sudoku", usernameDB, passwordDB);
                PreparedStatement ps = connessione.prepareStatement("SELECT * FROM classifica where nome = ?");){
            ps.setString(1, nome);
            ResultSet risultato = ps.executeQuery();
            risultato.last();
            if(risultato.getRow() == 1){
                String vecchioTempo = risultato.getString("tempo");
                if(tempo.compareTo(vecchioTempo) <= 0)
                    elenco = aggiornaGiocatore(nome, tempo);
                else
                    elenco = caricaGiocatori();
            } else 
                elenco = registraGiocatore(nome, tempo);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return elenco;
    }
    
    private List<Giocatore> registraGiocatore(String nome, String tempo){   //03
        try(Connection connessione = DriverManager.getConnection("jdbc:mysql://localhost:"+portaDB+"/sudoku", usernameDB, passwordDB);
                PreparedStatement ps = connessione.prepareStatement("INSERT INTO classifica VALUES (?, ?)");){
            ps.setString(1, nome);
            ps.setString(2, tempo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return caricaGiocatori();
    }
    
    private List<Giocatore> aggiornaGiocatore(String nome, String tempo){   //04
        try(Connection connessione = DriverManager.getConnection("jdbc:mysql://localhost:"+portaDB+"/sudoku", usernameDB, passwordDB);
                PreparedStatement ps = connessione.prepareStatement("UPDATE classifica SET tempo = ? WHERE nome = ?");){
            ps.setString(1, tempo);
            ps.setString(2, nome);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return caricaGiocatori();
    }
    
}

/*
(00)
Il costruttore inizializza i campi, che gli servono per satbilire la connessione
con il server, con i parametri di configurazione.
(01)
Il metodo carica i giocatori dal DB in ordine decrescente di tempo e con
un limite stabilito dai parametri di configurazione.
(02)
Il metodo cerca il giocatore nel DB, assecondo del risultato della ricerca fa
caricare la lista dei Giocatori piu' opportuna.
(03)
Il metodo registra il giocatore nel DB e fa ricaricare i giocatori.
(04)
Il metodo aggiorna il Giocatore con il nuovo tempo e ricarica la testa.
*/

