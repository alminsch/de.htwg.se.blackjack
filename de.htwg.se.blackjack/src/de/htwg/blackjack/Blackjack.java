package de.htwg.blackjack;

import java.util.Scanner;

import de.htwg.blackjack.controller.Controller;
import de.htwg.blackjack.view.tui.TextUI;

public class Blackjack {
	private static Scanner scanner;
    private static TextUI tui;
    private Controller controller;
    private static Blackjack instance = null;

    public static Blackjack getInstance() {
        if (instance == null) {
            instance = new Blackjack();
        }
        return instance;

    }

    private Blackjack() {

    }

    public static void main(final String[] args) {
    	Blackjack.getInstance();

    	// reads input on the tui continuesly until user quits
    	boolean go = true;
        scanner = new Scanner(System.in);
        while (go) {
            go = tui.userinputselection(scanner.next());
        }
    }
}
