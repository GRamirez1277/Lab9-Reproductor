package Prueba2Lab;

public class Canciones {
    
    String nombre, direccion;
    
    Canciones siguiente;
    Canciones anterior;

    public Canciones(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }
}
