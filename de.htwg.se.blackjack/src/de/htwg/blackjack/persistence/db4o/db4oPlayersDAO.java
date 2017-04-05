package de.htwg.blackjack.persistence.db4o;

import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.persistence.IPlayersDAO;

public class db4oPlayersDAO implements IPlayersDAO {

	private ObjectContainer db;
	
	public db4oPlayersDAO() {
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "db4oplayer.data");
	}
	
	@Override
	public void savePlayer(Player player) {
		db.store(player);
	}
	
	@Override
	public Player getPlayer(final String name) {
		List<Player> player = db.query(new Predicate<Player>() {

			private static final long serialVersionUID = 1L;

			public boolean match(Player player) {
				return (player.getPlayerName().equals(name));
			}
		});
		
		if (player.size() > 0) {
			// should not occur
			return player.get(0);
		}
		return null;
	}
	
	@Override
	public List<Player> getAllPlayers() {
		return db.query(Player.class);
	}
	
	@Override
	public void deletePlayer(Player player) {
		db.delete(player);
	}
	
	@Override
	public void closeDb() {
		db.close();
	}
}
