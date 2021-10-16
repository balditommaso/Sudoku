
import com.thoughtworks.xstream.*;
import java.io.*;
import java.net.*;

public class InviaEventiLog {

    private static void inviaEventoXML(EventoDiNavigazione evento, String ipServerLog, short portaServerLog){   //00
        XStream xs = new XStream();
        xs.useAttributeFor(EventoDiNavigazione.class, "nomeEvento");
        xs.useAttributeFor(EventoDiNavigazione.class, "indirizzoIp");
        xs.useAttributeFor(EventoDiNavigazione.class, "timestamp");
        String x = xs.toXML(evento);
        try(DataOutputStream dout = new DataOutputStream((new Socket(ipServerLog, 
                portaServerLog)).getOutputStream())
            ){
            dout.writeUTF(x);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println("Evento inviato.");
    }
    
    public static void creaLog(String nomeEvento, ParametriConfigurazione config){  //01
        inviaEventoXML(new EventoDiNavigazione(nomeEvento, config.indirizzoIpClient),
                config.indirizzoIpServerDiLog, config.portaServerDiLog);
    }
}

/*
(00) 
Il metodo invia l'evento di log ad un server di log il cui indirizzo e porta
vanno specificati
(01)
Il metodo crea l'evento di log e lo invia
*/
