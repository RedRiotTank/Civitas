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
public class CasillaCalle extends Casilla {

    static private final float FACTORALQUILERCALLE = 1.0f;
    static private final float FACTORALQUILERCASA = 1.0f;
    static private final float FACTORALQUILERHOTEL = 4.0f;

    private float precioCompra, precioEdificar, precioBaseAlquiler;
    private int numCasas, numHoteles;

    private Jugador propietario;

    CasillaCalle(String titulo, float precioEdificar, float precioCompra, float precioBaseAlquiler) {
        super(titulo);
        this.precioCompra = precioCompra;
        this.precioEdificar = precioEdificar;
        this.precioBaseAlquiler = precioBaseAlquiler;
    }

    float getPrecioCompra() {
        return this.precioCompra;
    }

    float getPrecioEdificar() {
        return this.precioEdificar;
    }

    int getNumCasas() {
        return this.numCasas;
    }

    int getNumHoteles() {
        return this.numHoteles;
    }

    float getPrecioAlquilerCompleto() {
        return precioBaseAlquiler * (CasillaCalle.FACTORALQUILERCASA + numCasas + CasillaCalle.FACTORALQUILERHOTEL * numHoteles);
    }

    boolean comprar(Jugador jugador) {
        this.propietario = jugador;
        this.propietario.paga(precioCompra);

        return true;
    }

    public int cantidadCasasHoteles() {
        return this.numCasas + this.numHoteles;
    }

    private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos) {
        this.informe(iactual, todos);
        Jugador jugador = todos.get(iactual);

        if (!this.tienePropietario()) {
            jugador.puedeComprarCasilla();
        } else {
            this.tramitarAlquiler(jugador);
        }

    }

    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        recibeJugador_calle(iactual, todos);
    }

    public boolean tienePropietario() {
        boolean resultado = false;
        if (this.propietario != null) {
            resultado = true;
        }

        return resultado;

    }

    public void tramitarAlquiler(Jugador jugador) {
        if (this.tienePropietario() && !this.esEsteElPropietario(jugador)) {
            jugador.pagaAlquiler(this.getPrecioAlquilerCompleto());
            this.propietario.recibe(this.getPrecioAlquilerCompleto());
        }
    }

    boolean construirCasa(Jugador jugador) {
        jugador.paga(precioEdificar);
        numCasas++;
        return true;
    }

    boolean construirHotel(Jugador jugador) {
        propietario.paga(precioEdificar);
        numHoteles++;
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

    boolean igualdadEstado(CasillaCalle otraParcela) {
        boolean igual = true;

        if (!this.getNombre().equals(otraParcela.getNombre())
                || this.getPrecioCompra() != otraParcela.getPrecioCompra()
                || this.getPrecioEdificar() != otraParcela.getPrecioEdificar()
                || this.precioBaseAlquiler != otraParcela.precioBaseAlquiler
                || this.getNumCasas() != otraParcela.getNumHoteles()
                || this.getNumHoteles() != otraParcela.getNumHoteles()) {
            igual = false;
        }

        return igual;

    }

    public String toString() {
        String Cadena = null;

        if (!this.tienePropietario()) {
            Cadena = "Calle " + this.getNombre() + ". \n  Precios: \n     Compra: " + precioCompra + ", Edificar: " + precioEdificar + ", Alquiler Base: " + precioBaseAlquiler
                    + ". \n     Cuenta con: " + numCasas + " casas y " + numHoteles + " hoteles.\n\n";
        } else {
            Cadena = this.getNombre() + " Esta calle es de: " + this.propietario.getNombre();
        }

        return Cadena;
    }

}
