import java.io.*;
import java.net.*;

public class SHola{

    public static void main(String[] args){

        try{
            // Instancia que espera solicitudes de conexion. Se indica el puerto a ocupar
            ServerSocket s = new ServerSocket(1234); 
            System.out.println("Esperando cliente...");

            for(;;){
                Socket cl = s.accept(); // Se queda en espera de una conexion 
                System.out.println("Conexion establecida desde " + cl.getInetAddress() + ":" + cl.getPort());

                // Puentes de entrada y salida con el cliente
                OutputStreamWriter salida = new OutputStreamWriter(cl.getOutputStream()); // Enviar datos
                InputStreamReader entrada = new InputStreamReader(cl.getInputStream()); // Recibir datos

                // Se envian datos al cliente
                String mensaje = "Jaime Villanueva Héctor Israel"; // Mensaje que queremos enviar
                PrintWriter pw = new PrintWriter(salida); // Flujo de salida de texto
                pw.println(mensaje); // Enviamos el mensaje al cliente
                pw.flush(); // Limpia la corriente

                // Se reciben datos del cliente
                BufferedReader br = new BufferedReader(entrada);
                String msg = br.readLine();
                System.out.println("Recibimos un mensaje desde cliente.");
                System.out.println("Mensaje:" + msg);

                br.close(); // Cierra el buffer
                pw.close(); // Cierra el printwriter
                cl.close(); // SE cierra el socket
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}