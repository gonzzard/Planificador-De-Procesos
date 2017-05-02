package Algoritmos;

import Utils.Constantes;
import java.util.List;
import Procesos.Proceso;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gonzalo
 */
public abstract class Algoritmo
{
    protected int tiempoProcesador = 0;
    
    protected JTable tblColaProcesos, tblColaProcesosListos, tblColaProcesosBloqueados, tblColaProcesosSinLlegar;

    protected List<Proceso> procesos, colaProcesosListos, colaProcesosBloqueados, colaProcesosSinLlegar;

    protected Proceso procesoEnCurso, procesoSaliente;

    protected DefaultTableModel modelTblColaProcesos, modelTblColaProcesosListos, modelTblColaProcesosBloqueados, modelTblColaProcesosSinLlegar;;

    protected boolean fin, inicio;

    /**
     * Variable de tipo <code>Graphics2D</code> que almacena los elementos
     * gráficos del panel.
     */
    protected Graphics2D graficaPanel, graficaBuffer;

    /**
     * Objeto de tipo <code>BufferImage</code> que almacena el buffer del panel
     * y un buffer del tamaño del panel y de color blanco.
     */
    protected BufferedImage bufferPanelGantt, vacio;

    public Algoritmo(List<Proceso> procesos, JTable tblColaProcesos,
            JTable tblColaProcesosListos, JTable tblColaProcesosBloqueados,
            JTable tblColaProcesosSinLlegar, Graphics2D graficaPanel, 
            Graphics2D graficaBuffer, BufferedImage bufferPanelGantt)
    {
        // Inicialización listas
        this.procesos = procesos;
        this.colaProcesosListos = new ArrayList<>();
        this.colaProcesosSinLlegar = new ArrayList<>();
        this.colaProcesosBloqueados = new ArrayList<>();
        
        // Ordenamos los procesos por orden de llegada y prioridad
        this.organizarProcesos(procesos);
        
        // Ponemos el proceso en curso a null
        this.procesoEnCurso = null;
        
        // Inicialización tablas
        this.tblColaProcesos = tblColaProcesos;
        this.tblColaProcesosListos = tblColaProcesosListos;
        this.tblColaProcesosBloqueados = tblColaProcesosBloqueados;
        this.tblColaProcesosSinLlegar = tblColaProcesosSinLlegar;
        
        // Inicialización modelos
        this.modelTblColaProcesos = (DefaultTableModel) this.tblColaProcesos.getModel();
        this.modelTblColaProcesosListos = (DefaultTableModel) this.tblColaProcesosListos.getModel();
        this.modelTblColaProcesosBloqueados = (DefaultTableModel) this.tblColaProcesosBloqueados.getModel();
        this.modelTblColaProcesosSinLlegar = (DefaultTableModel) this.tblColaProcesosSinLlegar.getModel();
        
        // Inicialización variables del programa
        this.fin = false;
        this.inicio = false;

        // Inicialización de los elementos del cronograma
        this.bufferPanelGantt = bufferPanelGantt;
        this.graficaPanel = graficaPanel;
        this.graficaBuffer = graficaBuffer;
        
        // Repintamos las tablas
        this.repintarTablas();    
    }

    public BufferedImage copiarBuffer(BufferedImage buffer)
    {
        ColorModel cm = buffer.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = buffer.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    private void organizarProcesos(List<Proceso> procesos)
    {
        Collections.sort(procesos, (Proceso p1, Proceso p2) ->
        {
            if (p1.tiempoDeLlegada < p2.tiempoDeLlegada)
            {
                return -1;
            } 
            else if (p1.tiempoDeLlegada == p2.tiempoDeLlegada)
            {
                if (p1.prioridad < p2.prioridad)
                {
                    return -1;
                } 
                else
                {
                    return 1;
                }
            }
            else
            {
                return 1;
            }
        });
        
        for (Proceso current : procesos)
        {
            colaProcesosSinLlegar.add(current);
        }        
    }
    
    public void organizarColaProcesosPorPrioridad()
    {
        Collections.sort(this.colaProcesosListos, (Proceso p1, Proceso p2) ->
        {
            if (p1.prioridad < p2.prioridad)
            {
                return -1;
            } 
            else
            {
                return 1;
            }
        });
    }

    public void añadirCuadrado(Color color, int id, int tiempo)
    {
        int desplazamientoY = 40 + 1 + (40 * (id - 1));
        int desplazamientoX = 40 + 1 + Constantes.ANCHO_CELDA_TIEMPO * tiempo;
             
        rellenarCuadrado(bufferPanelGantt, 
                    new Point(desplazamientoX, desplazamientoY),
                    Color.WHITE, 
                    color);
        
        graficaPanel.drawImage(bufferPanelGantt, 0, 0, null);
    }

    public void repintarColaProcesosSinLlegar()
    {
        limpiarTabla(modelTblColaProcesosSinLlegar);
        for (Proceso proceso : colaProcesosSinLlegar)
        {
            modelTblColaProcesosSinLlegar.addRow(new Object[]
            {
                "#" + proceso.id, proceso.tiempoDeLlegada
            });
        }
    }
    
    public void repintarColaProcesosListos()
    {
        limpiarTabla(modelTblColaProcesosListos);
        
        if (procesoEnCurso != null)
        {
            modelTblColaProcesosListos.addRow(new Object[]
            {
                "Ejecutando", "#" + procesoEnCurso.id, procesoEnCurso.tiempoCPU1, procesoEnCurso.tiempoES, 
                procesoEnCurso.tiempoCPU2, procesoEnCurso.prioridad
            });
        }
        else if (procesoSaliente != null)
        {
            modelTblColaProcesosListos.addRow(new Object[]
            {
                "Saliendo", "#" + procesoSaliente.id, procesoSaliente.tiempoCPU1, procesoSaliente.tiempoES, 
                procesoSaliente.tiempoCPU2, procesoSaliente.prioridad
            });
        }
            
        int i = 1;
        for (Proceso proceso : colaProcesosListos)
        {
            modelTblColaProcesosListos.addRow(new Object[]
            {
                i + "º", "#" + proceso.id, proceso.tiempoCPU1, proceso.tiempoES,
                proceso.tiempoCPU2, proceso.prioridad
            });

            i++;
        }
    }
    
     public void repintarColaProcesos()
    {
        limpiarTabla(modelTblColaProcesos);
        int i = 1;
        for (Proceso proceso : procesos)
        {
            modelTblColaProcesos.addRow(new Object[]
            {
                "#" + proceso.id, proceso.tiempoDeLlegada, proceso.tiempoCPU1, proceso.tiempoES, 
                proceso.tiempoCPU2, proceso.prioridad, proceso.getPorcentajeCompletado()
            });
            i++;
        }
    }
    
    public void repintarColaProcesosBloqueados()
    {
        limpiarTabla(modelTblColaProcesosBloqueados);
        for (Proceso proceso : colaProcesosBloqueados)
        {
            modelTblColaProcesosBloqueados.addRow(new Object[]
            {
                "#" + proceso.id, proceso.tiempoES, (proceso.tiempoES - proceso.tiempoEjecutadoEnES)
            });
        }
    }
    
    public void limpiarTabla(DefaultTableModel model)
    {
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) 
        {
            model.removeRow(i);
        }
    }
    
    public void rellenarCuadrado(BufferedImage image, Point node, Color targetColor, Color replacementColor)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int target = targetColor.getRGB();
        int replacement = replacementColor.getRGB();
        if (target != replacement)
        {
            Deque<Point> queue = new LinkedList<>();
            do
            {
                int x = node.x;
                int y = node.y;
                while (x > 0 && image.getRGB(x - 1, y) == target)
                {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && image.getRGB(x, y) == target)
                {
                    image.setRGB(x, y, replacement);
                    if (!spanUp && y > 0 && image.getRGB(x, y - 1) == target)
                    {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0 && image.getRGB(x, y - 1) != target)
                    {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1 && image.getRGB(x, y + 1) == target)
                    {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1 && image.getRGB(x, y + 1) != target)
                    {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.pollFirst()) != null);
        }
    }
    
    public void repintarTablas()
    {
        this.repintarColaProcesos();
        this.repintarColaProcesosBloqueados();
        this.repintarColaProcesosListos();
        this.repintarColaProcesosSinLlegar();
    }

    public abstract boolean run(int seconds);
    
    public abstract boolean PasoSiguiente();
}
