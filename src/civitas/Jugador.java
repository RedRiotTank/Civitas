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
public class Jugador implements Comparable<Jugador> {

    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected static int HotelesMax = 4;
    protected static float PasoPorSalida = 1000;
    private static float SaldoInicial = 7500;

    private int casillaActual;
    private String nombre;
    private boolean puedeComprar;
    private float saldo;

    private ArrayList<Casilla> propiedades;

    int cantidadCasasHoteles() {
        return this.propiedades.size();
    }

    //@Override
    public int compareTo(Jugador otro) {
        return Float.compare(this.saldo, otro.saldo);
    }

    boolean comprar(Casilla titulo) {
        return true;
    }

    boolean construirCasa(int ip) {
        return false;
    }

    boolean construirHotel(int ip) {
        return false;
    }

    boolean enBancarrota() {
        boolean bancarrota = false;
        if (this.saldo <= 0) {
            bancarrota = true;
        }

        return bancarrota;

    }

    private boolean existeLaPropiedad(int ip) { //?????
        boolean existe = false;
        for (int i = 0; i < this.propiedades.size(); i++) {
            if (ip == i) {
                existe = true;
            }
        }

        return existe;
    }

    private int getCasasMax() {
        return this.CasasMax;
    }

    int getCasasPorHotel() {
        return CasasPorHotel;
    }

    int getCasillaActual() {
        return this.casillaActual;
    }

    private int getHotelesMax() {
        return HotelesMax;
    }

    protected String getNombre() {
        return nombre;
    }

    private float getPremioPasoSalida() {
        return PasoPorSalida;
    }

    protected ArrayList<Casilla> getPropiedades() {
        return this.propiedades;
    }

    boolean getPuedeComprar() {
        return this.puedeComprar;
    }

    protected float getSaldo() {
        return saldo;
    }

    Jugador(String nombre) {
        casillaActual = 0;
        this.nombre = nombre;
        puedeComprar = false;
        propiedades = null;
    }

    protected Jugador(Jugador otro) {
        casillaActual = otro.getCasillaActual();
        this.nombre = otro.getNombre();
        puedeComprar = otro.puedeComprarCasilla();
        propiedades = otro.getPropiedades();
    }

    boolean modificaSaldo(float cantidad) {

        this.saldo = this.saldo + cantidad;
        Diario.getInstance().ocurreEvento(nombre + " modifica " + cantidad);

        return true;
    }

    boolean moverACasilla(int numCasilla) {
        this.casillaActual = numCasilla;
        this.puedeComprar = false;
        Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " se movió a la casilla " + this.getCasillaActual());
        return true;
    }

    boolean paga(float cantidad) {
        return this.modificaSaldo(-1 * cantidad);
    }

    boolean pagaAlquiler(float cantidad) {
        return this.paga(cantidad);

    }

    boolean pasaPorSalida() {
        this.recibe(PasoPorSalida);
        Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " ha pasado por la casilla de salida");

        return true;
    }

    boolean puedeComprarCasilla() {
        this.puedeComprar = true;
        return true;
    }

    private boolean puedoEdificarCasa(Casilla propiedad) {
        boolean puedoHacerCasa = false;

        if (this.puedoGastar(propiedad.getPrecioEdificar()) && propiedad.getNumCasas() < CasasMax) {
            puedoHacerCasa = true;
        }

        return puedoHacerCasa;
    }

    private boolean puedoEdificarHotel(Casilla propiedad) {
        boolean puedoHacerHotel = false;
        if (this.puedoGastar(propiedad.getPrecioEdificar()) && propiedad.getNumHoletes() < HotelesMax && propiedad.getNumCasas() >= 3) {
            puedoHacerHotel = true;
        }

        return puedoHacerHotel;
    }

    private boolean puedoGastar(float precio) {
        boolean puedeGastar = false;

        if (precio <= this.getSaldo()) {
            puedeGastar = true;
        }
        return puedeGastar;
    }

    boolean recibe(float cantidad) {
        return this.modificaSaldo(cantidad);
    }

    boolean tieneAlgoQueGestionar() {
        boolean TienePropiedades = false;

        if (this.propiedades.size() != 0) {
            TienePropiedades = true;
        }

        return TienePropiedades;
    }

    public String toString() {
        return this.nombre + " en casilla: " + this.casillaActual;
    }

}
