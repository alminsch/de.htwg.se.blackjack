package de.htwg.blackjack.view.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.GameStatus;
import de.htwg.blackjack.util.observer.IObserver;

public class PlayerBetInfoPanel extends JPanel implements IObserver {
    private static final long serialVersionUID = 1L;
    private IController controller;
    private JTextField tBet;
    private JTextField tTotal;

    public PlayerBetInfoPanel(IController controller) {
        this.controller = controller;
        controller.addObserver(this);

        this.setBorder(BorderFactory.createTitledBorder("Spieler Wetten"));

        JLabel lBet;
        JLabel lTotal;

        JPanel pbets = new JPanel(new GridLayout(2, 2));

        lBet = new JLabel("  Bet");
        lBet.setFont(new Font("Arial", Font.BOLD, 18));
        pbets.add(lBet);
        tBet = new JTextField("", 6);
        tBet.setEditable(false);
        pbets.add(tBet);

        lTotal = new JLabel("  Total");
        lTotal.setFont(new Font("Arial", Font.BOLD, 18));

        pbets.add(lTotal);
        tTotal = new JTextField("", 6);
        tTotal.setEditable(false);
        pbets.add(tTotal);
        pbets.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        this.add(pbets);
    }

    private void updateBetInformation() {
        tTotal.setText(Integer.toString(controller.getTotalPlayerBet()));
        tBet.setText(Integer.toString(controller.getDisplayBet()));
    }

    public void actionPerformed(ActionEvent e) {
        updateBetInformation();
    }


    @Override
    public void update(GameStatus status) {
    	updateBetInformation();
    }
}