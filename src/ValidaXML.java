
import java.io.*;
import javax.xml.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.xml.sax.*;


public class ValidaXML {
    public static boolean valida(String messaggioXML, String fileXSD) { //00
        try {
            StringReader reader = new StringReader(messaggioXML);
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
            Schema s = sf.newSchema(new StreamSource(new File(fileXSD))); 
            s.newValidator().validate(new StreamSource(reader)); 
        } catch (SAXException | IOException e) {
            if (e instanceof SAXException){
                System.out.println("Errore di validazione: " + e.getMessage());
                return false;
            }
            else{
                System.out.println(e.getMessage());
                return false;
            }   
        }
        return true;   
    }
}

/*
Il metodo valida stringhe XML con un apposito schema XSD, entrambi passati come 
parametri. Restituisce l'esito della validazione.
*/