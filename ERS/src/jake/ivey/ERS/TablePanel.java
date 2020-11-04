package jake.ivey.ERS;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class TablePanel extends JPanel {	
/*
 * Author: Jake Ivey
 * Date Created: 8/30/18
 * Last Updated: 9/11/18
 */
	//Serialization
	private static final long serialVersionUID = 1L;
	
	/******* Constants ********/
	private static final int CARDWIDTH = Deck.getCardWidth();
	private static final int CARDHEIGHT = Deck.getCardHeight();
	private static final int SPACING = 4;  //space between cards
	private static final int MARGIN = 10;  // margin around table
	private static final int WIDTH = 13*CARDWIDTH + 12*SPACING + 2*MARGIN;  //width of table
	private static final int HEIGHT = 9*CARDHEIGHT + 3*MARGIN;  //height of table
	private static final int PLAYERHANDX = (WIDTH/2 - (4*CARDWIDTH + 3*SPACING)/2) - 100;
	private static final int PLAYERHANDY = MARGIN;
	private static final int BOARDX = MARGIN;
	private static final int BOARDY = CARDHEIGHT + MARGIN + MARGIN;
	private static final int OVERLAP = (int)(.25*CARDWIDTH);
	
	private Deck deck;
	
	private Card movingCard;
	private Card[] lastCardPlayed = new Card[2];
	
	private int turn = 1;
	private int[] turnCount = new int[2];
	private boolean[] turnExtendedforFaceCard = new boolean[2];
	
	private CardStack cardPile = new CardStack(WIDTH/2 - CARDWIDTH, HEIGHT/2 - CARDHEIGHT, 5);
	private CardStack[] playerHand = new CardStack[2];

	
	public Dimension getPreferredSize() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		return size;
	}
	
	//constructor
	public TablePanel() {
		
		int x = PLAYERHANDX;
		int y = PLAYERHANDY;
		
		//creates new cardstack for each players hand
		for(int i = 0; i < 2; i++) {
			playerHand[i] = new CardStack(x,y,OVERLAP);
			y += HEIGHT - (2*MARGIN + CARDHEIGHT);
		}//end for
		x = BOARDX;
		y = BOARDY;
		
		newGame();
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				clicked(x,y);
				}//end mousePressed
			
		});
		
	}//end constructor
	
	public void newGame() {
		deck = new Deck();
		deal();
	}//end newGame
	
	private void deal() {
		
		for(int i = 0; i < 2; i++) {
			playerHand[i].clear();
		}
		cardPile.clear();
		//deal each player half of the cards
		for(int cards = 0; cards < deck.size() + 26; cards++) {
			for(int hand = 0; hand < 2; hand++) {
				Card card = deck.deal();
				
				playerHand[hand].add(card);
			}//end hand
		}//end cards
		repaint();
	}//end deal
	
	public void paintComponent(Graphics g) {
		//Play area
		Graphics2D g2 = (Graphics2D) g;
			if(cardPile.size() > 0) cardPile.draw(g2);  //if there is a card in the card pile draw it
			else {                                     //else draw the outline
				int x = cardPile.getX();
				int y = cardPile.getY();       
				Card.drawOutline(g2, x, y);
		}
		
		//draw each players hands
		for(int i = 0; i < 2; i++) {
			playerHand[i].draw(g2);
		}
	}
	
	private void playCard(int x,boolean turnExtendedforFaceCard){
		movingCard = playerHand[x].getLast();
		lastCardPlayed[x] = playerHand[x].getLast();
		playerHand[x].removeLast();
		
		movingCard.setXY(cardPile.getX(), cardPile.getY());
		repaint();
		
		cardPile.add(movingCard);
		
		movingCard = null;
		
		if(!turnExtendedforFaceCard) {
			oppositeTurn();
		}
	}
	
	private void clicked(int x, int y) {
		movingCard = null;
		boolean isValidSlap = isValidSlap();                 // is this a valip slap?
		     
		
		if(isValidSlap && cardPile.getLast().contains(x,y)) {
			while(cardPile.size() > 0) {
				Card card = cardPile.getFirst();
				playerHand[1].addToBeginning(card);
				cardPile.removeFirst();
				repaint();
			}
			turnCount[0] = 0;
			turnCount[1] = 0;
			turnExtendedforFaceCard[1] = false; 
			turnExtendedforFaceCard[0] = false;
			for(int i = 0; i < 2; i++) {
				lastCardPlayed[i] = null;
			}
			turn = 1;
		}
		
		if(turn == 1) { //what can I do on my turn?
			if(lastCardPlayed[0] != null && (lastCardPlayed[0].getValue() > 9 || lastCardPlayed[0].getValue() == 0)) {
				turnExtendedforFaceCard[1] = true;
				if(lastCardPlayed[0].getValue() == 0) turnCount[1] = 4;
				else if(lastCardPlayed[0].getValue() == 12) turnCount[1] = 3;
				else if(lastCardPlayed[0].getValue() == 11) turnCount[1] = 2;
				else if(lastCardPlayed[0].getValue() == 10) turnCount[1] = 1;
			}
			if(playerHand[1].getLast().contains(x,y)) {  //play a card
				playCard(1, turnExtendedforFaceCard[1]);
				
				//isGameOver();
				
				if(turnExtendedforFaceCard[1]) { // do i need to try and match a face card?
					if (lastCardPlayed[1] != null && (lastCardPlayed[1].getValue() == 0 || lastCardPlayed[1].getValue() > 9)) { //matched it
						turnExtendedforFaceCard[1] = false;
						//turnExtendedforFaceCard[0] = true;
						turn = 0;
						turnCount[1] = -1;
					}
					else if(lastCardPlayed[1] != null && (0 < lastCardPlayed[1].getValue() && lastCardPlayed[1].getValue() < 10)) {//didnt get it yet
						turnCount[1]--;
					}
					if(turnCount[1] == 0) {//couldnt do it, other player gets cards
						while(cardPile.size() > 0) {
							Card card = cardPile.getFirst();
							playerHand[0].addToBeginning(card);
							cardPile.removeFirst();
							repaint();
						}
						turnExtendedforFaceCard[1] = false;
						for(int i = 0; i < 2; i++) {
							lastCardPlayed[i] = null;
						}
						turn = 0;
					}
					
				}
				//turn = 0;
				}
			//turn = 0; 
			}//end if turn
			else { player2(x, y);}
			isGameOver();
	}//end clicked
	
	public boolean isValidSlap() {
		boolean isValid = false;
		if(cardPile.size() > 1 && (cardPile.getLast().getValue() == cardPile.getSecondLast().getValue())) isValid = true;
		if(cardPile.size() > 2 && (cardPile.getLast().getValue() == cardPile.getThirdLast().getValue())) isValid = true;
		return isValid;
	}
	
	public void player2(int x, int y) {
		
		if(turn == 0) {
			if(lastCardPlayed[1] != null && (lastCardPlayed[1].getValue() > 10 || lastCardPlayed[1].getValue() == 0)) {
				turnExtendedforFaceCard[0] = true;
				if(lastCardPlayed[1].getValue() == 0) turnCount[0] = 4;
				else if(lastCardPlayed[1].getValue() == 12) turnCount[0] = 3;
				else if(lastCardPlayed[1].getValue() == 11) turnCount[0] = 2;
				else if(lastCardPlayed[1].getValue() == 10) turnCount[0] = 1;
			}
			if(playerHand[0].getLast().contains(x,y)) { //play a card
				playCard(0, turnExtendedforFaceCard[0]);
			
				//isGameOver();
			
				if(turnExtendedforFaceCard[0]) { // do i need to try and match a face card?
					if ( lastCardPlayed[0] != null && (lastCardPlayed[0].getValue() == 0 || lastCardPlayed[0].getValue() > 9)) { //matched it
						turnExtendedforFaceCard[0] = false;
						//turnExtendedforFaceCard[1] = true;
						turn = 1;
						turnCount[0] = -1;
					}
					else if(lastCardPlayed[0] != null && (0 < lastCardPlayed[0].getValue() && lastCardPlayed[0].getValue() < 10)) {//didnt get it yet
						turnCount[0]-- ;
					}
					if(turnCount[0] == 0) {//couldnt do it, other player gets cards
						while(cardPile.size() > 0) {
							Card card = cardPile.getFirst();
							playerHand[1].addToBeginning(card);
							cardPile.removeFirst();
							repaint();
						}
						turnExtendedforFaceCard[0] = false;
						for(int i = 0; i < 2; i++) {
							lastCardPlayed[i] = null;
						}
						turn = 1;
					}
				
				}
		//turn = 1;
			}
			//turn = 1;
		}
	}
	
	public void oppositeTurn() {
		if(turn == 1) turn = 0;
		else if(turn == 0) turn = 1;
	}
	
	public void isGameOver() {
		boolean gameOver = false;
		int loser = -1;
		for(int i = 0; i < 2 && gameOver == false; i++) {
			if(playerHand[i].size() == 0) { 
				gameOver = true;
				loser = i;
				}
		}
		
		if(gameOver) {
			String message = "Player" + loser + "has lost the game. Would you like to play again?";
		
		//dialog for play again
			int option = JOptionPane.showConfirmDialog(this, message, "Play again?", JOptionPane.YES_NO_OPTION );
		
		
		//check user response
			if(option == JOptionPane.YES_OPTION)  newGame();
			else System.exit(0);
		}//end isGameOver
	}
}//end TablePanel
