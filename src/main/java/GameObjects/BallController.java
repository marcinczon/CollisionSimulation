package GameObjects;

import static FX_Controllers.ControllerFXML_CollisionTable2.controllerFXML_CollisionTable2;

import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import static GameObjects.GeneralParameters.generalParameters;
public class BallController
{



	static ObservableList<Ball> BALL_OBS_LIST = FXCollections.observableArrayList();

	
	public BallController()
	{
		System.out.println("Creating ball controller");		
	}
	
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
		}
	}

	public void addNewBall()
	{
		Ball ball = new Ball(BALL_OBS_LIST);
		Random random = new Random();
		ball.setStartSpeed(random.nextInt(200) - 100, random.nextInt(200) - 100);
		ball.setStartPosition(random.nextInt(200), random.nextInt(200));
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

	public static ObservableList<Ball> getBallList()
	{
		return BALL_OBS_LIST;
	}

	public static ObservableList<Ball> getBALL_OBS_LIST()
	{
		return BALL_OBS_LIST;
	}
}
