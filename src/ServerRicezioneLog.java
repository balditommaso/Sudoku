import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ServerRicezioneLog {

    private static final String FILELOG = "myfiles/ArchivioLog.txt";
    private static final String SCHEMALOG = "myfiles/log.xsd";
    
   
    public static void main(String[] args){ //00
        System.out.println("Server di Log avviato");
        ParametriConfigurazione config = new ParametriConfigurazione();
        try(ServerSocket server = new ServerSocket(config.portaServerDiLog)){
            while(true){
                try(Socket sd = server.accept();
                        DataInputStream din = new DataInputStream(sd.getInputStream())
                        ){
                    String messaggio = din.readUTF();
                    if(ValidaXML.valida(messaggio, SCHEMALOG) == false){
                        System.out.println("Formato del log non valido.");
                    } else {
                        System.out.println("Formato del log valido.");
                        messaggio += '\n';
                        Files.write(Paths.get(FILELOG), messaggio.getBytes(), 
                                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                        messaggio = new String(Files.readAllBytes(Paths.get(FILELOG)));
                        System.out.println(messaggio);
                    }
                } 
            }
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}

/*
(00)
Server che si mette in ascolto dei messaggi di log, i quali prima di essere 
salvati su un file di testo vengono validati.
*/
       
