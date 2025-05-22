/*
 * Click nbsp://netbeans/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbsp://netbeans/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.FuncionPrestamo;

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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.mycompany.proyecto.Controles.ControladorEquipamento;
import com.mycompany.proyecto.Controles.ControladorHorario;
import com.mycompany.proyecto.Controles.ControladorInsumo;
import com.mycompany.proyecto.Controles.ControladorLaboratorio;
import com.mycompany.proyecto.Controles.ControladorPrestamo;
import com.mycompany.proyecto.clases.Equipamiento;
import com.mycompany.proyecto.clases.Horario;
import com.mycompany.proyecto.clases.Insumo;
import com.mycompany.proyecto.clases.Laboratorio;

/**
 *
 * @author Usuario
 */
public class PanelSolicitarPrestamo extends JPanel {
    private static final Logger LOGGER = Logger.getLogger(PanelSolicitarPrestamo.class.getName());
    private JComboBox<String> comboLaboratorios;
    private JComboBox<String> comboHorarios;
    private JTable tablaEquipamientos;
    private JTable tablaInsumos;
    private DefaultTableModel modeloEquipamientos;
    private DefaultTableModel modeloInsumos;
    private JTextField txtCantidadInsumo;
    private JButton btnAgregarEquipamiento;
    private JButton btnAgregarInsumo;
    private JButton btnSolicitar;
    private JTextArea txtObservaciones;
    private ControladorLaboratorio controladorLaboratorio;
    private ControladorHorario controladorHorario;
    private ControladorPrestamo controladorPrestamo;
    private ControladorEquipamento controladorEquipamiento;
    private ControladorInsumo controladorInsumo;
    private int ruUsuario;
    private Map<Integer, Integer> insumoCantidades;

    public PanelSolicitarPrestamo(int ruUsuario) {
        this.ruUsuario = ruUsuario;
        controladorLaboratorio = new ControladorLaboratorio();
        controladorHorario = new ControladorHorario();
        controladorPrestamo = new ControladorPrestamo();
        controladorEquipamiento = new ControladorEquipamento();
        controladorInsumo = new ControladorInsumo();
        insumoCantidades = new HashMap<>();
        try {
            initComponents();
        } catch (Exception ex) {
            LOGGER.severe("Error al inicializar PanelSolicitarPrestamo: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error al cargar el panel: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titleLabel = new JLabel("Solicitar Préstamo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        titleLabel.setForeground(new Color(33, 37, 41));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel principal con secciones
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // Sección: Laboratorio y Horario
        JPanel sectionLabHorario = new JPanel(new GridBagLayout());
        sectionLabHorario.setBackground(Color.WHITE);
        sectionLabHorario.setBorder(createRoundedBorder(new Color(52, 58, 64), 10));
        sectionLabHorario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(52, 58, 64), 2),
                        "Laboratorio y Horario",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Roboto", Font.BOLD, 14),
                        new Color(33, 37, 41)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        GridBagConstraints gbcSection = new GridBagConstraints();
        gbcSection.insets = new Insets(5, 5, 5, 5);
        gbcSection.fill = GridBagConstraints.HORIZONTAL;

        // Laboratorio
        gbcSection.gridx = 0;
        gbcSection.gridy = 0;
        JLabel lblLaboratorio = new JLabel("Laboratorio:");
        lblLaboratorio.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblLaboratorio.setForeground(new Color(33, 37, 41));
        sectionLabHorario.add(lblLaboratorio, gbcSection);

        gbcSection.gridx = 1;
        comboLaboratorios = new JComboBox<>();
        comboLaboratorios.setFont(new Font("Roboto", Font.PLAIN, 14));
        comboLaboratorios.setBackground(Color.WHITE);
        comboLaboratorios.setPreferredSize(new Dimension(250, 30));
        sectionLabHorario.add(comboLaboratorios, gbcSection);

        // Horario
        gbcSection.gridx = 0;
        gbcSection.gridy = 1;
        JLabel lblHorario = new JLabel("Horario:");
        lblHorario.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblHorario.setForeground(new Color(33, 37, 41));
        sectionLabHorario.add(lblHorario, gbcSection);

        gbcSection.gridx = 1;
        comboHorarios = new JComboBox<>();
        comboHorarios.setFont(new Font("Roboto", Font.PLAIN, 14));
        comboHorarios.setBackground(Color.WHITE);
        comboHorarios.setPreferredSize(new Dimension(250, 30));
        sectionLabHorario.add(comboHorarios, gbcSection);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(sectionLabHorario, gbc);

        // Panel para dividir equipamientos e insumos
        JPanel splitPanel = new JPanel(new GridBagLayout());
        splitPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbcSplit = new GridBagConstraints();
        gbcSplit.insets = new Insets(5, 5, 5, 5);
        gbcSplit.fill = GridBagConstraints.BOTH;
        gbcSplit.weightx = 0.5;

        // Sección: Equipamientos (izquierda)
        modeloEquipamientos = new DefaultTableModel(new String[]{"ID", "Nombre", "Modelo", "Disp."}, 0);
        tablaEquipamientos = new JTable(modeloEquipamientos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEquipamientos.setRowHeight(22);
        tablaEquipamientos.setFont(new Font("Roboto", Font.PLAIN, 12));
        tablaEquipamientos.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 13));
        tablaEquipamientos.setShowGrid(true);
        tablaEquipamientos.setGridColor(new Color(220, 224, 228));

        DefaultTableCellRenderer rendererEquip = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 242, 245));
                if (isSelected) {
                    c.setBackground(new Color(200, 230, 255));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setForeground(Color.BLACK);
                }
                ((JComponent) c).setOpaque(true);
                return c;
            }
        };
        for (int i = 0; i < tablaEquipamientos.getColumnCount(); i++) {
            tablaEquipamientos.getColumnModel().getColumn(i).setCellRenderer(rendererEquip);
        }

        tablaEquipamientos.getColumnModel().getColumn(0).setPreferredWidth(35);
        tablaEquipamientos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaEquipamientos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaEquipamientos.getColumnModel().getColumn(3).setPreferredWidth(70);

        JScrollPane scrollEquipamientos = new JScrollPane(tablaEquipamientos);
        scrollEquipamientos.setPreferredSize(new Dimension(450, 120));
        scrollEquipamientos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollEquipamientos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollEquipamientos.setBorder(createRoundedBorder(new Color(52, 58, 64), 10));

        JPanel sectionEquipamientos = new JPanel(new BorderLayout());
        sectionEquipamientos.setBackground(Color.WHITE);
        sectionEquipamientos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(52, 58, 64), 2),
                        "Equipamientos",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Roboto", Font.BOLD, 14),
                        new Color(33, 37, 41)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        sectionEquipamientos.add(scrollEquipamientos, BorderLayout.CENTER);

        btnAgregarEquipamiento = new JButton("Agregar");
        btnAgregarEquipamiento.setFont(new Font("Roboto", Font.BOLD, 12));
        btnAgregarEquipamiento.setBackground(new Color(52, 168, 83));
        btnAgregarEquipamiento.setForeground(Color.WHITE);
        btnAgregarEquipamiento.setPreferredSize(new Dimension(120, 30));
        btnAgregarEquipamiento.setFocusPainted(false);
        btnAgregarEquipamiento.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregarEquipamiento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 58, 64), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        addHoverEffect(btnAgregarEquipamiento);

        JPanel btnPanelEquip = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnPanelEquip.setBackground(Color.WHITE);
        btnPanelEquip.add(btnAgregarEquipamiento);
        sectionEquipamientos.add(btnPanelEquip, BorderLayout.SOUTH);

        gbcSplit.gridx = 0;
        gbcSplit.gridy = 0;
        splitPanel.add(sectionEquipamientos, gbcSplit);

        // Sección: Insumos (derecha)
        modeloInsumos = new DefaultTableModel(new String[]{"ID", "Insumo", "Cant.", "Cat."}, 0);
        tablaInsumos = new JTable(modeloInsumos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaInsumos.setRowHeight(22);
        tablaInsumos.setFont(new Font("Roboto", Font.PLAIN, 12));
        tablaInsumos.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 13));
        tablaInsumos.setShowGrid(true);
        tablaInsumos.setGridColor(new Color(220, 224, 228));

        DefaultTableCellRenderer rendererInsumo = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 242, 245));
                if (isSelected) {
                    c.setBackground(new Color(200, 230, 255));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setForeground(Color.BLACK);
                }
                ((JComponent) c).setOpaque(true);
                return c;
            }
        };
        for (int i = 0; i < tablaInsumos.getColumnCount(); i++) {
            tablaInsumos.getColumnModel().getColumn(i).setCellRenderer(rendererInsumo);
        }

        tablaInsumos.getColumnModel().getColumn(0).setPreferredWidth(35);
        tablaInsumos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaInsumos.getColumnModel().getColumn(2).setPreferredWidth(70);
        tablaInsumos.getColumnModel().getColumn(3).setPreferredWidth(70);

        JScrollPane scrollInsumos = new JScrollPane(tablaInsumos);
        scrollInsumos.setPreferredSize(new Dimension(450, 120));
        scrollInsumos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollInsumos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollInsumos.setBorder(createRoundedBorder(new Color(52, 58, 64), 10));

        JPanel sectionInsumos = new JPanel(new BorderLayout());
        sectionInsumos.setBackground(Color.WHITE);
        sectionInsumos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(52, 58, 64), 2),
                        "Insumos",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Roboto", Font.BOLD, 14),
                        new Color(33, 37, 41)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        sectionInsumos.add(scrollInsumos, BorderLayout.CENTER);

        JPanel insumoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        insumoPanel.setBackground(Color.WHITE);
        JLabel lblCantidad = new JLabel("Cant.:");
        lblCantidad.setFont(new Font("Roboto", Font.PLAIN, 12));
        lblCantidad.setForeground(new Color(33, 37, 41));
        insumoPanel.add(lblCantidad);
        txtCantidadInsumo = new JTextField(4);
        txtCantidadInsumo.setFont(new Font("Roboto", Font.PLAIN, 12));
        txtCantidadInsumo.setPreferredSize(new Dimension(60, 25));
        txtCantidadInsumo.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validarCantidad(); }
            public void removeUpdate(DocumentEvent e) { validarCantidad(); }
            public void changedUpdate(DocumentEvent e) { validarCantidad(); }
            private void validarCantidad() {
                String text = txtCantidadInsumo.getText().trim();
                try {
                    int cantidad = Integer.parseInt(text);
                    if (cantidad <= 0) {
                        txtCantidadInsumo.setBorder(BorderFactory.createLineBorder(Color.RED));
                    } else {
                        txtCantidadInsumo.setBorder(BorderFactory.createLineBorder(new Color(52, 58, 64)));
                    }
                } catch (NumberFormatException ex) {
                    txtCantidadInsumo.setBorder(BorderFactory.createLineBorder(Color.RED));
                }
            }
        });
        insumoPanel.add(txtCantidadInsumo);
        btnAgregarInsumo = new JButton("Agregar");
        btnAgregarInsumo.setFont(new Font("Roboto", Font.BOLD, 12));
        btnAgregarInsumo.setBackground(new Color(52, 168, 83));
        btnAgregarInsumo.setForeground(Color.WHITE);
        btnAgregarInsumo.setPreferredSize(new Dimension(120, 30));
        btnAgregarInsumo.setFocusPainted(false);
        btnAgregarInsumo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregarInsumo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 58, 64), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        addHoverEffect(btnAgregarInsumo);
        insumoPanel.add(btnAgregarInsumo);

        sectionInsumos.add(insumoPanel, BorderLayout.SOUTH);

        gbcSplit.gridx = 1;
        gbcSplit.gridy = 0;
        splitPanel.add(sectionInsumos, gbcSplit);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(splitPanel, gbc);

        // Sección: Observaciones
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel sectionObservaciones = new JPanel(new BorderLayout());
        sectionObservaciones.setBackground(Color.WHITE);
        sectionObservaciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(52, 58, 64), 2),
                        "Observaciones",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Roboto", Font.BOLD, 14),
                        new Color(33, 37, 41)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txtObservaciones = new JTextArea(4, 20);
        txtObservaciones.setFont(new Font("Roboto", Font.PLAIN, 14));
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        txtObservaciones.setBorder(BorderFactory.createCompoundBorder(
                createRoundedBorder(new Color(52, 58, 64), 10),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JScrollPane scrollObservaciones = new JScrollPane(txtObservaciones);
        scrollObservaciones.setBorder(null);
        sectionObservaciones.add(scrollObservaciones, BorderLayout.CENTER);
        formPanel.add(sectionObservaciones, gbc);

        // Botón Solicitar
        btnSolicitar = new JButton("Solicitar Préstamo");
        btnSolicitar.setFont(new Font("Roboto", Font.BOLD, 14));
        btnSolicitar.setBackground(new Color(0, 123, 255));
        btnSolicitar.setForeground(Color.WHITE);
        btnSolicitar.setPreferredSize(new Dimension(200, 40));
        btnSolicitar.setFocusPainted(false);
        btnSolicitar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSolicitar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 58, 64), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        addHoverEffect(btnSolicitar);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnSolicitar, gbc);

        // Envolver el formPanel en un JScrollPane para pantallas pequeñas
        JScrollPane mainScroll = new JScrollPane(formPanel);
        mainScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScroll.setBorder(null);
        add(mainScroll, BorderLayout.CENTER);

        // Cargar datos iniciales con manejo de errores
        try {
            cargarLaboratorios();
        } catch (Exception ex) {
            LOGGER.severe("Error al cargar laboratorios iniciales: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los laboratorios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Listeners para los botones
        comboLaboratorios.addActionListener(e -> {
            if (comboLaboratorios.getItemCount() > 0 && comboLaboratorios.getSelectedItem() != null) {
                try {
                    cargarHorarios();
                } catch (Exception ex) {
                    LOGGER.severe("Error al cargar horarios: " + ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Error al cargar horarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAgregarEquipamiento.addActionListener(e -> {
            try {
                agregarEquipamiento();
            } catch (Exception ex) {
                LOGGER.severe("Error al agregar equipamiento: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error al agregar equipamiento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAgregarInsumo.addActionListener(e -> {
            try {
                agregarInsumo();
            } catch (Exception ex) {
                LOGGER.severe("Error al agregar insumo: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error al agregar insumo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSolicitar.addActionListener(e -> {
            try {
                solicitarPrestamo();
            } catch (Exception ex) {
                LOGGER.severe("Error al solicitar préstamo: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error al solicitar préstamo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    private void cargarLaboratorios() {
        try {
            List<Laboratorio> laboratorios = controladorLaboratorio.listar();
            comboLaboratorios.removeAllItems();
            if (laboratorios.isEmpty()) {
                System.out.println("No hay laboratorios registrados en la base de datos.");
                JOptionPane.showMessageDialog(this, "No hay laboratorios registrados.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            for (Laboratorio lab : laboratorios) {
                comboLaboratorios.addItem(lab.getIdLaboratorio() + " - " + lab.getUbicacion());
            }
            System.out.println("Laboratorios cargados: " + laboratorios.size());
        } catch (SQLException ex) {
            LOGGER.severe("Error al cargar laboratorios: " + ex.getMessage());
            throw new RuntimeException("Error al cargar laboratorios", ex);
        }
    }

    private void cargarHorarios() {
        try {
            String selectedLab = (String) comboLaboratorios.getSelectedItem();
            if (selectedLab == null) {
                System.out.println("No se seleccionó ningún laboratorio.");
                JOptionPane.showMessageDialog(this, "Seleccione un laboratorio primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idLaboratorio = Integer.parseInt(selectedLab.split(" - ")[0]);
            List<Horario> horarios = controladorHorario.listarPorLaboratorio(idLaboratorio);
            comboHorarios.removeAllItems();
            if (horarios.isEmpty()) {
                System.out.println("No hay horarios disponibles para el laboratorio ID: " + idLaboratorio);
                JOptionPane.showMessageDialog(this, "No hay horarios disponibles para este laboratorio.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            for (Horario h : horarios) {
                comboHorarios.addItem(h.getIdHorario() + " - " + h.getDia() + " " + h.getHora());
            }
            System.out.println("Horarios cargados: " + horarios.size());
            cargarEquipamientos(idLaboratorio);
            cargarInsumos(idLaboratorio);
        } catch (SQLException ex) {
            LOGGER.severe("Error al cargar horarios: " + ex.getMessage());
            throw new RuntimeException("Error al cargar horarios", ex);
        }
    }

    private void cargarEquipamientos(int idLaboratorio) {
        try {
            List<Equipamiento> equipamientos = controladorEquipamiento.listarPorLaboratorio(idLaboratorio);
            modeloEquipamientos.setRowCount(0);
            if (equipamientos.isEmpty()) {
                System.out.println("No hay equipamientos registrados para el laboratorio ID: " + idLaboratorio);
                JOptionPane.showMessageDialog(this, "No hay equipamientos registrados para este laboratorio.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int disponibles = 0;
            for (Equipamiento e : equipamientos) {
                if ("Disponible".equalsIgnoreCase(e.getDisponibilidad())) {
                    modeloEquipamientos.addRow(new Object[]{
                        e.getIdEquipamiento(),
                        e.getNombreEquipamiento(),
                        e.getModelo(),
                        e.getDisponibilidad()
                    });
                    disponibles++;
                }
            }
            System.out.println("Equipamientos disponibles cargados: " + disponibles);
            if (disponibles == 0) {
                JOptionPane.showMessageDialog(this, "No hay equipamientos disponibles para este laboratorio.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al cargar equipamientos: " + ex.getMessage());
            throw new RuntimeException("Error al cargar equipamientos", ex);
        }
    }

    private void cargarInsumos(int idLaboratorio) {
        try {
            List<Insumo> insumos = controladorInsumo.listarPorLaboratorio(idLaboratorio);
            modeloInsumos.setRowCount(0);
            if (insumos.isEmpty()) {
                System.out.println("No hay insumos registrados para el laboratorio ID: " + idLaboratorio);
                JOptionPane.showMessageDialog(this, "No hay insumos registrados para este laboratorio.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int disponibles = 0;
            for (Insumo i : insumos) {
                if ("Disponible".equalsIgnoreCase(i.getDisponibilidad()) && i.getCantidad() > 0) {
                    modeloInsumos.addRow(new Object[]{
                        i.getIdInsumo(),
                        i.getNombreInsumo(),
                        i.getCantidad(),
                        i.getCategoria()
                    });
                    disponibles++;
                }
            }
            System.out.println("Insumos disponibles cargados: " + disponibles);
            if (disponibles == 0) {
                JOptionPane.showMessageDialog(this, "No hay insumos disponibles (cantidad > 0 y estado 'Disponible') para este laboratorio.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al cargar insumos: " + ex.getMessage());
            throw new RuntimeException("Error al cargar insumos", ex);
        }
    }

    private void agregarEquipamiento() {
        int selectedRow = tablaEquipamientos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipamiento.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idEquipamiento = (int) modeloEquipamientos.getValueAt(selectedRow, 0);
        modeloEquipamientos.removeRow(selectedRow);
        insumoCantidades.computeIfAbsent(idEquipamiento, k -> 0);
        System.out.println(" Equipamiento agregado: ID " + idEquipamiento);
    }

    private void agregarInsumo() {
        int selectedRow = tablaInsumos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un insumo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String cantidadStr = txtCantidadInsumo.getText().trim();
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idInsumo = (int) modeloInsumos.getValueAt(selectedRow, 0);
        int cantidadDisponible = (int) modeloInsumos.getValueAt(selectedRow, 2);
        if (cantidad > cantidadDisponible) {
            JOptionPane.showMessageDialog(this, "Cantidad solicitada excede la disponible.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        insumoCantidades.put(idInsumo, cantidad);
        modeloInsumos.setValueAt(cantidadDisponible - cantidad, selectedRow, 2);
        txtCantidadInsumo.setText("");
        System.out.println("Insumo agregado: ID " + idInsumo + ", Cantidad: " + cantidad);
    }

    private void solicitarPrestamo() {
        System.out.println("Iniciando solicitud de préstamo");
        String selectedHorario = (String) comboHorarios.getSelectedItem();
        if (selectedHorario == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un horario.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: No se seleccionó un horario.");
            return;
        }
        if (insumoCantidades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un equipamiento o insumo.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error: No se agregaron equipamientos ni insumos.");
            return;
        }

        try {
            // Validar ruUsuario
            System.out.println("Validando ruUsuario: " + ruUsuario);
            if (ruUsuario <= 0) {
                throw new IllegalArgumentException("RU de usuario inválido: " + ruUsuario);
            }
            // Verificar si el usuario existe en la base de datos
            if (!controladorPrestamo.existeUsuario(ruUsuario)) {
                throw new SQLException("El usuario con RU " + ruUsuario + " no está registrado en la base de datos.");
            }
            System.out.println("Usuario validado: RU " + ruUsuario);

            // Parsear idHorario
            System.out.println("Horario seleccionado: " + selectedHorario);
            int idHorario;
            try {
                idHorario = Integer.parseInt(selectedHorario.split(" - ")[0]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                throw new IllegalArgumentException("ID de horario inválido: " + selectedHorario, ex);
            }
            System.out.println("ID de horario parseado: " + idHorario);

            // Validar txtObservaciones
            String observaciones = txtObservaciones != null ? txtObservaciones.getText() : "";
            System.out.println("Observaciones: " + observaciones);

            // Crear objeto Prestamo
            System.out.println("Creando objeto Prestamo");
            java.util.Date currentDate = new java.util.Date();
            String horaPrestamo = new SimpleDateFormat("HH:mm").format(currentDate);
            System.out.println("Fecha de préstamo: " + new java.sql.Date(currentDate.getTime()));
            System.out.println("Hora de préstamo: " + horaPrestamo);
            com.mycompany.proyecto.clases.Prestamo prestamo = new com.mycompany.proyecto.clases.Prestamo(
                0, // idPrestamo
                ruUsuario,
                null, // ruAdministrador
                idHorario,
                new java.sql.Date(currentDate.getTime()),
                horaPrestamo,
                "Pendiente",
                observaciones
            );
            System.out.println("Objeto Prestamo creado: " + prestamo);

            // Insertar préstamo
            System.out.println("Insertando préstamo en la base de datos");
            int idPrestamo = controladorPrestamo.insertar(prestamo);
            if (idPrestamo == -1) {
                throw new SQLException("No se pudo registrar el préstamo en la base de datos.");
            }
            System.out.println("Préstamo registrado con ID: " + idPrestamo);

            // Procesar equipamientos e insumos
            System.out.println("Procesando equipamientos e insumos");
            for (Map.Entry<Integer, Integer> entry : insumoCantidades.entrySet()) {
                int id = entry.getKey();
                int cantidad = entry.getValue();
                if (cantidad == 0) {
                    // Es un equipamiento
                    System.out.println("Insertando detalle de equipamiento: ID " + id);
                    controladorPrestamo.insertarDetalleEquipamiento(idPrestamo, id);
                    // No actualizamos la disponibilidad aquí; se hará al aceptar el préstamo
                } else {
                    // Es un insumo
                    System.out.println("Insertando detalle de insumo: ID " + id + ", Cantidad: " + cantidad);
                    controladorPrestamo.insertarDetalleInsumo(idPrestamo, id, cantidad);
                    // No actualizamos la cantidad aquí; se hará al aceptar el préstamo
                }
            }

            System.out.println("Mostrando mensaje de éxito");
            JOptionPane.showMessageDialog(this, "Préstamo solicitado con éxito. ID: " + idPrestamo, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Préstamo solicitado con éxito.");

            // Limpiar el formulario
            insumoCantidades.clear();
            txtObservaciones.setText("");
            comboHorarios.setSelectedIndex(-1);
            cargarInsumos(Integer.parseInt(((String) comboLaboratorios.getSelectedItem()).split(" - ")[0]));
            cargarEquipamientos(Integer.parseInt(((String) comboLaboratorios.getSelectedItem()).split(" - ")[0]));
        } catch (SQLException ex) {
            LOGGER.severe("Error SQL al solicitar préstamo: " + ex.getMessage());
            System.err.println("Error SQL: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error en la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            LOGGER.severe("Error de validación al solicitar préstamo: " + ex.getMessage());
            System.err.println("Error de validación: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error de validación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            LOGGER.severe("Error inesperado al solicitar préstamo: " + ex.getMessage());
            System.err.println("Error inesperado: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}