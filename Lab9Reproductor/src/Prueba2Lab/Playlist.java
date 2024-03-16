package Prueba2Lab;

public class Playlist {

    Canciones first;
    Canciones last;
    int tam;

    public Playlist() {
        first = last = null;
        tam = 0;
    }

    public boolean IsEmpty() {
        return first == null;
    }

    public void clear() {
        while (!IsEmpty()) {
            borrar(first);
        }
    }

    public void insertar(String nom, String dir) {
        Canciones nuevo = new Canciones(nom, dir);
        if (IsEmpty()) {
            first = nuevo;
            last = nuevo;
        } else {
            nuevo.anterior = last;
            last.siguiente = nuevo;
            last = nuevo;
        }
        tam++;
    }

    public int index(Canciones b) {
        Canciones aux = first;
        int con = 0;

        while (aux != null) {
            if (aux == b) {
                return con;
            }
            aux = aux.siguiente;
            con++;
        }
        return -1;
    }
    
    public Canciones get_cancion(int index){
        if (index < 0 || index >= tam) {
            return null;
        }
        
        int n = 0;
        Canciones aux = first;
        while (n != index) {            
            aux = aux.siguiente;
            n++;
        }
        
        return aux;
    }

    public void borrar(Canciones b) {
        if (b == first) {
            if (tam == 1) {
                first = null;
                tam--;
                return;
            }
            first.siguiente.anterior = null;
            first = first.siguiente;
            tam--;
            return;
        }
        tam--;
        if (b == last) {
            last.anterior.siguiente = null;
            last = last.anterior;
            return;
        }
        b.siguiente.anterior = b.anterior;
        b.siguiente.anterior.siguiente = b.siguiente;
    }
    
    public boolean buscar(String nombre, String ruta){
        Canciones aux = first;

        while (aux != null) {
            if (aux.nombre.equals(nombre) && aux.direccion.equals(ruta)) {
                return true;
            }
            aux = aux.siguiente;
        }
        return false;
    }
    
    public Canciones siguienteCancion(Canciones actual) {
        if (actual != null && actual.siguiente != null) {
            return actual.siguiente;
        }
        return null;
    }
}
