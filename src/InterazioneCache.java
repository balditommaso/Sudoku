
import java.io.*;

public class InterazioneCache { //00
    
    private final static String FILEBIN = "./myfiles/sudoku.bin";
    
    public final static void salvaPartitaBin(){        
        try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(FILEBIN))){
            oout.writeObject(new InformazioniPartita());
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    
    public final static InformazioniPartita caricaPartitaBin(){
        try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream(FILEBIN))){
            return (InformazioniPartita)oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
  
}

/*
(00)
La classe contiene i metodi per scrivere e leggere da un file binario salvato in 
locale.
*/
