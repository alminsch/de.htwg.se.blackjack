package de.htwg.blackjack.persistence;

import java.util.List;

import de.htwg.blackjack.entities.impl.Player;

public interface IPlayersDAO {

	Player getPlayer(final String name);
	void savePlayer(Player player);
	void deletePlayer(Player player);
	void closeDb();
	List<Player> getAllPlayers();
	
}
