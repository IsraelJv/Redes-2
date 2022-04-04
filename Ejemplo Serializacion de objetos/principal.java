import java.io.*;

public class principal {
    
    static public void main(String[] args){

        Persona p1 = new Persona("Israel", 24, 100.50, true);
        System.out.println("Datos antes de hacer el txt");
        System.out.println(p1);

        try {
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("prueba.txt"));
            o.writeObject(p1);
            o.close();
        } catch (Exception e) {
        }
        

        try {
            ObjectInputStream i = new ObjectInputStream(new FileInputStream("prueba.txt"));
            Persona p2 = (Persona) i.readObject();
            System.out.println("Datos que se leen del txt");
            System.out.println(p2);
            i.close();
        } catch (Exception e) {
        }
    }

    static class Persona implements Serializable{
        
        private transient String nombre;
        private int edad;
        private double sueldo;
        private transient boolean isActivo;

        public Persona(String nombre, int edad, double sueldo, boolean isActivo){
            this.nombre = nombre;
            this.edad = edad;
            this.sueldo = sueldo;
            this.isActivo = isActivo;
        }

        public String toString(){
            return "Nombre: " + nombre + "\nEdad: " + edad + "\nSueldo: " + sueldo + "\nEsta activo: " + isActivo;
        }
    }
}
