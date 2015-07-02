package de.htwg.blackjack.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewPlayer extends JDialog implements ActionListener {

	private static final long serialVersionUID = 5557823589405227981L;

	JTextField NameTextField = new JTextField(10);

	JButton npButton = new JButton("Spieler erstellen");
	JButton quitButton = new JButton("Abbrechen");
	private String name;

	public NewPlayer(JFrame f) {

		this.setModal(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(400, 200));
		this.setTitle("Neuer Spieler");

		JPanel panel1 = new JPanel();

		panel1.add(new JLabel("Name"));
		panel1.add(NameTextField);
		panel1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		JPanel panel2 = new JPanel();
		panel2.add(npButton);
		panel2.add(quitButton);
		npButton.addActionListener(this);
		quitButton.addActionListener(this);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(panel1);
		panel.add(panel2);

		this.add(panel);
		setResizable(false);
		pack();
	}

	public void shownewplayerDialog() {
		NameTextField.setText("");

		name = "";
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == npButton) {
			this.name = NameTextField.getText();
			if (name.equals("")) {
				JOptionPane.showMessageDialog(this, "Bitte Name eingeben", "Warnung", JOptionPane.WARNING_MESSAGE);
			} else { setVisible(false); }
		}
		if (source == quitButton) {
			setVisible(false);
		}
	}

	public String getName() { return name; }

}