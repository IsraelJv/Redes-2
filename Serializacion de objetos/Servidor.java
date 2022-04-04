import java.net.*;
import java.io.*;

public class Servidor {

    public static void main(String[] args) {

        try {
            ServerSocket s = new ServerSocket(3000);

            Socket cl = s.accept();
            DataInputStream entrada = new DataInputStream(cl.getInputStream());
            recibirArchivo(entrada);
            entrada.close();
            cl.close();

            ObjectInputStream recuperando_archivo = new ObjectInputStream(new FileInputStream("archivo.txt"));
            Persona p2 = (Persona) recuperando_archivo.readObject();
            recuperando_archivo.close();
            System.out.println(p2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Persona implements Serializable {

        private String nombre;
        private int edad;
        private double sueldo;
        private transient boolean isActivo;
        private transient char letra;
        private transient int porcentajeGanasDeVivir;
    
        public Persona(String nombre, int edad, double sueldo, boolean isActivo, char letra, int porcentajeGanasDeVivir) {
            this.nombre = nombre;
            this.edad = edad;
            this.sueldo = sueldo;
            this.isActivo = isActivo;
            this.letra = letra;
            this.porcentajeGanasDeVivir = porcentajeGanasDeVivir;
        }
    
        public String toString() {
            return "Nombre: " + nombre + "\nEdad: " + edad + "\nSueldo: " + sueldo + "\nEsta activo: " + isActivo +
                    "\nLetra favorita: " + letra + "\nPorcentaje de ganas de seguir respirando: " + porcentajeGanasDeVivir + "%";
        }
    }

    // CODIGO RECICLADO DE LA TAREA ANTERIOR
    static void recibirArchivo(DataInputStream entrada) throws Exception {

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
        while (recibidos < tam) {
            n = entrada.read(b);
            dos.write(b, 0, n);
            dos.flush();
            recibidos = recibidos + n;
            porcentaje = (int) (recibidos * 100 / tam);
            System.out.print("\n\nArchivo recibido.");
        }

        dos.close();
    }
}
