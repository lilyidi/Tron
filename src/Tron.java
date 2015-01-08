/**class/method comments
 *must explaine what it does, what variables/prameters it cares about
 *
 * @(#)Tron.java
 *Lily Wu
 * @version 1.00 2013/1/22
 */
/*Image ship;
 *ship=new ImageIcon("ship.png").getImage();
 *g.drawImage(ship,sx,sy,this);
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

//the Player class holds information about each player: the colour, the trail that it has made, and the number of boosts it has left
class Player {
	private String colour;
	private int boxx,boxy;
	private ArrayList<Point> trail=new ArrayList<Point>();
	private int boostLeft=3;
	private Color colourCode;
	
	public Player(String [] info, Color colCode){
		boxx=Integer.parseInt(info[0]);
		boxy=Integer.parseInt(info[1]);
		colour=info[2];
		colourCode=colCode;
	}
	public ArrayList<Point> getTrail(){
		return trail;
	}
	public int getX(){
		return boxx;
	}
	public int getY(){
		return boxy;
	}
	public String getColour(){
		return colour;
	}
	public Color getColourCode(){
		return colourCode;
	}
	public void setCol(Color col, String c){
	  colour = c;
	  colourCode = col;
	}
	public void addPos(int x, int y){
		Point point=new Point(x,y);
		trail.add(point);
	}
	public boolean inTrail(int x, int y, Player pEnemy){
		Point point=new Point(x,y);
		if(trail.contains(point) && trail.indexOf(point)!=(trail.size()-1)){
			return true;
		}
		if(pEnemy.getTrail().contains(point)){
			return true;
		}
		return false;
	}
	public void useBoost(){
		boostLeft-=1;
	}
	public int getBoostLeft(){
		return boostLeft;
	}
}
//class Tron is where the user can move from frame to frame, starting with the startMenu, to the ColourMenu,the game, and finally the endPanel
public class Tron extends JFrame implements ActionListener{
	private GamePanel game;
	private JLabel bg;
	private ImageIcon bgPic=new ImageIcon("grid.png");
	private Timer myTimer;
	private JLabel boost1, boost2 ;
	private ImageIcon [] boostPics1=new ImageIcon [4];		//for player 1
	private ImageIcon [] boostPics2=new ImageIcon [4];		//for player 2

    public Tron() {
    	super("Tron");
    	setLayout(null);
    	StartMenu menu=new StartMenu(this);
    	game=new GamePanel();
    	for (int i=0; i<4; i++){
	    	boostPics1[i]=new ImageIcon("1boost"+i+".png");
	    }
	    for (int i=0; i<4; i++){
	    	boostPics2[i]=new ImageIcon("2boost"+i+".png");
	    }
	    boost1=new JLabel("1boost3.png");
	    boost1.setLocation(0,0);
  		boost1.setSize(400,60);
  		
  		boost2=new JLabel("2boost3.png");
  		boost2.setLocation(400,0);
  		boost2.setSize(400,60);
  		
  		setSize(800,600);		
  		bg=new JLabel (bgPic);
  		bg.setSize(800,540);
  		bg.setLocation(0,60);
  		add(bg);
  		add(boost1);
  		add(boost2);
  		add(game);
  		myTimer=new Timer(30,this);
  
  		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    } 
    
    public void actionPerformed(ActionEvent evt){
    	Object source = evt.getSource();
    	
    	if(source==myTimer){
			game.movePlayer1();
			game.movePlayer2();
			setBoost1();
			setBoost2();
    		game.repaint();
    		//End
    		String winner=game.whoWon();
    		if (!winner.equals("")){
    			EndPanel end= new EndPanel(winner);
    			myTimer.stop();
    			setVisible(false);
    		}
    	}
    	
    }
    public void setBoost1(){
    	boost1.setIcon(boostPics1[game.getBoost1()]);
    }

    public void setBoost2(){
    	boost2.setIcon(boostPics2[game.getBoost2()]);
    }
    
    public void setP1Col(Color col, String c){
      game.setP1Col(col, c);
    }
    
    public void setP2Col(Color col, String c){
      game.setP2Col(col, c);
    }
    public void startTimer(){
    	myTimer.start();
    }
  	public void stopTimer(){
  		myTimer.stop();
  	}
    public static void main(String[] args) {
      Tron frame=new Tron();
    }
}

//The GamePanel is where the game takes place. It keeps track of the game.
//It moves the players, shows/checks how many boosts are left for each player, and checks who wins
class GamePanel extends JPanel implements KeyListener{
	private int dx1=5,dy1,dx2=-5,dy2, boxx1, boxy1, boxx2, boxy2;
	private boolean []keys;
	private Player p1, p2;
	private Color p1Col=Color.cyan, p2Col=Color.yellow;
	
	public GamePanel(){
	    keys=new boolean[KeyEvent.KEY_LAST+1];
	    
	    addKeyListener(this);
	    String [] info1={"200","300","Cyan"};
	    String [] info2={"600","300","Yellow"};
	    p1=new Player(info1,p1Col);
	    boxx1=p1.getX();
	    boxy1=p1.getY();
	    
	    p2=new Player(info2,p2Col);
	    boxx2=p2.getX();
	    boxy2=p2.getY();
	    setSize(800,600);
	}
	public void setP1Col(Color col, String c){
	  p1.setCol(col, c);
	}
	public void setP2Col(Color col, String c){
	  p2.setCol(col, c);
	}
	public void addNotify(){
		super.addNotify();
		requestFocus();
	}
	public String whoWon(){
		//if tie
		if ((p1.inTrail(boxx1, boxy1,p2) && p2.inTrail(boxx2,boxy2,p1))||((boxx1==800 || boxx1==0 || boxy1==600 || boxy1==60)&&(boxx2==800 || boxx2==0 || boxy2==600 || boxy2==60))){
			return "tie";
		}
		else if (boxx1==800 || boxx1==0 || boxy1==600 || boxy1==60 || p1.inTrail(boxx1, boxy1,p2)){
			return p1.getColour();
		}
		else if (boxx2==800 || boxx2==0 || boxy2==600 || boxy2==60 || p2.inTrail(boxx2,boxy2,p1)){
			return p2.getColour();
		}
		return "";
	}
	//change the direction of player 1
	public void changeDirection1(int ndx,int ndy){
		dx1=ndx;
		dy1=ndy;
	}
	//change the direction of player 2
	public void changeDirection2(int ndx, int ndy){
		dx2=ndx;
		dy2=ndy;
	}
	//moves player 1
	public void movePlayer1(){
		boxx1 += dx1;
		boxy1 += dy1;
		p1.addPos(boxx1, boxy1);

	}
	//moves player 2
	public void movePlayer2(){
		boxx2+=dx2;
		boxy2+=dy2;		
		p2.addPos(boxx2, boxy2);
	}
	public void keyTyped(KeyEvent e){}
	
	//checks where each player is moving to and if he used a boost based on the keys he presses.
	public void keyPressed(KeyEvent e){
		keys[e.getKeyCode()]=true;
		if(keys[KeyEvent.VK_RIGHT] && dx2!=-5){
			changeDirection2(5,0);			
		}
		if (keys[KeyEvent.VK_LEFT] && dx2!=5){
			changeDirection2(-5,0);
		}
		if (keys[KeyEvent.VK_UP] && dy2!=5){
			changeDirection2(0,-5);
		}
		if (keys[KeyEvent.VK_DOWN] && dy2!=-5){
			changeDirection2(0,5);
		}
		if (keys[KeyEvent.VK_ENTER] && p2.getBoostLeft()>0){
			p2.useBoost();
			for (int i=0; i<10; i++){
				movePlayer2();
			}			
		}
		if (keys[KeyEvent.VK_D] && dx1!=-5){
			changeDirection1(5,0);
		}
		if (keys[KeyEvent.VK_A] && dx1!=5){
			changeDirection1(-5,0);
		}
		if(keys[KeyEvent.VK_W] && dy1!=5){
			changeDirection1(0,-5);
		}
		if (keys[KeyEvent.VK_S] && dy1!=-5){
			changeDirection1(0,5);
		}
		if (keys[KeyEvent.VK_SPACE] && p1.getBoostLeft()>0){
			p1.useBoost();
			for (int i=0; i<10; i++){
				movePlayer1();
			}
		}
	}	
	public int getBoost1(){
		return p1.getBoostLeft();
	}
	public int getBoost2(){
		return p2.getBoostLeft();
	}
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()]=false;
	}
	public void paintComponent(Graphics g1){
     g1.setColor(p1.getColourCode());
     for (Point pos: p2.getTrail()){  
     	g1.fillRect((int)(pos.getX()),(int)(pos.getY()),5,5);
     }
     g1.setColor(p2.getColourCode());
     for (Point pos: p1.getTrail()){
     	g1.fillRect((int)(pos.getX()),(int)(pos.getY()),5,5);
     }
  }
}

