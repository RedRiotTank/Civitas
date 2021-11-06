/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

/**
 *
 * @author albertoplaza
 */
public class GestionInmobiliaria {
    private int propiedad;
    private OperacionInmobiliaria operacion;
    
    public GestionInmobiliaria(OperacionInmobiliaria gest, int ip){
        
        this.operacion = gest;
        this.propiedad = ip;
    
    }

    public int getPropiedad() {
        return propiedad;
    }

    public OperacionInmobiliaria getOperacion() {
        return operacion;
    }
    
    
}
