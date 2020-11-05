package snakeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class gamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;	//Higher number means slower

	snakeBody body = new snakeBody(GAME_UNITS, GAME_UNITS, 6);

	int applesEaten;	//snake
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random rand;

	gamePanel(){
		rand = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame(){
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw(g);


	}

	public void draw(Graphics g){

		if(running){
			for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++){
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_HEIGHT, i*UNIT_SIZE);
			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

			for(int i=0; i<body.getBody(); i++){
				if(i == 0){
					g.setColor(Color.green);
					g.fillRect(body.getX(i), body.getY(i), UNIT_SIZE, UNIT_SIZE);
				}
				else{
					g.setColor(new Color(45, 180, 0));
					g.fillRect(body.getX(i), body.getY(i), UNIT_SIZE, UNIT_SIZE);
				}
			}
		}
		else{
			gameOver(g);
		}
	}

	public void newApple(){
		appleX = rand.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = rand.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}

	public void move(){	//snake method
		for(int i = body.getBody(); i > 0; i--){
			body.setXY(i, body.getX(i - 1), body.getY(i - 1));
		}

		switch(direction){
			case 'U':
				body.setY(0, body.getY(0)-UNIT_SIZE);
				//y[0]=y[0]-UNIT_SIZE;
				break;
			case 'D':
				body.setY(0, body.getY(0)+UNIT_SIZE);
				//y[0]=y[0]+UNIT_SIZE;
				break;
			case 'L':
				body.setX(0, body.getX(0)-UNIT_SIZE);
				//x[0]=x[0]-UNIT_SIZE;
				break;
			case 'R':
				body.setX(0, body.getX(0)+UNIT_SIZE);
				//x[0]=x[0]+UNIT_SIZE;
				break;
		}
	}

	public void checkApple(){
		if((body.getX(0) == appleX) && (body.getY(0) == appleY)){
			//bodyParts++;
			body.setBody(body.getBody()+1);
			applesEaten++;
			newApple();
		}
	}

	public void checkCollision(){	//snake method

		//check if collides with body
		for(int i=body.getBody(); i>0; i--){
			if((body.getX(0) == body.getX(i))&& (body.getY(0) == body.getY(i)))			{
				running = false;
			}
		}

		if(body.getX(0) < 0){
			running = false;
		}
		if(body.getX(0) > SCREEN_WIDTH){
			running = false;
		}
		if(body.getY(0) < 0){
			running = false;
		}
		if(body.getY(0) > SCREEN_HEIGHT){
			running = false;
		}

		if(!running){
			timer.stop();
		}
	}

	public void gameOver(Graphics g){
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(running){
			move();
			checkApple();
			checkCollision();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){

			switch(e.getKeyCode()){
				case KeyEvent.VK_LEFT:
					if(direction != 'R'){
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L'){
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if(direction != 'D'){
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if(direction != 'U'){
						direction = 'D';
					}
					break;
			}

		}
	}
	
}