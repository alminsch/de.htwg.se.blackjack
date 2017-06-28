package de.htwg.blackjack.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Player;

public class PlayerRanking extends JDialog {
	
	private static final long serialVersionUID = -3354122585315273459L;
	final IController controller;
	private final JTable table;
	private DefaultTableModel tableModel = new DefaultTableModel();

    public PlayerRanking(JFrame mainFrame, final IController controller) {
    	this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(640, 480));
        this.setTitle("Player Ranking");

        this.controller = controller;
        
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        
        
        // quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {

        	}
        });
         
        setResizable(false);
        pack();
        setLocationRelativeTo(mainFrame);
    }
    
    public void shownewplayerDialog() {
    	loadData();
        setVisible(true);
    }
    
    private void loadData() {
    	// Names of columns
        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Rank");
        columnNames.add("Player");
        columnNames.add("Budget");
        
        // Data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        
        int idx = 1;
        Map<String, Integer> mapToSort = new HashMap<String, Integer>();
        for (Player player : controller.getAllPlayersFromDB()) {
        	mapToSort.put(player.getName(), player.getBudget());
        }
        List<Map.Entry<String, Integer>> sortedList = sortList(mapToSort);
        
        for(Entry<String, Integer> player : sortedList) {
        	 Vector<Object> vector = new Vector<Object>();
        	 vector.add(idx);
        	 vector.add(player.getKey());
        	 vector.add(player.getValue());
        	 data.add(vector);
        	 idx++;
        	 if(idx == 21) {
        		 break;
        	 }
        }

        tableModel.setDataVector(data, columnNames);
    }
    
    private List<Map.Entry<String, Integer>> sortList(Map<String, Integer> mapToSort) {
    
	    Comparator<Map.Entry<String, Integer>> byMapValues = new Comparator<Map.Entry<String, Integer>>() {
	    	@Override
	        public int compare(Map.Entry<String, Integer> left, Map.Entry<String, Integer> right) {
	            return right.getValue().compareTo(left.getValue());
	        }
	    };
	
	    List<Map.Entry<String, Integer>> sortedList = new ArrayList<Map.Entry<String, Integer>>();

	    sortedList.addAll(mapToSort.entrySet());

	    Collections.sort(sortedList, byMapValues);
	    return sortedList;
    }

}