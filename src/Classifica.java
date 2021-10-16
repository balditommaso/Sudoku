
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;


public class Classifica extends TableView{  //00
    private final ObservableList<Giocatore> classificaOsservabile = FXCollections.observableArrayList();
    private final ArchivioGiocatori archivio;
    
    public Classifica(ParametriConfigurazione config){  //01
        archivio = new ArchivioGiocatori(config);
        TableColumn classificaCol = new TableColumn("CLASSIFICA");
        
        TableColumn nomeCol = new TableColumn("NOME");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nomeCol.prefWidthProperty().bind(this.widthProperty().multiply(0.6));
        
        TableColumn tempoCol = new TableColumn("TEMPO");
        tempoCol.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        tempoCol.prefWidthProperty().bind(this.widthProperty().multiply(0.4));
        
        List<Giocatore> listaGiocatori = archivio.caricaGiocatori();
        for(Giocatore giocatore: listaGiocatori){
            classificaOsservabile.add(giocatore);
        }
        
        this.setPrefHeight(300);
        this.getColumns().addAll(classificaCol);
        classificaCol.getColumns().addAll(nomeCol, tempoCol);
        this.setItems(classificaOsservabile);
    }
    
    public void aggiornaClassifica(String nome, String tempo){  //02
        classificaOsservabile.remove(0, classificaOsservabile.size());
        List<Giocatore> listaGiocatori = archivio.cercaGiocatore(nome, tempo);
        for(Giocatore giocatore: listaGiocatori){
            classificaOsservabile.add(giocatore);
        }
        this.setItems(classificaOsservabile);
    }
    
}


/*
(00)
Classifica estende TableView, rappresenta un elemento grafico dell'applicazione
(01)
Il costruttore inizializza le colonne della TableView, la loro grafica e la 
riempie con i Giocatori.
(02)
Rimuove i giocatori dalla classifica e aggiunge i nuovi.
*/
