


import javafx.application.*;
import javafx.event.ActionEvent;
import static javafx.geometry.Pos.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;


public class Sudoku extends Application {
    
    private final InterfacciaSudoku interfaccia = new InterfacciaSudoku();
    private static GestorePartita gestore;
    private static Classifica classifica;

    public void start(Stage stage) {
        ParametriConfigurazione config = new ParametriConfigurazione(); //00
        InformazioniPartita info = InterazioneCache.caricaPartitaBin();
        gestore = new GestorePartita(config);
        
        InviaEventiLog.creaLog("AVVIO", config);    //01

        HBox riquadroTempo;
        VBox riquadroGiocatore;
        if(info == null){   //02
            riquadroTempo = interfaccia.getRiqadroTempo("00:00");
            riquadroGiocatore = interfaccia.getRiquadroGiocatore("");
        } else {
            riquadroTempo = interfaccia.getRiqadroTempo(info.getTempoPartita());
            riquadroGiocatore = interfaccia.getRiquadroGiocatore(info.getNomeGiocatore());
        }

        gestore.setTimer();
        GridPane grigliaSudoku = interfaccia.caricaGriglia();
        
        Button bottoneInizia = interfaccia.getBottoneInizia();  //03
        Button bottoneFine = interfaccia.getBottoneFine();
        Button bottoneAiuto = interfaccia.getBottoneAiuto();
        bottoneInizia.setOnAction((ActionEvent e) -> {
            InviaEventiLog.creaLog("INIZIA", config);
            gestore.nuovaPartita(grigliaSudoku.getChildren());
            InterfacciaSudoku.cambiaStatoAiuto();
        });
        bottoneFine.setOnAction((ActionEvent e) -> {
            InviaEventiLog.creaLog("FINE", config);
            gestore.finePartita();
            InterfacciaSudoku.cambiaStatoAiuto();
        });
        bottoneAiuto.setOnAction((ActionEvent e) -> {
            InviaEventiLog.creaLog("AIUTO", config);
            gestore.rivelaCasella();
        });
        
        VBox riquadroInterazione = new VBox(
                bottoneInizia,
                bottoneFine,
                bottoneAiuto,
                classifica = new Classifica(config)
        );
        riquadroInterazione.setAlignment(CENTER);
        riquadroInterazione.setSpacing(20);

        GridPane layout = interfaccia.getLayout();   //04
        layout.add(riquadroTempo, 0, 0);
        layout.add(riquadroGiocatore, 1, 0);
        layout.add(grigliaSudoku, 0, 1);
        layout.add(riquadroInterazione, 1, 1);
        
        if(info != null && info.getTempoPartita().contains(":") && !info.getTempoPartita().equals("00:00")){    //05
            GestorePartita.ripristinaPartita(grigliaSudoku.getChildren(), info.getGrigliaSudoku(), info.getTempoPartita());
            InterfacciaSudoku.cambiaStatoAiuto();
        }
        
        VBox main = new VBox(interfaccia.getTitolo(), layout);  //06           
        main.setAlignment(CENTER);
        main.setMaxSize(config.larghezzaFinestra,  config.altezzaFinestra);
        
        Group root = new Group(main);
        Scene scena = new Scene(root, config.larghezzaFinestra, config.altezzaFinestra);
        scena.getStylesheets().add("stile.css");    //07
        scena.setFill(Color.BISQUE);
        
        stage.setTitle("SUDOKU");
        stage.setOnCloseRequest((WindowEvent we) -> {   //08
            InviaEventiLog.creaLog("TERMINE", config);
            InterazioneCache.salvaPartitaBin();
        });
        stage.setScene(scena);
        stage.show();
    }
    
    public static void aggiornaClassifica(){    //09
        classifica.aggiornaClassifica(InterfacciaSudoku.getNome(), InterfacciaSudoku.getTempo());
        InterfacciaSudoku.cambiaStatoAiuto();
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
}

/*
(00)
Prende i parametri di configurazione che dovra' passare anceh alle vaarie 
componenti dell'applicazione.
(01)
Non appena avviata l'applicazione invia l'evento di log di invio.
(02)
L'interfaccia viene inizializzata con parametri diversi in base alla presenza 
o meno di informazioni in cache.
(03)
Ad ogni bottone e' stato specificato com comportarsi una volta premuto ed 
inseriti nel leyout.
(04)
Dispongo le varie componenti nel layout.
(05)
Inizializzo la griglia con i valori salvati in cache se presenti.
(06)
Una VBox contiene sia il titolo che il layout, e' stata aggiunta per una 
questione di allineameneto.
(07)
Viene impostato un foglio di stile.
(08)
In caso di chiusura dell'applicazione vengono salvate le informazioni sullo 
stato della partita e si invia il log al server.
*/