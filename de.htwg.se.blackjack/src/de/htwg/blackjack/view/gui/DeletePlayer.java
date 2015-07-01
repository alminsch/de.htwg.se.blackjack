package de.htwg.blackjack.view.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.blackjack.entities.impl.Player;

public class DeletePlayer extends JDialog implements ActionListener {

	JComboBox box;
	JButton loeschenButton = new JButton("Löschen");
	JButton quitButton = new JButton("Abbrechen");
	private String name;

	public DeletePlayer(JFrame f) {
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(500,200));
		setTitle("Löschen");

		//panelb Combobox
		JPanel panelb = new JPanel();
		String[] deflt = {"default"};
		box = new JComboBox(deflt);
		box.addActionListener(this);
		panelb.add(box);

		//panelLA Löschen/Abbrechen
		JPanel panelLQ = new JPanel();

		loeschenButton.addActionListener(this);
		quitButton.addActionListener(this);

		panelLQ.add(loeschenButton);
		panelLQ.add(quitButton);

		//panel zusammen
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createTitledBorder("Spieler Löschen"));

		panel.add(panelb);
		panel.add(panelLQ);

		this.add(panel);
		this.setResizable(false);
		this.pack();
	}

	public void showdeletepersondialog(List<Player> namen) {
		renewbox(namen);
		setVisible(true);
	}

	public void renewbox(List<Player> list) {
		box.removeAllItems();
		for (Player p : list) {
			box.addItem(p);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == loeschenButton) {
			name = box.getSelectedItem().toString();
			setVisible(false);
		}
		if (source == quitButton) {
			setVisible(false);
		}
	}

	public String getName() {
		return name;
	}
}