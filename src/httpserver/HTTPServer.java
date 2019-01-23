
package httpserver;

import java.io.BufferedReader;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * *****************************************************************************
 * Servidor HTTP que atiende peticiones de tipo 'GET' recibidas por el puerto 
 * 8066
 *
 * NOTA: para probar este código, comprueba primero de que no tienes ningún otro
 * servicio por el puerto 8066 (por ejemplo, con el comando 'netstat' si estás
 * utilizando Windows)
 */
class HTTPServer {
    private static ServerSocket socServidor;

    /**
     * **************************************************************************
     * procedimiento principal que asigna a cada petición entrante un socket 
     * cliente, por donde se enviará la respuesta una vez procesada 
     *
     * @param args the command line arguments
     */
public static void main(String[] args) throws IOException, Exception {
    
      
    try{
    socServidor = new ServerSocket(8066);
    imprimeDisponible();
    //ante una petición entrante, procesa la petición por el socket cliente
    //por donde la recibe
    while (true) {
        //a la espera de peticiones
        Socket socCliente = socServidor.accept();
        //atiendo un cliente
        System.out.println("Atendiendo al cliente ");
        
        HiloDespachador hilo = new HiloDespachador(socCliente);
        hilo.start();
                
    }
    } catch (IOException ex){
        ex.getMessage();
    }
}
    
    
    /**
     * Método que retorna la fecha y hora actual de un modo apropiado para
     * usarlo en cabeceras HTTP
     * @return 
     */
    public static String getDateValue(){
        DateFormat df = new SimpleDateFormat(
        "EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(new Date());
    }
    


  /**
   * **************************************************************************
   * muestra un mensaje en la Salida que confirma el arranque, y da algunas
   * indicaciones posteriores
   */
  private static void imprimeDisponible() {

    System.out.println("El Servidor WEB se está ejecutando y permanece a la "
            + "escucha por el puerto 8066.\nEscribe en la barra de direcciones "
            + "de tu explorador preferido:\n\nhttp://localhost:8066\npara "
            + "solicitar la página de bienvenida\n\nhttp://localhost:8066/"
            + "quijote\npara solicitar una página del Quijote,\n\nhttp://"
            + "localhost:8066/directorio/pagina1 o http://localhost:8066/directorio/pagina2\n"
            + "para solicitar las 2 páginas dentro de /directorio/,\n\n"
            + "http://localhost:8066/q\npara simular un error");
  }
}
