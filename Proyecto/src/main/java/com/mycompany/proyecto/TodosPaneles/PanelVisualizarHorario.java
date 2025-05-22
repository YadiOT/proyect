/*
 * Click nbsp://netbeans/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://netbeans/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.TodosPaneles;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.mycompany.proyecto.Controles.ControladorHorario;
import com.mycompany.proyecto.Controles.ControladorLaboratorio;
import com.mycompany.proyecto.clases.Horario;
import com.mycompany.proyecto.clases.Laboratorio;

/**
 *
 * @author Usuario
 */
public class PanelVisualizarHorario extends JPanel {
    // Constantes para los colores de estado
    private static final Color COLOR_ASIGNADO = new Color(100, 149, 237); // Azul
    private static final Color COLOR_DISPONIBLE = new Color(144, 238, 144); // Verde claro
    private static final Color COLOR_NO_DISPONIBLE = new Color(255, 102, 102); // Rojo claro
    private static final Color COLOR_PRESTAMO = new Color(255, 165, 0); // Naranja
    
    // Controladores
    private ControladorHorario controladorHorario;
    private ControladorLaboratorio controladorLaboratorio;
    
    // Panel principal del horario
    private JPanel panelHorario;
    
    // ComboBox para seleccionar laboratorio
    private JComboBox<String> comboLaboratorio;
    
    // Mapa para guardar las celdas del horario
    private JPanel[][] celdas;
    
    // Arreglos para días y horas
    private final String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    private final String[] horas = {"7:30-9:00", "9:15-10:45", "11:00-12:30", "12:30-13:30"};
    
    public PanelVisualizarHorario() {
        controladorHorario = new ControladorHorario();
        controladorLaboratorio = new ControladorLaboratorio();
        
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titleLabel = new JLabel("Visualizar Horario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        titleLabel.setForeground(new Color(33, 37, 41));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Panel superior para la selección de laboratorio
        JPanel panelSeleccion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelSeleccion.setBackground(Color.WHITE);
        panelSeleccion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(52, 58, 64), 2),
                        "Selección de Laboratorio",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Roboto", Font.BOLD, 14),
                        new Color(33, 37, 41)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JLabel lblLaboratorio = new JLabel("Laboratorio:");
        lblLaboratorio.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblLaboratorio.setForeground(new Color(33, 37, 41));
        
        comboLaboratorio = new JComboBox<>();
        comboLaboratorio.setFont(new Font("Roboto", Font.PLAIN, 14));
        comboLaboratorio.setBackground(Color.WHITE);
        comboLaboratorio.setPreferredSize(new Dimension(250, 30));
        cargarLaboratorios();
        
        JButton btnVer = new JButton("Ver Horario");
        btnVer.setFont(new Font("Roboto", Font.BOLD, 14));
        btnVer.setBackground(new Color(0, 123, 255));
        btnVer.setForeground(Color.WHITE);
        btnVer.setPreferredSize(new Dimension(150, 35));
        btnVer.setFocusPainted(false);
        btnVer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 58, 64), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        addHoverEffect(btnVer);
        
        panelSeleccion.add(lblLaboratorio);
        panelSeleccion.add(comboLaboratorio);
        panelSeleccion.add(btnVer);
        
        // Panel para el horario
        panelHorario = new JPanel(new BorderLayout());
        panelHorario.setBackground(Color.WHITE);
        panelHorario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(52, 58, 64), 2),
                        "Horario del Laboratorio",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Roboto", Font.BOLD, 14),
                        new Color(33, 37, 41)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        // Agregar panels al panel principal
        add(panelSeleccion, BorderLayout.NORTH);
        add(panelHorario, BorderLayout.CENTER);
        
        // Acción para el botón Ver
        btnVer.addActionListener(e -> {
            if (comboLaboratorio.getSelectedItem() != null) {
                String selectedItem = comboLaboratorio.getSelectedItem().toString();
                int idLaboratorio = Integer.parseInt(selectedItem.split(" - ")[0]);
                mostrarHorario(idLaboratorio);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor seleccione un laboratorio", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void cargarLaboratorios() {
        try {
            List<Laboratorio> laboratorios = controladorLaboratorio.listar();
            comboLaboratorio.removeAllItems();
            for (Laboratorio lab : laboratorios) {
                comboLaboratorio.addItem(lab.getIdLaboratorio() + " - " + lab.getUbicacion());
            }
            
            if (laboratorios.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay laboratorios registrados en la base de datos", 
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar la lista de laboratorios: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarHorario(int idLaboratorio) {
        panelHorario.removeAll();
        
        // Panel para los días (encabezados)
        JPanel panelDias = new JPanel(new GridLayout(1, 5));
        panelDias.setBackground(Color.WHITE);
        for (String dia : dias) {
            JLabel label = new JLabel(dia, SwingConstants.CENTER);
            label.setFont(new Font("Roboto", Font.BOLD, 13));
            label.setForeground(new Color(33, 37, 41));
            label.setBorder(BorderFactory.createLineBorder(new Color(52, 58, 64)));
            label.setBackground(new Color(240, 242, 245));
            label.setOpaque(true);
            panelDias.add(label);
        }
        
        // Panel para las horas (primera columna)
        JPanel panelHoras = new JPanel(new GridLayout(4, 1));
        panelHoras.setBackground(Color.WHITE);
        for (String hora : horas) {
            JLabel label = new JLabel(hora, SwingConstants.RIGHT);
            label.setFont(new Font("Roboto", Font.BOLD, 12));
            label.setForeground(new Color(33, 37, 41));
            label.setBorder(BorderFactory.createLineBorder(new Color(52, 58, 64)));
            label.setBackground(new Color(240, 242, 245));
            label.setOpaque(true);
            label.setBorder(BorderFactory.createCompoundBorder(
                    label.getBorder(),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5)));
            panelHoras.add(label);
        }
        
        // Panel de la cuadrícula principal
        JPanel gridPanel = new JPanel(new GridLayout(4, 5));
        gridPanel.setBackground(Color.WHITE);
        celdas = new JPanel[4][5];
        
        // Inicializar todas las celdas como disponibles
        for (int hora = 0; hora < 4; hora++) {
            for (int dia = 0; dia < 5; dia++) {
                celdas[hora][dia] = crearCeldaHorario("Disponible", "", 0, "", "", COLOR_DISPONIBLE);
                gridPanel.add(celdas[hora][dia]);
            }
        }
        
        try {
            List<Horario> horarios = controladorHorario.listar();
            Map<String, Map<String, Horario>> horariosPorDiaYHora = new HashMap<>();
            
            for (Horario h : horarios) {
                if (h.getIdLaboratorio() == idLaboratorio) {
                    if (!horariosPorDiaYHora.containsKey(h.getDia())) {
                        horariosPorDiaYHora.put(h.getDia(), new HashMap<>());
                    }
                    horariosPorDiaYHora.get(h.getDia()).put(h.getHora(), h);
                }
            }
            
            for (int hora = 0; hora < horas.length; hora++) {
                for (int dia = 0; dia < dias.length; dia++) {
                    if (horariosPorDiaYHora.containsKey(dias[dia]) && 
                        horariosPorDiaYHora.get(dias[dia]).containsKey(horas[hora])) {
                        
                        Horario h = horariosPorDiaYHora.get(dias[dia]).get(horas[hora]);
                        Color color;
                        
                        switch (h.getEstado()) {
                            case "Asignado":
                                color = COLOR_ASIGNADO;
                                break;
                            case "Disponible":
                                color = COLOR_DISPONIBLE;
                                break;
                            case "No Disponible":
                                color = COLOR_NO_DISPONIBLE;
                                break;
                            case "Préstamo":
                                color = COLOR_PRESTAMO;
                                break;
                            default:
                                color = COLOR_DISPONIBLE;
                        }
                        
                        celdas[hora][dia] = crearCeldaHorario(
                            h.getEstado(),
                            h.getMateria(),
                            h.getParalelo(),
                            h.getSemestre(),
                            h.getCarrera(),
                            color
                        );
                        
                        gridPanel.remove(hora * 5 + dia);
                        gridPanel.add(celdas[hora][dia], hora * 5 + dia);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar horarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Crear el panel completo del horario
        JPanel completoPanel = new JPanel(new BorderLayout());
        completoPanel.setBackground(Color.WHITE);
        completoPanel.add(panelDias, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(panelHoras, BorderLayout.WEST);
        centerPanel.add(gridPanel, BorderLayout.CENTER);
        
        completoPanel.add(centerPanel, BorderLayout.CENTER);
        
        panelHorario.add(completoPanel, BorderLayout.CENTER);
        
        // Leyenda de colores
        JPanel leyendaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        leyendaPanel.setBackground(Color.WHITE);
        leyendaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(52, 58, 64), 2),
                        "Leyenda",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Roboto", Font.BOLD, 14),
                        new Color(33, 37, 41)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        agregarLeyendaColor(leyendaPanel, "Asignado", COLOR_ASIGNADO);
        agregarLeyendaColor(leyendaPanel, "Disponible", COLOR_DISPONIBLE);
        agregarLeyendaColor(leyendaPanel, "No Disponible", COLOR_NO_DISPONIBLE);
        agregarLeyendaColor(leyendaPanel, "Préstamo", COLOR_PRESTAMO);
        
        panelHorario.add(leyendaPanel, BorderLayout.SOUTH);
        
        panelHorario.revalidate();
        panelHorario.repaint();
    }
    
    private JPanel crearCeldaHorario(String estado, String materia, int paralelo, String semestre, String carrera, Color color) {
        JPanel celda = new JPanel();
        celda.setLayout(new BoxLayout(celda, BoxLayout.Y_AXIS));
        celda.setBorder(createRoundedBorder(new Color(52, 58, 64), 10));
        celda.setBackground(color);
        celda.setPreferredSize(new Dimension(120, 80));
        
        if (!materia.isEmpty()) {
            JLabel lblMateria = new JLabel(materia);
            JLabel lblParalelo = new JLabel("Par: " + paralelo);
            JLabel lblSemestre = new JLabel("Sem: " + semestre);
            JLabel lblCarrera = new JLabel(carrera);
            JLabel lblEstado = new JLabel(estado);
            
            for (JLabel lbl : new JLabel[]{lblMateria, lblParalelo, lblSemestre, lblCarrera, lblEstado}) {
                lbl.setFont(new Font("Roboto", Font.PLAIN, 10));
                lbl.setForeground(new Color(33, 37, 41));
                lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
                celda.add(lbl);
            }
        } else {
            JLabel lblDisponible = new JLabel("Disponible");
            lblDisponible.setFont(new Font("Roboto", Font.BOLD, 12));
            lblDisponible.setForeground(new Color(33, 37, 41));
            lblDisponible.setAlignmentX(Component.CENTER_ALIGNMENT);
            celda.add(lblDisponible);
        }
        
        return celda;
    }
    
    private void agregarLeyendaColor(JPanel panel, String texto, Color color) {
        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(20, 20));
        colorBox.setBorder(createRoundedBorder(new Color(52, 58, 64), 10));
        
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Roboto", Font.PLAIN, 12));
        lblTexto.setForeground(new Color(33, 37, 41));
        
        panel.add(colorBox);
        panel.add(lblTexto);
        panel.add(Box.createHorizontalStrut(10));
    }
    
    // Agrega efecto hover a los botones
    private void addHoverEffect(JButton button) {
        Color originalColor = button.getBackground();
        Color hoverColor = originalColor.brighter();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
    }
    
    // Crea un borde redondeado personalizado
    private Border createRoundedBorder(Color color, int radius) {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        };
    }
}