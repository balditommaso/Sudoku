
import java.util.*;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.*;



public class GestorePartita {

    private final int celleNascoste;
    private static final List<TextField> listaCelle = new ArrayList<>();    
    private static Timeline timeline;
    private static int tempoCorrente;
    private static int celleDaTrovare;
    private static int malus;
    private static String[] soluzioneGriglia;
    
    public GestorePartita(ParametriConfigurazione config){  //00
        celleNascoste = config.numeroCelleNascoste;
        malus = config.malus;
        soluzioneGriglia = config.soluzioneSudoku.split(",");
    }
    
    public void nuovaPartita(ObservableList<Node> listaNodi){  //01
        List<Integer> numeriNascosti = generaNumeriNascosti();
        celleDaTrovare = celleNascoste;
        tempoCorrente = 0;
        for(int i = 0; i < listaNodi.size(); i++){
            if(listaNodi.get(i) instanceof StackPane){
                StackPane cella = (StackPane)listaNodi.get(i);
                TextField tf = (TextField)cella.getChildren().get(0);
                tf.setDisable(false);
                if(numeriNascosti.contains(i)){
                    tf.setEditable(true);
                    final int indice = i;
                    tf.setOnKeyReleased((Event e)->{
                        controllaNumero(tf, indice);
                    });
                } else 
                    InterfacciaSudoku.scopriCella(tf, soluzioneGriglia[i]);
                listaCelle.add(tf);
            }            
        }
        timeline.play();
    }
    
    public static String[] getStatoPartita(){   //02
        String[] statoPartita = new String[listaCelle.size()];
        for(int i = 0; i < statoPartita.length; i++){
            statoPartita[i] = listaCelle.get(i).getText();
        }
        return statoPartita;
    }

    public static void ripristinaPartita(ObservableList<Node> listaNodi, String[] backupGriglia, String backupTempo) {  //03
        for(int i = 0; i < listaNodi.size(); i++){
            if(listaNodi.get(i) instanceof StackPane){
                StackPane cella = (StackPane)listaNodi.get(i);
                TextField tf = (TextField)cella.getChildren().get(0);
                tf.setDisable(false);
                listaCelle.add(tf);
                if(backupGriglia[i].equals("")){
                    celleDaTrovare++;
                    tf.setEditable(true);
                    final int indice = i;
                    tf.setOnKeyReleased((Event e)->{
                        controllaNumero(tf, indice);
                    });
                }
                else
                    InterfacciaSudoku.scopriCella(tf, backupGriglia[i]);
            }
        }
        
        String[] tempo = backupTempo.split(":");        
        tempoCorrente = Integer.parseInt(tempo[0])*60 + Integer.parseInt(tempo[1]);
        if(tempoCorrente != 0)
            timeline.play();
    }
    
    public static void finePartita(){   //04
        timeline.stop();
        InterfacciaSudoku.aggiornaTempo("00", "00");
        InterfacciaSudoku.resetNome();

        for (TextField cella : listaCelle) {
            InterfacciaSudoku.resetCella(cella);
        }
        listaCelle.clear();
    }
    
    public void setTimer() {    //05
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), 
                (ActionEvent ev) ->{
            tempoCorrente++;
            String secondi = Integer.toString(tempoCorrente%60);
            String minuti = Integer.toString(tempoCorrente/60);
            if(tempoCorrente%60 < 10)
                secondi = "0" + secondi;
            if(tempoCorrente/60 < 10)
                minuti = "0" + minuti; 
            InterfacciaSudoku.aggiornaTempo(minuti, secondi);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    
    public void rivelaCasella() {   //06
        if(celleDaTrovare == 1)
            return;
        Random rand = new Random();
        int indiceEstratto;
        while(true){
            indiceEstratto = rand.nextInt(81);
            TextField tf = listaCelle.get(indiceEstratto);
            if(tf.getText().equals("")){
                InterfacciaSudoku.scopriCella(tf, soluzioneGriglia[indiceEstratto]);
                tempoCorrente += malus;
                celleDaTrovare--;
                break;
            }
        }
    }
    
    private static void controllaNumero(TextField cella, int indice){   //07
        String numeroInserito = cella.getText();
        System.out.println(numeroInserito + " - " + soluzioneGriglia[indice]);
        if(soluzioneGriglia[indice].equals(numeroInserito)){
            InterfacciaSudoku.sceltaGiusta(cella, soluzioneGriglia[indice]);
            celleDaTrovare--;
            if(celleDaTrovare == 0){
                Sudoku.aggiornaClassifica();
                finePartita();
            }
        } else {
            InterfacciaSudoku.sceltaSbagliata(cella);
            tempoCorrente += malus;
        }
    }

    private List<Integer> generaNumeriNascosti() {  //08
        int numeriDaGenerare = celleNascoste;
        List<Integer> indiciNascosti = new ArrayList<>();
        Random rand = new Random();
        int indiceEstratto;

        for(int i = 0; i < numeriDaGenerare; i++){
            do{
                indiceEstratto = rand.nextInt(81);
            }while(indiciNascosti.contains(indiceEstratto));
            indiciNascosti.add(indiceEstratto);
        }
        return indiciNascosti;
    }

}

/*
(00)
Il costruttore inizializza i campi con i valori opportuni dei parametri di
configurazione. La soluzione viene estartta, viene fatto lo split e inserito
in un array di stringhe.
(01)
Dalla lista dei nodi (si aspetta il GridPane del Sudoku) elabora i TextField in
base alla soluzione ed gli indici estartti per essere nascosti.
(02)
Il metodo serve per estrarre lo stato della partita, restituisce un array di 
stringhe con i valori di tutte le celle, quelle modificate o meno.
(03)
Il metodo invece di iniziare una nuova partita riempie la griglia del Sudoku
(GridPane) con i valori passatogli, Inoltre ripristaina anche il tempo della
vecchia sessione e se e' necessario fa ripartire il tempo.
(04)
Pulisce la griglia del Sudoku, ferma e resetta il tempo della partita ed il nome 
del giocatore.
(05)
Il metodo setta un timer che quando e' attivo attiva un evento ogni secondo,
l'evento viene gestito incrementando di un secondo il tempo tel timer.
(06)
Il metodo scopre il valore di una cella a caso tra quelle non ancora trovate ed 
incrementa il valore del timer di un certo malus.
(07)
Il metodo effettua una verifica del valore inserito nella cella e la modifica
in base all'esito.
(08)
Il metodo in base al numero di celle da nascondere restituisce un array di 
interi che rappresentano gli indici da nascondere della griglia. I numeri 
casuali estratti sono senza ripetizione.

*/