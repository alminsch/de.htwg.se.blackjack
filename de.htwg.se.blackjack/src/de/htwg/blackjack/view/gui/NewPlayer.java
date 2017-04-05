package de.htwg.blackjack.view.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.swing.JTextField;

public class NewPlayer extends JDialog implements ActionListener {

    private static final long serialVersionUID = 5557823589405227981L;

    JTextField NameTextField = new JTextField(10);

    JButton npButton = new JButton("Spieler erstellen");
    JButton quitButton = new JButton("Abbrechen");
    private String name;
    JLabel result;
    String currentPattern;

    public NewPlayer(JFrame f) {

        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 500));
        this.setTitle("Neuer Spieler");

        String[] playerNames = {};
        
        JPanel panel1 = new JPanel();

        JLabel nameLabel1 = new JLabel("Enter your name or");
        JLabel nameLabel2 = new JLabel("select one from the list:");

        JComboBox patternList = new JComboBox(playerNames);
        patternList.setEditable(true);
        patternList.addActionListener(this);
        
        panel1.add(nameLabel1);
        panel1.add(nameLabel2);
        panel1.add(patternList);
        //panel1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        
        JPanel panel2 = new JPanel();
        JLabel resultLabel = new JLabel("Player",
                JLabel.LEADING);
        
        result = new JLabel(" ");
        result.setForeground(Color.black);
        result.setBorder(BorderFactory.createCompoundBorder(
        		BorderFactory.createLineBorder(Color.black),
        		BorderFactory.createEmptyBorder(5,5,5,5)
        		));
        panel2.add(resultLabel);
        panel2.add(result);
        
        
        
        JPanel panel3 = new JPanel();
        panel3.add(npButton);
        panel3.add(quitButton);
        npButton.addActionListener(this);
        quitButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);

        this.add(panel);
        setResizable(false);
        pack();
    }

    public void shownewplayerDialog() {
        NameTextField.setText("");

        name = "";
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == npButton) {
            this.name = NameTextField.getText();
            if (name.equals("")) {
                JOptionPane.showMessageDialog(this, "Bitte Name eingeben", "Warnung", JOptionPane.WARNING_MESSAGE);
            } else { setVisible(false); }
        }
        if (source == quitButton) {
            setVisible(false);
        }
    }

    public String getName() { return name; }

}
