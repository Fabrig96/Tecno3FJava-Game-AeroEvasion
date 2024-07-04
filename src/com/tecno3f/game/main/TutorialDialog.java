package com.tecno3f.game.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Esta clase representa un JDialog para tutorial del juego
 * @author FabrizioAG
 */
class TutorialDialog extends JDialog {
    public TutorialDialog(JFrame parent) {
        super(parent, "Tutorial del Juego", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        // Contenido del tutorial
        JTextArea tutorialText = new JTextArea(
                "Bienvenido a AstroEvasion!\n\n" +
                "Instrucciones:\n" +
                "- Usa las teclas de flecha (arriba,abajo) para mover la nave.\n" +
                "- Esquiva los asteroides para ganar puntos. (Cada uno suma 100 puntos)\n" +
                "- ¡No dejes que los asteroides choquen con tu nave!\n\n" +
                "¡Diviértete jugando!");

        tutorialText.setEditable(false);
        tutorialText.setWrapStyleWord(true);
        tutorialText.setLineWrap(true);
        tutorialText.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(tutorialText);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }
}