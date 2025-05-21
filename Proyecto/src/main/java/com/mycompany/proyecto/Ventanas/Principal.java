/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.Ventanas;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

// Importaciones de todos los paneles necesarios
import com.mycompany.proyecto.TodosPaneles.PanelDocentes;
import com.mycompany.proyecto.TodosPaneles.PanelAdministradores;
import com.mycompany.proyecto.TodosPaneles.PanelEditar;
import com.mycompany.proyecto.TodosPaneles.PanelEstudiantes;
import com.mycompany.proyecto.TodosPaneles.PanelLaboratorio;
import com.mycompany.proyecto.TodosPaneles.PanelEquipo;
import com.mycompany.proyecto.TodosPaneles.PanelHorario;
import com.mycompany.proyecto.TodosPaneles.PanelVisualizarHorario;
import com.mycompany.proyecto.TodosPaneles.PanelHistorialEquipo;
import com.mycompany.proyecto.TodosPaneles.PanelVisualizarEquipo;
import com.mycompany.proyecto.FuncionPrestamo.PanelVisualizarPrestamos;
import com.mycompany.proyecto.PanelesDeMateriales.PanelEditarHerramientas;
import com.mycompany.proyecto.PanelesDeMateriales.PanelEditarInsumos;
import com.mycompany.proyecto.PanelesDeMateriales.PanelDetalleHerramientas;
import com.mycompany.proyecto.PanelesDeMateriales.PanelHerramientas;
import com.mycompany.proyecto.PanelesDeMateriales.PanelInsumos;
import com.mycompany.proyecto.PanelSanciones.PanelListaSanciones;
import com.mycompany.proyecto.PanelSanciones.PanelSancionar;
import com.mycompany.proyecto.FuncionReportes.PanelReporteEquipos;
import com.mycompany.proyecto.FuncionReportes.PanelReportePrestamo;
/**
 *
 * @author Usuario
 */
public class Principal extends JFrame {

    private JPanel contentPanel;
    private JLabel usuarioLabel;
    private int ruUsuario; // Añadido para almacenar el RU del usuario
    private static final Color PRIMARY_COLOR = new Color(0, 105, 92); // Teal oscuro para encabezados
    private static final Color SECONDARY_COLOR = new Color(240, 255, 255); // Fondo claro con toque teal
    private static final Color ACCENT_COLOR = new Color(255, 99, 71); // Coral para botones
    private static final Color TEXT_COLOR = Color.WHITE; // Blanco para texto en botones
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 100); // Sombra más pronunciada
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 15);

    public Principal(int ruUsuario) {
        this.ruUsuario = ruUsuario; // Inicializar ruUsuario
        // Configuración de la ventana
        setTitle("Sistema de Control y Préstamo de Laboratorios - Administrador");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 600));
        setLayout(new BorderLayout());

        // Panel de fondo con degradado radial
        JPanel backgroundPanel = createBackgroundPanel();
        setContentPane(backgroundPanel);

        // Panel de encabezado
        JPanel headerPanel = createHeaderPanel();
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel de menú principal
        JPanel menuPanel = createMenuPanel();
        backgroundPanel.add(menuPanel, BorderLayout.CENTER);

        // Centrar la ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createBackgroundPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                RadialGradientPaint gradient = new RadialGradientPaint(
                    getWidth() / 2f, getHeight() / 2f, getWidth(),
                    new float[]{0.0f, 1.0f},
                    new Color[]{SECONDARY_COLOR, new Color(180, 230, 230)}
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        panel.setLayout(new BorderLayout());
        return panel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel titleLabel = new JLabel("Universidad Salesiana de Bolivia");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(HEADER_FONT);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        userPanel.setOpaque(false);

        usuarioLabel = new JLabel("Administrador ");
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setFont(BUTTON_FONT);
        usuarioLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        userPanel.add(usuarioLabel);

        JButton logoutButton = new JButton("Cerrar Sesión") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(SHADOW_COLOR);
                g2d.fillRoundRect(3, 3, getWidth() - 4, getHeight() - 4, 20, 20);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 120, 100), 0, getHeight(), new Color(231, 76, 60));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2d.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // No dibujar borde adicional
            }
        };
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(BUTTON_FONT);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(new RoundedBorder(12));
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(255, 140, 120));
                logoutButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(new Color(231, 76, 60));
                logoutButton.repaint();
            }
        });
        logoutButton.addActionListener(e -> dispose());
        userPanel.add(logoutButton);

        headerPanel.add(userPanel, BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(220, 0));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new RoundedBorder(15));

        JLabel welcomeLabel = new JLabel("Bienvenido al Sistema de Control y Préstamo de Laboratorios", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        welcomeLabel.setForeground(new Color(33, 44, 55));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        buttonPanel.add(createMenuButton("Laboratorios", new String[]{"Horarios", "Editar Horario", "Editar Laboratorio"}));
        buttonPanel.add(createMenuButton("Usuarios", new String[]{"Docentes", "Estudiantes", "Administradores", "Editar Usuarios"}));
        buttonPanel.add(createMenuButton("Equipos", new String[]{"Máquinas", "Editar Equipos", "Detalle Equipos", "Generar Reportes Equipos"}));
        buttonPanel.add(createMenuButton("Préstamos", new String[]{"Visualizar Préstamos", "Generar Reportes"}));
        buttonPanel.add(createMenuButton("Materiales", new String[]{"Herramientas", "Insumos", "Editar Herramientas", "Editar Insumos", "Detalle Herramientas"}));
        buttonPanel.add(createMenuButton("Sanciones", new String[]{"Lista de Sanciones", "Sancionar"}));

        panel.add(buttonPanel, BorderLayout.WEST);
        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    private JButton createMenuButton(String title, String[] subOptions) {
        JButton button = new JButton(title) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Sombra exterior
                g2d.setColor(SHADOW_COLOR);
                g2d.fillRoundRect(4, 4, getWidth() - 5, getHeight() - 5, 20, 20);
                // Fondo del botón con gradiente
                GradientPaint gradient = new GradientPaint(0, 0, ACCENT_COLOR, 0, getHeight(), new Color(200, 70, 50));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                // Sombra interna para efecto 3D
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);
                g2d.dispose();

                // Dibujar texto
                g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setColor(TEXT_COLOR);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 2;
                g2d.drawString(getText(), x, y);
                g2d.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // No dibujar borde adicional
            }
        };
        button.setForeground(TEXT_COLOR);
        button.setFont(BUTTON_FONT);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(20));
        button.setPreferredSize(new Dimension(200, 50));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 120, 90));
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
                button.repaint();
            }
        });

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBackground(SECONDARY_COLOR);
        popupMenu.setBorder(new RoundedBorder(10));

        for (String subOption : subOptions) {
            JMenuItem menuItem = new JMenuItem(subOption);
            menuItem.setFont(LABEL_FONT);
            menuItem.setBackground(SECONDARY_COLOR);
            menuItem.setForeground(new Color(33, 44, 55));
            menuItem.setBorder(new EmptyBorder(10, 15, 10, 15));
            menuItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    menuItem.setBackground(new Color(200, 240, 240));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    menuItem.setBackground(SECONDARY_COLOR);
                }
            });
            menuItem.addActionListener(e -> mostrarContenido(title, subOption));
            popupMenu.add(menuItem);
        }

        button.addActionListener(e -> popupMenu.show(button, button.getWidth(), 0));
        return button;
    }

    private void mostrarContenido(String categoria, String subOpcion) {
        contentPanel.removeAll();

        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setOpaque(false);
        contentWrapper.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(categoria.toUpperCase() + " > " + subOpcion);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(33, 44, 55));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentWrapper.add(titleLabel, BorderLayout.NORTH);

        JPanel contenidoEspecifico = new JPanel();
        contenidoEspecifico.setOpaque(false);

        switch (categoria) {
            case "Laboratorios":
                if (subOpcion.equals("Horarios")) {
                    contenidoEspecifico = crearPanelHorarios();
                } else if (subOpcion.equals("Editar Horario")) {
                    contenidoEspecifico = crearPanelEditarHorario();
                } else if (subOpcion.equals("Editar Laboratorio")) {
                    contenidoEspecifico = crearPanelEditarLaboratorio();
                }
                break;
            case "Usuarios":
                if (subOpcion.equals("Docentes")) {
                    contenidoEspecifico = crearPanelDocentes();
                } else if (subOpcion.equals("Estudiantes")) {
                    contenidoEspecifico = crearPanelEstudiantes();
                } else if (subOpcion.equals("Administradores")) {
                    contenidoEspecifico = crearPanelAdministradores();
                } else if (subOpcion.equals("Editar Usuarios")) {
                    contenidoEspecifico = crearPanelEditarUsuarios();
                }
                break;
            case "Equipos":
                if (subOpcion.equals("Máquinas")) {
                    contenidoEspecifico = crearPanelMaquinas();
                } else if (subOpcion.equals("Editar Equipos")) {
                    contenidoEspecifico = crearPanelEditarEquipos();
                } else if (subOpcion.equals("Detalle Equipos")) {
                    contenidoEspecifico = crearPanelDetalleEquipos();
                } else if (subOpcion.equals("Generar Reportes Equipos")) {
                    contenidoEspecifico = crearPanelReportesEquipos();
                }
                break;
            case "Préstamos":
                if (subOpcion.equals("Visualizar Préstamos")) {
                    contenidoEspecifico = crearPanelVisualizarPrestamos();
                } else if (subOpcion.equals("Generar Reportes")) {
                    contenidoEspecifico = crearPanelGenerarReportePrestamos();
                }
                break;
            case "Materiales":
                if (subOpcion.equals("Herramientas")) {
                    contenidoEspecifico = crearPanelHerramientas();
                } else if (subOpcion.equals("Insumos")) {
                    contenidoEspecifico = crearPanelInsumos();
                } else if (subOpcion.equals("Editar Herramientas")) {
                    contenidoEspecifico = crearPanelEditarHerramientas();
                } else if (subOpcion.equals("Editar Insumos")) {
                    contenidoEspecifico = crearPanelEditarInsumos();
                } else if (subOpcion.equals("Detalle Herramientas")) {
                    contenidoEspecifico = crearPanelDetalleHerramientas();
                }
                break;
            case "Sanciones":
                if (subOpcion.equals("Lista de Sanciones")) {
                    contenidoEspecifico = crearPanelListaSanciones();
                } else if (subOpcion.equals("Sancionar")) {
                    contenidoEspecifico = crearPanelSancionar();
                }
                break;
        }

        contentWrapper.add(contenidoEspecifico, BorderLayout.CENTER);
        contentPanel.add(contentWrapper, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel crearPanelHorarios() {
        return new PanelVisualizarHorario();
    }

    private JPanel crearPanelEditarHorario() {
        return new PanelHorario();
    }

    private JPanel crearPanelEditarLaboratorio() {
        return new PanelLaboratorio();
    }

    private JPanel crearPanelDocentes() {
        return new PanelDocentes();
    }

    private JPanel crearPanelEstudiantes() {
        return new PanelEstudiantes();
    }

    private JPanel crearPanelAdministradores() {
        return new PanelAdministradores();
    }

    private JPanel crearPanelEditarUsuarios() {
        return new PanelEditar();
    }

    private JPanel crearPanelMaquinas() {
        return new PanelVisualizarEquipo();
    }

    private JPanel crearPanelEditarEquipos() {
        return new PanelEquipo();
    }

    private JPanel crearPanelDetalleEquipos() {
        return new PanelHistorialEquipo();
    }

    private JPanel crearPanelReportesEquipos() {
        return new PanelReporteEquipos();
    }

    private JPanel crearPanelVisualizarPrestamos() {
        return new PanelVisualizarPrestamos(ruUsuario);
    }

    private JPanel crearPanelGenerarReportePrestamos() {
        return new PanelReportePrestamo();
    }

    private JPanel crearPanelHerramientas() {
        return new PanelHerramientas();
    }

    private JPanel crearPanelInsumos() {
        return new PanelInsumos();
    }

    private JPanel crearPanelEditarHerramientas() {
        return new PanelEditarHerramientas();
    }

    private JPanel crearPanelEditarInsumos() {
        return new PanelEditarInsumos();
    }

    private JPanel crearPanelDetalleHerramientas() {
        return new PanelDetalleHerramientas();
    }

    private JPanel crearPanelListaSanciones() {
        return new PanelListaSanciones();
    }

    private JPanel crearPanelSancionar() {
        return new PanelSancionar();
    }

    private static class RoundedBorder implements Border {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(new Color(120, 120, 120));
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}
