package de.htwg.blackjack.controller.actors;

import java.util.Queue;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import de.htwg.blackjack.entities.impl.Dealer;
import de.htwg.blackjack.entities.impl.Player;

public class EvaluateGameWithActors {
	private static EvaluateGameWithActors eGwA = new EvaluateGameWithActors();
	private String statusLine;
	private ActorSystem system;
	
	public EvaluateGameWithActors() {
		system = ActorSystem.create("blackjackActorsystem");
	}
	public static EvaluateGameWithActors getInstance() {
		return eGwA;
	}
	
	public String getStatusLine() {
		return statusLine;
	}
	
	public void evaluateRound(Queue<Player> playerListPlaying, Dealer dealer ) {
		// create actors
		ActorRef actorPlayerRef[] = new ActorRef[playerListPlaying.size()];
		int idx = 0;
		for (Player player : playerListPlaying) {		
			actorPlayerRef[idx] = system.actorOf(PlayerActor.props(player));
			idx++;
		}
		
		final ActorRef actorDealerRef = system.actorOf(DealerActor.props(dealer));

		actorDealerRef.tell(new DealerActor.CalcDealerValue(actorPlayerRef), ActorRef.noSender());
		
		// TODO: system.terminate(); in try catch??
//		try {
//	    } finally {
//	        system.terminate();
//	    }
	}
	
}
