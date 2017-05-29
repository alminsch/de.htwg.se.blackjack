package de.htwg.blackjack.controller.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import de.htwg.blackjack.controller.actors.ControllerActor.CalcDealerValue;
import de.htwg.blackjack.entities.impl.Dealer;

public class DealerActor extends AbstractActor {

	private final Dealer dealer;
	private final ActorRef controllerActor;

	static public Props props(Dealer dealer, ActorRef controllerActor) {
		return Props.create(DealerActor.class, () -> new DealerActor(dealer, controllerActor));
	}

	public DealerActor(Dealer dealer, ActorRef controllerActor) {
		this.dealer = dealer;
		this.controllerActor = controllerActor;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(CalcDealerValue.class, this::receiveCalcDealerValue)
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
		controllerActor.tell(new ControllerActor.FinalDealerValue(finaldealervalue), getSelf());
	}
}
