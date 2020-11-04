package jake.ivey.ERS;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
//import java.awt.image.BufferedImage;

public class Card {
/*
 * Author: Jake Ivey
 * Date Created: 9/4/2018
 * Last Updated: 9/11/2018
 */
	
	/*******VARIABLES*******/
	private String rank = "";
	private int suit = -1;
	private int value = 0;
	private Image img = null;
	private static int width = 0;
	private static int height = 0;
	private int x = 0;
	private int y = 0;
	
	//Constructor
	public Card(String rank, int suit, int value, Image img) {
		this.rank = rank;
		this.suit = suit;
		this.value = value;
		this.img = img;
		width = img.getWidth(null);
		height = img.getHeight(null);
	}//end constructor
	
	public String getRank() {
		return rank;
	}
	
	public int getSuit() {
		return suit;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public Image getImage() {
		return img;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}//end setXY
	
	public void addToXY(int changeX, int changeY) {
		x += changeX;
		y += changeY;
	}//end addToXY
	
	public void Draw(Graphics2D g) {
		g.drawImage(img, x, y, null);
		Card.drawOutline(g, x, y);
	}//end Draw
	
	public static void drawOutline(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.drawRoundRect(x, y, width, height, 8, 8);
	}//end drawOutline
	
	public boolean contains(int pointX, int pointY) {
		boolean contains = false;
		
		if(pointX >= x && pointX <= x + width && pointY >= y && pointY <= y + height) contains = true;
		
		return contains;
	}//end contains()
}
