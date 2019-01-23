
package httpserver;

import java.time.ZoneId;
import java.time.ZonedDateTime;


//Mensajes que intercambia el Servidor con el Cliente según protocolo HTTP
public class Mensajes {
    public static final String lineaInicial_OK = "HTTP/1.1 200 OK";
    public static final String lineaInicial_NotFound = "HTTP/1.1 404 Not Found"; 
}
