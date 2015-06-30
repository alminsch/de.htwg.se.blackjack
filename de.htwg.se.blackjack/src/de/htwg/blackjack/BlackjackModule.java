package de.htwg.blackjack;

import com.google.inject.AbstractModule;

import de.htwg.blackjack.controller.IController;

public class BlackjackModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(IController.class).to(
                de.htwg.blackjack.controller.impl.Controller.class);
    }

}

