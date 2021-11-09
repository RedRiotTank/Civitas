package JuegoTexto;

import vistaTextualCivitas.VistaTextual;
import controladorCivitas.Controlador;
import civitas.CivitasJuego;
import java.util.ArrayList;

/**
 *
 * @author albertoplaza
 */
public class JuegoTexto {

    public static void main(String[] args) {
        
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Alberto");
        nombres.add("Jaime");
        nombres.add("kitano");
        nombres.add("Isma");
              
        CivitasJuego juego = new CivitasJuego(nombres, false);
        VistaTextual juegoTextual = new VistaTextual(juego);
        Controlador controlador = new Controlador(juego, juegoTextual);
        
        System.out.println("<============ CIVITAS ============> \nComienza el juego:");
        
        controlador.juega();
    }

}
