package com.tecno3f.game;

import javax.swing.*;

import com.tecno3f.game.main.JuegoMain;
import com.tecno3f.game.main.SoundPlayer;
import com.tecno3f.game.main.RankingDialog;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Esta clase sirve para representar la lógica del juego
 * 
 * @author FabrizioAG
 */
public class JuegoPanel extends JPanel implements ActionListener {
	Nave nave;
	int acelerationNave;
	ArrayList<Asteroide> asteroides;
	Timer timer, moveTimer;;
	int score;
	boolean gameOver;
	BufferedImage fondo;
	int fondoX;
	String usuario;
	Connection conn;
	JuegoMain mainFrame;
	private int puntajeMaximo;
	JButton reintentarButton;
	JButton rankingScoresButton;
	private SoundPlayer soundPlayer;
	private SoundPlayer soundPlayerGO;

	public JuegoPanel(JuegoMain mainFrame, Connection conn) {
		this.mainFrame = mainFrame;
		this.conn = conn;

		this.setPreferredSize(new Dimension(800, 600));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);

		nave = new Nave(50, 300);
		asteroides = new ArrayList<>();
		timer = new Timer(16, this);
		score = 0;
		gameOver = false;
		fondoX = 0;
		acelerationNave = 5;
		try {
			fondo = ImageIO.read(new File("src/com/tecno3f/game/assets/bg.jpg"));
		} catch (IOException e) {
			System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
		}

		soundPlayer = new SoundPlayer("src/com/tecno3f/game/assets/bgSoundGame.wav");

		reintentarButton = new JButton("Reintentar");
		reintentarButton.setFont(new Font("Arial", Font.BOLD, 20));
		reintentarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reintentarJuego();
			}
		});
		reintentarButton.setVisible(false);

		this.add(reintentarButton);

		rankingScoresButton = new JButton("Ranking Scores");
		rankingScoresButton.setFont(new Font("Arial", Font.BOLD, 20));
		rankingScoresButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RankingDialog rankingDialog = new RankingDialog(mainFrame, conn);
				rankingDialog.setLocationRelativeTo(null);
				rankingDialog.setVisible(true);
				rankingDialog.setLocationRelativeTo(mainFrame);
			}
		});
		rankingScoresButton.setVisible(false);

		this.add(rankingScoresButton);

		moveTimer = new Timer(16, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (upPressed) {
					nave.move(-5 - acelerationNave);
				}
				if (downPressed) {
					nave.move(5 + acelerationNave);
				}

				nave.keepWithinBounds(getHeight());
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_UP) {
					upPressed = true;
				}
				if (key == KeyEvent.VK_DOWN) {
					downPressed = true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_UP) {
					upPressed = false;
				}
				if (key == KeyEvent.VK_DOWN) {
					downPressed = false;
				}
			}
		});
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
		score = 0;
		gameOver = false;
		asteroides.clear();
		nave = new Nave(50, 300);
		soundPlayer.playLoop();
		timer.start();
		moveTimer.start();

	}

	private void actualizarScoreMaximo() {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT scoreMaximo FROM jugadores WHERE usuario = ?");
			stmt.setString(1, usuario);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				puntajeMaximo = rs.getInt("scoreMaximo");
				if (score > puntajeMaximo) {
					PreparedStatement updateStmt = conn
							.prepareStatement("UPDATE jugadores SET scoreMaximo = ? WHERE usuario = ?");
					updateStmt.setInt(1, score);
					updateStmt.setString(2, usuario);
					updateStmt.executeUpdate();
					updateStmt.close();
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void reintentarJuego() {
		reintentarButton.setVisible(false);
		rankingScoresButton.setVisible(false);
		setUsuario(usuario);
		soundPlayerGO.stop();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		if (fondo != null) {
			int width = getWidth();
			int height = getHeight();
			g2d.drawImage(fondo, fondoX, 0, width * 2, height, this);
			g2d.drawImage(fondo, fondoX + width * 4, 0, width * 2, height, this);
		}

		if (gameOver) {

			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.BOLD, 50));
			g2d.drawString("GAME OVER", 250, 300);
			g2d.setFont(new Font("Arial", Font.BOLD, 30));
			g2d.drawString("Score: " + score, 350, 350);

			g2d.setFont(new Font("Arial", Font.BOLD, 20));
			g2d.drawString("|- " + usuario.toUpperCase() + " -|", 25, 50);
			g2d.drawString("Puntaje Máximo: " + puntajeMaximo, 10, 80);

			reintentarButton.setBounds(300, 400, 200, 50);
			reintentarButton.setVisible(true);

			rankingScoresButton.setBounds(500, 40, 200, 50);
			rankingScoresButton.setVisible(true);

		} else {
			nave.draw(g2d);

			for (Asteroide asteroide : asteroides) {
				asteroide.draw(g2d);
			}

			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.BOLD, 20));
			g2d.drawString("Score: " + score, 10, 20);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (!gameOver) {
			Random rand = new Random();
			if (rand.nextInt(100) < 6) {
				int y = rand.nextInt(600 - 50);
				int speed = 16 + rand.nextInt(3);
				asteroides.add(new Asteroide(800, y, speed));
			}

			Iterator<Asteroide> iterator = asteroides.iterator();
			while (iterator.hasNext()) {
				Asteroide asteroide = iterator.next();
				asteroide.move();
				if (asteroide.x < -50) {
					iterator.remove();
					score += 100;
				}
				if (asteroide.getBounds().intersects(nave.getBounds())) {
					gameOver = true;
					soundPlayer.stop();
					soundPlayerGO = new SoundPlayer("src/com/tecno3f/game/assets/soundGameOver.wav");
					soundPlayerGO.play();
					timer.stop();
					moveTimer.stop();
					actualizarScoreMaximo();
				}
			}

			if (fondo != null) {
				fondoX -= 50;
				if (fondoX <= -getWidth()) {
					fondoX = 0;
				}
			}

			repaint();
		} else {
			timer.stop();
			moveTimer.stop();
			actualizarScoreMaximo();
			repaint();
		}

	}

	private boolean upPressed = false;
	private boolean downPressed = false;
}
