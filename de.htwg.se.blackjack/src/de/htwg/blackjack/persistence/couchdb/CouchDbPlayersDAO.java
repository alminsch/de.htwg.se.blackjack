package de.htwg.blackjack.persistence.couchdb;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.persistence.IPlayersDAO;

public class CouchDbPlayersDAO implements IPlayersDAO {

	private CouchDbConnector db = null;
	
	// the webinterface to the database: http://lenny2.in.htwg-konstanz.de:5984/_utils/
	public CouchDbPlayersDAO() {
		HttpClient client = null;
		try {
			client = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984").build();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		CouchDbInstance dbInstance = new StdCouchDbInstance(client);
		db = dbInstance.createConnector("blackjack_albe_db", true);
	}

	@Override
	public Player getPlayer(String name) {
		PersistentPlayer pPlayer = db.find(PersistentPlayer.class, name);
		return persPlayerToPlayer(pPlayer);
	}

	@Override
	public void savePlayer(Player player) {
		if (containsPlayer(player)) {
			db.update(playerToPersPlayer(player));
		} else {
			db.create(player.getName(), playerToPersPlayer(player));
		}
	}

	@Override
	public void deletePlayer(Player player) {
		db.delete(playerToPersPlayer(player));
	}

	@Override
	public void closeDb() {
		// NOP
	}

	@Override
	public List<Player> getAllPlayers() {
		List<Player> playerList = new ArrayList<Player>();
		ViewQuery viewQuery = new ViewQuery().allDocs();
		ViewResult viewResult = db.queryView(viewQuery);

		for (Row row : viewResult.getRows()) {
			playerList.add(getPlayer(row.getId()));
		}
		return playerList;
	}

	private PersistentPlayer playerToPersPlayer(Player player) {
		if (player == null) {
			return null;
		}
		PersistentPlayer pPlayer = null;
		if (containsPlayer(player)) {
			pPlayer = (PersistentPlayer) db.find(PersistentPlayer.class, player.getName());
		} else {
			pPlayer = new PersistentPlayer();
			pPlayer.setPlayerName(player.getName());
		}
		pPlayer.setBudget(player.getBudget());
		return pPlayer;
	}

	private Player persPlayerToPlayer(PersistentPlayer perPlayer) {
		Player player = null;
		if (perPlayer != null) {
			player = new Player(perPlayer.getPlayerName());
			player.setBudget(perPlayer.getBudget());
		}
		return player;
	}

	private boolean containsPlayer(Player player) {
		if (getPlayer(player.getName()) == null) {
			return false;
		}
		return true;
	}
}
