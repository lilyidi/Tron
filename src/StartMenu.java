
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

//the StartMenu introduces the game (the starting screen) and leads into the colourMenu 
class StartMenu extends JFrame implements ActionListener{
	private JLabel bg;
	private ImageIcon startPic=new ImageIcon("GridStart.png");
	private JButton start=new JButton("Start");
	private ColourMenu colourMenu;
	
	public StartMenu(Tron cm){
		colourMenu= new ColourMenu(cm);
		
		JLayeredPane layeredPane=new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(800, 600));

		setSize(800,600);
		bg=new JLabel (startPic);		
		bg.setBounds(0, 0,800,600);
		layeredPane.add(bg,new Integer(1));
		
		start.setBounds(500,300,150,40);
		layeredPane.add(start,new Integer(2));
		start.addActionListener(this);
		
		add(layeredPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent evt){
    	Object source = evt.getSource();
    	if (source==start){
    		colourMenu.setVisible(true);
    		setVisible(false);
    	}
	}
}
//this class allows the players to choose the colours they wish to use using radio buttons for each player to select.
class ColourMenu extends JFrame implements ActionListener{
	private String[] colours={"Cyan","Yellow","Magenta","Green","Pink","Orange"};
	private Color [] colourCodes={Color.cyan,Color.yellow,Color.magenta,Color.green, Color.pink, Color.orange};
	private JRadioButton [] colourBtns1=new JRadioButton [6];		//for player 1
	private JRadioButton [] colourBtns2=new JRadioButton [6];		//for player 2
	private JButton play=new JButton("Play");
	private Color p1ColCode, p2ColCode;
	private Tron tron;
	
	public ColourMenu(Tron game){
		tron=game;
		setSize(800,600);
		JPanel colourPanel1=new JPanel();
		JPanel p1TitlePane=new JPanel();
		JPanel colourPanel2=new JPanel();
		JPanel p2TitlePane=new JPanel();
		JPanel playPanel=new JPanel();
		GridLayout colourgrid = new GridLayout(3, 2, 10, 10);
		FlowLayout flow = new FlowLayout (FlowLayout.LEFT);
		BoxLayout box = new BoxLayout (getContentPane (), BoxLayout.Y_AXIS);
		
		setLayout(box);
		p1TitlePane.setLayout(flow);
		p1TitlePane.add(new JLabel("Player 1 (A-D-W-S, SPACE for boosts) Choose Colour:"));
		p2TitlePane.setLayout(flow);
		p2TitlePane.add(new JLabel("Player 2 (Arrow Keys, ENTER for boosts) Choose Colour:"));
		playPanel.setLayout(flow);
		for (int i=0; i<6; i++){
			colourBtns1[i]= new JRadioButton(colours[i], false);
			colourBtns2[i]=new JRadioButton(colours[i],false);
		}
		colourBtns1[0]=new JRadioButton(colours[0],true);
		colourBtns2[1]=new JRadioButton(colours[1],true);
		
		//Player 1's options
		ButtonGroup colourP1=new ButtonGroup();
		colourPanel1.setLayout(colourgrid);
		colourP1.add (colourBtns1 [0]);
		colourP1.add (colourBtns1 [1]);
		colourP1.add (colourBtns1 [2]);
		colourP1.add (colourBtns1 [3]);
		colourP1.add (colourBtns1 [4]);
		colourP1.add (colourBtns1 [5]);
		add(p1TitlePane);
		colourPanel1.add (colourBtns1 [0]);
		colourPanel1.add (colourBtns1 [1]);
		colourPanel1.add (new JLabel ("  "));
		colourPanel1.add (colourBtns1 [2]);
		colourPanel1.add (colourBtns1 [3]);
		colourPanel1.add (new JLabel ("  "));
		colourPanel1.add (colourBtns1 [4]);
		colourPanel1.add (colourBtns1 [5]);
		colourPanel1.add (new JLabel ("  "));
		add(colourPanel1);
		
		//Player 2's options
		ButtonGroup colourP2=new ButtonGroup();
		colourPanel2.setLayout(colourgrid);
		colourP2.add (colourBtns2 [0]);
		colourP2.add (colourBtns2 [1]);
		colourP2.add (colourBtns2 [2]);
		colourP2.add (colourBtns2 [3]);
		colourP2.add (colourBtns2 [4]);
		colourP2.add (colourBtns2 [5]);
		add(p2TitlePane);
		colourPanel2.add (colourBtns2 [0]);
		colourPanel2.add (colourBtns2 [1]);
		colourPanel2.add (new JLabel ("  "));
		colourPanel2.add (colourBtns2 [2]);
		colourPanel2.add (colourBtns2 [3]);
		colourPanel2.add (new JLabel ("  "));
		colourPanel2.add (colourBtns2 [4]);
		colourPanel2.add (colourBtns2 [5]);
		colourPanel2.add (new JLabel ("  "));		
		add(colourPanel2);
			
		play.addActionListener(this);
		playPanel.add(play);
		add(playPanel);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		pack ();
		setBackground(new Color(0,0,0));
	}
	public void actionPerformed(ActionEvent evt){
		Object source=evt.getSource();
		
		if (source==play){
    		if (player1Selected().equals(player2Selected())){
				JOptionPane.showMessageDialog (null, "The two players must have different colours!");
    		}
    		else{
    			tron.setVisible(true);
    			tron.startTimer();
    			setVisible(false);
    		}
		}
	}
	public String player1Selected(){
		for (int i=0; i<6; i++){
			if (colourBtns1[i].isSelected()){
			  tron.setP1Col(colourCodes[i], colours[i]);
				return colours[i];
			}
		}
		return colours[0];
	}
	public String player2Selected(){
		for (int i=0; i<6; i++){
			if (colourBtns2[i].isSelected()){
			  tron.setP2Col(colourCodes[i], colours[i]);
				return colours[i];
			}
		}
		return colours[1];
	}
	public void paintComponent(Graphics g){
     g.setColor(p1ColCode);
     g.fillRect(700,100,50,50);
     g.setColor(p2ColCode);
     g.fillRect(700,500,50,50);
	}

}