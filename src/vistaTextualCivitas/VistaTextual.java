package vistaTextualCivitas;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionJuego;
import controladorCivitas.Respuesta;
import civitas.OperacionInmobiliaria;
import java.util.ArrayList;
import java.util.Scanner;

public class VistaTextual implements Vista {

    private static final String separador = "=====================";

    private Scanner in;
    
    //private int iGestion;
    private int iPropiedad;

    CivitasJuego juegoModel;

    public VistaTextual(CivitasJuego juegoModel) {
        in = new Scanner(System.in);
        this.juegoModel = juegoModel;
        iPropiedad = -1;
    }

    public void pausa() {
        System.out.print("\nPulsa una tecla");
        in.nextLine();
    }

    int leeEntero(int max, String msg1, String msg2) {
        Boolean ok;
        String cadena;
        int numero = -1;
        do {
            System.out.print(msg1);
            cadena = in.nextLine();
            try {
                numero = Integer.parseInt(cadena);
                ok = true;
            } catch (NumberFormatException e) { // No se ha introducido un entero
                System.out.println(msg2);
                ok = false;
            }
            if (ok && (numero < 0 || numero >= max)) {
                System.out.println(msg2);
                ok = false;
            }
        } while (!ok);

        return numero;
    }

    int menu(String titulo, ArrayList<String> lista) {
        String tab = "  ";
        int opcion;
        System.out.println(titulo);
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(tab + i + "-" + lista.get(i));
        }

        opcion = leeEntero(lista.size(),
                "\n" + tab + "Elige una opción: ",
                tab + "Valor erróneo");
        return opcion;
    }

    @Override
    public void actualiza() {
        int posicion;
        
        System.out.println(this.juegoModel.getJugadorActual().toString());
        
        if (this.juegoModel.finalDelJuego()) {

            System.out.println("FIN DEL JUEGO\n------RANKING------");
            for (int i = 0; i < this.juegoModel.getJugadores().size(); i++) {
                posicion = i + 1;
                System.out.println(posicion + "º: " + this.juegoModel.getJugadores().get(i).toString());
            }
        }
    }

    @Override
    public Respuesta comprar() {
        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("NO");
        respuestas.add("SI");

        Respuesta resultado = Respuesta.values()[this.menu("¿Quiere comprar la calle?", respuestas)];

        return resultado;
    }

    @Override
    public OperacionInmobiliaria elegirOperacion() {

        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("CONSTRUIR_CASA");
        respuestas.add("CONSTRUIR_HOTEL");
        respuestas.add("TERMINAR");
        
        OperacionInmobiliaria resultado = OperacionInmobiliaria.values()[this.menu("Seleccione la operación inmobiliaria que desea", respuestas)];

        return resultado;

    }

    @Override
    public int elegirPropiedad() {
        ArrayList<String> propiedades = new ArrayList<>();
        
        for(int i=0; i<this.juegoModel.getNumPropiedadesJugadorActual(); i++)
            propiedades.add("Propiedad " + (i+1));
        
        
        this.iPropiedad = this.menu("¿A que propiedad se refiere?", propiedades);
        
        return this.iPropiedad;
    }

    @Override
    public void mostrarSiguienteOperacion(OperacionJuego operacion) {
        System.out.println("\nLa siguiente operación a realizar es: " + operacion.toString() + "\n");
    }

    @Override
    public void mostrarEventos() {
        while(Diario.getInstance().eventosPendientes()){
            System.out.println(Diario.getInstance().leerEvento());
        }
    }

}
