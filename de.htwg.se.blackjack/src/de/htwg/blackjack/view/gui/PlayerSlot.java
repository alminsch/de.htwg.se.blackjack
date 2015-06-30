package de.htwg.blackjack.view.gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Card;
import de.htwg.blackjack.entities.impl.GameStatus;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.util.observer.IObserver;

public class PlayerSlot extends JLayeredPane implements IObserver {

	private static final long serialVersionUID = 1L;
	private Player player;
	public PlayerSlot(IController controller) {
		controller.addObserver(this);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	private void printPlayerCards() {

		int idx = 1;
		int y = 0;
		for(Card c : player.cardsinhand) {
			ImageIcon icon = new ImageIcon("BlackjackImages/" + c.toString() +".png");
			Image img = icon.getImage().getScaledInstance(icon.getIconHeight()-130, icon.getIconWidth()-20,Image.SCALE_FAST);
			JLabel p = new JLabel(new ImageIcon(img));
			p.setBounds(0, y, 140, 160);
			this.add(p, new Integer(idx));
		    y = y+30;
			idx++;
		}
	}


	public void update(GameStatus e) {
		if(player != null)
			printPlayerCards();
	}
}