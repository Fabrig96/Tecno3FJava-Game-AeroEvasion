package com.tecno3f.game.main;

import javax.swing.*;

import com.tecno3f.game.JuegoPanel;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Esta clase representa el main del juego integrando los diferentes layout
 * @author FabrizioAG
 */
public class JuegoMain extends JFrame {
	CardLayout cardLayout;
	JPanel cardPanel;
	LoginPanel loginPanel;
	RegistroPanel registroPanel;
	JuegoPanel juegoPanel;
	Connection conn;
	private SoundPlayer loginSoundPlayer;

	public JuegoMain() {

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/gameae_db", "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		loginSoundPlayer = new SoundPlayer("src/com/tecno3f/game/assets/bgSoundMain.wav");

		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		loginPanel = new LoginPanel(this, conn, loginSoundPlayer);
		registroPanel = new RegistroPanel(this, conn);
		juegoPanel = new JuegoPanel(this, conn);

		cardPanel.add(loginPanel, "login");
		cardPanel.add(registroPanel, "registro");
		cardPanel.add(juegoPanel, "juego");

		add(cardPanel);

		cardLayout.show(cardPanel, "login");

		setTitle("AstroEvasion");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		ImageIcon icon = new ImageIcon("src/com/tecno3f/game/assets/gameIcono.png");
		setIconImage(icon.getImage());
	}

	public void showLoginPanel() {
		cardLayout.show(cardPanel, "login");
		loginSoundPlayer.playLoop();
	}

	public void showRegistroPanel() {
		cardLayout.show(cardPanel, "registro");
	}

	public void showJuegoPanel(String usuario) {

		loginSoundPlayer.stop();
		juegoPanel.setUsuario(usuario);
		cardLayout.show(cardPanel, "juego");
		juegoPanel.requestFocusInWindow();
	}

	public static void main(String[] args) {
		new JuegoMain();
	}
}
