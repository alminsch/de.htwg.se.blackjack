package de.htwg.blackjack.controller.actors;

import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import de.htwg.blackjack.controller.IController;
import de.htwg.blackjack.controller.actors.EvaluateGameWithActors.RefContainer;
import de.htwg.blackjack.controller.actors.PlayerActor.PlayerResult;

public class ControllerActor extends AbstractActor {

	private final IController controller;
	private List<ActorRef> actorPlayerRef;
	private ActorRef actorDealerRef;
	private int receivePlayerResultCount;
	private StringBuilder sb;

	static public Props props(IController controller) {
		return Props.create(ControllerActor.class, () -> new ControllerActor(controller));
	}

	public ControllerActor(IController controller) {
		this.controller = controller;
		this.receivePlayerResultCount = 0;
		this.sb = new StringBuilder();
	}

	static public class FinalDealerValue {
		public final int finalDealerValue;

		public FinalDealerValue(int finalDealerValue) {
			this.finalDealerValue = finalDealerValue;
		}
	}

	static public class CalcDealerValue {
		public CalcDealerValue() {
		}
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(RefContainer.class, rc -> {
			actorDealerRef = rc.actorDealerRef;
			actorPlayerRef = rc.actorPlayerRef;
			actorDealerRef.tell(new CalcDealerValue(), getSelf());
		}).match(FinalDealerValue.class, fdv -> {
			if (!actorPlayerRef.isEmpty()) {
				for (ActorRef playerRef : actorPlayerRef) {
					playerRef.tell(new FinalDealerValue(fdv.finalDealerValue), getSelf());
				}
			}
		}).match(PlayerResult.class, pr -> {
			receivePlayerResultCount++;
			sb.append(pr.resultString + "    ");
			if (receivePlayerResultCount >= controller.getPlayingPlayerList().size()) {
				EvaluateGameWithActors.getInstance().setStatusLine(sb.toString());
				controller.endOfRound();
				EvaluateGameWithActors.getInstance().stopActors();
			}
		}).build();
	}
}
