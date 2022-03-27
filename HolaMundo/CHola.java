import java.io.*;
import java.net.*;

public class CHola {
    
    public static void main(String[] args){
        
        try{
            // Flujo de caracteres
            /**
             * br1 -> es el objeto en donde se almacenarán los caracteres leidos desde el teclado
             * input -> Convierte los bytes leídos en caracteres Unicode
             * System.in -> Lee bytes del teclado
             */
            InputStreamReader input = new InputStreamReader(System.in);
            BufferedReader br1 = new BufferedReader(input); 
            
            System.out.printf("Escriba la dirección del servidor: ");
            String host = br1.readLine();
            System.out.printf("\nEscriba un puerto: ");
            int pto = Integer.parseInt(br1.readLine());
            Socket cl = new Socket(host, pto); // Se crea un socket con el host y el puerto del servidor

            // Puentes de entrada y salida con el servidor
            OutputStreamWriter salida = new OutputStreamWriter(cl.getOutputStream()); // Enviar datos
            InputStreamReader entrada = new InputStreamReader(cl.getInputStream()); // Recibir datos

            // Se reciben datos del servidor
            BufferedReader br2 = new BufferedReader(entrada); // Lee texto de una secuencia de entrada almacenado en el buffer
            String mensaje = br2.readLine(); // Leemos 
            System.out.println("Recibimos un mensaje desde servidor.");
            System.out.println("Mensaje:" + mensaje);

            // Se envian datos al servidor
            PrintWriter pw = new PrintWriter(salida);
            pw.println(mensaje);
            pw.flush();      

            br1.close(); // Cierra el buffer
            br2.close(); // Cierra el buffer
            cl.close(); // Cierra el socket
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
