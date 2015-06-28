package de.htwg.blackjack.view.gui;

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
	public DealerPanel(IController controller) {

		this.controller = controller;
		controller.addObserver(this);
	}

	public void printdealercards() {
		List<Card> dealerCards = controller.getDealerCards();
		int x = 0;
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
	}

	public void update(GameStatus status) {
		printdealercards();
	}
}
