// Agregamos bibliotecas, definimos la clase , el main(), iniciamos el bloque try y definimos el socket
import java.net.*;
import java.io.*;

public class ServidorArchivo{
    
    public static void main(String[] args){
        
        try{
            ServerSocket s = new ServerSocket(7000);

            // Iniciamos el ciclo infinito y esperamos una conexión
            for(;;){
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde" + cl.getInetAddress() + ":"+ cl.getPort());

                // Definimos un flujo de nivel de bits de entrada ligado al socket
                DataInputStream entrada = new DataInputStream(cl.getInputStream());

                // Leemos el número de archivos que nos va a enviar el cliente
                int numArchivos = entrada.readInt();

                // Número de veces que vamos a ejecutar la función recibirArchivo
                for(int i=0; i<numArchivos; i++)
                    recibirArchivo(entrada);

                // Cerramos los flujos, el socket y el resto del programa
                entrada.close();
                cl.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static void recibirArchivo(DataInputStream entrada) throws Exception{
        
        // Leemos los datos principales del archivo y creamos un flujo para escribir el archivo de salida
        byte[] b = new byte[1024];
        String nombre = entrada.readUTF();
        System.out.println("Recibimos el archivo: " + nombre);
        long tam = entrada.readLong();
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));

        // Preparamos los datos para recibir los paquetes de datos del archivo
        long recibidos = 0;
        int n, porcentaje;

        // Definimos el ciclo donde estaremos recibiendo los datos enviados por el cliente
        while(recibidos < tam){
            n = entrada.read(b);
            dos.write(b,0,n);
            dos.flush();
            recibidos = recibidos + n;
            porcentaje = (int)(recibidos*100/tam);
            System.out.print("\n\nArchivo recibido.");
        }

        dos.close();
    }
}