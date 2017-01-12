package de.htwg.blackjack.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.GameStatus;
import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.util.observer.IObserver;


public class BlackjackFrame extends JFrame implements IObserver {
    private Logger logger = Logger.getLogger("de.htwg.blackjack.view.gui");
    private static final long serialVersionUID = 5021430814412159276L;
    private IController controller;
    private StatusPanel statusPanel;
    private PlayerBetInfoPanel pBetInfoPanel;
    private DealerPanel dealerPanel;
    private JPanel contentPane;

    private PlayerSlot[] playerslot;

    private int slotcount = 0;

    // JButtons
    private JButton bHit;
    private JButton bStand;
    //private JButton bDouble;

    private JButton bPlus;
    private JButton bMinus;
    private JButton bSetBet;

    // JMenuBar

    MenuBar mb;
    NewPlayer np;
    PlayerBetInfoPanel bip;
    private BufferedImage img;

    @Inject
    public BlackjackFrame(final IController controller) {

        // setLookAndFeel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.error("Error:", e);
        }

        this.controller = controller;
        controller.addObserver(this);

        np = new NewPlayer(this);

        this.setTitle("Blackjack");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // contentPane
        initImage();
        contentPane = new JPanel() {

            private static final long serialVersionUID = -3044889237354849022L;

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

        // Button Double
        /*bDouble = new JButton("Double");
        bDouble.setBounds(350, 860, 100, 50);
        bDouble.setBorder(BorderFactory.createEmptyBorder());
        bDouble.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                controller.doublebet();
            }
        });
        contentPane.add(bDouble);*/

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

        //MenuBar
        mb = new MenuBar(controller, this);
        this.setJMenuBar(new MenuBar(controller, this));

        int[] x = new int[3];
        int[] y = new int[3];
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

        this.setContentPane(contentPane);
    }

    void initImage() {
        try {
            img = ImageIO.read(BlackjackFrame.class.getResource("/BlackjackImages/BJTisch.png"));
        } catch (Exception e) {
            logger.error("Error:", e);
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
            playerslot[slotcount].setPlayer(player, slotcount + 1, getRandomColor());
            slotcount++;
        }

        if (status == GameStatus.AUSWERTUNG) {
            statusPanel.setText("[STRG-N] für neue Runde");
            constructPane(controller);
        } else {
            statusPanel.setText(controller.getStatus());
            constructPane(controller);
            repaint();
        }
    }

    public Color getRandomColor() {
        int r = (int)(Math.random()*256);
        int g = (int)(Math.random()*256);
        int b= (int)(Math.random()*256);
        Color color = new Color(r, g, b);

        Random random = new Random();
        final float hue = random.nextFloat();
        final float saturation = 0.9f;
        final float luminance = 1.0f;
        color = Color.getHSBColor(hue, saturation, luminance);
        return color;
    }
}

