package Vistas;

import Algoritmos.Algoritmo;
import Algoritmos.Implementacion.FIFS;
import Procesos.Proceso;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JLabel;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gonzalo
 */
public class VentanaPrincipal extends javax.swing.JFrame
{
    private int idProceso;

    protected List<Proceso> procesos;

    private Boolean FIFS, RR, SJF;

    private Algoritmo algoritmo;

    public DefaultTableModel modelTblProcesos, modelNoLlegados;

    public BufferedImage bufferPanelGantt, vacio;
    
    protected Graphics2D graficaPanel, graficaBuffer;

    /**
     * Creates new form NewJFrame
     */
    public VentanaPrincipal()
    {
        // Inicialización de los componentes del frame
        initComponents();
        
        // Inicialización de procesos
        this.idProceso = 1;
        this.procesos = new ArrayList<>();

        // Inicialización de los modelos de las tablas
        this.modelTblProcesos = (DefaultTableModel) tblProcesos.getModel();
        this.modelNoLlegados = (DefaultTableModel) tblNoLlegados.getModel();

        // Inicialización de botones
        this.btnListo.setEnabled(false);
        this.btnPasoSiguiente.setEnabled(false);
        this.btnSimular.setEnabled(false);
        this.btnGraficas.setEnabled(false);
        this.btnAniadirProceso.setEnabled(false);
        this.btnGuardar.setEnabled(false);
        
        
        int ancho = panelGantt.getWidth();
        int alto = panelGantt.getHeight();

        bufferPanelGantt = (BufferedImage) panelGantt.createImage(ancho, alto);
        vacio = copiarBuffer(bufferPanelGantt);
        Graphics2D g3 = bufferPanelGantt.createGraphics();
        g3.setColor(Color.white);
        g3.fillRect(0, 0, ancho, alto);
        graficaPanel = (Graphics2D) panelGantt.getGraphics();
        graficaBuffer = (Graphics2D) bufferPanelGantt.getGraphics();
        
        graficaPanel.drawImage(bufferPanelGantt, 0, 0, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        panelGantt = new javax.swing.JPanel();
        pnlControl = new javax.swing.JPanel();
        btnSimular = new javax.swing.JButton();
        btnAniadirProceso = new javax.swing.JButton();
        chxFIFS = new javax.swing.JCheckBox();
        chxSJF = new javax.swing.JCheckBox();
        chxRR = new javax.swing.JCheckBox();
        btnListo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnGraficas = new javax.swing.JButton();
        btnEncender = new javax.swing.JButton();
        btnPasoSiguiente = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProcesos = new javax.swing.JTable();
        spColaProcesosListos = new javax.swing.JScrollPane();
        tblColaProcesosListos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNoLlegados = new javax.swing.JTable();
        lblColaProcesos = new javax.swing.JLabel();
        lblNoLlegados = new javax.swing.JLabel();
        lblColaListos = new javax.swing.JLabel();
        lblCronograma = new javax.swing.JLabel();
        lblPanelControl = new javax.swing.JLabel();
        lblColaBloqueados = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblColaBloqueados = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulador planificador de procesos. Gonzalo de las Heras - Daniel Fernández");
        setMinimumSize(new java.awt.Dimension(1060, 620));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelGantt.setBackground(new java.awt.Color(255, 255, 255));
        panelGantt.setMaximumSize(new java.awt.Dimension(1005, 150));
        panelGantt.setMinimumSize(new java.awt.Dimension(1005, 150));
        panelGantt.setPreferredSize(new java.awt.Dimension(1020, 150));
        panelGantt.addContainerListener(new java.awt.event.ContainerAdapter()
        {
            public void componentAdded(java.awt.event.ContainerEvent evt)
            {
                panelGanttComponentAdded(evt);
            }
        });
        panelGantt.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                panelGanttComponentShown(evt);
            }
        });

        javax.swing.GroupLayout panelGanttLayout = new javax.swing.GroupLayout(panelGantt);
        panelGantt.setLayout(panelGanttLayout);
        panelGanttLayout.setHorizontalGroup(
            panelGanttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        panelGanttLayout.setVerticalGroup(
            panelGanttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        getContentPane().add(panelGantt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 1040, 240));
        panelGantt.getAccessibleContext().setAccessibleName("");

        pnlControl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        btnSimular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/simular.png"))); // NOI18N
        btnSimular.setMaximumSize(new java.awt.Dimension(48, 48));
        btnSimular.setMinimumSize(new java.awt.Dimension(48, 48));
        btnSimular.setPreferredSize(new java.awt.Dimension(48, 48));
        btnSimular.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                btnSimularMousePressed(evt);
            }
        });

        btnAniadirProceso.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        btnAniadirProceso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/anadir.png"))); // NOI18N
        btnAniadirProceso.setActionCommand("");
        btnAniadirProceso.setMaximumSize(new java.awt.Dimension(48, 48));
        btnAniadirProceso.setMinimumSize(new java.awt.Dimension(48, 48));
        btnAniadirProceso.setPreferredSize(new java.awt.Dimension(48, 48));
        btnAniadirProceso.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                btnAniadirProcesoMousePressed(evt);
            }
        });

        chxFIFS.setText("FIFS");
        chxFIFS.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                chxFIFSMousePressed(evt);
            }
        });

        chxSJF.setText("SJF");
        chxSJF.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                chxSJFMousePressed(evt);
            }
        });

        chxRR.setText("RR");
        chxRR.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                chxRRMousePressed(evt);
            }
        });

        btnListo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/tick.png"))); // NOI18N
        btnListo.setMaximumSize(new java.awt.Dimension(48, 48));
        btnListo.setMinimumSize(new java.awt.Dimension(48, 48));
        btnListo.setPreferredSize(new java.awt.Dimension(48, 48));
        btnListo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnListoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/guardar.png"))); // NOI18N
        btnGuardar.setMaximumSize(new java.awt.Dimension(48, 48));
        btnGuardar.setMinimumSize(new java.awt.Dimension(48, 48));
        btnGuardar.setPreferredSize(new java.awt.Dimension(48, 48));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                btnGuardarMousePressed(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnGuardarActionPerformed(evt);
            }
        });

        btnGraficas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/graficas.png"))); // NOI18N
        btnGraficas.setMaximumSize(new java.awt.Dimension(48, 48));
        btnGraficas.setMinimumSize(new java.awt.Dimension(48, 48));
        btnGraficas.setPreferredSize(new java.awt.Dimension(48, 48));
        btnGraficas.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                btnGraficasMousePressed(evt);
            }
        });

        btnEncender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/encender.png"))); // NOI18N
        btnEncender.setMaximumSize(new java.awt.Dimension(48, 48));
        btnEncender.setMinimumSize(new java.awt.Dimension(48, 48));
        btnEncender.setPreferredSize(new java.awt.Dimension(48, 48));
        btnEncender.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                btnEncenderMousePressed(evt);
            }
        });

        btnPasoSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/pasoSiguiente.png"))); // NOI18N
        btnPasoSiguiente.setMaximumSize(new java.awt.Dimension(48, 48));
        btnPasoSiguiente.setMinimumSize(new java.awt.Dimension(48, 48));
        btnPasoSiguiente.setPreferredSize(new java.awt.Dimension(48, 48));
        btnPasoSiguiente.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                btnPasoSiguienteMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnlControlLayout = new javax.swing.GroupLayout(pnlControl);
        pnlControl.setLayout(pnlControlLayout);
        pnlControlLayout.setHorizontalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlControlLayout.createSequentialGroup()
                        .addComponent(chxFIFS)
                        .addGap(18, 18, 18)
                        .addComponent(chxSJF)
                        .addGap(18, 18, 18)
                        .addComponent(chxRR))
                    .addGroup(pnlControlLayout.createSequentialGroup()
                        .addComponent(btnEncender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAniadirProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnListo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSimular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPasoSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGraficas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlControlLayout.setVerticalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGraficas, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnEncender, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAniadirProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnListo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnSimular, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPasoSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chxFIFS)
                    .addComponent(chxSJF)
                    .addComponent(chxRR))
                .addContainerGap())
        );

        getContentPane().add(pnlControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 460, 106));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(454, 404));

        tblProcesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Id", "Llegada", "CPU1", "E/S", "CPU2", "Prioridad", "Progreso"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, true, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        tblProcesos.setToolTipText(null);
        tblProcesos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblProcesos.setShowGrid(true);
        tblProcesos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblProcesos);
        if (tblProcesos.getColumnModel().getColumnCount() > 0)
        {
            tblProcesos.getColumnModel().getColumn(0).setResizable(false);
            tblProcesos.getColumnModel().getColumn(1).setResizable(false);
            tblProcesos.getColumnModel().getColumn(2).setResizable(false);
            tblProcesos.getColumnModel().getColumn(3).setResizable(false);
            tblProcesos.getColumnModel().getColumn(4).setResizable(false);
            tblProcesos.getColumnModel().getColumn(5).setResizable(false);
            tblProcesos.getColumnModel().getColumn(6).setResizable(false);
        }
        DefaultTableCellRenderer centerRendererTblProcesos = new DefaultTableCellRenderer();
        centerRendererTblProcesos.setHorizontalAlignment( JLabel.CENTER );
        tblProcesos.getColumnModel().getColumn(0).setCellRenderer(centerRendererTblProcesos);
        tblProcesos.getColumnModel().getColumn(1).setCellRenderer(centerRendererTblProcesos);
        tblProcesos.getColumnModel().getColumn(2).setCellRenderer(centerRendererTblProcesos);
        tblProcesos.getColumnModel().getColumn(3).setCellRenderer(centerRendererTblProcesos);
        tblProcesos.getColumnModel().getColumn(4).setCellRenderer(centerRendererTblProcesos );
        tblProcesos.getColumnModel().getColumn(5).setCellRenderer(centerRendererTblProcesos);
        tblProcesos.getColumnModel().getColumn(6).setCellRenderer(centerRendererTblProcesos);

        ((DefaultTableCellRenderer)tblProcesos.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 531, 107));

        spColaProcesosListos.setPreferredSize(new java.awt.Dimension(454, 404));

        tblColaProcesosListos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Posición", "Id", "CPU1", "E/S", "CPU2", "Prioridad"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        tblColaProcesosListos.setToolTipText("Cola");
        tblColaProcesosListos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblColaProcesosListos.setName("");
        tblColaProcesosListos.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblColaProcesosListos.setShowGrid(true);
        tblColaProcesosListos.getTableHeader().setReorderingAllowed(false);
        spColaProcesosListos.setViewportView(tblColaProcesosListos);
        Dimension d = tblColaProcesosListos.getPreferredSize();
        spColaProcesosListos.setPreferredSize(
            new Dimension(d.width,tblColaProcesosListos.getRowHeight()*5+1));
        if (tblColaProcesosListos.getColumnModel().getColumnCount() > 0)
        {
            tblColaProcesosListos.getColumnModel().getColumn(0).setResizable(false);
            tblColaProcesosListos.getColumnModel().getColumn(1).setResizable(false);
            tblColaProcesosListos.getColumnModel().getColumn(2).setResizable(false);
            tblColaProcesosListos.getColumnModel().getColumn(3).setResizable(false);
            tblColaProcesosListos.getColumnModel().getColumn(4).setResizable(false);
            tblColaProcesosListos.getColumnModel().getColumn(5).setResizable(false);
        }
        ((DefaultTableCellRenderer)tblColaProcesosListos.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderertblColaProcesosListos= new DefaultTableCellRenderer();
        centerRenderertblColaProcesosListos.setHorizontalAlignment( JLabel.CENTER );
        tblColaProcesosListos.getColumnModel().getColumn(0).setCellRenderer(centerRenderertblColaProcesosListos);
        tblColaProcesosListos.getColumnModel().getColumn(1).setCellRenderer(centerRenderertblColaProcesosListos);
        tblColaProcesosListos.getColumnModel().getColumn(2).setCellRenderer(centerRenderertblColaProcesosListos);
        tblColaProcesosListos.getColumnModel().getColumn(3).setCellRenderer(centerRenderertblColaProcesosListos);
        tblColaProcesosListos.getColumnModel().getColumn(4).setCellRenderer(centerRenderertblColaProcesosListos);
        tblColaProcesosListos.getColumnModel().getColumn(5).setCellRenderer(centerRenderertblColaProcesosListos);

        getContentPane().add(spColaProcesosListos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 450, 107));
        spColaProcesosListos.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        tblNoLlegados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Id", "Llegada"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        tblNoLlegados.setShowGrid(true);
        jScrollPane3.setViewportView(tblNoLlegados);
        if (tblNoLlegados.getColumnModel().getColumnCount() > 0)
        {
            tblNoLlegados.getColumnModel().getColumn(0).setResizable(false);
            tblNoLlegados.getColumnModel().getColumn(1).setResizable(false);
        }
        ((DefaultTableCellRenderer)tblNoLlegados.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderertblNoLlegados= new DefaultTableCellRenderer();
        centerRenderertblNoLlegados.setHorizontalAlignment( JLabel.CENTER );
        tblNoLlegados.getColumnModel().getColumn(0).setCellRenderer(centerRenderertblNoLlegados);
        tblNoLlegados.getColumnModel().getColumn(1).setCellRenderer(centerRenderertblNoLlegados);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 190, 180, 107));

        lblColaProcesos.setFont(new java.awt.Font("Lucida Console", 0, 18)); // NOI18N
        lblColaProcesos.setText("Cola de procesos");
        getContentPane().add(lblColaProcesos, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        lblNoLlegados.setFont(new java.awt.Font("Lucida Console", 0, 18)); // NOI18N
        lblNoLlegados.setText("No llegados");
        getContentPane().add(lblNoLlegados, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 160, -1, -1));

        lblColaListos.setFont(new java.awt.Font("Lucida Console", 0, 18)); // NOI18N
        lblColaListos.setText("Cola de listos");
        getContentPane().add(lblColaListos, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, -1, -1));

        lblCronograma.setFont(new java.awt.Font("Lucida Console", 0, 18)); // NOI18N
        lblCronograma.setText("Cronograma");
        getContentPane().add(lblCronograma, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, -1, -1));

        lblPanelControl.setFont(new java.awt.Font("Lucida Console", 0, 18)); // NOI18N
        lblPanelControl.setText("Panel de control");
        getContentPane().add(lblPanelControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, -1, -1));

        lblColaBloqueados.setFont(new java.awt.Font("Lucida Console", 0, 18)); // NOI18N
        lblColaBloqueados.setText("Cola de bloqueados");
        getContentPane().add(lblColaBloqueados, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, -1, -1));

        tblColaBloqueados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Id", "E/S", "E/S Restante"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        tblColaBloqueados.setShowGrid(true);
        jScrollPane4.setViewportView(tblColaBloqueados);
        if (tblColaBloqueados.getColumnModel().getColumnCount() > 0)
        {
            tblColaBloqueados.getColumnModel().getColumn(0).setResizable(false);
            tblColaBloqueados.getColumnModel().getColumn(1).setResizable(false);
            tblColaBloqueados.getColumnModel().getColumn(2).setResizable(false);
        }
        ((DefaultTableCellRenderer)tblColaBloqueados.getTableHeader().getDefaultRenderer())
        .setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderertblColaBloqueados= new DefaultTableCellRenderer();
        centerRenderertblColaBloqueados.setHorizontalAlignment( JLabel.CENTER );
        tblColaBloqueados.getColumnModel().getColumn(0).setCellRenderer(centerRenderertblColaBloqueados);
        tblColaBloqueados.getColumnModel().getColumn(1).setCellRenderer(centerRenderertblColaBloqueados);
        tblColaBloqueados.getColumnModel().getColumn(2).setCellRenderer(centerRenderertblColaBloqueados);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, 260, 107));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAniadirProcesoMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnAniadirProcesoMousePressed
    {//GEN-HEADEREND:event_btnAniadirProcesoMousePressed
        if (idProceso <= 5)
        {
            this.modelTblProcesos.addRow(new Object[]
            {
            idProceso, 0, 3, 3, 5, 1
            });
            idProceso++;
            this.btnListo.setEnabled(true);
            if (idProceso == 6)
            {
                this.btnAniadirProceso.setEnabled(false);
                this.btnAniadirProceso.setFocusPainted(false);
            }
        }
    }//GEN-LAST:event_btnAniadirProcesoMousePressed

    private void btnSimularMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnSimularMousePressed
    {//GEN-HEADEREND:event_btnSimularMousePressed
        algoritmo.run(1);
        this.btnPasoSiguiente.setFocusPainted(false);
        this.btnPasoSiguiente.setEnabled(false);
        this.btnSimular.setFocusPainted(false);
        this.btnSimular.setEnabled(false);
        this.btnGuardar.setEnabled(true);
        this.btnGraficas.setEnabled(true);
    }//GEN-LAST:event_btnSimularMousePressed

    private void btnPasoSiguienteMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnPasoSiguienteMousePressed
    {//GEN-HEADEREND:event_btnPasoSiguienteMousePressed
        if (algoritmo.PasoSiguiente())
        {
            this.btnPasoSiguiente.setFocusPainted(false);
            this.btnPasoSiguiente.setEnabled(false);
            this.btnSimular.setFocusPainted(false);
            this.btnSimular.setEnabled(false);
        }
    }//GEN-LAST:event_btnPasoSiguienteMousePressed

    private void btnListoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnListoActionPerformed
    {//GEN-HEADEREND:event_btnListoActionPerformed
        btnAniadirProceso.setEnabled(false);
        btnListo.setEnabled(false);
        btnSimular.setEnabled(true);
        btnPasoSiguiente.setEnabled(true);
        tblProcesos.setEnabled(false);
        chxFIFS.setEnabled(false);
        chxRR.setEnabled(false);
        chxSJF.setEnabled(false);
        añadirProcesosDesdeTabla();
        //volcarProcesosEnCola();
        algoritmo = new FIFS(procesos, tblProcesos, tblColaProcesosListos, tblColaBloqueados,
                tblNoLlegados, graficaPanel, graficaBuffer, bufferPanelGantt);
    }//GEN-LAST:event_btnListoActionPerformed

    private void chxFIFSMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_chxFIFSMousePressed
    {//GEN-HEADEREND:event_chxFIFSMousePressed
        chxRR.setSelected(false);
        chxSJF.setSelected(false);
    }//GEN-LAST:event_chxFIFSMousePressed

    private void chxSJFMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_chxSJFMousePressed
    {//GEN-HEADEREND:event_chxSJFMousePressed
        chxFIFS.setSelected(false);
        chxRR.setSelected(false);
    }//GEN-LAST:event_chxSJFMousePressed

    private void chxRRMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_chxRRMousePressed
    {//GEN-HEADEREND:event_chxRRMousePressed
        chxFIFS.setSelected(false);
        chxSJF.setSelected(false);
    }//GEN-LAST:event_chxRRMousePressed

    private void panelGanttComponentAdded(java.awt.event.ContainerEvent evt)//GEN-FIRST:event_panelGanttComponentAdded
    {//GEN-HEADEREND:event_panelGanttComponentAdded

    }//GEN-LAST:event_panelGanttComponentAdded

    private void panelGanttComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_panelGanttComponentShown
    {//GEN-HEADEREND:event_panelGanttComponentShown

    }//GEN-LAST:event_panelGanttComponentShown

    private void btnGuardarMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnGuardarMousePressed
    {//GEN-HEADEREND:event_btnGuardarMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarMousePressed

    private void btnGraficasMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnGraficasMousePressed
    {//GEN-HEADEREND:event_btnGraficasMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGraficasMousePressed

    private void btnEncenderMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_btnEncenderMousePressed
    {//GEN-HEADEREND:event_btnEncenderMousePressed
        this.btnEncender.setEnabled(false);
        this.btnEncender.setFocusPainted(false);
        this.btnAniadirProceso.setEnabled(true);
        this.prepararDiagramaGantt();
    }//GEN-LAST:event_btnEncenderMousePressed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnGuardarActionPerformed
    {//GEN-HEADEREND:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new VentanaPrincipal().setVisible(true);
            }
        });
       
    }

    private void añadirProcesosDesdeTabla()
    {
        for (int i = 0; i < tblProcesos.getRowCount(); i++)
        {
            procesos.add(new Proceso((int) tblProcesos.getValueAt(i, 0),
                    (int) tblProcesos.getValueAt(i, 1),
                    (int) tblProcesos.getValueAt(i, 2),
                    (int) tblProcesos.getValueAt(i, 3),
                    (int) tblProcesos.getValueAt(i, 4),
                    (int) tblProcesos.getValueAt(i, 5),
                    null));
        }
    }

    private void volcarProcesosEnCola()
    {
        Iterator iterador = procesos.iterator();
        int i = 1;
        while (iterador.hasNext())
        {
            Proceso current = (Proceso) iterador.next();
            modelNoLlegados.addRow(new Object[]
            {
                current.id, current.tiempoDeLlegada
            });
            i++;
        }
    }

    private void prepararDiagramaGantt()
    {
        int factorX = panelGantt.getHeight() / 6;
        int factorY = panelGantt.getWidth() / 50;

        for (int i = 0; i < 7; i++)
        {
            graficaBuffer.drawLine(0, factorX * i, panelGantt.getWidth(), factorX * i);
        }

        graficaBuffer.drawLine(0, panelGantt.getHeight() - 1, panelGantt.getWidth(), panelGantt.getHeight() - 1);

        for (int i = 0; i < 51; i++)
        {
            graficaBuffer.drawLine(factorY * i + 40, 0, factorY * i + 40, panelGantt.getHeight());

        }

        graficaBuffer.drawLine(0, 0, 0, panelGantt.getHeight());

        for (int i = 0; i < 50; i++)
        {
            graficaBuffer.drawString((i / 10) + "", 46 + (factorY * i), 17);
            graficaBuffer.drawString((i % 10) + "", 46 + (factorY * i), 34);
        }

        graficaBuffer.drawLine(panelGantt.getWidth() - 1, 0, panelGantt.getWidth() - 1, panelGantt.getHeight());
        
        
        graficaBuffer.drawLine(0, 0, 40, 40);
        
        graficaPanel.drawImage(bufferPanelGantt, 0, 0, null);
    }
    
    public BufferedImage copiarBuffer(BufferedImage buffer)
    {
        ColorModel cm = buffer.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = buffer.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAniadirProceso;
    private javax.swing.JButton btnEncender;
    private javax.swing.JButton btnGraficas;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnListo;
    private javax.swing.JButton btnPasoSiguiente;
    private javax.swing.JButton btnSimular;
    private javax.swing.JCheckBox chxFIFS;
    private javax.swing.JCheckBox chxRR;
    private javax.swing.JCheckBox chxSJF;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblColaBloqueados;
    private javax.swing.JLabel lblColaListos;
    private javax.swing.JLabel lblColaProcesos;
    private javax.swing.JLabel lblCronograma;
    private javax.swing.JLabel lblNoLlegados;
    private javax.swing.JLabel lblPanelControl;
    private javax.swing.JPanel panelGantt;
    private javax.swing.JPanel pnlControl;
    private javax.swing.JScrollPane spColaProcesosListos;
    private javax.swing.JTable tblColaBloqueados;
    private javax.swing.JTable tblColaProcesosListos;
    private javax.swing.JTable tblNoLlegados;
    private javax.swing.JTable tblProcesos;
    // End of variables declaration//GEN-END:variables
}
