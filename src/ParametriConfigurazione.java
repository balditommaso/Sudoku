import com.thoughtworks.xstream.XStream;
import java.nio.file.*;

public class ParametriConfigurazione{   //00
    private final String FILECONFIG = "./myfiles/config.xml";
    private final String SCHEMACONFIG = "./myfiles/config.xsd";
    
    public final String indirizzoIpDB;
    public final short portaDB;
    public final String indirizzoIpServerDiLog;
    public final String indirizzoIpClient;
    public final short portaServerDiLog;
    public final String usernameDB;
    public final String passwordDB;
    public final int altezzaFinestra;
    public final int larghezzaFinestra;
    public final int malus;
    public final int numeroRigheClassifica;
    public final int numeroCelleNascoste;
    public final String soluzioneSudoku;
    
    public ParametriConfigurazione(){   
        ParametriConfigurazione c = null;
        XStream xs = new XStream();
        xs.useAttributeFor(ParametriConfigurazione.class, "indirizzoIpDB");
        xs.useAttributeFor(ParametriConfigurazione.class, "portaDB");
        xs.useAttributeFor(ParametriConfigurazione.class, "indirizzoIpServerDiLog");
        xs.useAttributeFor(ParametriConfigurazione.class, "indirizzoIpClient");
        xs.useAttributeFor(ParametriConfigurazione.class, "portaServerDiLog");
        String contenuto = null;
        try {
            contenuto = new String(Files.readAllBytes(Paths.get(FILECONFIG)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }  
        if(ValidaXML.valida(contenuto, SCHEMACONFIG) == true){
            System.out.println("Il file di configurazione valido.");
            c = (ParametriConfigurazione)xs.fromXML(contenuto);
        } else {
            System.out.println("Il file di configurazione non valido.");
        }  
        this.indirizzoIpDB = c.indirizzoIpDB;
        this.portaDB = c.portaDB;
        this.indirizzoIpServerDiLog = c.indirizzoIpServerDiLog;
        this.indirizzoIpClient = c.indirizzoIpClient;
        this.portaServerDiLog = c.portaServerDiLog;
        this.usernameDB = c.usernameDB;
        this.passwordDB = c.passwordDB;
        this.altezzaFinestra = c.altezzaFinestra;
        this.larghezzaFinestra = c.larghezzaFinestra;
        this.malus = c.malus;
        this.numeroRigheClassifica = c.numeroRigheClassifica;
        this.numeroCelleNascoste = c.numeroCelleNascoste;
        this.soluzioneSudoku = c.soluzioneSudoku;
    }
}

/*
(00) 
La classe prende i parametri di configurazione dallo specifico file XML dopo 
prima averlo verificato con il file XSD
*/