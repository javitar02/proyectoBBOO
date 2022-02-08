package empleados;

import java.util.Objects;

public class Empleado {
    private String nombre,ap1,ap2,dni;
    private Nomina nomina;

    public Empleado(String nombre) {
        this.nombre = nombre;
    }

    public Empleado(String nombre, String ap1, String ap2, String dni, Nomina nomina) {
        this.nombre = nombre;
        this.ap1 = ap1;
        this.ap2 = ap2;
        this.dni = dni;
        this.nomina = nomina;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp1() {
        return ap1;
    }

    public void setAp1(String ap1) {
        this.ap1 = ap1;
    }

    public String getAp2() {
        return ap2;
    }

    public void setAp2(String ap2) {
        this.ap2 = ap2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Nomina getNomina() {
        return nomina;
    }

    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(nombre, empleado.nombre) && Objects.equals(ap1, empleado.ap1) && Objects.equals(ap2, empleado.ap2) && Objects.equals(dni, empleado.dni) && Objects.equals(nomina, empleado.nomina);
    }

    @Override
    public String toString() {
        return "Empleado->" +
                " nombre='" + nombre + " " + ap1 + " " + ap2 + '\'' +
                ", dni='" + dni + '\'' +
                ", nomina=" + nomina;
    }
}

