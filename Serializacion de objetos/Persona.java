import java.io.Serializable;

public class Persona implements Serializable {

    private String nombre;
    private int edad;
    private double sueldo;
    private transient boolean isActivo;
    private transient char letra;
    private transient short porcentajeGanasDeVivir;

    public Persona(String nombre, int edad, double sueldo, boolean isActivo, char letra, short porcentajeGanasDeVivir) {
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
