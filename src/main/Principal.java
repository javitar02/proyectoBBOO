package main;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.db4o.query.QueryComparator;
import empleados.Empleado;
import empleados.Nomina;
import restaurantes.Categoria;
import restaurantes.Producto;
import restaurantes.Restaurante;

import java.util.*;

public class Principal {
    private static final String BD_RESTAURANTE = "bd_txt/restaurante.oo";
    private static final Scanner teclado = new Scanner(System.in);

    //MAIN: EN ÉL SE ABRE LA BDAT, SE CIERRA Y SE SOLICITAN LAS OPCIONES QUE SERÁN TRATADAS
    public static void main(String[] args) {
        int opc;
        ObjectContainer db;

        db = abrirBd();
        do {
            opc = solicitarOpcion();
            tratarOpcion(opc, db);
        } while (opc != 9);

        db.close();
    }

    //MÉTODO ABRIR BD: SE CONFIGURA LA BD PARA QUE SE ACTUALICEN LOS CAMBIOS REALIZADOS
    //MEDIANTE EL CASCADE ON UPDATE QUE SE PONE A VERDADERO
    private static ObjectContainer abrirBd() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Restaurante.class).cascadeOnUpdate(true);
        config.common().objectClass(Empleado.class).cascadeOnUpdate(true);

        return Db4oEmbedded.openFile(config, BD_RESTAURANTE);
    }

    //MÉTODO SOLICITAR OPCIÓN: PIDE AL USUARIO UNA OPCIÓN ENTRE 1 Y 9
    //MEDIANTE UN BUCLE SE CONTROLA QUE EL MENÚ NO SE INTERRUMPA HASTA QUE EL USUARIO PULSE 9
    private static int solicitarOpcion() {
        int opc;

        System.out.println("\n1.Insertar bar de ejemplo");
        System.out.println("2.Insertar restaurante");
        System.out.println("3.Registrar producto");
        System.out.println("4.Asociar empleado a restaurante");
        System.out.println("5.Busqueda productos por nombre en orden alfabetico");
        System.out.println("6.Busqueda restaurante por nombre");
        System.out.println("7.Busqueda de empleado por cualquier parte del nombre ordenado por salario");
        System.out.println("8.Busqueda empleados por rango salario que pertenecen al mismo restaurante");
        System.out.println("9.Salir");
        do {
            System.out.println("\nIntroduce una opcion:");
            opc = Integer.parseInt(teclado.nextLine());
        } while (opc < 1 || opc > 9);
        return opc;
    }

    //MÉTODO TRATAR OPCIÓN: MEDIANTE UN SWITCH SE CONTROLA LA OPCIÓN DEL USUARIO
    //SE LLAMA AL MÉTODO QUE CORRESPONDA
    private static void tratarOpcion(int opc, ObjectContainer db) {
        switch (opc) {
            case 1 -> insertarBarEjemplo(db);
            case 2 -> insertarRestaurante(db);
            case 3 -> insertaProducto(db);
            case 4 -> asociarEmpleadoARestaurante(db);
            case 5 -> busquedaProductosPorOrdenAlfabetico(db);
            case 6 -> busquedaRestaurantePorNombre(db);
            case 7 -> busquedaEmpleadoPorNombre(db);
            case 8 -> busquedaEmpleadoPertenecientesAlMismoBarOrdenadosPorSalario(db);
        }
    }

    //MÉTODO SOLICITAR CADENA: PIDE AL USUARIO UNA CADENA POR TECLADO Y LA DEVUELVE
    private static String solicitarCadena(String msg) {
        System.out.println(msg);

        return teclado.nextLine();
    }

    //MÉTODO SOLICITAR CANTIDAD DOBLE: PIDE AL USUARIO UN NÚMERO TIPO DOUBLE POR TECLADO Y LO DEVUELVE
    private static double solicitarCantidadDoble(String msg) {
        double cantidad;

        System.out.println(msg);
        cantidad = Double.parseDouble(teclado.nextLine());

        return cantidad;
    }

    //MÉTODO SOLICITAR CANTIDAD ENTERA: PIDE AL USUARIO UN NÚMERO TIPO ENTERO POR TECLADO Y LO DEVUELVE
    private static int solicitarCantidadEntera(String msg) {
        int cantidad;

        System.out.println(msg);
        cantidad = Integer.parseInt(teclado.nextLine());

        return cantidad;
    }

    //MÉTODO SOLICITAR CATEGORÍA: PIDE AL USUARIO UNA CATEGORÍA Y LA DEVUELVE
    //MEDIANTE UN BUCLE SE CONTROLA QUE EL USUARIO SIEMPRE INTRODUZCA UNA CATEGORÍA DISPONIBLE
    //MEDIANTE UN SWITCH SE INTERPRETA LA CATEGORÍA DEL PRODUCTO (OP1-BEBIDA, OP2-COMIDA, OP3-POSTRE)
    private static Categoria solicitarCategoria() {
        int opt;
        Categoria c = null;

        do {
            System.out.println("Introduce la categoria (1-BEBIDA 2-COMIDA 3-POSTRE)");
            opt = Integer.parseInt(teclado.nextLine());
        } while (opt < 1 || opt > 3);

        switch (opt) {
            case 1 -> c = new Categoria("BEBIDA");
            case 2 -> c = new Categoria("COMIDA");
            case 3 -> c = new Categoria("POSTRE");
        }

        return c;
    }

    //MÉTODO INSERTA BAR EJEMPLO: SE CREAN E INSTANCIAN LOS OBJETOS NECESARIOS PARA INSERTAR EL BAR DE EJEMPLO
    //CREAMOS UN OBJETO RESTAURANTE, UN EMPLEADO Y VARIOS PRODUCTOS
    //AL AÑADIR AMBOS AL RESTAURANTE, SE VAN SUMANDO A UN ARRAY QUE CONTIENE LA CLASE RESTAURANTE
    //UNA VEZ ESTÉ CREADO EL RESTAURANTE, SE HACE EL STORE PARA QUE SE GUARDE EN LA BD
    private static void insertarBarEjemplo(ObjectContainer db) {
        Restaurante restaurante = new Restaurante("RestAccdat");
        ObjectSet<Restaurante> os = db.queryByExample(restaurante);

        if (os.size() == 0) {
            Nomina nomina = new Nomina("ASDAS12321", 1200);
            Empleado yo = new Empleado("Javi", "Torrejon", "Aragon", "12345678A", nomina);
            restaurante.annadirEmpleado(yo);

            Categoria catAgua = new Categoria("BEBIDA");
            Producto p1 = new Producto("Agua", catAgua, 1);
            restaurante.annadirProducto(p1);

            Categoria catEnsalada = new Categoria("COMIDA");
            Producto p2 = new Producto("Ensalada", catEnsalada, 4.50);
            restaurante.annadirProducto(p2);

            Categoria catPastelVegano = new Categoria("POSTRE");
            Producto p3 = new Producto("Pastel Vegano", catPastelVegano, 2.75);
            restaurante.annadirProducto(p3);

            db.store(restaurante);
        } else {
            System.out.println("Ya has insertado el restaurante RestAccdat");
        }
    }

    //MÉTODO INSERTAR RESTAURANTE: SOLICITA EL NOMBRE E INSERTA EL RESTAURANTE CON SUS EMPLEADOS
    //SE CONTROLA QUE EL RESTAURANTE NO EXISTA, EN CUYO CASO SE MOSTRARÁ MENSAJE DE ERROR
    //MEDIANTE UN BUCLE INTERACTIVO, SE CONTROLA QUE EL USUARIO INTRODUZCA EMPLEADOS HASTA QUE QUIERA
    private static void insertarRestaurante(ObjectContainer db) {
        String nombre = solicitarCadena("Introduce el nombre del restaurante: ");
        int opcion = 0;

        Restaurante patron = new Restaurante(nombre);
        ObjectSet<Restaurante> os = db.queryByExample(patron);
        Empleado emp = null;

        if (os.size() == 0) {
            while (opcion != 2) {
                opcion = solicitarCantidadEntera("¿Desea introducir un empleado? (1-SI : 2-NO): ");

                if (opcion == 1) {
                    insertaEmpleado(db, patron);
                }
            }
            db.store(patron);
            System.out.println("Pase un buen dia");
        } else {
            System.out.println("Lo sentimos, el restaurante ya existe");
        }
        System.out.println();
    }

    //MÉTODO INSERTAR EMPLEADO: SOLICITA LOS DATOS E INSERTA EL EMPLEADO
    //SE CONTROLA QUE EL EMPLEADO NO EXISTA CON EL DNI, EN CUYO CASO SE MOSTRARÁ MENSAJE DE ERROR
    //UNA VEZ ESTÉ CREADO EL OBJETO EMPLEADO CON SUS PARÁMETROS, SE HACE STORE PARA GUARDAR LOS CAMBIOS EN LA BD
    //ADEMÁS SE AÑADE AL ARRAY DE EMPLEADOS CORRESPONDIENTE
    private static void insertaEmpleado(ObjectContainer db, Restaurante nuevo) {
        String nomEmpleado, ap1, ap2, dni, iban;
        double saldo;
        Empleado aInsertar;

        nomEmpleado = solicitarCadena("Introduce el nombre del empleado: ");
        ap1 = solicitarCadena("Introduce el apellido 1 del empleado: ");
        ap2 = solicitarCadena("Introduce el apellido 2 del empleado: ");
        dni = solicitarCadena("Introduce el dni del empleado: ");
        iban = solicitarCadena("Introduce el iban de la cuenta: ");
        saldo = solicitarCantidadDoble("Introduce el saldo: ");

        Nomina nomina = new Nomina(iban, saldo);
        Empleado patron = new Empleado(null, null, null, dni, null);


        ObjectSet<Empleado> os = db.queryByExample(patron);
        if (os.size() == 0) {
            aInsertar = new Empleado(nomEmpleado, ap1, ap2, dni, nomina);
            nuevo.annadirEmpleado(aInsertar);
            db.store(aInsertar);
        } else {
            System.out.println("\nLo sentimos, el empleado ya existe");
        }

    }

    //MÉTODO INSERTAR PRODUCTO: SOLICITA LOS DATOS E INSERTA EL PRODUCTO
    //SE CONTROLA QUE EL PRODUCTO NO EXISTA, EN CUYO CASO SE MOSTRARÁ MENSAJE DE ERROR
    //ADEMÁS SE VERIFICA QUE NO EXISTA 2 VECES EL MISMO EN UN RESTAURANTE
    //UNA VEZ ESTÉ CREADO EL OBJETO PRODUCTO CON SUS PARÁMETROS, SE HACE STORE PARA GUARDAR LOS CAMBIOS EN LA BD
    //ADEMÁS, SE AÑADE AL ARRAY DE PRODUCTOS CORRESPONDIENTE
    private static void insertaProducto(ObjectContainer db) {
        String nomRestaurante, nomProducto;
        double precio;
        Categoria c = null;
        boolean existeProducto = false;
        ArrayList<Producto> listaProd = new ArrayList<>();

        nomRestaurante = solicitarCadena("Introduce el nombre del restaurante: ");

        Restaurante patron = new Restaurante(nomRestaurante);
        ObjectSet<Restaurante> osRestaurante = db.queryByExample(patron);

        Restaurante aux;
        Producto nuevoProducto;

        if (osRestaurante.size() == 0) {
            System.out.println("\nError, el restaurante no existe");
        } else {
            aux = osRestaurante.next();
            nomProducto = solicitarCadena("Introduce el nombre del producto: ");
            precio = solicitarCantidadDoble("Introduce el precio del producto: ");
            c = solicitarCategoria();

            nuevoProducto = new Producto(nomProducto, c, precio);
            listaProd = aux.getProductos();

            for (Producto p : listaProd) {
                if (p.getNombre().equals(nomProducto)) {
                    existeProducto = true;
                }
            }

            if (existeProducto) {
                System.out.println("El producto no se puede insertar 2 veces");
            } else {
                aux.annadirProducto(nuevoProducto);
                db.store(aux);
            }

        }
    }

    //MÉTODO ASOCIAR EMPLEADO A RESTAURANTE: SOLICITA LOS DATOS E INSERTA EL EMPLEADO
    //MEDIANTE UN BUCLE Y UNA VARIABLE BOOLEANA, SE PIDE AL USUARIO UN NOMBRE DE UN RESTAURANTE HASTA QUE INSERTE UNO QUE EXISTA
    //SE CONTROLA QUE EL EMPLEADO NO ESTÉ TRABAJANDO EN OTRO RESTAURANTE CON EL DNI, EN CUYO CASO SE MOSTRARÁ MENSAJE DE ERROR
    //UNA VEZ ESTÉ CREADO EL OBJETO EMPLEADO CON SUS PARÁMETROS, SE HACE STORE PARA GUARDAR LOS CAMBIOS EN LA BD
    //POR ÚLTIMO SE ASOCIA AL RESTAURANTE AÑADIÉNDOLO AL ARRAY DE EMPLEADOS CORRESPONDIENTE
    private static void asociarEmpleadoARestaurante(ObjectContainer db) {
        String nomRestaurante, dni;
        boolean restauranteEncontrado = false;
        Restaurante auxiliar = null;

        while (!restauranteEncontrado) {
            nomRestaurante = solicitarCadena("Introduce el nombre del restaurante: ");
            Restaurante patron = new Restaurante(nomRestaurante);
            ObjectSet<Restaurante> osRestaurante = db.queryByExample(patron);

            if (osRestaurante.size() == 0) {
                System.out.println("Error, el restaurante no existe");
            } else {
                restauranteEncontrado = true;
                auxiliar = osRestaurante.next();
            }
        }

        dni = solicitarCadena("Introduce el DNI del empleado: ");
        Empleado patron = new Empleado(null, null, null, dni, null);
        ObjectSet<Empleado> osEmpleado = db.queryByExample(patron);

        if (osEmpleado.size() == 0) {
            insertaEmpleado(db, auxiliar);
        } else {
            System.out.println("Error, el empleado ya esta trabajando en otro restaurante");
        }
    }

    //MÉTODO BÚSQUEDA DE PRODUCTOS: SOLICITA EL NOMBRE DEL RESTAURANTE Y LO BUSCA
    //SE MOSTRARÁ MENSAJE DE ERROR SI NO EXISTE
    //EN CASO DE QUE EXISTA, SE SOLICITA LA CADENA POR LA QUE SE VAN A FILTRAR LOS PRODUCTOS
    //SE OBTIENE UN ARRAY CON LOS PRODUCTOS QUE LA CONTENGAN, SI SU TAMAÑO ES 0 SE MUESTRA MENSAJE DE ERROR
    //SI NO, SE ORDENA EL ARRAY CON EL SORT (LA CLASE PRODUCTO IMPLEMENTA COMPARABLE, Y EN ELLA HAY UN MÉTODO QUE COMPARA LOS NOMBRES ALFABÉTICAMENTE)
    //UNA VEZ ORDENADO SE MUESTRA EL ARRAY
    private static void busquedaProductosPorOrdenAlfabetico(ObjectContainer db) {
        String nomRestaurante = solicitarCadena("Introduce el nombre del restaurante: ");
        Restaurante patron = new Restaurante(nomRestaurante);
        Restaurante restaurante;
        ArrayList<Producto> listProductos = new ArrayList<>();

        ObjectSet<Restaurante> os = db.queryByExample(patron);
        if (os.size() == 0) {
            System.out.println("Error, el restaurante no existe");
        } else {
            restaurante = os.next();
            String cadena = solicitarCadena("Introduce la cadena a buscar en el nombre del producto: ");
            listProductos = restaurante.getProductos();

            if (listProductos.isEmpty()) {
                System.out.println("No hay productos en el restaurante " + nomRestaurante + " que contengan la cadena " + cadena);
            } else {
                Collections.sort(listProductos);
                System.out.println(listProductos);
            }

        }
    }

    //MÉTODO BÚSQUEDA DE RESTAURANTE: SOLICITA EL NOMBRE DEL RESTAURANTE Y LO BUSCA
    //SE MOSTRARÁ MENSAJE DE ERROR SI NO EXISTE
    //EN CASO DE QUE EXISTA, SE USA EL QUERY CON UN NUEVO PREDICADO QUE CONTIENE EL FILTRO QUE SE DESEA (QUE EXISTA EL RESTAURANTE)
    //DESPUÉS SE USA EL QUERY COMPARATOR CON EL NÚM DE EMPLEADOS, QUE SE OBTIENE CON EL TAMAÑO DEL ARRAY DE EMPLEADOS DE CADA RESTAURANTE
    //POR ÚLTIMO, SE ORDENAN LOS RESTAURANTES POR NÚM DE EMPLEADOS
    //SI EL ARRAY DE EMPLEADOS ESTA VACÍO SE MUESTRA MENSAJE DE ERROR, SINO SE MUESTRAN LOS DATOS
    private static void busquedaRestaurantePorNombre(ObjectContainer db) {
        String cadena = solicitarCadena("Introduce la cadena que va a contener el nombre del restaurante: ");
        ObjectSet<Restaurante> osRestaurante = db.query(new Predicate<>() {
                                                            @Override
                                                            public boolean match(Restaurante restaurante) {
                                                                return restaurante.getNombre().contains(cadena);
                                                            }
                                                        }, new QueryComparator<Restaurante>() {

                                                            @Override
                                                            public int compare(Restaurante rest1, Restaurante rest2) {
                                                                return -1 * (Integer.compare(rest1.getEmpleados().size(), rest2.getEmpleados().size()));
                                                            }

                                                        }

        );
        if (osRestaurante.next() == null) {
            System.out.println("\nNo hay registros para enseñar ");
        } else {
            for (Restaurante r : osRestaurante) {
                System.out.println(r);
            }
        }

    }

    //MÉTODO BÚSQUEDA DE EMPLEADO: SOLICITA EL NOMBRE DEL EMPLEADO Y LO BUSCA
    //SE MOSTRARÁ MENSAJE DE ERROR SI NO EXISTE
    //EN CASO DE QUE EXISTA, SE USA EL QUERY CON UN NUEVO PREDICADO QUE CONTIENE EL FILTRO QUE SE DESEA (QUE LA CADENA ESTÉ CONTENIDA EN EL NOMBRE)
    //DESPUÉS SE USA EL QUERY COMPARATOR CON EL SUELDO DE LOS EMPLEADOS, QUE SE OBTIENE ACCEDIENDO AL OBJETO NÓMINA Y DENTRO DE ELLA AL GETSUELDO
    //POR ÚLTIMO, SE ORDENAN LOS EMPLEADOS POR SU SALARIO
    //SI EL ARRAY DE EMPLEADOS ESTA VACÍO SE MUESTRA MENSAJE DE ERROR, SINO SE MUESTRAN LOS DATOS
    private static void busquedaEmpleadoPorNombre(ObjectContainer db) {
        String cadenaNombre = solicitarCadena("Introduce la cadena a buscar en el nombre completo del empleado: ");
        ObjectSet<Empleado> osEmpleado = db.query(new Predicate<>() {
                                                      @Override
                                                      public boolean match(Empleado emp) {
                                                          return emp.getNombre().contains(cadenaNombre);
                                                      }
                                                  }, new QueryComparator<Empleado>() {

                                                      @Override
                                                      public int compare(Empleado e1, Empleado e2) {
                                                          return -1 * (Double.compare(e1.getNomina().getSueldo(), e2.getNomina().getSueldo()));
                                                      }

                                                  }
        );

        if (osEmpleado.next() == null) {
            System.out.println("\nNo hay registros para enseñar ");
        } else {
            for (Empleado e : osEmpleado) {
                System.out.println(e);
            }
        }
    }

    //MÉTODO BÚSQUEDA DE EMPLEADO ORDENADOS POR SALARIO EN EL MISMO RESTAURANTE: SOLICITA EL NOMBRE DEL RESTAURANTE Y LO BUSCA
    //SE MOSTRARÁ MENSAJE DE ERROR SI NO EXISTE
    //EN CASO DE QUE EXISTA, SE SOLICITAN EL RANGO MÁX Y MÍN DE SALARIO
    //SE USA EL QUERY CON UN NUEVO PREDICADO QUE CONTIENE EL FILTRO QUE SE DESEA (QUE LOS EMPLEADOS PERTENEZCAN AL MISMO RESTAURANTE Y
    //QUE SE CUMPLAN LOS RANGOS DEL SALARIO)
    //DESPUÉS SE USA EL QUERY COMPARATOR CON EL SUELDO DE LOS EMPLEADOS, QUE SE OBTIENE ACCEDIENDO AL OBJETO NÓMINA Y DENTRO DE ELLA AL GETSUELDO
    //POR ÚLTIMO, SE ORDENAN LOS EMPLEADOS POR SU SALARIO
    //SI EL ARRAY DE EMPLEADOS ESTA VACÍO SE MUESTRA MENSAJE DE ERROR, SINO SE MUESTRAN LOS DATOS
    private static void busquedaEmpleadoPertenecientesAlMismoBarOrdenadosPorSalario(ObjectContainer db) {
        String nomRestaurante = solicitarCadena("Nombre restaurante: ");

        ObjectSet<Restaurante> restaurantes = db.queryByExample(new Restaurante(nomRestaurante));
        Restaurante restaurante = restaurantes.next();

        double salarioMax = solicitarCantidadDoble("Rango max: ");
        double salarioMin = solicitarCantidadDoble("Rango min: ");

        ObjectSet<Empleado> osEmpleados = null;
        if (restaurante != null) {
            osEmpleados = db.query(new Predicate<>() {
                @Override
                public boolean match(Empleado empleado) {
                    return restaurante.getEmpleados().contains(empleado) && empleado.getNomina().getSueldo() >= salarioMin && empleado.getNomina().getSueldo() <= salarioMax;
                }
            }, (QueryComparator<Empleado>) (emp1, emp2) -> (-1) * Double.compare(emp1.getNomina().getSueldo(), emp2.getNomina().getSueldo()));

            if (osEmpleados == null) {
                System.out.println("No se han encontrado registros");
            } else {
                for (Empleado e : osEmpleados) {
                    System.out.println("Nombre: "+e.getNombre()+" Sueldo: "+e.getNomina().getSueldo()+"€");
                }
            }
        } else {
            System.out.println("Restaurante no encontrado");
        }
    }
}





