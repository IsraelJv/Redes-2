import java.net.*;
import java.io.*;
import javax.swing.JFileChooser;

public class Cliente {
    
    static public void main(String[] args){

        Persona p1 = new Persona("Alguien", 24, 100.5, true, 'I', 1); 
        System.out.println(p1);

        crearArchivo(p1);

        crearConexion("localhost", 3000);
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

    static void crearConexion(String host, int puerto){
        try {
            Socket conexion = new Socket(host, puerto);
            DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            
            enviarArchivo(salida);

            salida.close();
            conexion.close();
        }catch(Exception e){
        }
    }

    static void crearArchivo(Persona p){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("archivo.txt"));
            oos.writeObject(p);
            oos.close();
        }catch(Exception e){
        }
    }



    // CODIGO RECICLADO DE TAREA ANTERIOR

    static void enviarArchivo(DataOutputStream salida) throws Exception{

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
