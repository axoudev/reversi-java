package reversi.main;

import java.util.ArrayList;

public class EventChannel {
	private ArrayList<Subscriber> subscriberList = new ArrayList();
	
	
	/**
	 * M�thode qui envoie les statistiques � tout les Subscribers
	 * @param stats Correspond aux statistiques de la parties
	 */
	public void publish(GameStats stats) {
		for(Subscriber subscriber : subscriberList) {
			subscriber.fire(stats);
		}
	}
	
	/**
	 * M�thode qui permet d'ajouter un subscriber � l'EventChannel
	 * @param subscriber
	 */
	public void add(Subscriber subscriber) {
		this.subscriberList.add(subscriber);
	}
}
