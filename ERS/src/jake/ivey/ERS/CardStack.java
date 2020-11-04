package jake.ivey.ERS;


//import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class CardStack {
/*
 * Author: Jake Ivey
 * Date Created: 9/11/2018
 * Last Updated: 9/11/2018
 */
	
	ArrayList<Card> cards = new ArrayList<Card>();
	int stackX = 0;
	int stackY = 0;
	int overlap = 0;
	
	public CardStack(int stackX, int stackY, int overlap) {
		this.stackX = stackX;
		this.stackY = stackY;
		this.overlap = overlap;
	}//end constructor
	
	public void add(Card card) {
		int cardX = stackX + overlap * cards.size();
		int cardY = stackY;
		card.setXY(cardX, cardY);
		cards.add(card);
	}//end add
	
	public void addToBeginning(Card card) {
		card.setXY(stackX, stackY);
		cards.add(0, card);
		for(int i = 1; i < cards.size(); i++) {
			Card nextCard = cards.get(i);
			nextCard.addToXY(overlap, 0);
		}//end for
	}//end addtoB
	
	public void draw(Graphics2D g) {
		if(cards.size() > 0 && overlap == 0) {
			int lastIndex = cards.size() - 1;
			Card card = cards.get(lastIndex);
			card.Draw(g);
		} else {
			for(int i = 0; i < cards.size(); i++) {
				Card card = cards.get(i);
				card.Draw(g);
			}//end for
		}//end else
	}//end draw
	
	
	public Card getFirst() {
		int index = 0;
		return cards.get(index);
	}//end getLast
	
	//get last card
	public Card getLast() {
		int index = cards.size() - 1;
		return cards.get(index);
	}//end getLast
	
	public Card getSecondLast() {
		int index = cards.size() - 2;
		return cards.get(index);
	}//end getLast
	
	public Card getThirdLast() {
		int index = cards.size() - 3;
		return cards.get(index);
	}//end getLast
	
	public void removeLast() {
		int index = cards.size() - 1;
		cards.remove(index);
	}//end removeLast
	
	public void removeFirst() {
		int index = 0;
		cards.remove(index);
	}//end removeLast
	
	/******GETTERS AND SETTERS******/
	public int size() {
		return cards.size();
	}
	public int getX() {
		return stackX;
	}
	public int getY() {
		return stackY;
	}
	public void clear() {
		cards.clear();
	}
}
