package GameObjects;

import static Parameters.ScreenParameter.screenParameter;

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
import static FX_Controllers.ControllerFXML_MessageTable.controllerFXML_MessageTable;

public class Ball
{

	// Object References
	public static Ball ballBaseReference;
	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;

	// Internal use class
	private BallParameter parameter = new BallParameter();
	private PhysicsCalculations physicsCalculations = new PhysicsCalculations(parameter);
	private CollisionsCalculations collisionsCalculations = new CollisionsCalculations();
	private CollisionBits collisionBits;

	// JavaFX class
	private Circle circle = new Circle();;
	private Label labelNumber = new Label();

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
		initializeParameters();
		initializePhisicsThreed();
		initializeCollisionThreed();
	}

	public void initializeParameters()
	{
		labelNumber.setText(String.format("%d", parameter.getBaseNumber()));
		parameter.setWeight(random.nextInt(20) + 20);
		circle.setRadius(parameter.getWeight());
		setTransparent();
	}

	public void initializePhisicsThreed()
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
							controllerFXML_MessageTable.appendMessage(e.toString());
						}
					}
				}
			}
		};

		threadPhysicCalc = new Thread(runnablePhysicCalc);
		threadPhysicCalc.start();
		threadPhysicCalc.setName(String.format("Thread Physic Calculation %d", parameter.getBaseNumber()));

	}

	public void initializeCollisionThreed()
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
							collisionsCalculations.CollisionTwoBall(balls, BALL_OBS_LIST_REFERENCE.get(getParameter().getBaseNumber()));
							collisionBits.setOccupied(balls.getParameter().getBaseNumber());
							balls.collisionBits.setOccupied(parameter.getBaseNumber());
						}
						if (intersectsEmpty && collisionBits.isOccupied(balls.getParameter().getBaseNumber()))
						{
							collisionBits.resetOccupied(balls.getParameter().getBaseNumber());
						}

					}
				}
			}
		});
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
		ArrayList<Node> nodeList = new ArrayList<Node>();
		nodeList.add(circle);
		nodeList.add(labelNumber);
		nodeList.addAll(collisionBits.getNodes());
		return nodeList;
	}

	public BallParameter getParameter()
	{
		return parameter;
	}

	public void setWeight(int weight)
	{
		parameter.setWeight(weight);
		circle.setRadius(weight);
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

	public void setRed()
	{
		circle.setFill(Color.RED);
	}

	public void setBlue()
	{
		circle.setFill(Color.BLUE);
	}

	public void setTransparent()
	{
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.TRANSPARENT);
	}

	public CollisionBits getCollisionBits()
	{
		return collisionBits;
	}

	public void setCircle(Circle circle)
	{
		this.circle = circle;
	}

}
