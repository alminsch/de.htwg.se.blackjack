package de.htwg.blackjack.controller.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import de.htwg.blackjack.controller.actors.PlayerActor.PlayerResult;
import de.htwg.blackjack.entities.impl.Dealer;

public class DealerActor extends AbstractActor {

	private Dealer dealer;
	private StringBuilder sb;

	static public Props props(Dealer dealer) {
		return Props.create(DealerActor.class, () -> new DealerActor(dealer));
	}

	static public class CalcDealerValue {
		ActorRef actorPlayerRef[];

		public CalcDealerValue(ActorRef actorPlayerRef[]) {
			this.actorPlayerRef = actorPlayerRef;
		}
	}

	public DealerActor(Dealer dealer) {
		this.dealer = dealer;
	}
	
	static public class FinalDealerValue {
		public final int finalDealerValue;
		
		public FinalDealerValue(int finalDealerValue) {
			this.finalDealerValue = finalDealerValue;
		}
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(CalcDealerValue.class, this::receiveCalcDealerValue)
				.match(PlayerResult.class, this::receivePlayerResult)
				.build();
	}

	private void receiveCalcDealerValue(CalcDealerValue cdv) {
		while (dealer.getHandValue()[0] < 17 && dealer.getHandValue()[1] < 17) {
			dealer.actionhit();
		}

		int finaldealervalue = dealer.getCardValue();
		if (finaldealervalue > 21) {
			finaldealervalue = 0;
		}
		
		for (int i = 0; i < cdv.actorPlayerRef.length; i++) {
			cdv.actorPlayerRef[i].tell(new FinalDealerValue(finaldealervalue), getSelf());
		}
	}
	
	private void receivePlayerResult(PlayerResult rpr) {
//		rpr.resultString
	}
}
