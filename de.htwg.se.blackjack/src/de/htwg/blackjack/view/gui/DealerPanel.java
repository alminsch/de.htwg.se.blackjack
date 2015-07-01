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

public class DealerPanel extends JLayeredPane implements IObserver{

	IController controller;
	JLabel dealervalue;

	public DealerPanel(IController controller) {

		this.controller = controller;
		controller.addObserver(this);
		setdealervalue();
	}

	public void printdealercards() {
		List<Card> dealerCards = controller.getDealerCards();
		int x = 50;
		int idx = 1;
		for(Card c : dealerCards) {
			ImageIcon icon = new ImageIcon("BlackjackImages/" + c.toString() +".png");
			Image img = icon.getImage().getScaledInstance(icon.getIconHeight()-130, icon.getIconWidth()-20,Image.SCALE_FAST);
			JLabel p = new JLabel(new ImageIcon(img));
			p.setBounds(x, 0, 140, 160);
			this.add(p, new Integer(idx));
			x = x+25;
			idx++;
		}
		int cvalue = controller.getCardValue(controller.getDealer());
		dealervalue.setText("Wert: "+ cvalue);
	}

	public void update(GameStatus status) {
		printdealercards();
	}

	private void setdealervalue() {
		dealervalue = new JLabel();
		dealervalue.setBounds(110, 150, 200, 50);
		dealervalue.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
		this.add(dealervalue);
	}

	public void reset() {
		this.removeAll();
		setdealervalue();
	}
}
