package restaurantes;

import empleados.Empleado;

import java.util.ArrayList;
import java.util.Objects;

public class Restaurante{
    private ArrayList <Empleado> empleados;
    private ArrayList <Producto> productos;
    private String nombre;

    public Restaurante(){
        productos = new ArrayList<Producto>();
        empleados = new ArrayList<Empleado>();
    }

    public Restaurante(ArrayList <Producto> productos) {
        productos = new ArrayList<Producto>();
        empleados = new ArrayList<Empleado>();
    }

    public Restaurante(String nombre) {
        this.nombre = nombre;
        empleados = new ArrayList<Empleado>();
        productos = new ArrayList<Producto>();
    }



    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void annadirEmpleado(Empleado e){
        empleados.add(e);
    }

    public void annadirProducto(Producto p){
        productos.add(p);
    }

    @Override
    public String toString() {
        return "Restaurante " + nombre + " "
                + empleados + ", productos=" + productos + " numero Empleados: "+this.getEmpleados().size();
    }

}
