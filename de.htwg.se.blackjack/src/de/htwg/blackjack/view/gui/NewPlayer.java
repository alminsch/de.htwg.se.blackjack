package de.htwg.blackjack.view.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Player;

public class NewPlayer extends JDialog implements ActionListener {

    private static final long serialVersionUID = 5557823589405227981L;

    final IController controller;
    JTextField nameTextField = new JTextField(20);
    JButton applyButton = new JButton("Add Player");
    JButton quitButton = new JButton("Cancel");
    JComboBox<String> playerList; 
    private String name;

    public NewPlayer(JFrame mainFrame, final IController controller) {
    	this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 200));
        this.setTitle("New Player");

        this.controller = controller;
        String[] playerNames = {};
        
        // panel1
        JPanel panel1 = new JPanel();      
        JPanel panelL = new JPanel();
        panelL.setLayout(new BoxLayout(panelL,BoxLayout.Y_AXIS));
        JLabel nameLabel1 = new JLabel("Enter your name or ");
        JLabel nameLabel2 = new JLabel("select one from the list:");
        panelL.add(nameLabel1);
        panelL.add(nameLabel2);

        playerList = new JComboBox<String>(playerNames);
        playerList.setEditable(true);
        playerList.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
                String newSelection = (String)cb.getSelectedItem();
                nameTextField.setText(newSelection);
			}
        });
        
        panel1.add(panelL);
        panel1.add(playerList);
        panel1.setBorder(BorderFactory.createEmptyBorder(20,20,4,4));

        // panel2
        JPanel panel2 = new JPanel();
        JLabel resultLabel = new JLabel("Player",
                JLabel.LEADING);      
       
        panel2.add(resultLabel);
        nameTextField.setEditable(false);
        panel2.add(nameTextField);   
        panel2.setBorder(BorderFactory.createEmptyBorder(20,20,4,4));
        
        // panel3
        JPanel panel3 = new JPanel();
        
        // delete all entities in DB
        JButton deleteAll = new JButton("Delete All");
        deleteAll.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		controller.deleteAllPlayersFromDB();
        		nameTextField.setText("");
                name = "";
                playerList.removeAllItems();
        	}
        });
        
        panel3.add(applyButton);
        panel3.add(Box.createHorizontalStrut(50));
        panel3.add(quitButton);
        applyButton.addActionListener(this);
        quitButton.addActionListener(this);
        

        // combine panels
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(panel1);
        panel.add(panel2);
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
        	playerList.addItem(player.getPlayerName()); 	
        }
        playerList.setSelectedIndex(-1);
        nameTextField.setText("");
        name = "";
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == applyButton) {
            this.name = nameTextField.getText();
            if (name.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter name", "Warning", JOptionPane.WARNING_MESSAGE);
            } else { setVisible(false); }
        }
        if (source == quitButton) {
            setVisible(false);
        }
    }

    public String getName() { return name; }
}