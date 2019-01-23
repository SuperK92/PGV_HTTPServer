
package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class HiloDespachador extends Thread {

    private Socket socketCliente;

    public HiloDespachador(Socket socketCliente) {

        this.socketCliente = socketCliente;

    }

  public void run() {

    try{
      
        procesaPeticion(socketCliente);
        //cierra la conexión entrante
        socketCliente.close();
        System.out.println("cliente atendido");
          
    } catch (IOException ex) {
        Logger.getLogger(HiloDespachador.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
   /**
   *****************************************************************************
   * procesa la petición recibida
   *
   * @throws IOException
   */
  private static void procesaPeticion(Socket socketCliente) throws IOException {
    //variables locales
    String peticion;
    String html;
    String pagina = "";

    //Flujo de entrada
    InputStreamReader inSR = new InputStreamReader(
            socketCliente.getInputStream());
    //espacio en memoria para la entrada de peticiones
    BufferedReader bufLeer = new BufferedReader(inSR);

    //objeto de java.io que entre otras características, permite escribir 
    //'línea a línea' en un flujo de salida
    PrintWriter printWriter = new PrintWriter(
            socketCliente.getOutputStream(), true);

    //mensaje petición cliente
    peticion = bufLeer.readLine();

    //para compactar la petición y facilitar así su análisis, suprimimos todos 
    //los espacios en blanco que contenga
    peticion = peticion.replaceAll(" ", "");
    
    //if(peticion.contains("pagina1")) pagina = "pagina1";
    //if(peticion.contains("pagina2")) pagina = "pagina2";
   

    //si realmente se trata de una petición 'GET' (que es la única que vamos a
    //implementar en nuestro Servidor)
    if (peticion.startsWith("GET")) {
        //extrae la subcadena entre 'GET' y 'HTTP/1.1'
        peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));

        //si corresponde a la página de inicio
        if (peticion.length() == 0 || peticion.equals("/")) {
            html = Paginas.html_index;
            printWriter.println(Mensajes.lineaInicial_OK);
            printWriter.println(Paginas.fecha); 
            printWriter.println(Paginas.primeraCabecera);
            printWriter.println("Content-Length: " + (html.length()));
            printWriter.println("\n");
            printWriter.println(html);
            printWriter.flush();
        } //si corresponde a la página del Quijote
        else if (peticion.equals("/quijote")) {
            html = Paginas.html_quijote;
            printWriter.println(Mensajes.lineaInicial_OK);
            printWriter.println(Paginas.fecha);
            printWriter.println(Paginas.primeraCabecera);
            printWriter.println("Content-Length: " + html.length());
            printWriter.println("\n");
            printWriter.println(html);
            printWriter.flush();
        }
//        else if (peticion.equals("/directorio/" + pagina)) {
//            if(pagina.equals("pagina1"))
//              html = Paginas.html_pagina1;
//            else
//              html = Paginas.html_pagina2;  
//            printWriter.println(Mensajes.lineaInicial_OK);
//            printWriter.println(Paginas.fecha);
//            printWriter.println(Paginas.primeraCabecera);
//            printWriter.println("Content-Length: " + html.length());
//            printWriter.println("\n");
//            printWriter.println(html);
//            printWriter.flush();
//        } 
        
        else if (peticion.startsWith("/directorio/")) {
            if(peticion.endsWith("pagina2"))
              html = Paginas.html_pagina2;
            else
              html = Paginas.html_pagina1;  
            printWriter.println(Mensajes.lineaInicial_OK);
            printWriter.println(Paginas.fecha);
            printWriter.println(Paginas.primeraCabecera);
            printWriter.println("Content-Length: " + html.length());
            printWriter.println("\n");
            printWriter.println(html);
            printWriter.flush();
        } 
        else {
            html = Paginas.html_noEncontrado;
            printWriter.println(Mensajes.lineaInicial_NotFound);
            printWriter.println(Paginas.fecha); 
            printWriter.println(Paginas.primeraCabecera);
            printWriter.println("Content-Length: " + html.length());
            printWriter.println("\n");
            printWriter.println(html);
            printWriter.flush();
        }
    
    }
  }

}
