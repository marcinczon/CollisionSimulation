package GameObjects;

import java.util.ArrayList;
import java.util.Random;

import Calculations.CollisionsCalculations;
import Calculations.PhysicsCalculations;
import Status.CollisionBits;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import static GameObjects.ScreenParameter.screenParameter;

public class Ball
{

	// Object References
	public static Ball ballBaseReference;
	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;

	// Internal use class
	private BallParameter parameter = new BallParameter();
	private PhysicsCalculations physicsCalculations = new PhysicsCalculations(parameter);
	private CollisionBits collisionBits;

	
	// JavaFX class
	private Circle circle = new Circle();;
	private Label labelNumber = new Label();
	private ArrayList<Node> nodeList = new ArrayList<Node>();

	// Threads
	private Thread threadCollisionDetetion;
	private Thread threadPhysicCalc;

	private Runnable runableCollisionDetetion;
	private Runnable runnablePhysicCalc;

	// General
	Random random = new Random();

	public Ball(ObservableList<Ball> BALL_OBS_LIST_REFERENCE)
	{
		ballBaseReference = this;
		this.BALL_OBS_LIST_REFERENCE = BALL_OBS_LIST_REFERENCE;

		// Set start parameters
		initializeStartParameters();
		setGray();
		initialize();
		calculationsCollision();
	}

	public void initializeStartParameters()
	{
		labelNumber.setText(String.format("%d", parameter.getBaseNumber()));
		parameter.setWeight(random.nextInt(20));
		circle.setRadius(parameter.getWeight());
	}

	public void initialize()
	{
		runnablePhysicCalc = new Runnable()
		{
			public void run()
			{
				synchronized (this)
				{
					while (true)
					{
						try
						{
							physicsCalculations.startCalculatePhysics();
							if (!parameter.isLockPosition())
							{
								parameter.setLastPositionX(parameter.getPositionX());
								parameter.setLastPositionY(parameter.getPositionY());
							}
							updatePosition();
							Thread.sleep(parameter.getThreedTiming());

						} catch (InterruptedException e)
						{
							System.err.println(e.toString());
						}
					}
				}
			}
		};

		threadPhysicCalc = new Thread(runnablePhysicCalc);
		threadPhysicCalc.start();
		threadPhysicCalc.setName(String.format("Thread Physic Calculation %d", parameter.getBaseNumber()));

	}

	public void updatePosition()
	{
		Platform.runLater(new Runnable()
		{
			public void run()
			{
				circle.setCenterX(parameter.getPositionX());
				circle.setCenterY(parameter.getPositionY());
				labelNumber.setLayoutX(parameter.getPositionX());
				labelNumber.setLayoutY(parameter.getPositionY());
			}
		});
	}

	public void calculationsCollision()
	{

		runableCollisionDetetion = new Runnable()
		{
			public void run()
			{
				synchronized (this)
				{
					while (true)
					{
						try
						{

							calculationCollisionFunction();
							Thread.sleep(parameter.getThreedTiming());
						} catch (InterruptedException e)
						{

							e.printStackTrace();
						}
					}
				}
			}
		};

		threadCollisionDetetion = new Thread(runableCollisionDetetion);
		threadCollisionDetetion.start();
		threadCollisionDetetion.setName(String.format("Thread Collision Detection %d", parameter.getBaseNumber()));
	}

	public void calculationCollisionFunction()
	{
		Platform.runLater(new Runnable()
		{
			public void run()
			{
				for (Ball balls : BALL_OBS_LIST_REFERENCE)
				{
					if (balls.getParameter().getBaseNumber() != getParameter().getBaseNumber())
					{
						Shape collisionShape = Shape.intersect((Shape) balls.getCircle(), circle);
						boolean intersectsEmpty = collisionShape.getBoundsInLocal().isEmpty();
						if (!intersectsEmpty && !collisionBits.isOccupied(balls.getParameter().getBaseNumber()))
						{
							CollisionsCalculations.CollisionTwoBall(balls, BALL_OBS_LIST_REFERENCE.get(getParameter().getBaseNumber()));
							collisionBits.setOccupied(balls.getParameter().getBaseNumber());
							balls.collisionBits.setOccupied(parameter.getBaseNumber());
							resetPosition();
						}
						if (intersectsEmpty && collisionBits.isOccupied(balls.getParameter().getBaseNumber()))
						{

							System.out.println(parameter.getBaseNumber() + "Reset Ball");
							collisionBits.resetOccupied(balls.getParameter().getBaseNumber());

						}

					}
				}
			}
		});
	}

	public void updateStatusBit()
	{
		collisionBits.updateRange();
	}

	public void resetPosition()
	{
		parameter.setPositionX(parameter.getLastPositionX());
		parameter.setPositionY(parameter.getLastPositionY());
		updatePosition();
	}

	public void safeCollisionPosition()
	{
		parameter.setLockPosition(true);
	}

	public void resetCollisionPosition()
	{
		parameter.setLockPosition(false);
	}

	// Manual Movments

	public void manualUp()
	{
		parameter.setPositionY(parameter.getLastPositionY() - parameter.getManualDistance());
		updatePosition();
	}

	public void manualDown()
	{
		parameter.setPositionY(parameter.getLastPositionY() + parameter.getManualDistance());
		updatePosition();
	}

	public void manualLeft()
	{
		parameter.setPositionX(parameter.getLastPositionX() - parameter.getManualDistance());
		updatePosition();
	}

	public void manualRight()
	{
		parameter.setPositionX(parameter.getLastPositionX() + parameter.getManualDistance());
		updatePosition();
	}

	// Getters and Setters

	public Circle getCircle()
	{
		return circle;
	}
	public ArrayList<Node> getNodes()
	{
		nodeList.add(circle);
		nodeList.add(labelNumber);
		return nodeList;
	}

	public BallParameter getParameter()
	{
		return parameter;
	}

	public void setStartSpeed(int startSpeedX, int startSpeedY)
	{
		parameter.setVelocityX(startSpeedX);
		parameter.setVelocityY(startSpeedY);
	}

	public void setStartPosition(int startPosX, int startPosY)
	{
		parameter.setPositionX(startPosX);
		parameter.setPositionY(startPosY);
	}
	public void setReferenceBallList(ObservableList<Ball> BALL_OBS_LIST_REFERENCE)
	{
		this.BALL_OBS_LIST_REFERENCE = BALL_OBS_LIST_REFERENCE;
		collisionBits = new CollisionBits(BALL_OBS_LIST_REFERENCE, ballBaseReference);
	}
	public void setGray()
	{
		circle.setFill(Color.GRAY);
	}

	public void setRED()
	{
		circle.setFill(Color.RED);
	}

	public void setBlue()
	{
		circle.setFill(Color.BLUE);
	}

	public CollisionBits getCollisionBits()
	{
		return collisionBits;
	}

}
