package JuegoTexto;

import vistaTextualCivitas.VistaTextual;
import controladorCivitas.Controlador;
import civitas.CivitasJuego;
import java.util.ArrayList;

//Civitas Práctica 1
// AVISO: Esta versión de Civitas es de prueba, el main es completamente exprimental y a lo largo 
//        de todo el código podemos encontrarnos con números mágicos, será depurada en futuras versiones.


import java.util.ArrayList;

/**
 *
 * @author albertoplaza
 */
public class JuegoTexto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
/*
        //Ej 1
        int jugadores = 4;
        int[] EmpiezaTirdas = new int[jugadores];
        int emp;
        Dado Dado = new Dado();
        for (int i = 0; i < 100; i++) {
            emp = Dado.quienEmpieza(jugadores);
            EmpiezaTirdas[emp]++;
        }

        for (int i = 0; i < jugadores; i++) {
            System.out.println("El jugador " + i + " ha salido primero "
                    + EmpiezaTirdas[i] + " veces.");
        }

        //Salen aproximadamente las mismas veces todos los resultados.
        //Ej 2
        for (int i = 2; i < 12; i++) {
            if (i % 2 == 0) // Dado.setDebug(true);
            //  else
            //Dado.setDebug(false);
            {
                System.out.println(Dado.tirar());
            }

        }
        Dado.setDebug(false);
        //Ej 3
        System.out.println("Última tirada del dado: " + Dado.tirar());
        System.out.println("La información que deuvuelve ahora mismo getUltimoResultado() es : " + Dado.getUltimoResultado());

        //Ej 4:
        System.out.println(EstadoJuego.INICIO_TURNO);
        System.out.println(TipoSorpresa.PAGARCOBRAR);
        System.out.println(TipoCasilla.CALLE);

        // Ej 5
        Casilla cas1 = new Casilla(TipoCasilla.CALLE, "Lazaro", 100, 200, 50);
        Casilla cas2 = new Casilla(TipoCasilla.CALLE, "Pilar", 200, 300, 100);
        Casilla cas3 = new Casilla(TipoCasilla.CALLE, "Bodega", 300, 400, 150);

        Tablero tablero = new Tablero();
        tablero.aniadeCasilla(cas1);
        tablero.aniadeCasilla(cas2);
        tablero.aniadeCasilla(cas3);

        System.out.println(tablero.getCasilla(1).toString());
        System.out.println(tablero.getCasilla(2).toString());
        System.out.println(tablero.getCasilla(3).toString());

        //Ej 6
        float pMasCara = 0;
        int indiceCara = 0;
        float pMasBarata = 0;
        int indiceBarata = 0;
        float media = 0;
        for (int i = 1; i <= 3; i++) {
            if (pMasCara < tablero.getCasilla(i).getPrecioCompra()) {
                pMasCara = tablero.getCasilla(i).getPrecioCompra();
                indiceCara = i;
            }
            if (pMasBarata > tablero.getCasilla(i).getPrecioCompra() || pMasBarata == 0) {
                pMasBarata = tablero.getCasilla(i).getPrecioCompra();
                indiceBarata = i;
            }
            media += tablero.getCasilla(i).getPrecioCompra();
        }

        media = media / 3;

        System.out.println("La casa más cara es: " + tablero.getCasilla(indiceCara).getNombre()
                + "\nLa casa más barata es: " + tablero.getCasilla(indiceBarata).getNombre()
                + "\nLa media es: " + media);

        //Ej 7      Para este ej recomiendo comentar las lineas 40, 41 y 42 para verlo bien
        Diario.getInstance().ocurreEvento("Event");
        Diario.getInstance().ocurreEvento("Event2");
        System.out.println(Diario.getInstance().leerEvento());
        System.out.println(Diario.getInstance().leerEvento());
        System.out.println(Diario.getInstance().leerEvento());

        //Ej 8
        Dado.tirar();
        System.out.println("Ha salido: " + Dado.getUltimoResultado());
        System.out.println("Casilla: " + tablero.nuevaPosicion(0, Dado.getUltimoResultado()));
        
*/
        
        
        
        //-----------------------------------------------------------------------------
        
        
        System.out.println("\n");
        
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Alberto");
        nombres.add("Jaime");
        nombres.add("kitano");
        nombres.add("Isma");
              
        CivitasJuego juego = new CivitasJuego(nombres, false);
        VistaTextual juegoTextual = new VistaTextual(juego);
        Controlador controlador = new Controlador(juego, juegoTextual);
        controlador.juega();
    }

}
