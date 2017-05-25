package de.htwg.blackjack.view.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Player;

public class DeletePlayer extends JDialog implements ActionListener {

	private static final long serialVersionUID = -3600424586465989179L;
	final IController controller;
    String playerToDelete;
    JButton quitButton = new JButton("Cancel");
    JButton applyButton = new JButton("Delete");
    JComboBox<String> playerList; 

    public DeletePlayer(JFrame mainFrame, final IController controller) {
    	this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 200));
        this.setTitle("New Player");

        this.controller = controller;
        String[] playerNames = {};
        
        // panelL     
        JPanel panelL = new JPanel();
        panelL.setLayout(new BoxLayout(panelL,BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel("Select Player to Delete");
        panelL.add(nameLabel);

        playerList = new JComboBox<String>(playerNames);
        playerList.setEditable(false);
        playerList.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
        		playerToDelete = (String)cb.getSelectedItem();
			}
        });
        
        // delete player
        applyButton.addActionListener(this);
        
        // panel1
        JPanel panel1 = new JPanel();  
        panel1.add(panelL);
        panel1.add(playerList);
        panel1.add(applyButton);
        
        panel1.setBorder(BorderFactory.createEmptyBorder(20,20,4,4));

        // panel3
        JPanel panel3 = new JPanel();
        panel3.add(quitButton);
        quitButton.addActionListener(this);
        

        // combine panels
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(panel1);
        panel.add(new JSeparator(SwingConstants.HORIZONTAL));
        panel.add(panel3);

        this.add(panel);
        setResizable(false);
        pack();
        setLocationRelativeTo(mainFrame);
    }


    public void shownewplayerDialog() {
        playerList.removeAllItems();
        for(Player player : controller.getAllPlayersFromDB()) {
        	playerList.addItem(player.getName()); 	
        }
        playerList.setSelectedIndex(-1);
        playerToDelete = "";
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == applyButton) {
        	if (playerToDelete.equals("")) {
                JOptionPane.showInputDialog(this, "Please select Player to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
            	controller.deletePlayerFromDB(controller.getPlayerFromDB(playerToDelete));
            	playerList.removeItem(playerToDelete);
                playerList.setSelectedIndex(-1);
            }
        }
        if (source == quitButton) {
            setVisible(false);
        }
    }
}