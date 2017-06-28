package de.htwg.blackjack.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.htwg.blackjack.entities.impl.Player;
import de.htwg.blackjack.persistence.IPlayersDAO;

public class HibernatePlayersDAO implements IPlayersDAO {

	@Override
	public Player getPlayer(String name) {
		Session session = HibernateUtil.getInstance().getCurrentSession();
		session.beginTransaction();
		PersistentPlayer player = (PersistentPlayer)session.get(PersistentPlayer.class, name);
		return persPlayerToPlayer(player);
	}

	@Override
	public void savePlayer(Player player) {
		Transaction tx = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			
			PersistentPlayer pPlayer = playerToPersPlayer(player);
			
			session.saveOrUpdate(pPlayer);
			
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			throw new RuntimeException(ex.getMessage());
			}
		}
	}


	@Override
	public void deletePlayer(Player player) {
		Transaction tx = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			
			PersistentPlayer pPlayer = (PersistentPlayer) session.get(PersistentPlayer.class, player.getName());
			session.delete(pPlayer);
			
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) 
				tx.rollback();
			throw new RuntimeException(ex.getMessage());
		}	
	}

	@Override
	public List<Player> getAllPlayers() {
		Transaction tx = null;
		Session session = null;
		List<Player> players = null;
		
		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(PersistentPlayer.class);
			
			@SuppressWarnings("unchecked")
			List<PersistentPlayer> persistenPlayerList = criteria.list();
			players = new ArrayList<Player>();
			
			for(PersistentPlayer pPlayer : persistenPlayerList) {
				Player player = persPlayerToPlayer(pPlayer);
				players.add(player);
			}
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			throw new RuntimeException(ex.getMessage());
		}	
		return players;
	}

	private PersistentPlayer playerToPersPlayer(Player player) {
		if (player == null) {
			return null;
		}
		PersistentPlayer pPlayer = null;
		if(containsPlayer(player)) {
			Session session = HibernateUtil.getInstance().getCurrentSession();
			pPlayer = (PersistentPlayer) session.get(PersistentPlayer.class, player.getName());
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
	
	@Override
	public void closeDb() {
		// NOP
	}
}
