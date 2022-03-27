// Se agregan las bibliotecas pertinentes, se define la clase, la función main() y se inicia el bloque try-catch
import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class ClienteArchivo{
    public static void main(String[] args){
        
        try{
            // Se define un flujo de entrada para obtener los datos del servidor
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Escriba la dirección del servidor: ");
            String host = br.readLine();
            System.out.printf("Escriba el puerto: ");
            int puerto = Integer.parseInt(br.readLine());

            // Preguntamos el número de archivos a enviar
            System.out.printf("Escriba el número de archivos a mandar: ");
            int numArchivos = Integer.parseInt(br.readLine());

            // Socket
            Socket conexion = new Socket(host, puerto);

            // Se define un flujo orientado a bytes (para enviar los datos por el socket)
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());

            // Decirle al servidor cuanto archivos se van a mandar
            salida.writeInt(numArchivos);

            for(int i=0; i<numArchivos; i++)
                enviarArchivos(salida);

            salida.close();
            conexion.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    static void enviarArchivos(DataOutputStream salida) throws Exception{

        // Se utiliza para elegir el archivo a enviar
        JFileChooser jf = new JFileChooser();
        int r = jf.showOpenDialog(null);


        if(r==JFileChooser.APPROVE_OPTION){
            File f = jf.getSelectedFile();  // Manejador
            String archivo = f.getAbsolutePath(); // Dirección
            String nombre = f.getName(); // Nombre
            long tam = f.length();  // Tamaño

            // Se define un flujo orientado a bytes (se usa para leer el archivo)
            DataInputStream dis = new DataInputStream(new FileInputStream(archivo));

            // Enviamos los datos generales del archivo por el socket
            salida.writeUTF(nombre);
            salida.flush();
            salida.writeLong(tam);
            salida.flush();

            // Leemos los datos  contenidos en el archivo en paquetes de 1024 y los enviamos por el socket
            byte[] b = new byte[1024];
            long enviados = 0;
            int porcentaje, n;
            while(enviados < tam){
                n = dis.read(b);
                salida.write(b,0,n);
                salida.flush();
                enviados = enviados+n;
                porcentaje = (int)(enviados*100/tam);
                System.out.print("Enviado: " + porcentaje + "%\r");
            }

            // Cerramos los flujos, el socket, terminamos bloques y cerramos la clase
            System.out.print("\nArchivo enviado");
            //dos.close();
            dis.close();
        }

    }
}