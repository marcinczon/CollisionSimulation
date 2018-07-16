package GameObjects;

import static FX_Controllers.ControllerFXML_CollisionTable.controllerFXML_CollisionTable;
import static Parameters.GeneralParameters.generalParameters;
import static FX_Controllers.ControllerFXML_MessageTable.controllerFXML_MessageTable;

import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class BallController
{
	static ObservableList<Ball> BALL_OBS_LIST = FXCollections.observableArrayList();

	public void addFewBalls()
	{
		Random rand = new Random();
		for (int i = 0; i < generalParameters.getNewBalls(); i++)
		{
			addNewBall();
			try
			{
				Thread.sleep(rand.nextInt(10));
			} catch (InterruptedException e)
			{

				e.printStackTrace();
			}
			updateStatusBits();
		}
	}

	// Zmienic, podczas klikniecia ma byc widoczna kula a dopiero po puszczriu
	// przycisku ma siê utworzyæ
	public Ball createNewBall()
	{
		return new Ball(BALL_OBS_LIST);
	}

	public Ball addNewBall()
	{
		Ball ball = new Ball(BALL_OBS_LIST);
		Random random = new Random();
		
		ball.setStartSpeed(random.nextInt(200) - 100, random.nextInt(200) - 100);
		ball.setStartPosition(random.nextInt(200), random.nextInt(200));
		
		ball.setReferenceBallList(BALL_OBS_LIST);
		BALL_OBS_LIST.add(ball);
		
		updateStatusBits();

		return ball;
	}

	public Ball addNewBall(int posX, int posY, int speedX, int speedY, int weight)
	{
		Ball ball = new Ball(BALL_OBS_LIST);
		
		ball.setStartPosition(posX, posY);
		ball.setStartSpeed(speedX, speedY);
		ball.setWeight(weight);
		
		ball.setReferenceBallList(BALL_OBS_LIST);
		BALL_OBS_LIST.add(ball);
		
		updateStatusBits();

		return ball;
	}
	
	private void updateStatusBits()
	{
		for (Ball ball : BALL_OBS_LIST)
		{
			ball.updateStatusBit();
		}
	}

	public void removeBallFromList(int index)
	{
		BALL_OBS_LIST.remove(index);
	}

	public ArrayList<Node> getNodeFromBallList()
	{
		ArrayList<Node> nodeList = new ArrayList<Node>();
		nodeList.clear();
		for (Ball ball : BALL_OBS_LIST)
		{
			nodeList.addAll(ball.getNodes());
		}
		return nodeList;
	}

	public static ObservableList<Ball> getBallList()
	{
		return BALL_OBS_LIST;
	}

	public static ObservableList<Ball> getBALL_OBS_LIST()
	{
		return BALL_OBS_LIST;
	}
	public  void  clearBallList()
	{
		BALL_OBS_LIST.clear();
	}
}
