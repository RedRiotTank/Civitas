/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

/**
 *
 * @author albertoplaza
 */
public class JugadorEspeculador extends Jugador{
    
    protected static final int FactorEspeculador = 2;
    /*Añado FactorDescuentoPago para que se pueda cambiar este
    cómodamente en el futuro*/
    protected static final int FactorDescuentoPago = 2;
    
     protected JugadorEspeculador(Jugador otro){
         super(otro);
         this.actualizaPropiedadesPorConversion(this);
    }
     
     boolean paga(float cantidad) {
         
        return super.paga(cantidad/FactorDescuentoPago);
    }
     
    public  int getCasasMax() {
        return CasasMax * FactorEspeculador;
    }
    
    public  int getHotelesMax() {
        return HotelesMax * FactorEspeculador;
    }
    
       public String toString() {

        return " (Es especulador): \n" + super.toString() ;
    }
    
}
