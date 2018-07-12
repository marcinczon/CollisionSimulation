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

	public BallController()
	{
		controllerFXML_MessageTable.appendMessage("Creating ball controller");
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
			for (Ball ball : BALL_OBS_LIST)
			{
				ball.updateStatusBit();
			}
		}
	}

	public void createBall(int posX, int posY, int speedX, int speedY)
	{
		addNewBall(posX, posY, speedX, speedY);

		for (Ball ball : BALL_OBS_LIST)
		{
			ball.updateStatusBit();
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

	public void addNewBall(int posX, int posY, int speedX, int speedY)
	{
		Ball ball = new Ball(BALL_OBS_LIST);
		Random random = new Random();
		ball.setStartPosition(posX, posY);
		ball.setStartSpeed(speedX, speedY);
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
}
