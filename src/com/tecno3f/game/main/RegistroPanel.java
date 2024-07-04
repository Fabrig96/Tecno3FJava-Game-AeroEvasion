package com.tecno3f.game.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Esta clase sirve para representar el panel Registro
 * @author FabrizioAG
 */
public class RegistroPanel extends ImagePanel {
	private JTextField usuarioField;
	private JPasswordField contrasenaField;
	private JButton registroButton;
	private JButton volverButton;
	private JuegoMain mainFrame;
	private Connection conn;

	public RegistroPanel(JuegoMain mainFrame, Connection conn) {
		super("src/com/tecno3f/game/assets/bg_main.jpg");
		this.mainFrame = mainFrame;
		this.conn = conn;

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel titleLabel = new JLabel("Registro");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
		titleLabel.setForeground(Color.YELLOW);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(titleLabel, gbc);

		JLabel usuarioLabel = new JLabel("Usuario:");
		usuarioLabel.setFont(new Font("Arial", Font.BOLD, 24));
		usuarioLabel.setForeground(Color.YELLOW);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;
		add(usuarioLabel, gbc);

		usuarioField = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(usuarioField, gbc);

		JLabel contrasenaLabel = new JLabel("Contraseña:");
		contrasenaLabel.setFont(new Font("Arial", Font.BOLD, 24));
		contrasenaLabel.setForeground(Color.YELLOW);
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(contrasenaLabel, gbc);

		contrasenaField = new JPasswordField(15);
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(contrasenaField, gbc);

		registroButton = new JButton("Registrar");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(registroButton, gbc);

		volverButton = new JButton("Volver");
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(volverButton, gbc);

		registroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});

		volverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showLoginPanel();
			}
		});
	}

	private void registrar() {
		String usuario = usuarioField.getText();
		String contrasena = new String(contrasenaField.getPassword());

		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO jugadores (usuario, contrasena) VALUES (?, ?)");
			stmt.setString(1, usuario);
			stmt.setString(2, contrasena);
			stmt.executeUpdate();
			stmt.close();

			JOptionPane.showMessageDialog(this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			mainFrame.showLoginPanel();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al registrar: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
