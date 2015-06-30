package de.htwg.blackjack.view.gui;

import java.awt.Font;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.GameStatus;
import de.htwg.blackjack.util.observer.IObserver;

public class PlayerPanel extends JLayeredPane implements IObserver{

	IController controller;
	JLabel playervalue;

	public PlayerPanel(IController controller) {

		this.controller = controller;
		controller.addObserver(this);
		playervalue = new JLabel();
		playervalue.setBounds(110, 150, 200, 50);
		playervalue.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		this.add(playervalue);
	}

	public void printdealercards() {
		int cvalue = controller.getDealerValue();
		playervalue.setText("Wert: "+ cvalue);
	}

	public void update(GameStatus status) {
		printdealercards();
	}
}
