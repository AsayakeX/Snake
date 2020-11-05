package snakeGame;

public class snakeBody{

	final int[] x;
	final int[] y;

	int bodyParts;// = 6;
	//int applesEaten = 0;

	snakeBody(){

		x = new int[0];
		y = new int[0];
		bodyParts = 0;

	}

	snakeBody(int xSize, int ySize, int numParts){
		x = new int[xSize];
		y = new int[ySize];
		bodyParts = numParts;
	}

	public int getX(int index){
		return x[index];
	}

	public int getY(int index){
		return y[index];
	}

	public int getBody(){
		return bodyParts;
	}

	public void setX(int index, int num){
		x[index] = num;
	}

	public void setY(int index, int num){
		y[index] = num;
	}

	public void setXY(int index, int numX, int numY){
		setX(index, numX);
		setY(index, numY);
	}

	public void setBody(int num){
		bodyParts = num;
	}
}