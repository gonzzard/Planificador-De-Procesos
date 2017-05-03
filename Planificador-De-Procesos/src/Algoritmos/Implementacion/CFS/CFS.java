package Algoritmos.Implementacion.CFS;

import Algoritmos.Algoritmo;
import Procesos.Proceso;
import RedBlackBST.*;
import Utils.Constantes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Gonzalo
 */
public class CFS extends Algoritmo
{
    private RedBlackTree arbolDeProcesos;

    public CFS(List<Proceso> procesos, JTable tblColaProcesos,
            JTable tblColaProcesosListos, JTable tblColaProcesosBloqueados,
            JTable tblColaProcesosSinLlegar, Graphics2D graficaPanel,
            Graphics2D graficaBuffer, BufferedImage bufferPanelGantt)
    {
        super(procesos, tblColaProcesos, tblColaProcesosListos, tblColaProcesosBloqueados,
                tblColaProcesosSinLlegar, graficaPanel, graficaBuffer, bufferPanelGantt);

        this.arbolDeProcesos = new RedBlackTree();
    }

    @Override
    public boolean run(int seconds)
    {
        while (!fin)
        {
            PasoSiguiente();
        }
        return true;
    }

    @Override
    public boolean PasoSiguiente()
    {
        if (!fin)
        {
            System.out.println("*********************** T = " + tiempoProcesador);

            procesoSaliente = null;

            tratarColaProcesosSinLlegar();

            repintarColaProcesosSinLlegar();

            tratarColaProcesosBloqueados();

            repintarColaProcesosBloqueados();

            tratarColaProcesosListos();

            repintarColaProcesosListos();

            repintarColaProcesosBloqueados();

            repintarColaProcesos();

            if (colaProcesosSinLlegar.isEmpty() && arbolDeProcesos.root.key == null
                    && colaProcesosBloqueados.isEmpty())
            {
                fin = true;
            } else
            {
                tiempoProcesador++;
            }

            System.out.println("********************************************");
            System.out.println("********************************************\n");
        } else
        {
            System.out.println("*** FIN DE LA SIMULACIÓN");
            System.out.println("********************************************");
            System.out.println("********************************************\n");
        }
        return fin;
    }

    private void tratarColaProcesosBloqueados()
    {
        System.out.println("*** INICIO TRATAMIENTO COLA DE BLOQUEADOS");

        Proceso procesoBloqueado;

        if (colaProcesosBloqueados.isEmpty())
        {
            System.out.println("No hay procesos bloqueados");
        }

        // Tratamos la cola de bloqueados
        for (int i = 0; i < colaProcesosBloqueados.size(); i++)
        {
            procesoBloqueado = colaProcesosBloqueados.get(i);
            procesoBloqueado.tiempoEjecutadoEnES++;
            añadirCuadrado(Color.RED, procesoBloqueado.id, tiempoProcesador);
            System.out.println("El proceso #" + procesoBloqueado.id + " se encuentra bloqueado");

            if (procesoBloqueado.tiempoEjecutadoEnES == procesoBloqueado.tiempoES)
            {
                // Terminó su tiempo bloqueado
                if (procesoBloqueado.tiempoCPU2 == 0)
                {
                    // No tiene ejecución en  CPU2, terminó su ejecución general
                    procesoBloqueado.estado = Constantes.PROCESO_COMPLETADO;
                    procesoBloqueado.ultimaModificacion = tiempoProcesador;
                    System.out.println("El proceso #" + procesoBloqueado.id + " sale a bloqueado y termina su ejecución");
                } else
                {
                    // Tiene pendiente su ejecución en CPU2, entra en CPU2
                    procesoBloqueado.estado = Constantes.PROCESO_EN_CPU2;
                    procesoBloqueado.ultimaModificacion = tiempoProcesador;
                    bloqueadosAListos.add(procesoBloqueado);
                    //colaProcesosListos.add(procesoBloqueado);
                    //arbolDeProcesos.insert(procesoBloqueado);
                    System.out.println("El proceso #" + procesoBloqueado.id + " sale a CPU2");
                }
                colaProcesosBloqueados.remove(i);
                i--;
            }
        }

        System.out.println("*** FIN TRATAMIENTO COLA DE BLOQUEADOS\n");
    }

    private void tratarColaProcesosSinLlegar()
    {
        System.out.println("*** INICIO TRATAMIENTO COLA DE NO LLEGADOS");

        Proceso procesoSinLlegar;

        // Logs
        if (colaProcesosSinLlegar.isEmpty())
        {
            System.out.println("Estado de la cola de no llegados: No hay procesos SIN llegar");
        } else
        {
            String log = "";
            for (Proceso proceso : colaProcesosSinLlegar)
            {
                log = log.concat("#" + proceso.id + " ");
            }
            System.out.println("Estado de la cola de no llegados (" + colaProcesosSinLlegar.size() + "): " + log);
        }

        // Proceso
        for (int i = 0; i < colaProcesosSinLlegar.size(); i++)
        {
            procesoSinLlegar = colaProcesosSinLlegar.get(i);

            if (procesoSinLlegar.tiempoDeLlegada == tiempoProcesador)
            {
                procesoSinLlegar.estado = Constantes.PROCESO_EN_CPU1;
                colaProcesosSinLlegar.remove(i);
                i--;
                procesoSinLlegar.runTime += tiempoProcesador;
                colaProcesosListos.add(procesoSinLlegar);
                arbolDeProcesos.insert(procesoSinLlegar);
                System.out.println("El proceso #" + procesoSinLlegar.id + " SI ha llegado, se mueve a listos");
            } else
            {
                System.out.println("El proceso #" + procesoSinLlegar.id + " todavía NO ha llegado");
            }
        }

        System.out.println("*** FIN TRATAMIENTO COLA DE NO LLEGADOS\n");
    }

    private void tratarColaProcesosListos()
    {
        System.out.println("*** INICIO TRATAMIENTO NUEVO PROCESO DE COLA DE LISTOS");

        RedBlackNode nodoActual = sacarProceso(tiempoProcesador);
        Proceso procesoIzquierda = (Proceso) nodoActual.key;

        // Saco un proceso y le hago ejecutarse
        if (procesoEnCurso == null && arbolDeProcesos.size() > 0
                && procesoIzquierda.ultimaModificacion != tiempoProcesador)
        {
            procesoEnCurso = procesoIzquierda;
            arbolDeProcesos.remove(nodoActual);
            colaProcesosListos.remove(procesoEnCurso);

            System.out.println("Sacando Proceso de la cola de listo, #" + procesoEnCurso.id);
        } else
        {
            System.out.println("No hay procesos listos");
        }

        System.out.println("****** INICIO TRATAMIENTO PROCESOS EN ESPERA");

     
        
        // Marcamos los procesos en espera
        for (Proceso procesoEnEspera : colaProcesosListos)
        {
            System.out.println("El proceso #" + procesoEnEspera.id + " está en espera");
            procesoEnEspera.tiempoDeEsperaAcum++;
            añadirCuadrado(Color.ORANGE, procesoEnEspera.id, tiempoProcesador);
        }
        
        for (Proceso procesoBloqueado : bloqueadosAListos)
        {
            colaProcesosListos.add(procesoBloqueado);
            arbolDeProcesos.insert(procesoBloqueado);
        }
        
        bloqueadosAListos.clear();

        System.out.println("****** FIN TRATAMIENTO PROCESOS EN ESPERA");

        if (procesoEnCurso != null)
        {
            if (procesoEnCurso.estado == Constantes.PROCESO_EN_CPU1)
            {
                System.out.println("El proceso #" + procesoEnCurso.id + " está en CPU1");
                procesoEnCurso.tiempoEjecutadoEnCPU1++;
                añadirCuadrado(Color.GREEN, procesoEnCurso.id, tiempoProcesador);
                procesoIzquierda.runTime++;

                if (procesoEnCurso.tiempoEjecutadoEnCPU1 == procesoEnCurso.tiempoCPU1)
                {
                    //Ha terminado su ejecución en CPU1
                    if (procesoEnCurso.tiempoEjecutadoEnES == procesoEnCurso.tiempoES)
                    {
                        // No tiene pendiente ejecución en ES
                        if (procesoEnCurso.tiempoCPU2 == 0)
                        {
                            // No tiene ejecución en  CPU2, terminó su ejecución general
                            procesoEnCurso.estado = Constantes.PROCESO_COMPLETADO;
                            System.out.println("El proceso #" + procesoEnCurso.id + " ha terminado");
                            procesoEnCurso = null;
                        } else
                        {
                            // Tiene pendiente su ejecución en CPU2, entra en CPU2
                            procesoEnCurso.estado = Constantes.PROCESO_EN_CPU2;
                            colaProcesosListos.add(procesoIzquierda);
                            System.out.println("El proceso #" + procesoEnCurso.id + " entra en CPU2");
                            procesoEnCurso = null;
                        }
                    } else
                    {
                        // Tiene ejecución pendiente en ES
                        procesoEnCurso.estado = Constantes.PROCESO_EN_ES;
                        colaProcesosBloqueados.add(procesoEnCurso);
                        procesoSaliente = procesoEnCurso;
                        System.out.println("El proceso #" + procesoEnCurso.id + " entra en E/S");
                        procesoEnCurso = null;
                    }
                } else
                {
                    System.out.println("El proceso #" + procesoEnCurso.id + " seguirá en CPU1");
                    procesoEnCurso = null;
                    colaProcesosListos.add(procesoIzquierda);
                    arbolDeProcesos.insert(procesoIzquierda);
                }
            } else if (procesoEnCurso.estado == Constantes.PROCESO_EN_CPU2)
            {
                System.out.println("El proceso #" + procesoEnCurso.id + " está en CPU2");

                procesoEnCurso.tiempoEjecutadoEnCPU2++;
                añadirCuadrado(Color.GREEN, procesoEnCurso.id, tiempoProcesador);
                procesoIzquierda.runTime++;

                if (procesoEnCurso.tiempoEjecutadoEnCPU2 == procesoEnCurso.tiempoCPU2)
                {
                    // Terminó su ejecución en CPU2 y su ejecución en general
                    System.out.println("El proceso #" + procesoEnCurso.id + " ha terminado");
                    procesoEnCurso.estado = Constantes.PROCESO_COMPLETADO;
                    procesoEnCurso = null;
                } else
                {
                    procesoEnCurso = null;
                    colaProcesosListos.add(procesoIzquierda);
                    arbolDeProcesos.insert(procesoIzquierda);
                }
            }
        }

        System.out.println("*** FIN TRATAMIENTO NUEVO PROCESO DE COLA DE LISTOS \n");
    }

    /**
     * Saca el proceso de más a la izquierda del árbol, es decir, el que tiene
     * el menor tiempo de ejecución restante.
     */
    public RedBlackNode sacarProceso(int tiempoProcesador)
    {
        RedBlackNode nodoActual = arbolDeProcesos.root;

        while (nodoActual.left.key != null)
        {
            nodoActual = nodoActual.left;
        }

        Proceso procesoActual = (Proceso) nodoActual.key;

        if (procesoActual.ultimaModificacion != tiempoProcesador || procesoActual.ultimaModificacion == -1)
        {
            
        } else
        {
            nodoActual = nodoActual.parent;
        }

        return nodoActual;
    }
}
