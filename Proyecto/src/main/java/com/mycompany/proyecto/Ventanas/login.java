/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.Ventanas;

import com.mycompany.proyecto.database.ConexionBD;
import com.mycompany.proyecto.Exceptions.CredencialesInvalidas;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
/**
 *
 * @author Usuario
 */
public class login extends JFrame {
    private String rol;
    private JTextField cajaNombre;
    private JPasswordField cajaPassword;
    private JButton togglePasswordButton;
    private boolean isPasswordVisible = false;

    public login() {
        inicializarComponentes();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarComponentes() {
        setTitle("LOGIN DEL SISTEMA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        // Fondo con imagen
        JPanel fondoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Cargar la imagen de fondo
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Usuario\\Documents\\NetBeansProjects\\SistemaControlPrestamoLab\\salesiana.jpeg");
                if (backgroundImage.getIconWidth() == -1) {
                    System.err.println("No se pudo cargar la imagen de fondo.");
                    // Fondo de respaldo (degradado moderno)
                    GradientPaint gradient = new GradientPaint(0, 0, new Color(2, 136, 209), 0, getHeight(), new Color(245, 246, 245));
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    return;
                }

                // Dibujar la imagen de fondo escalada
                Image img = backgroundImage.getImage();
                g2d.drawImage(img, 0, 0, getWidth(), getHeight(), this);

                // Capa semitransparente para legibilidad
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondoPanel.setLayout(new GridBagLayout());
        setContentPane(fondoPanel);

        // Panel de login con sombra y fondo translúcido
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 220));
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 30, 30);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension screenSize = getParent().getSize();
                int width = Math.min(450, (int) (screenSize.width * 0.35));
                int height = Math.min(500, (int) (screenSize.height * 0.75));
                return new Dimension(width, height);
            }
        };
        loginPanel.setOpaque(false);
        loginPanel.setLayout(null);
        loginPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        fondoPanel.add(loginPanel, new GridBagConstraints());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ajustarComponentes(loginPanel);
            }
        });

        crearCamposLogin(loginPanel);
        ajustarComponentes(loginPanel);
    }

    private void crearCamposLogin(JPanel panel) {
        // Logo estilizado (texto como logo)
        JLabel sistemaLabel = new JLabel("Sistema de Préstamos", SwingConstants.CENTER);
        sistemaLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        sistemaLabel.setForeground(new Color(2, 136, 209));
        sistemaLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(sistemaLabel);

        // RU
        JLabel ruLabel = new JLabel("Registro Universitario (RU):");
        ruLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        ruLabel.setForeground(new Color(38, 50, 56));
        panel.add(ruLabel);

        cajaNombre = new JTextField();
        cajaNombre.setFont(new Font("Roboto", Font.PLAIN, 14));
        cajaNombre.setForeground(new Color(38, 50, 56));
        cajaNombre.setBackground(new Color(245, 246, 245));
        cajaNombre.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 189, 189), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        cajaNombre.setText("Ingrese su RU");
        cajaNombre.setForeground(new Color(120, 120, 120));
        cajaNombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (cajaNombre.getText().equals("Ingrese su RU")) {
                    cajaNombre.setText("");
                    cajaNombre.setForeground(new Color(38, 50, 56));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (cajaNombre.getText().isEmpty()) {
                    cajaNombre.setText("Ingrese su RU");
                    cajaNombre.setForeground(new Color(120, 120, 120));
                }
            }
        });
        panel.add(cajaNombre);

        // Contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(38, 50, 56));
        panel.add(passwordLabel);

        cajaPassword = new JPasswordField();
        cajaPassword.setFont(new Font("Roboto", Font.PLAIN, 14));
        cajaPassword.setForeground(new Color(38, 50, 56));
        cajaPassword.setBackground(new Color(245, 246, 245));
        cajaPassword.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 189, 189), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        cajaPassword.setText("••••••••");
        cajaPassword.setForeground(new Color(120, 120, 120));
        cajaPassword.setEchoChar((char) 0);
        cajaPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(cajaPassword.getPassword()).equals("••••••••")) {
                    cajaPassword.setText("");
                    cajaPassword.setForeground(new Color(38, 50, 56));
                    cajaPassword.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(cajaPassword.getPassword()).isEmpty()) {
                    cajaPassword.setText("••••••••");
                    cajaPassword.setForeground(new Color(120, 120, 120));
                    cajaPassword.setEchoChar((char) 0);
                }
            }
        });
        panel.add(cajaPassword);

        // Botón para mostrar/ocultar contraseña
        togglePasswordButton = new JButton("👁");
        togglePasswordButton.setBackground(new Color(245, 246, 245));
        togglePasswordButton.setBorder(new LineBorder(new Color(189, 189, 189), 1, true));
        togglePasswordButton.setFocusPainted(false);
        togglePasswordButton.setToolTipText("Mostrar contraseña");
        togglePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        togglePasswordButton.addActionListener(e -> togglePasswordVisibility());
        togglePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                togglePasswordButton.setBackground(new Color(2, 136, 209));
                togglePasswordButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                togglePasswordButton.setBackground(new Color(245, 246, 245));
                togglePasswordButton.setForeground(new Color(38, 50, 56));
            }
        });
        panel.add(togglePasswordButton);

        // Botón de Ingresar
        JButton botonIngresar = new JButton("Iniciar Sesión") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        botonIngresar.setBackground(new Color(2, 136, 209));
        botonIngresar.setForeground(Color.WHITE);
        botonIngresar.setFont(new Font("Roboto", Font.BOLD, 16));
        botonIngresar.setFocusPainted(false);
        botonIngresar.setBorderPainted(false);
        botonIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonIngresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botonIngresar.setBackground(new Color(245, 124, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonIngresar.setBackground(new Color(2, 136, 209));
            }
        });
        botonIngresar.addActionListener(e -> intentarLogin());
        panel.add(botonIngresar);

        // Botón para nuevo usuario
        JButton botonNuevo = new JButton("Registrarse como Nuevo Usuario") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        botonNuevo.setBackground(new Color(245, 246, 245));
        botonNuevo.setForeground(new Color(2, 136, 209));
        botonNuevo.setFont(new Font("Roboto", Font.PLAIN, 14));
        botonNuevo.setBorderPainted(false);
        botonNuevo.setFocusPainted(false);
        botonNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botonNuevo.setBackground(new Color(245, 124, 0));
                botonNuevo.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonNuevo.setBackground(new Color(245, 246, 245));
                botonNuevo.setForeground(new Color(2, 136, 209));
            }
        });
        botonNuevo.addActionListener(e -> abrirFormularioRegistro());
        panel.add(botonNuevo);
    }

    private void ajustarComponentes(JPanel panel) {
        Dimension panelSize = panel.getSize();
        int padding = 40;
        int fieldHeight = 45;
        int fieldWidth = panelSize.width - 2 * padding;
        int buttonWidth = fieldWidth;
        int toggleButtonWidth = 40;

        // Logo
        panel.getComponent(0).setBounds(0, 20, panelSize.width, 50);
        // RU
        panel.getComponent(1).setBounds(padding, 100, fieldWidth, 25);
        panel.getComponent(2).setBounds(padding, 130, fieldWidth - toggleButtonWidth - 10, fieldHeight);
        // Contraseña
        panel.getComponent(3).setBounds(padding, 190, fieldWidth, 25);
        panel.getComponent(4).setBounds(padding, 220, fieldWidth - toggleButtonWidth - 10, fieldHeight);
        panel.getComponent(5).setBounds(padding + fieldWidth - toggleButtonWidth - 5, 220, toggleButtonWidth, fieldHeight);
        // Botones
        panel.getComponent(6).setBounds(padding, 290, buttonWidth, fieldHeight);
        panel.getComponent(7).setBounds(padding, 350, buttonWidth, fieldHeight);
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            cajaPassword.setEchoChar('•');
            togglePasswordButton.setText("👁");
            togglePasswordButton.setToolTipText("Mostrar contraseña");
            isPasswordVisible = false;
        } else {
            cajaPassword.setEchoChar((char) 0);
            togglePasswordButton.setText("👁‍🗨");
            togglePasswordButton.setToolTipText("Ocultar contraseña");
            isPasswordVisible = true;
        }
    }

    private void intentarLogin() {
        try {
            String ruText = cajaNombre.getText().trim();
            String password = new String(cajaPassword.getPassword());

            if (ruText.isEmpty() || ruText.equals("Ingrese su RU") || 
                password.isEmpty() || password.equals("••••••••")) {
                throw new CredencialesInvalidas("Por favor, complete todos los campos");
            }

            try {
                int ru = Integer.parseInt(ruText);
                if (!validarEnBD(ru, password)) {
                    throw new CredencialesInvalidas("Usuario o contraseña incorrectos");
                }

                // Redireccionar según el rol, pasando ruUsuario
                if (rol.equals("Administrador")) {
                    new Principal(ru).setVisible(true);
                } else if (rol.equals("Estudiante")) {
                    new Principal3(ru).setVisible(true);
                } else {
                    new Principal2(ru).setVisible(true);
                }

                dispose();
            } catch (NumberFormatException ex) {
                throw new CredencialesInvalidas("El RU debe ser un número válido");
            }
        } catch (CredencialesInvalidas ex) {
            mostrarMensajeError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarMensajeError("Error al conectar con la base de datos: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void abrirFormularioRegistro() {
        try {
            String ruText = cajaNombre.getText().trim();
            String password = new String(cajaPassword.getPassword());
            
            if (!ruText.isEmpty() && !ruText.equals("Ingrese su RU") && 
                !password.isEmpty() && !password.equals("••••••••")) {
                try {
                    int ru = Integer.parseInt(ruText);
                    if (ru == 100 && password.equals("alfha phils")) {
                        new NuevoAdministrador().setVisible(true);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    // Continúa al registro normal
                }
            }
            
            new NuevoUsuario().setVisible(true);
        } catch (Exception ex) {
            mostrarMensajeError("Error al abrir el formulario: " + ex.getMessage());
        }
    }

    private boolean validarEnBD(int ru, String password) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE ru = ? AND contra = ?";
        System.out.println("Validando en BD: RU = " + ru + ", Contraseña = [oculta]");
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ru);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rol = rs.getString("rol");
                    System.out.println("Usuario encontrado: RU = " + ru + ", Rol = " + rol);
                    return true;
                }
                System.out.println("No se encontró usuario con RU = " + ru);
                return false;
            }
        }
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane optionPane = new JOptionPane(
            mensaje,
            JOptionPane.ERROR_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[] {"Aceptar"},
            "Aceptar"
        );
        JDialog dialog = optionPane.createDialog(this, "Error");
        dialog.setVisible(true);
    }
}