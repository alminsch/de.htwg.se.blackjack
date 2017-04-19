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
		return copyPlayer(player);
	}

	@Override
	public void savePlayer(Player player) {
		Transaction tx = null;
		Session session = null;
		
		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			
			PersistentPlayer pPlayer = copyPlayer(player);
			
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
			
			PersistentPlayer pPlayer = (PersistentPlayer) session.get(PersistentPlayer.class, player.getPlayerName());
			session.delete(pPlayer);
			
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) 
				tx.rollback();
			throw new RuntimeException(ex.getMessage());
		}	
	}

	@Override
	public void closeDb() {
		// NOP
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
				Player player = copyPlayer(pPlayer);
				players.add(player);
			}
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			throw new RuntimeException(ex.getMessage());
		}	
		return players;
	}

	private PersistentPlayer copyPlayer(Player player) {
		PersistentPlayer pPlayer = null;
		if(containsPlayer(player)) {
			Session session = HibernateUtil.getInstance().getCurrentSession();
			pPlayer = (PersistentPlayer) session.get(PersistentPlayer.class, player.getPlayerName());
		} else {
			pPlayer = new PersistentPlayer();
			pPlayer.setPlayerName(player.getPlayerName());
		}
		pPlayer.setBudget(player.getBudget());
		return pPlayer;
	}
	
	private boolean containsPlayer(Player player) {
		if (getPlayer(player.getPlayerName()) == null) {
			return false;
		}
		return true;
	}

	private Player copyPlayer(PersistentPlayer perPlayer) {
		Player player = null;
		if (perPlayer != null) {
			player = new Player(perPlayer.getPlayerName());
			player.setBudget(perPlayer.getBudget());
			//System.out.println("Budget of player in db:" + player.getBudget());
		}
		return player;
	}
}
