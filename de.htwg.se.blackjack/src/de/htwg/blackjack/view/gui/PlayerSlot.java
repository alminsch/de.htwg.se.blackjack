package de.htwg.blackjack.view.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.LinkedList;

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
    private JLabel playervalue;
    private JLabel playername;
    private JLabel budget;
    private LinkedList<JLabel> cards = new LinkedList<JLabel>();

    public PlayerSlot(IController controller) {
        controller.addObserver(this);
    }

    public void setPlayer(Player player, int i, Color color) {
        this.player = player;
        playervalue = new JLabel();
        playervalue.setBounds(150,100, 200, 50);
        playervalue.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
        this.add(playervalue);
        playername = new JLabel();
        playername.setBounds(150,150,200,50);
        playername.setFont(new Font("Arial", Font.CENTER_BASELINE, 23));
        playername.setText("Spieler " +i+ ": " + player.getPlayerName());
        playername.setForeground(color);
        this.add(playername);
        budget = new JLabel();
        budget.setBounds(150,180,200,50);
        budget.setFont(new Font("Arial", Font.CENTER_BASELINE, 20));
        budget.setText("Budget: " + player.getbudget());
        this.add(budget);
    }

    private void printPlayerCards() {

        int idx = player.getCardsInHand().size();
        int y = 0;
        for(Card c : player.cardsinhand) {
            ImageIcon icon = new ImageIcon("BlackjackImages/" + c.toString() +".png");
            Image img = icon.getImage().getScaledInstance(icon.getIconHeight()-130, icon.getIconWidth()-20,Image.SCALE_FAST);
            JLabel p = new JLabel(new ImageIcon(img));
            p.setBounds(0, y, 140, 160);
            this.add(p,idx);
            cards.add(p);
            y = y+30;
            idx--;
        }
        String s = player.handvalue[0] + " / " + player.handvalue[1];
        playervalue.setText("Wert: "+ s);
        budget.setText("Budget: " + player.getbudget());
    }


    public void update(GameStatus e) {
        if(player != null)
            printPlayerCards();
    }

    public void reset() {
        for(JLabel c : cards) {
            this.remove(c);
        }
    }

    public void remove() {
        this.removeAll();
    }
}
