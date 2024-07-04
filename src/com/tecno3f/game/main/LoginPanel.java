package com.tecno3f.game.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Esta clase sirve para representar el panel Login 
 * @author FabrizioAG
 */
public class LoginPanel extends ImagePanel {
	private JTextField usuarioField;
	private JPasswordField contrasenaField;
	private JButton loginButton;
	private JButton registroButton;
	private JButton tutorialButton;
	private JuegoMain mainFrame;
	private Connection conn;
	private SoundPlayer soundPlayer;

	public LoginPanel(JuegoMain mainFrame, Connection conn, SoundPlayer soundPlayer) {
		super("src/com/tecno3f/game/assets/bg_main.jpg");
		this.mainFrame = mainFrame;
		this.conn = conn;
		this.soundPlayer = soundPlayer;

		soundPlayer.playLoop();

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel titleLabel = new JLabel("AstroEvasion");
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

		loginButton = new JButton("Login");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(loginButton, gbc);

		registroButton = new JButton("Registro");
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(registroButton, gbc);

		tutorialButton = new JButton("Tutorial");
		tutorialButton.setBackground(Color.ORANGE);
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(tutorialButton, gbc);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		registroButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.showRegistroPanel();
			}
		});

		tutorialButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Mostrar la ventana de diálogo del tutorial
				TutorialDialog tutorialDialog = new TutorialDialog(mainFrame);
				tutorialDialog.setLocationRelativeTo(null);
				tutorialDialog.setVisible(true);
				tutorialDialog.setLocationRelativeTo(mainFrame);
			}
		});
	}

	private void login() {
		String usuario = usuarioField.getText();
		String contrasena = new String(contrasenaField.getPassword());

		try {
			PreparedStatement stmt = conn
					.prepareStatement("SELECT * FROM jugadores WHERE usuario = ? AND contrasena = ?");
			stmt.setString(1, usuario);
			stmt.setString(2, contrasena);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				mainFrame.showJuegoPanel(usuario);
				soundPlayer.stop();
			} else {
				JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
