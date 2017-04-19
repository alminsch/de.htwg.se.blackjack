package de.htwg.blackjack.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import de.htwg.blackjack.controller.IController;

public class MenuBar extends JMenuBar {

    // JMenuBar
	private static final long serialVersionUID = 4033280135064819188L;
	private JMenu fileMenu;
    private JMenuItem newMenuItem, quitMenuItem;

    private JMenu pMenu;
    private JMenuItem newPlayerItem, deletePlayerItem, playerRankingItem;
    IController controller;
    JFrame blackjackfr;
    NewPlayer newPlayerDialog;
    DeletePlayer deletePlayerDialog;
    PlayerRanking playerRankingDialog;

    public MenuBar(final IController controller, JFrame mainFrame ) {
        newPlayerDialog = new NewPlayer(mainFrame, controller);
        deletePlayerDialog = new DeletePlayer(mainFrame, controller);
        playerRankingDialog = new PlayerRanking(mainFrame, controller);
        
        // newMenuItem
        newMenuItem = new JMenuItem("New Game");
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                controller.startnewround();
            }
        });
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                InputEvent.CTRL_DOWN_MASK));

        // quitMenuItem
        quitMenuItem = new JMenuItem("Quit Game");
        quitMenuItem.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        quitMenuItem.setMnemonic(KeyEvent.VK_Q);
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                InputEvent.CTRL_DOWN_MASK));
        
        // add to fileMenu
        fileMenu = new JMenu("Datei");
        fileMenu.add(newMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(quitMenuItem);

        // newPlayerItem
        newPlayerItem = new JMenuItem("New Player");
        newPlayerItem.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                newPlayerDialog.shownewplayerDialog();
                if (newPlayerDialog.getName() != "") { 
                    controller.addNewPlayer(newPlayerDialog.getName());
                }
            }
        });
        newPlayerItem.setMnemonic(KeyEvent.VK_P);
        newPlayerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
                InputEvent.CTRL_DOWN_MASK));

        // deletePlayerItem
        deletePlayerItem = new JMenuItem("Delete Player");
        deletePlayerItem.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		deletePlayerDialog.shownewplayerDialog();
            }
        });
        deletePlayerItem.setMnemonic(KeyEvent.VK_D);
        deletePlayerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                InputEvent.CTRL_DOWN_MASK));
        
        // playerRankingItem
        playerRankingItem = new JMenuItem("Player Ranking");
        playerRankingItem.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		playerRankingDialog.shownewplayerDialog();
            }
        });
        
        // add to pMenu
        pMenu = new JMenu("Spieler Optionen");
        pMenu.add(newPlayerItem);
        pMenu.add(deletePlayerItem);
        pMenu.add(new JSeparator());
        pMenu.add(playerRankingItem);
        

        this.add(fileMenu);
        this.add(pMenu);
    }

}
