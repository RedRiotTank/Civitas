package controladorCivitas;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.GestionInmobiliaria;
import civitas.OperacionJuego;
import vistaTextualCivitas.VistaTextual;
import civitas.OperacionInmobiliaria;

/**
 *
 * @author albertoplaza
 */
public class Controlador {

    private CivitasJuego juego;
    private VistaTextual vista;

    public Controlador(CivitasJuego juego, VistaTextual vista) {
        this.juego = juego;
        this.vista = vista;
    }

    public void juega() {
        boolean FinJuego = false;
        int NumPropiedad = 0;

        while (!FinJuego) {

            vista.actualiza();
            vista.pausa();
            
            OperacionJuego OpJuego = this.juego.siguientePaso();
            
            vista.mostrarSiguienteOperacion(OpJuego);

            if (OpJuego != OperacionJuego.PASAR_TURNO) 
                vista.mostrarEventos();
            else
                System.out.println("=====================\nNUEVO TURNO\n");

            FinJuego = juego.finalDelJuego();

            if (!FinJuego) 

                switch (OpJuego) {
                    case COMPRAR:
                        if (vista.comprar() == Respuesta.SI) 
                            juego.comprar();
                        
                        juego.siguientePasoCompletado(OpJuego);
                        break;

                    case GESTIONAR:
                        OperacionInmobiliaria OpInmobiliaria = vista.elegirOperacion();
                        if (OpInmobiliaria != OperacionInmobiliaria.TERMINAR) 
                            NumPropiedad = vista.elegirPropiedad();
                        
                        GestionInmobiliaria gestion = new GestionInmobiliaria(OpInmobiliaria, NumPropiedad);

                        switch (gestion.getOperacion()) {
                            case TERMINAR:
                                juego.siguientePasoCompletado(OpJuego);
                                break;
                                
                            case CONSTRUIR_CASA:
                                juego.contruirCasa(NumPropiedad);
                                break;    
                                
                            case CONSTRUIR_HOTEL:
                                juego.contruirHotel(NumPropiedad);
                                break;
                        }
                        break;    
                }
           
        }

        vista.actualiza();
 
    }
}
