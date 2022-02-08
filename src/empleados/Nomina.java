//CLASE NÓMINA
package empleados;

//ATRIBUTOS
public class Nomina {
    private String iban;
    private double sueldo;

    //CONSTRUCTOR
    public Nomina(String iban, double sueldo) {
        this.iban = iban;
        this.sueldo = sueldo;
    }

    //GETTERS Y SETTERS
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    //TO STRING
    @Override
    public String toString() {
        return "Nomina con iban "+iban+ " y saldo de "+sueldo+"€";
    }
}
