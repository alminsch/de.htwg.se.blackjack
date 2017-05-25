package de.htwg.blackjack.controller.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import de.htwg.blackjack.controller.actors.DealerActor.FinalDealerValue;
import de.htwg.blackjack.entities.IParticipant;
import de.htwg.blackjack.entities.impl.Dealer;
import de.htwg.blackjack.entities.impl.Player;

public class PlayerActor extends AbstractActor {
	private Player player;
	
	static public Props props(Player player) {
		return Props.create(PlayerActor.class, () -> new PlayerActor(player));
	}
	
	public PlayerActor(Player player) {
		this.player = player;
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
			playerResultString = "Spieler: " + player.getName() + ": no change   ";
		} else if (finalplayervalue > 21) {
			player.deletefrombudget(totalplayerbet);
			playerResultString = "Spieler: " + player.getName() + ": lost -" + totalplayerbet;
		} else if (finalplayervalue > fdv.finalDealerValue) {
			player.addtobudget(totalplayerbet);
			playerResultString = "Spieler: " + player.getName() + ": win + " + totalplayerbet;
		} else {
			player.deletefrombudget(totalplayerbet);
			playerResultString = "Spieler: " + player.getName() + ": lost -" + totalplayerbet;
		}
		getSender().tell(new PlayerResult(playerResultString), getSelf());
	}
}
