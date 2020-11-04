package jake.ivey.ERS;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
//import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class DrawDeckImage {
/*
 * Author: Jake Ivey
 * Date Created: 9/7/2018
 * Last Updated: 9/11/2018
 */
	private static String[] ranks = Deck.getRanks();
	private static String[] suits = Deck.getSuitSymbols();
	
	private static int cardWidth = Deck.getCardWidth();
	private static int cardHeight = Deck.getCardHeight();
	private static int imageWidth = cardWidth*13;
	private static int imageHeight = cardHeight*4;
	
	private static BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
	
	public static void main(String[] args) {
		Graphics2D g = img.createGraphics();
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRoundRect(0, 0, imageWidth, imageHeight, 8, 8);

		
		Font font = new Font(Font.DIALOG, Font.BOLD, 24);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		
		
		
		for(int row = 0, y = 0; row < 4; row++, y += cardHeight) {
			
			
			for(int col = 0, x = 0; col < 13; col++, x += cardWidth) {
				
				
				g.setColor(Color.WHITE);
				g.fillRoundRect(x, y, cardWidth - 1, cardHeight - 1, 8, 8);
				g.setColor(Color.BLACK);
				g.drawRoundRect(x, y, cardWidth, cardHeight, 8, 8);
				
				if(row < 2) g.setColor(Color.RED);
				else g.setColor(Color.BLACK);
				
				String rank = ranks[col];
				int rankWidth = fm.stringWidth(rank);
				int fromLeft = x + cardWidth/2 - rankWidth/2;
				int fromTop = y + 20;
				g.drawString(rank, fromLeft, fromTop);
				
				String suit = suits[row];
				int suitWidth = fm.stringWidth(suit);
				fromLeft = x + cardWidth/2 - suitWidth/2;
				fromTop = y + 45;
				g.drawString(suit, fromLeft, fromTop);
				
				
				
			}
		}
		
		
		String fileName = "cards.png";
		File file = new File(fileName);
		
		try {
			ImageIO.write(img, "png", file);
		}
		catch(IOException e) {
			String message = "file caould not be saved";
			JOptionPane.showMessageDialog(null, message);
		}
		
		
	}
	
	
	
};

