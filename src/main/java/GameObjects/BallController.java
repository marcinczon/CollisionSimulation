package GameObjects;

import static FX_Controllers.ControllerFXML_CollisionTable2.controllerFXML_CollisionTable2;

import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class BallController
{
	private int newBalls = 2;

	private int ScreenSizeX = 0;
	private int ScreenSizeY = 0;
	


	static ObservableList<Ball> BALL_OBS_LIST = FXCollections.observableArrayList();

	
	public BallController()
	{
		System.out.println("Creating ball controller");		
	}
	
	public void addFewBalls()
	{
		Random rand = new Random();
		for (int i = 0; i < newBalls; i++)
		{
			addNewBall();
			try
			{
				Thread.sleep(rand.nextInt(10));
			} catch (InterruptedException e)
			{

				e.printStackTrace();
			}
		}
	}

	public void addNewBall()
	{
		Ball ball = new Ball(BALL_OBS_LIST);
		Random random = new Random();
		ball.setMaxPosition(700, 500);
		ball.setStartSpeed(random.nextInt(200) - 100, random.nextInt(200) - 100);
		ball.setStartPosition(random.nextInt(700), random.nextInt(500));
		ball.setReferenceBallList(BALL_OBS_LIST);
		BALL_OBS_LIST.add(ball);
		
	}

	public void removeBallFromList(int index)
	{
		BALL_OBS_LIST.remove(index);
	}

	public ArrayList<Node> getNodeFromBallList()
	{
		ArrayList<Node> nodeList = new ArrayList<Node>();

		for (Ball ball : BALL_OBS_LIST)
		{
			nodeList.addAll(ball.getNodes());
		}
		return nodeList;
	}

	public void updateScreenSize()
	{
		for (Ball ball : BALL_OBS_LIST)
		{
			ball.setMaxPosition(ScreenSizeX, ScreenSizeY);
		}
	}

	public static ObservableList<Ball> getBallList()
	{
		return BALL_OBS_LIST;
	}

	public int getScreenSizeX()
	{
		return ScreenSizeX;
	}

	public void setScreenSizeX(int screenSizeX)
	{
		ScreenSizeX = screenSizeX;
		updateScreenSize();
	}

	public int getScreenSizeY()
	{
		return ScreenSizeY;
	}

	public void setScreenSizeY(int screenSizeY)
	{
		ScreenSizeY = screenSizeY;
		updateScreenSize();
	}

	public static ObservableList<Ball> getBALL_OBS_LIST()
	{
		return BALL_OBS_LIST;
	}
}
