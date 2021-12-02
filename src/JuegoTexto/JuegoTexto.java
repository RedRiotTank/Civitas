package JuegoTexto;

import vistaTextualCivitas.VistaTextual;
import controladorCivitas.Controlador;
import civitas.CivitasJuego;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author albertoplaza
 */
public class JuegoTexto {

    public static void main(String[] args) {
        
        
        Scanner numJug = new Scanner(System.in);
        int numjugadores;
        
        Scanner nomJug = new Scanner(System.in);
        String nombreJugador;
        
        ArrayList<String> nombres = new ArrayList<>();
        
        do{
        System.out.println("Introduzca el número de jugadores (2-4): ");
        numjugadores = numJug.nextInt();
        
        }while(numjugadores > 4 || numjugadores < 2);
        
        for(int i=0; i<numjugadores; i++){
            System.out.print("Introduzca el nombre del jugador número " + (i+1) + ": ");
            nombreJugador = numJug.next();
            nombres.add(nombreJugador);
        }
                  
        CivitasJuego juego = new CivitasJuego(nombres, false);
        VistaTextual juegoTextual = new VistaTextual(juego);
        Controlador controlador = new Controlador(juego, juegoTextual);
        
        System.out.println("<============ CIVITAS ============> \nComienza el juego:");
        
        controlador.juega();
    }

}
