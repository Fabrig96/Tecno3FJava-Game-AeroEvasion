package com.tecno3f.game.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RankingDialog extends JDialog {
	Connection conn;

	public RankingDialog(JFrame parent, Connection conn) {
		super(parent, "Ranking Scores", true);
		this.conn = conn;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);

		JTable table = new JTable();
		String sql = "SELECT * FROM jugadores ORDER BY scoreMaximo DESC";
		Statement st;

		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("Usuario");
		modelo.addColumn("ScoreMaximo");
		table.setModel(modelo);
		String[] dato = new String[2];

		try {
			st = conn.createStatement();
			ResultSet result = st.executeQuery(sql);

			while (result.next()) {
				// System.out.println(result.getString(1));
				dato[0] = result.getString(1);
				dato[1] = result.getString(3);
				modelo.addRow(dato);
			}
			result.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane(table);
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