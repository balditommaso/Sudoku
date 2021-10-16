
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import static javafx.geometry.Pos.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;


public class InterfacciaSudoku {    //00
    
    private static TextField campoTempo;
    private static TextField campoNome;
    private static Button bottoneAiuto;
    
    public Button getBottoneInizia(){
        Button bottoneInizia = new Button("Inizia");
        bottoneInizia.setMinWidth(100);
        return bottoneInizia;
    }
    
    public Button getBottoneFine(){
        Button bottoneFine = new Button("Fine");
        bottoneFine.setMinWidth(100);
        return bottoneFine;
    }
    
    public Button getBottoneAiuto(){    //01
        bottoneAiuto = new Button("Aiuto");
        bottoneAiuto.setDisable(true);
        bottoneAiuto.setMinWidth(100);
        return bottoneAiuto;
    }    
   
    public Label getTitolo(){
        Label titolo = new Label("SUDOKU");
        titolo.setStyle("-fx-font-size: 40px");
        return titolo;
    }
    
    public GridPane getLayout(){    //02
        GridPane layoutSudoku = new GridPane();
        layoutSudoku.setHgap(40);
        layoutSudoku.setVgap(20);
        ColumnConstraints colonnaSinistra = new ColumnConstraints();
        colonnaSinistra.setPercentWidth(60);
        ColumnConstraints colonnaDestra = new ColumnConstraints();
        colonnaDestra.setPercentWidth(40);
        layoutSudoku.getColumnConstraints().addAll(colonnaSinistra, colonnaDestra);
        layoutSudoku.setPadding(new Insets(20,40,20,40));
        return layoutSudoku;
    }
    
    public HBox getRiqadroTempo(String tempo){
        HBox riquadroTempo = new HBox(
                getEtichettaTempo(), 
                getCampoTempo(tempo)
        ); 
        riquadroTempo.setAlignment(CENTER);
        riquadroTempo.setSpacing(20);
        return riquadroTempo;
    }
    
    public VBox getRiquadroGiocatore(String nome){
        VBox riquadroGiocatore = new VBox(
                getEtichettaGiocatore(),
                getCampoNome(nome)
        );
        riquadroGiocatore.setAlignment(CENTER);
        return riquadroGiocatore;
    }

    public GridPane caricaGriglia(){    //03
        GridPane grid = new GridPane();
        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                StackPane cella = new StackPane();
                cella.getStyleClass().add("cell");
                cella.pseudoClassStateChanged(right, col == 2 || col == 5);
                cella.pseudoClassStateChanged(bottom, row == 2 || row == 5);
                
                TextField tf = creaCella();
                tf.setDisable(true);
                cella.getChildren().add(tf);
                grid.add(cella, col, row);
            }
        }
        return grid;
    }
    
    public static void resetCella(TextField cella){
        cella.setText("");
        cella.setStyle("-fx-background-color: WHITE");
        cella.setDisable(true);
    }
    
    public static void scopriCella(TextField cella, String soluzione){
        cella.setText(soluzione);
        cella.setStyle("-fx-background-color: GREY");
        cella.setEditable(false);
    }
    
    public static void sceltaGiusta(TextField cella, String soluzione){
        cella.setText(soluzione);
        cella.setEditable(false);
        cella.setStyle("-fx-background-color: GREEN");
    }
    
    public static void sceltaSbagliata(TextField cella){
        cella.setStyle("-fx-background-color: ORANGE");
        cella.setText("");
    }
    
    public static void aggiornaTempo(String minuti, String secondi){          
        campoTempo.setText(minuti + ":" + secondi);
    }
    
    public static void resetNome(){
        campoNome.setText("");
    }
    
    public static String getNome(){
        return campoNome.getText();
    }
    
    public static String getTempo(){
        return campoTempo.getText();
    }
    
    public static void cambiaStatoAiuto(){
        if(bottoneAiuto.isDisable() == true)
            bottoneAiuto.setDisable(false);
        else
            bottoneAiuto.setDisable(true);
    }
    
    private Label getEtichettaTempo(){
        Label etichettaTempo = new Label("Tempo:");
        return etichettaTempo;
    }
    
    private Label getEtichettaGiocatore(){
        Label etichettaGiocatore = new Label("Giocatore:");
        return etichettaGiocatore;
    }
    
    private TextField getCampoNome(String nome){
        campoNome = new TextField(nome);
        campoNome.setPromptText("Inserire il nome");
        campoNome.setFont(Font.font("Verdana", 15));
        campoNome.setAlignment(CENTER);
        return campoNome;
    }
    
    private TextField getCampoTempo(String tempo){
        campoTempo = new TextField(tempo);
        campoTempo.setEditable(false);
        campoTempo.setFont(Font.font("Verdana", 15));
        campoTempo.setAlignment(CENTER);
        campoTempo.setMaxWidth(80);
        return campoTempo;
    }
    
    private TextField creaCella(){
        TextField tf = new TextField();
        tf.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        tf.setAlignment(CENTER);
        return tf ;
    }
    
}


/*
(00)
La classe si occupa della inizializzazione e gestione di tutti gli elementi
grafici dell'applicazione.
(01)
Il campo bottoneAiuto oltre ad essere inizializzato viene anche modificato da 
altri metodi, per questo motivo ha un campo a differenza degli altri bottoni.
(02)
GridLayout e' un GridPane all'interno del quale si organizza tutta l'interfaccia
dell'applicazione, deve essere solo riempito.
(03)
Il metodo genera una griglia del Sudoku all'interno di un GridPane, inmposta 
delle classi di stile in modo da avere l'effetto delle macro griglie.

*/

