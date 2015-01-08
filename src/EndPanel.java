/**
 * @(#)EndPanel.java
 *
 *
 * @author 
 * @version 1.00 2013/2/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

//the EndPanel is end screen. It shows who won the game and allows the user to play again.
class EndPanel extends JFrame implements ActionListener{
	private JLabel bg;
	private JButton playAgain=new JButton("Play Again");
	private JButton newGame=new JButton("New Game");
	private ImageIcon playerWon;
	private String[] colours={"Cyan","Yellow","Magenta","Green","Blue","Orange"};
	
	public EndPanel(String winnerCol){
		for (String colour: colours){
			if (winnerCol.equals(colour)){
				playerWon=new ImageIcon(colour+"wins.png");
			}
		}
		if(winnerCol.equals("tie")){
			playerWon=new ImageIcon("tie.png");
		}
		JLayeredPane layeredPane=new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(800, 600));

		setSize(800,600);
		bg=new JLabel (playerWon);		
		bg.setBounds(0, 0,800,600);
		layeredPane.add(bg,new Integer(1));
		
		playAgain.setBounds(500,300,150,40);
		layeredPane.add(playAgain,new Integer(2));
		playAgain.addActionListener(this);
		
		
		add(layeredPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent evt){
		Object source = evt.getSource();
		if (source==playAgain){
			Tron frame=new Tron();
			setVisible(false);
		}
	}
}