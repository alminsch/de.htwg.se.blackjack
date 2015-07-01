package de.htwg.blackjack.view.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.GameStatus;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.util.observer.IObserver;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.google.inject.Inject;

public class BlackjackFrame extends JFrame implements IObserver {

	private IController controller;
	private StatusPanel statusPanel;
	private PlayerBetInfoPanel pBetInfoPanel;
	private DealerPanel dealerPanel;
	private JPanel contentPane;

	private PlayerSlot playerslot[];

	private int slotcount = 0;

	// JButtons
	private JButton bHit;
	private JButton bStand;
	private JButton bInsurance;
	private JButton bDouble;

	private JButton bPlus;
	private JButton bMinus;
	private JButton bSetBet;

	// JMenuBar
	private JMenuBar menuBar;

	private JMenu fileMenu;
	private JMenuItem newMenuItem, quitMenuItem;

	private JMenu pMenu;
	private JMenuItem newPlayerItem;

	NewPlayer np;
	PlayerBetInfoPanel bip;
	private BufferedImage img;

	@Inject
	public BlackjackFrame(final IController controller) {

		// setLookAndFeel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.controller = controller;
		controller.addObserver(this);

		np = new NewPlayer(this);

		this.setTitle("Blackjack");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// contentPane
		initImage();
		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		contentPane.setLayout(null);

		// Button Hit
		bHit = new JButton("Hit");
		bHit.setBounds(20, 860, 100, 50);
		bHit.setBorder(BorderFactory.createEmptyBorder());
		bHit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.playerhit();
			}
		});
		contentPane.add(bHit);

		// Button Stand
		bStand = new JButton("Stand");
		bStand.setBounds(130, 860, 100, 50);
		bStand.setBorder(BorderFactory.createEmptyBorder());
		bStand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.stand();
			}
		});
		contentPane.add(bStand);

		// Button Insurance
		bInsurance = new JButton("Insurance");
		bInsurance.setBounds(240, 860, 100, 50);
		bInsurance.setBorder(BorderFactory.createEmptyBorder());
		bInsurance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.insurance();
			}
		});
		contentPane.add(bInsurance);

		// Button Double
		bDouble = new JButton("Double");
		bDouble.setBounds(350, 860, 100, 50);
		bDouble.setBorder(BorderFactory.createEmptyBorder());
		bDouble.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.doublebet();
			}
		});
		contentPane.add(bDouble);

		// Button Plus
		bPlus = new JButton("+ 100");
		bPlus.setBounds(1200, 750, 70, 50);
		bPlus.setBorder(BorderFactory.createEmptyBorder());
		bPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.increasebet();
			}
		});
		contentPane.add(bPlus);

		// Button Minus
		bMinus = new JButton("- 100");
		bMinus.setBounds(1200, 800, 70, 50);
		bMinus.setBorder(BorderFactory.createEmptyBorder());
		bMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.decreasebet();
			}
		});
		contentPane.add(bMinus);

		// Button SetBet
		bSetBet = new JButton("Set Bet");
		bSetBet.setBounds(1200, 850, 70, 50);
		bSetBet.setBorder(BorderFactory.createEmptyBorder());
		bSetBet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.setbetforround();
			}
		});
		contentPane.add(bSetBet);

		menuBar = new JMenuBar();
		// fileMenu
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
			public void actionPerformed(ActionEvent event) {
				controller.exit();
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
			public void actionPerformed(ActionEvent event) {
				np.shownewplayerDialog();
				if (np.getName() != "") {
					controller.addnewPlayer(np.getName());
				}
			}
		});
		newPlayerItem.setMnemonic(KeyEvent.VK_P);
		newPlayerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				InputEvent.CTRL_DOWN_MASK));
		pMenu.add(newPlayerItem);
		menuBar.add(fileMenu);
		menuBar.add(pMenu);

		// PlayerBetInfoPanel
		pBetInfoPanel = new PlayerBetInfoPanel(controller);
		Dimension size = pBetInfoPanel.getPreferredSize();
		pBetInfoPanel.setBounds(1280, 750, size.width, size.height);
		contentPane.add(pBetInfoPanel);

		// StatusPanel
		statusPanel = new StatusPanel();
		statusPanel.setBounds(0, 0, 1495, 100);
		contentPane.add(statusPanel);

		// DealerPanel
		JLabel dealername = new JLabel("Dealer");
		dealername.setBounds(700, 50, 100, 50);
		dealername.setFont(new Font("Arial", Font.CENTER_BASELINE, 30));
		contentPane.add(dealername);

		dealerPanel = new DealerPanel(controller);
		dealerPanel.setBounds(600, 100, 500, 500);
		contentPane.add(dealerPanel);

		int x[] = new int[3];
		int y[] = new int[3];
		x[0] = 200;
		x[1] = 600;
		x[2] = 1000;
		y[0] = 500;
		y[1] = 500;
		y[2] = 500;

		playerslot = new PlayerSlot[3];
		for (int i = 0; i < playerslot.length; i++) {
			playerslot[i] = new PlayerSlot(controller);
			playerslot[i].setBounds(x[i], y[i], 500, 500);
			contentPane.add(playerslot[i]);
		}

		this.setJMenuBar(menuBar);
		this.setContentPane(contentPane);
	}

	void initImage() {
		try {
			img = ImageIO.read(new File("BlackJackImages/BJTisch.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final void constructPane(IController controller) {
		dealerPanel.reset();
		for (int i = 0; i < playerslot.length; i++) {
			playerslot[i].reset();
		}
		this.setSize(1500, 1000);
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void update(GameStatus status) {
		if (status == GameStatus.NEW_PLAYER) {
			List<Player> l = controller.getPlayerList();
			Player player = l.get(l.size() - 1);
			playerslot[slotcount].setPlayer(player, slotcount + 1);
			slotcount++;
		}
		if (status == GameStatus.NP_NOPERMISSION) {
			statusPanel.setText("Spieler können nur zu Beginn einer neuen Runde erstellt werden");
			constructPane(controller);
		}

		if (status == GameStatus.AUSWERTUNG) {
			statusPanel.setText("Auswertung");
			constructPane(controller);
		} else {
			statusPanel.setText(controller.getStatus());
			constructPane(controller);
			repaint();
		}
	}
}
