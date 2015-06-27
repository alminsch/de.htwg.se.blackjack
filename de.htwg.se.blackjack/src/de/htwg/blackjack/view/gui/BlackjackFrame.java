package de.htwg.blackjack.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.util.observer.Event;
import de.htwg.blackjack.util.observer.IObserver;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.google.inject.Inject;

public class BlackjackFrame extends JFrame implements IObserver {

	private IController controller;
	private Container pane;
	private StatusPanel statusPanel;

	//JButtons
	private JButton bHit;
	private JButton bStand;
	private JButton bInsurance;
	private JButton bDouble;

	private JButton bPlus;
	private JButton bMinus;
	private JButton	bSetBet;

	//JMenuBar
	private JMenuBar menuBar;

	private JMenu fileMenu;
	private JMenuItem newMenuItem, quitMenuItem;

	private JMenu npMenu;
	private JMenuItem newPlayerItem;


	NewPlayer np;
	private BufferedImage img;

	@Inject
	public BlackjackFrame(final IController controller) {

		//setLookAndFeel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.controller = controller;
		controller.addObserver(this);
		np = new NewPlayer(this);
		statusPanel = new StatusPanel();


  		this.setTitle("Blackjack");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//contentPane
		initImage();
		JPanel contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
		        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
			}
		};
		contentPane.setLayout(null);

		//Button Hit
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

		//Button Stand
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

		//Button Insurance
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

		//Button Double
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

		//Button Plus
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

		//Button Minus
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

		//Button SetBet
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


		//JPanel bets

		JTextField tBet;
		JLabel lBet;
		JTextField tTotal;
		JLabel lTotal;

        JPanel pbets = new JPanel(new GridLayout(2, 2));

        lBet = new JLabel("  Bet");
        lBet.setFont(new Font("Arial", Font.BOLD, 18));
        pbets.add(lBet);
        tBet = new JTextField("", 13);
        tBet.setEditable(false);
        pbets.add(tBet);

        lTotal = new JLabel("  Total");
        lTotal.setFont(new Font("Arial", Font.BOLD, 18));

        pbets.add(lTotal);
        tTotal = new JTextField("", 13);
        tTotal.setEditable(false);
        pbets.add(tTotal);
        pbets.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //JPanel header
        JPanel pheader = new JPanel();
        JLabel header = new JLabel("Spieler Wetten ");
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pheader.add(header);
        pheader.setBorder(BorderFactory.createLineBorder(Color.gray));

        //JPanel bets add all Panels
        JPanel pallbets = new JPanel();
        pallbets.setLayout(new BoxLayout(pallbets, BoxLayout.Y_AXIS));

        pallbets.setBorder(BorderFactory.createLineBorder(Color.black));
        pallbets.add(pheader);
        pallbets.add(pbets);
        pallbets.setBounds(1280, 750, 200, 150);

		contentPane.add(pallbets);


        menuBar = new JMenuBar();
        //fileMenu
        fileMenu = new JMenu("Datei");
        fileMenu.setMnemonic(KeyEvent.VK_D);

        npMenu = new JMenu("Neuer Spieler");

        //newMenuItem
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

        //quitMenuItem
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
        npMenu.add(newPlayerItem);
        menuBar.add(fileMenu);
        menuBar.add(npMenu);

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

		this.setSize(1500, 1000);
		this.setResizable(false);
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
