package jake.ivey.ERS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import jake.ivey.mycomponents.TitleLabel;

public class ERS extends JFrame {
	/*
	 * Author: Jake Ivey
	 * Date Created: 8/30/18
	 * Last Updated: 9/4/18
	 */
		
		//Serialization of recreation
		private static final long serialVersionUID = 1L;
		
		//create TablePanel
		private TablePanel tablePanel = new TablePanel();
		
		//constructor
		public ERS() {
			initGUI();
			setTitle("ERS (Egyptian Rat Slapping)"); //title
			setResizable(false);  //do not resize window
			pack();    //pack the window
			setLocationRelativeTo(null);   //center the window
			setVisible(true);   //make window visible
			setDefaultCloseOperation(EXIT_ON_CLOSE);  //exit window on close
			
		}
		
		//Initialize GUI - creates GUI
		private void initGUI() {
			TitleLabel gameTitle = new TitleLabel("ERS");
			add(gameTitle, BorderLayout.PAGE_START);
			add(tablePanel, BorderLayout.CENTER);
			
			//button panel
			JPanel btnPanel = new JPanel();
			btnPanel.setBackground(Color.BLACK);
			add(btnPanel, BorderLayout.PAGE_END);
					
			//start btn
			JButton startBTN = new JButton("New Game");
			startBTN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tablePanel.newGame();
				}
			});
			btnPanel.add(startBTN);
			
			JButton helpBTN = new JButton("Help");
			helpBTN.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
					String message = "The objective of this game is to gain all the cards. The main path to obtaining them is through slapping.\n"
							+ "If there are two cards of the same value on top of each other, you can slap the pile and you gain all cards in the pile.\n"
							+ "If there are two cards of the same value seperated by no more that one card, you can slap the pile and gain all the cards in the pile.\n"
							+ "Be careful though, if you slap incorrectly, you have to burn a card to the bottom of the pile.\n"
							+ "\nIf a face card is played, there is a special set of rules that must be followed:\n"
							+ "\nEach face card has an assigned 'value' given to it.\n"
							+ "Ace = 4, King = 3, Queen = 2, Jack = 1\n"
							+ "\nWhen one of these are played, the next player then has that many turns to also play a face card.\n"
							+ "If they do not produce a face card in the alotted turns, the player who played the face card recieves all cards in the pile.\n"
							+ "If they do, the next player then has a respective amount of turns to play a face card.\n"
							+ "This cycle continues until a player is unable to produce a face card and the player who laid the most recent face card recieves the card pile.\n"
							+ "\n"
							+ "First person to lose all of their cards is the loser!";
					JOptionPane.showMessageDialog(ERS.this, message);
				}
			});
			btnPanel.add(helpBTN);
		}	//end initGUI()
		
		
		public static void main(String[] args) {
			try {
				String className = UIManager.getCrossPlatformLookAndFeelClassName();
				UIManager.setLookAndFeel(className);
			}
			catch(Exception e){}
			
			EventQueue.invokeLater(new Runnable(){
				public void run() {
					new ERS();
				} //end run
			}//end Runnable
		);
	

}
}
