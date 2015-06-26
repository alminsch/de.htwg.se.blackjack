package de.htwg.blackjack.view.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
class BackGroundPane extends JPanel {
    Image img = null;

    BackGroundPane(String imagefile) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

        if (imagefile != null) {
            MediaTracker mt = new MediaTracker(this);
            img = Toolkit.getDefaultToolkit().getImage(imagefile);
            mt.addImage(img, 0);
            try {
                mt.waitForAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
    }
}