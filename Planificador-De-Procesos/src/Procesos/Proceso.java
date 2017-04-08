/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Procesos;


import Utils.Constantes;
import javax.swing.JProgressBar;

/**
 *
 * 
 */
public class Proceso
{

    public int id;
    public int tiempoCPU1, tiempoCPU2, tiempoES;
    public int prioridad;
    public int estado;
    public JProgressBar barraDeProgreso;
    public int tiempoDeLlegada;
    public int tiempoEjecutadoEnCPU1, tiempoEjecutadoEnCPU2, tiempoEjecutadoEnES;
    public int tiempoDeSalida;
    public int ultimaModificacion;


    public Proceso(int id, int tiempoDeLlegada, int tiempoCPU1, int tiempoES,
            int tiempoCPU2, int prioridad, JProgressBar barraDeProgreso)
    {
        this.id = id;
        this.tiempoDeLlegada = tiempoDeLlegada;
        this.tiempoCPU1 = tiempoCPU1;
        this.tiempoCPU2 = tiempoCPU2;
        this.tiempoES = tiempoES;
        
        this.estado = Constantes.PROCESO_EN_CPU1;
        
        this.tiempoEjecutadoEnCPU1 = 0;
        this.tiempoEjecutadoEnCPU2 = 0;
        this.tiempoEjecutadoEnES = 0;
        this.tiempoDeSalida = tiempoCPU1 + tiempoCPU2 + tiempoES;
        
        this.ultimaModificacion = -1;
    }
    
    /**
     * 
     * @return 
     */
    public String getPorcentajeCompletado()
    {
        int completado = tiempoEjecutadoEnCPU1 + tiempoEjecutadoEnCPU2 + tiempoEjecutadoEnES;
        int total = tiempoCPU1 + tiempoCPU2 + tiempoES;
        
        return (completado * 100) / total + "%"; 
    }
}
