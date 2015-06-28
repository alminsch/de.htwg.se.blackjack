package de.htwg.blackjack.view.gui;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class StatusPanel extends JPanel {

    private final JLabel statusLabel = new JLabel("");
    private static final long serialVersionUID = 1L;

    public StatusPanel() {
    	this.setOpaque(false);

    	statusLabel.setFont(new Font("Arial", Font.ITALIC, 22));
    	statusLabel.setHorizontalAlignment(JLabel.CENTER);
    	statusLabel.setVerticalAlignment(JLabel.CENTER);

    	this.setBorder(BorderFactory.createEmptyBorder());
        this.add(statusLabel);
    }

    public final void setText(final String text) {
        statusLabel.setText(" " + text);
    }

    public void clear() {
        statusLabel.setText(" ");
    }
}
