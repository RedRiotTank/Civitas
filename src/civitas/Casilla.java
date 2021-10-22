/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author albertoplaza
 */
public class Casilla {

    static private float FACTORALQUILERCALLE = 1.0f;
    static private float FACTORALQUILERCASA = 1.0f;
    static private float FACTORALQUILERHOTEL = 4.0f;

    private TipoCasilla tipo;
    private String Nombre;
    private float precioCompra, precioEdificar, precioBaseAlquiler;
    private int numCasas, numHoteles;

    private Jugador propietario;
    private MazoSorpresas mazo;
    private Sorpresa sorpresa;

    public int cantidadCasasHoteles() {
        return this.numCasas + this.numHoteles;
    }

    Casilla(TipoCasilla tipo, String nombre) {  //tipo casilla es Descanso
        this.init();
        this.tipo = tipo;   // como lo hago para que se inicialice por defecto???
        this.Nombre = nombre;

    }

    Casilla(TipoCasilla tipo, String titulo, float precioEdificar, float precioCompra, float precioBaseAlquiler) {
        init();
        this.tipo = tipo;
        this.Nombre = titulo;
        this.precioCompra = precioCompra;
        this.precioEdificar = precioEdificar;
        this.precioBaseAlquiler = precioBaseAlquiler;
    }

    Casilla(TipoCasilla tipo, String nombre, MazoSorpresas mazo) {
        this.init();
        this.tipo = tipo;
        this.Nombre = nombre;
        this.mazo = mazo;
    }

    boolean comprar(Jugador jugador) {
        return false;
    }

    String getNombre() {
        return Nombre;
    }

    float getPrecioCompra() {
        return precioCompra;
    }

    float getPrecioEdificar() {
        return precioEdificar;
    }

    void informe(int iactual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("El jugador: " + propietario.getNombre()
                + " ha caido en la casilla: " + this.toString());

    }

    private void init() {
        tipo = null;
        Nombre = null;
        precioCompra = 0;
        precioEdificar = 0;
        precioBaseAlquiler = 0;
        numCasas = 0;
        numHoteles = 0;
        propietario = null;
        mazo = null;
        sorpresa = null;
    }

    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    }

    private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos) {
    }

    private void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos) {
    }

    public boolean tienePropietario() {
        boolean resultado = false;
        if (this.propietario != null) {
            resultado = true;
        }
        return resultado;
    }

    public void tramitarAlquiler(Jugador jugador) {
        if (this.tienePropietario() && this.esEsteElPropietario(jugador)) {
            jugador.pagaAlquiler(this.getPrecioAlquilerCompleto());
        }
        this.propietario.recibe(this.getPrecioAlquilerCompleto());
    }

    int getNumCasas() {
        return numCasas;
    }

    int getNumHoletes() {
        return numHoteles;
    }

    float getPrecioAlquilerCompleto() {
        return precioBaseAlquiler * (FACTORALQUILERCASA + numCasas + FACTORALQUILERHOTEL * numHoteles);
    }

    boolean construirCasa(Jugador jugador) {
        numCasas += 1;
        return true;
    }

    boolean construirHotel(Jugador jugador) {
        numHoteles += 1;
        return true;
    }

    boolean derruirCasas(int numero, Jugador jugador) {
        boolean satisfactorio = false;
        if (this.esEsteElPropietario(jugador) && this.getNumCasas() >= numero) {
            this.numCasas = this.numCasas - numero;
            satisfactorio = true;
        }

        return satisfactorio;
    }

    public boolean esEsteElPropietario(Jugador jugador) {
        boolean resultado = false;
        if (jugador == this.propietario) {
            resultado = true;
        }

        return resultado;

    }

    boolean igualdadIdentidad(Casilla otraParcela) {
        boolean igual = false;

        if (this == otraParcela) {
            igual = true;
        }
        return igual;
    }

    boolean igualdadEstado(Casilla otraParcela) {
        boolean igual = true;

        if (!this.Nombre.equals(otraParcela.getNombre())
                || this.getPrecioCompra() != otraParcela.getPrecioCompra()
                || this.getPrecioEdificar() != otraParcela.getPrecioEdificar()
                || this.precioBaseAlquiler != otraParcela.precioBaseAlquiler
                || this.getNumCasas() != otraParcela.getNumHoletes()
                || this.getNumHoletes() != otraParcela.getNumHoletes()) {
            igual = false;
        }

        return igual;
    }

    public String toString() {
        String Cadena = null;
        if (!this.tienePropietario()) {
            Cadena = "Calle " + Nombre + ". Precios: Compra: " + precioCompra + ", Edificar: " + precioEdificar + ", Alquiler Base: " + precioBaseAlquiler
                    + ", Casas: " + numCasas + ", Hoteles: " + numHoteles + ".";
        } else {
            Cadena = this.propietario.getNombre();
        }
        return Cadena;
    }
}
