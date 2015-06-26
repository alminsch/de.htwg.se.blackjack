package de.htwg.blackjack.view.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.util.observer.Event;
import de.htwg.blackjack.util.observer.IObserver;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import com.google.inject.Inject;

public class BlackjackFrame extends JFrame implements IObserver {

	private IController controller;
	private Container pane;
	private StatusPanel statusPanel;

	NewPlayer np;

	@Inject
	public BlackjackFrame(final IController controller) {
		this.controller = controller;
		controller.addObserver(this);

		JMenuBar menuBar;

		JMenu fileMenu;
        JMenuItem newMenuItem, quitMenuItem, newPlayerItem;

  		setTitle("Blackjack");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		pane = getContentPane();
//      pane.setLayout(new BorderLayout());

        setContentPane(new BackGroundPane("BlackJackImages/BJTisch.png"));

        menuBar = new JMenuBar();
        //fileMenu
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //newMenuItem
        newMenuItem = new JMenuItem("Neues Spiel");
        newMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.createnewgame();
			}
        });
//        newMenuItem.setMnemonic(KeyEvent.VK_N);
//        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
//                InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(newMenuItem);

        //quitMenuItem
        quitMenuItem = new JMenuItem("Beenden");
        quitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.exit();
            }
        });
//        quitMenuItem.setMnemonic(KeyEvent.VK_Q);
//        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
//                InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(quitMenuItem);
        fileMenu.add(new JSeparator());
        //newPlayerItem
        newPlayerItem = new JMenuItem("Neuer Spieler");
        newPlayerItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	np.shownewplayerDialog();
            	if(np.getName() != "") {
            		controller.addnewPlayer(np.getName());
            	}
            }
        });
//        newPlayerItem.setMnemonic(KeyEvent.VK_P);
//        newPlayerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
//                InputEvent.CTRL_DOWN_MASK));
        fileMenu.add(newPlayerItem);
        menuBar.add(fileMenu);

        np = new NewPlayer(this);
        statusPanel = new StatusPanel();
        setJMenuBar(menuBar);
//        constructPane(controller);
//        add(new JButton("Button"));
	}

	public final void constructPane(IController controller) {

		this.setSize(1500, 1000);
		this.setVisible(true);
		repaint();
	}

	@Override
	public void update(Event e) {
		statusPanel.setText(controller.getStatus());
//        if (e instanceof SizeChangedEvent) {
        constructPane(controller);
//        }
        repaint();
	}
}
