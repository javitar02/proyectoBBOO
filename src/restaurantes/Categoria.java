//CLASE CATEGORIA
package restaurantes;

//ATRIBUTOS
public class Categoria {
    private String categoria;

    //CONSTRUCTOR
    public Categoria(String categoria) {
        this.categoria = categoria;
    }

    //GETTERS Y SETTERS
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    //TO STRING
    @Override
    public String toString() {
        return categoria;
    }
}
