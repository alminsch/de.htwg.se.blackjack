package de.htwg.blackjack.controller.actors;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.entities.impl.Player;

public class EvaluateGameWithActors {
	private static EvaluateGameWithActors eGwA = new EvaluateGameWithActors();
	private String statusLine;
	private ActorSystem system;

	public EvaluateGameWithActors() {
	}

	public static EvaluateGameWithActors getInstance() {
		return eGwA;
	}

	public String getStatusLine() {
		return statusLine;
	}
	public void setStatusLine(String statusLine) {
		this.statusLine = statusLine;
	}

	static public class RefContainer {
		public final List<ActorRef> actorPlayerRef;
		public final ActorRef actorDealerRef;

		public RefContainer(List<ActorRef> actorPlayerRef, ActorRef actorDealerRef) {
			this.actorPlayerRef = actorPlayerRef;
			this.actorDealerRef = actorDealerRef;
		}
	}

	public void evaluateRound(IController controller) {
		system = ActorSystem.create("blackjackActorsystem");
		List<ActorRef> actorPlayerRef = new ArrayList<ActorRef>();

		final ActorRef actorControllerRef = system.actorOf(ControllerActor.props(controller));
		final ActorRef actorDealerRef = system.actorOf(DealerActor.props(controller.getDealer(), actorControllerRef));
		
		for (Player player : controller.getPlayingPlayerList()) {
			actorPlayerRef.add(system.actorOf(PlayerActor.props(player, actorControllerRef)));
		}
		actorControllerRef.tell(new RefContainer(actorPlayerRef, actorDealerRef), ActorRef.noSender());
	}
	
	public void stopActors() {
		system.terminate();
	}
}
