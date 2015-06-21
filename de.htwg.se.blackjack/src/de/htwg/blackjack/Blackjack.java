package de.htwg.blackjack;

import java.util.Scanner;
import org.apache.log4j.PropertyConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.view.tui.TextUI;

public class Blackjack {

	private static Scanner scanner;
    private static TextUI tui;
    private IController controller;
    private static Blackjack instance = null;

    public static Blackjack getInstance() {
        if (instance == null) {
            instance = new Blackjack();
        }
        return instance;
    }

    private Blackjack() {
    	// Set up logging through log4j
        PropertyConfigurator.configure("log4j.properties");

        // Set up Google Guice Dependency Injector
        Injector injector = Guice.createInjector(new BlackjackModule());

        // Build up the application, resolving dependencies automatically by
        // Guice
        controller = injector.getInstance(IController.class);

        //@SuppressWarnings("unused")
        //BlackjackFrame gui = injector.getInstance(BlackjackFrame.class);
        tui = injector.getInstance(TextUI.class);

        //tui.printTUI();

        // Create an initial game
        controller.createnewgame();
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
