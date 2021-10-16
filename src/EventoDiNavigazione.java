
import java.text.*;
import java.util.*;


public class EventoDiNavigazione {
    public final String nomeEvento;
    public final String indirizzoIp;
    public final String timestamp;
    
    public EventoDiNavigazione(String nomeEvento, String indirizzoIp){  //00
        timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.nomeEvento = nomeEvento;
        this.indirizzoIp = indirizzoIp;
    }
     
}

/*
(00)
Il costruttore costruisce in modo autonomo il timestamp.
*/