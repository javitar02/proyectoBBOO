//CLASE PRODUCTO
package restaurantes;

import java.util.Objects;

//ATRIBUTOS (IMPLEMENTA INTERFAZ COMPARABLE)
public class Producto implements Comparable<Producto>{
    private String nombre;
    private Categoria categoria;
    private double precio;

    //CONSTRUCTOR 1
    public Producto(String nombre, Categoria categoria, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    //CONSTRUCTOR 2
    public Producto(String nombre) {
        this.nombre = nombre;
    }

    //GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    //TO STRING
    @Override
    public String toString() {
        return "Producto->" +
                " nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", precio=" + precio;
    }

    //COMPARTE TO (AL IMPLEMENTAR COMPARABLE, SE COMPARAN LOS NOMBRES PARA ORDENARLOS ALFABÉTICAMENTE)
    @Override
    public int compareTo(Producto o) {
        return this.nombre.compareTo(o.nombre);
    }
}
