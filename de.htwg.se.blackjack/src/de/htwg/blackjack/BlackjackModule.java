package de.htwg.blackjack;

import com.google.inject.AbstractModule;

import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.persistence.IPlayersDAO;

public class BlackjackModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(IController.class).to(de.htwg.blackjack.controller.impl.Controller.class);
        bind(IPlayersDAO.class).to(de.htwg.blackjack.persistence.db4o.Db4oPlayersDAO.class);
    }
    
}

