package de.htwg.blackjack.controller.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import de.htwg.blackjack.controller.actors.ControllerActor.FinalDealerValue;
import de.htwg.blackjack.entities.impl.Player;

public class PlayerActor extends AbstractActor {
	private final Player player;
	private final ActorRef controllerActor;
	
	
	static public Props props(Player player, ActorRef controllerActor) {
		return Props.create(PlayerActor.class, () -> new PlayerActor(player, controllerActor));
	}
	
	public PlayerActor(Player player, ActorRef controllerActor) {
		this.player = player;
		this.controllerActor = controllerActor;
	}
	
	static public class PlayerResult {
		public final String resultString;
		public PlayerResult(String resultString) {
			this.resultString = resultString;
		}
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(FinalDealerValue.class, this::receiveFinalDealerValue)
				.build();
	}

	private void receiveFinalDealerValue(FinalDealerValue fdv) {
		int totalplayerbet = player.getplayerbet();
		int finalplayervalue = player.getCardValue();
		String playerResultString;
		if (finalplayervalue == fdv.finalDealerValue) {
			playerResultString = "Player " + player.getName() + ": no change";
		} else if (finalplayervalue > 21) {
			player.deletefrombudget(totalplayerbet);
			playerResultString = "Player " + player.getName() + ": lost -" + totalplayerbet;
		} else if (finalplayervalue > fdv.finalDealerValue) {
			player.addtobudget(totalplayerbet);
			playerResultString = "Player " + player.getName() + ": win +" + totalplayerbet;
		} else {
			player.deletefrombudget(totalplayerbet);
			playerResultString = "Player " + player.getName() + ": lost -" + totalplayerbet;
		}
		controllerActor.tell(new PlayerResult(playerResultString), getSelf());
	}
}
