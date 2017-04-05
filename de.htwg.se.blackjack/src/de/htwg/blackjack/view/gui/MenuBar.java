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
    private JMenuItem newPlayerItem, deletePlayerItem;
    IController controller;
    JFrame blackjackfr;
    NewPlayer newPlayerDialog;
    DeletePlayer deletePlayerDialog;

    public MenuBar(final IController controller, JFrame mainFrame ) {
        newPlayerDialog = new NewPlayer(mainFrame, controller);
        deletePlayerDialog = new DeletePlayer(mainFrame);

        fileMenu = new JMenu("Datei");
        fileMenu.setMnemonic(KeyEvent.VK_D);
        pMenu = new JMenu("Spieler Optionen");

        // newMenuItem
        newMenuItem = new JMenuItem("Neues Spiel");
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                controller.startnewround();
            }
        });
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(newMenuItem);

        // quitMenuItem
        quitMenuItem = new JMenuItem("Beenden");
        quitMenuItem.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        quitMenuItem.setMnemonic(KeyEvent.VK_Q);
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(quitMenuItem);
        fileMenu.add(new JSeparator());

        // newPlayerItem
        newPlayerItem = new JMenuItem("Neuer Spieler");
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
        pMenu.add(newPlayerItem);

        // deletePlayerItem
        deletePlayerItem = new JMenuItem("Neuer Spieler");
        deletePlayerItem.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                deletePlayerDialog.shownewplayerDialog();
                if (deletePlayerDialog.getName() != "") {
                    //controller.addnewPlayer(dp.getName());
                }
            }
        });

        newPlayerItem.setMnemonic(KeyEvent.VK_D);
        newPlayerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                InputEvent.CTRL_DOWN_MASK));
        pMenu.add(newPlayerItem);

        this.add(fileMenu);
        this.add(pMenu);
    }

}
