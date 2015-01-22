package ConnectFourAnimated;

import java.util.Calendar;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class NewConnectFour extends JPanel 
							implements MouseMotionListener, MouseListener {

	final static int NUM_ROWS = 7;
	final static int NUM_COLS = 7;
	final static int NUM_TO_WIN = 4;
	String blue = "Blue";//blue player's name
	String red = "Red";//red player's name
	int B = 0;//blue score
	int R = 0;//red score
	int games = 3;//games to win, default to 3
	int bMatch = 0;//number of matches won by blue
	int rMatch = 0;//number of matches won by red
	int row;
	int column = 2;
	int x, y;//x and y of mouse input
	char player;
	boolean myClick;//when mouse is clicked
	final static int P_S = 40;//piece size
	boolean showDots;//flashing dots
	
	
	char[][] board = new char[NUM_ROWS][NUM_COLS];

	public void initBoard() {//creates initial board
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				board[i][j] = '.';
			}
		}
	}

	public void addPiece(int row, int col, char piece) {//adds piece to board and repaints
		board[row][col] = piece;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.yellow); // frame is yellow
		g.fillRect(P_S, P_S, NUM_COLS * P_S, NUM_ROWS * P_S); // draw frame
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				switch (board[i][j]) {
				case '.':
					g.setColor(Color.black); // no piece (black)
					break;
				case 'X':
					g.setColor(Color.blue); // X (blue)
					break;
				case 'O':
					g.setColor(Color.red); // O (red)
					break;
				case 'x':
					g.setColor(Color.blue);
					break;
				case 'o':
					g.setColor(Color.red);
					break;
				default:
					g.setColor(Color.white); // shouldn't happen (white)
					break;
				}
				g.fillOval(j * P_S + P_S, ((NUM_ROWS - 1) - i) * P_S + P_S, P_S - 2, P_S - 2);
			}
		}
		if(showDots == true)
		{
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLS; j++) {
					switch (board[i][j]) {
					case 'x':
					case 'o':
						g.setColor(Color.green);
						g.fillOval(j * P_S + P_S + (P_S / 4) - 1, ((NUM_ROWS - 1) - i) * P_S + P_S + (P_S / 4) - 1, P_S / 2, P_S / 2);
						break;
					default:
						break;
					}
				}
			}
		}
		switch(player)
		{
		case 'X':
			g.setColor(Color.blue);
			g.drawString(blue + "'s Turn", P_S * 4 - 15, P_S * 8 + P_S / 2);
			break;
		case 'O':
			g.setColor(Color.red);
			g.drawString(red + "'s Turn", P_S * 4 - 15, P_S * 8 + P_S / 2);
			break;
		case 'x':
			g.setColor(Color.blue);
			if(B >= games)
			{
				g.drawString(blue + " wins the match!", P_S * 2 + P_S / 2, P_S * 8 + P_S / 2);
			}
			else
			{
				g.drawString("The Winner is..." + blue, P_S * 3, P_S * 8 + P_S / 2);
			}
			break;
		case 'o':
			g.setColor(Color.red);
			if(R >= games)
			{
				g.drawString(red + " wins the match!", P_S * 2 + P_S / 2, P_S * 8 + P_S / 2);
			}
			else
			{
				g.drawString("The Winner is..." + red, P_S * 3, P_S * 8 + P_S / 2);
			}
			break;
		case 'D':
			g.setColor(Color.black);
			g.drawString("It is a draw...", P_S * 3, P_S * 8 + P_S / 2);
			break;
		default:
			break;
		}
		//next block draws piece above board
		g.fillOval(x - P_S / 2, 0, P_S - 2, P_S - 2);
		
		g.setColor(Color.blue);
		g.drawString(blue + "'s Score: " + B, P_S, P_S * 9);
		g.drawString("Match Score: " + bMatch, P_S, P_S * 9 + P_S / 3);
		
		g.setColor(Color.red);
		g.drawString(red + "'s Score: " + R, P_S * 6 - P_S / 2, P_S * 9);
		g.drawString("Match Score: " + rMatch, P_S * 6 - P_S / 2, P_S * 9 + P_S / 3);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{	
		x = e.getX();
		y = e.getY();
		//check if we have a valid column
		XYtoColumnRow(x, y);
		if(column >= 0 && column < NUM_COLS && row >= 0 && row <= NUM_ROWS)
		{
			myClick = true;
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void XYtoColumnRow(int x, int y)
	{
		column = (x / P_S) - 1;
		row = ((9 * P_S - y) / P_S) - 1;
	}
	
	public boolean addPieceToColumn()//adding X or O to board after player input
	{
		for(int i = 0; i < NUM_ROWS; i++)
		{
			if(board[i][column] == '.')
			{
				board[i][column] = player;//adds piece to board under player
				return true;
			}
		}
		return false;
	}
	
	public boolean checkForWinner()
	{	//TWO DIFFERENT APPROACHES TO THIS METHOD:
		//approach 1: check current column top to bottom
		//same rules as approach two except never check upleft or upright
		//approach 2: check from last move
		//don't need to check directly up
		//don't check down, downleft, downright if row is less than NUM_TO_WIN - 1
		//don't check left, downleft, upleft if column is less than NUM_TO_WIN - 1
		//don't check right, downright, upright if NUM_TO_WIN is greater than NUM_COLS - column
		//check the conditions that remain
		
		//Using approach 2
		
		for(int i = 0; i < NUM_ROWS; i++)
		{
			row = i;
			for(int j = 0; j < NUM_COLS; j++)
			{
				column = j;
				if(checkDown() || 
						checkLeft() || 
						checkDownLeft() || 
						checkDownRight())//if any of these are true, return true and there's a winner
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkDown()//checks down for winner
	{
		if(row >= NUM_TO_WIN - 1)
		{
			for(int i = 0; i < NUM_TO_WIN; i++)
			{
				if(board[row - i][column] != player)//checking down column for character not player, if NUM_TO_WIN players in a row, there is a winner
				{
					return false;
				}
			}
			if(player == 'X')
			{
				player = 'x';//change to winning blue marker
			}
			else if(player == 'O')
			{
				player = 'o';//change to winning red marker
			}
			//paint the winning pieces green
			for(int i = 0; i < NUM_TO_WIN; i++)
			{
				board[row - i][column] = player;
			}
			return true;
		}
		return false;
	}
	
	public boolean checkLeft()//checking left on row for winner
	{
		if(column >= NUM_TO_WIN - 1)
		{
			for(int i = 0; i < NUM_TO_WIN; i++)
			{
				if(board[row][column - i] != player)
				{
					return false;
				}
			}
			if(player == 'X')
			{
				player = 'x';//change to winning blue marker
			}
			else if(player == 'O')
			{
				player = 'o';//change to winning red marker
			}
			//paint the winning pieces green
			for(int i = 0; i < NUM_TO_WIN; i++)
			{
				board[row][column - i] = player;
			}
			return true;
		}
		return false;
	}
	
	public boolean checkDownLeft()//checking downleft on row and column for winner
	{
		if(column >= NUM_TO_WIN - 1 && row >= NUM_TO_WIN - 1)
		{
			for(int i = 0; i < NUM_TO_WIN; i++)
			{
				if(board[row - i][column - i] != player)
				{
					return false;
				}
			}
			if(player == 'X')
			{
				player = 'x';//change to winning blue marker
			}
			else if(player == 'O')
			{
				player = 'o';//change to winning red marker
			}
			//paint the winning pieces green
			for(int i = 0; i < NUM_TO_WIN; i++)
			{
				board[row - i][column - i] = player;
			}
			return true;
		}
		return false;
	}
	
	public boolean checkDownRight()//checking downright on row and column for winner
	{
		if(column <= NUM_COLS - NUM_TO_WIN && row >= NUM_TO_WIN - 1)
		{
			for(int i = 0; i < NUM_TO_WIN; i++)
			{
				if(board[row - i][column + i] != player)
				{
					return false;
				}
			}
			if(player == 'X')
			{
				player = 'x';//change to winning blue marker
			}
			else if(player == 'O')
			{
				player = 'o';//change to winning red marker
			}
			//paint the winning pieces green
			for(int i = 0; i < NUM_TO_WIN; i++)
			{
				board[row - i][column + i] = player;
			}
			return true;
		}
		return false;
	}
	
	public boolean checkForDraw()//checking for draw
	{
		for(int i = NUM_ROWS - 1; i >= 0; i--)
		{
			for(int j = 0; j < NUM_COLS; j++)
			{
				if(board[i][j] == '.')//if there are still periods on board, continue game
				{
					return false;
				}
			}
		}//if no periods left on board, return true and draw (unless there is a winner once all periods are gone)
		return true;
	}

	public static void main(String[] args) throws InterruptedException {
		
		char lastStarter = 'X';
		
		JFrame frame = new JFrame();//new jframe
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exits when window is closed
		NewConnectFour c4Comp = new NewConnectFour();//creates new connect four called c4comp
		c4Comp.setPreferredSize(new Dimension(P_S * (NUM_COLS + 2), P_S * (NUM_ROWS + 3) - P_S / 2));//sets size of window to one piece_size larger on each side of board
		frame.getContentPane().add(c4Comp, BorderLayout.CENTER);//centers board in window
		
		frame.pack();
		frame.setVisible(true);
		
		frame.addMouseMotionListener(c4Comp);
		frame.addMouseListener(c4Comp);
		
		c4Comp.myClick = false;
		
		c4Comp.blue = JOptionPane.showInputDialog("Input Blue Player's Name");
		c4Comp.red = JOptionPane.showInputDialog("Input Red Player's Name");
		
		while(true)
		{
			c4Comp.initBoard();//clears board
			lastStarter = (lastStarter == 'X') ? 'O' : 'X';//swap starting player from game to game
			c4Comp.player = lastStarter;
			c4Comp.repaint();
			
			while(!c4Comp.checkForWinner() && !c4Comp.checkForDraw())
			{
				c4Comp.player = (c4Comp.player == 'X') ? 'O' : 'X';//each turn, switches player from X (blue) to O (red)
				while(!c4Comp.myClick)
				{
					TimeUnit.MILLISECONDS.sleep(1);//don't know why this is necessary, something about a race condition?
				}
				
				c4Comp.myClick = false;
				
				while(!c4Comp.addPieceToColumn())//if you try to add to a column that's full, try again
				{
					while(!c4Comp.myClick)
					{
						TimeUnit.MILLISECONDS.sleep(1);
					}
					c4Comp.myClick = false;
				}
				c4Comp.repaint();
			}
			if(c4Comp.checkForWinner())//if there's a winner
			{
				if(c4Comp.player == 'x')
				{
					c4Comp.B++;
				}
				else
				{
					c4Comp.R++;
				}
				c4Comp.repaint();
			}
			else if(c4Comp.checkForDraw())//if there's a draw
			{
				c4Comp.player = 'D';
			}
			for(int x = 0; x < 10; x++)//turn dots on and off 10 times
			{
				c4Comp.showDots = true;
				c4Comp.repaint();
				TimeUnit.MILLISECONDS.sleep(250);
				
				c4Comp.showDots = false;
				c4Comp.repaint();
				TimeUnit.MILLISECONDS.sleep(250);
			}
			if(c4Comp.B >= c4Comp.games)
			{
				//blue has won match, reset scores and play again
				c4Comp.showDots = true;
				c4Comp.bMatch++;
				c4Comp.repaint();
				TimeUnit.SECONDS.sleep(5);
				c4Comp.B = 0;
				c4Comp.R = 0;
				c4Comp.showDots = false;
				c4Comp.repaint();
			}
			else if(c4Comp.R >= c4Comp.games)
			{
				//red has won match, reset scores and play again
				c4Comp.showDots = true;
				c4Comp.rMatch++;
				c4Comp.repaint();
				TimeUnit.SECONDS.sleep(5);
				c4Comp.B = 0;
				c4Comp.R = 0;
				c4Comp.showDots = false;
				c4Comp.repaint();
			}
			JOptionPane.showConfirmDialog(c4Comp, "Play again?", "Restart", -1);
		}
	}
}