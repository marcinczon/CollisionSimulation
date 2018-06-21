package GameObjects;

import java.util.ArrayList;
import java.util.Random;

import Calculations.CollisionsCalculations;
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

public class Ball
{
	public static Ball ballBaseReference;
	private static int ballNumbers;
	private int ballNumber;

	private Circle ball;
	private Label labelNumber = new Label();
	private CollisionBits collisionBits;
	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;

	private Color color;

	// Threads
	Thread threadCollisionDetetion;
	Thread threadPhysicCalc;

	Runnable runableCollisionDetetion;
	Runnable runnablePhysicCalc;

	// Screen Size -> change to automatic
	private double ScreenMinX = 0;
	private double ScreenMaxX = 0;
	private double ScreenMinY = 0;
	private double ScreenMaxY = 0;

	// Calculation variable
	private int Weight;
	private int ThreedTiming = 20;
	private double Timing = 0.05;
	private double Gravity = 9.8;
	private double VxDecline = 10;
	private double VyDecline = 10;

	// For axes X
	private double PositionX = 0;
	private double PositionXActual = 0;
	private double deltaSx = 0;

	private double VxActual = 0;
	private double Vx0 = 0;

	// For axes Y
	private double PositionY = 0;
	private double PositionYActual = 0;
	private double deltaSy = 0;

	private double VyActual = 0;
	private double Vy0 = 0;
	private double Vy1 = 0;

	// Property to connect with table
	IntegerProperty P_BallNumerProperty = new SimpleIntegerProperty();
	IntegerProperty P_PosXProperty = new SimpleIntegerProperty();
	IntegerProperty P_PosYProperty = new SimpleIntegerProperty();
	IntegerProperty P_VxActualProperty = new SimpleIntegerProperty();
	IntegerProperty P_VyActualProperty = new SimpleIntegerProperty();

	public Ball(ObservableList<Ball> BALL_OBS_LIST_REFERENCE)
	{
		ballBaseReference = this;
		this.BALL_OBS_LIST_REFERENCE = BALL_OBS_LIST_REFERENCE;
		Random random = new Random();
		ball = new Circle();

		ballNumber = ballNumbers;
		ballNumbers++;
		P_BallNumerProperty.set(ballNumber);
		labelNumber.setText(String.format("%d", ballNumber));

		// Set start parameters
		Weight = random.nextInt(20) + 10;
		ball.setRadius(Weight);
		setGray();
		// ball.setFill(Color.color(Math.random(), Math.random(), Math.random()));

		initialize();
		calculationsCollision();
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
							calculationPhisicsX();
							calculationPhisicsY();
							updatePosition();
							// calculationsCollision();

							Thread.sleep(ThreedTiming);

						} catch (InterruptedException e)
						{
							System.err.println(e.toString());
							System.out.println("Blad w Starcie Pilki");
						}
					}
				}
			}
		};

		threadPhysicCalc = new Thread(runnablePhysicCalc);
		threadPhysicCalc.start();
		threadPhysicCalc.setName(String.format("Thread Physic Calculation %d", this.ballNumber));

	}

	public void updatePosition()
	{
		Platform.runLater(new Runnable()
		{
			public void run()
			{
				ball.setCenterX(PositionXActual);
				ball.setCenterY(PositionYActual);
				labelNumber.setLayoutX(PositionXActual);
				labelNumber.setLayoutY(PositionYActual);

				P_PosXProperty.set((int) PositionXActual);
				P_PosYProperty.set((int) ScreenMaxY - (int) PositionYActual);
				P_VxActualProperty.set((int) VxActual);
				P_VyActualProperty.set((int) VyActual);

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
							Thread.sleep(ThreedTiming);
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
		threadCollisionDetetion.setName(String.format("Thread Collision Detection %d", this.ballNumber));
	}

	public void calculationCollisionFunction()
	{
		Platform.runLater(new Runnable()
		{
			public void run()
			{
				for (Ball balls : BALL_OBS_LIST_REFERENCE)
				{
					if (balls.getBallNumber() != getBallNumber())
					{
						Shape collisionShape = Shape.intersect((Shape) balls.getBall(), ball);
						boolean intersects = collisionShape.getBoundsInLocal().isEmpty();
						if (!intersects && !collisionBits.isOccupied(balls.getBallNumber()))
						{
							CollisionsCalculations.CollisionTwoBall(balls, BALL_OBS_LIST_REFERENCE.get(getBallNumber()));
							collisionBits.setOccupied(balls.getBallNumber());
							balls.collisionBits.setOccupied(ballNumber);
						}
						if (intersects && collisionBits.isOccupied(balls.getBallNumber()))
						{
							collisionBits.resetOccupied(balls.getBallNumber());
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

	private void calculationPhisicsX()
	{
		Vx0 = VxActual;
		deltaSx = Vx0 * Timing;
		PositionX = PositionX + deltaSx;

		if (PositionX >= ScreenMaxX)
		{
			PositionX = ScreenMaxX;
			Vx0 = -(Vx0 - VxDecline);
		}
		if (PositionX <= ScreenMinX)
		{
			Vx0 = -(Vx0 + VxDecline);
			PositionX = 0;
		}
		VxActual = Vx0;
		PositionXActual = PositionX;
	}

	public void calculationPhisicsY()
	{
		Vy0 = VyActual;
		Vy1 = Vy0 + Gravity * Timing;
		Vy0 = Vy1;
		deltaSy = (Vy0 * Timing + (Gravity * Timing * Timing) / 2);
		PositionY = PositionY + deltaSy;

		if (PositionY >= ScreenMaxY)
		{
			PositionY = ScreenMaxY;
			Vy0 = -(Vy0 - VyDecline);
		}
		if (PositionY <= ScreenMinY)
		{
			Vy0 = -Vy0;
			PositionY = 0;
		}
		VyActual = Vy0;
		PositionYActual = PositionY;
	}

	// Manual Movments

	public void manualUp()
	{
		PositionY = PositionYActual = PositionYActual - 10;
		updatePosition();
	}

	public void manualDown()
	{
		PositionY = PositionYActual = PositionYActual + 10;
		updatePosition();
	}

	public void manualLeft()
	{
		PositionX = PositionXActual = PositionXActual - 10;
		updatePosition();
	}

	public void manualRight()
	{
		PositionX = PositionXActual = PositionXActual + 10;
		updatePosition();
	}

	// Table Properties - !! REMEMBER add __Property to name of method !!

	public IntegerProperty P_BallNumberProperty()
	{
		return P_BallNumerProperty;
	}

	public IntegerProperty P_PosXProperty()
	{
		return P_PosXProperty;
	}

	public IntegerProperty P_PosYProperty()
	{
		return P_PosYProperty;
	}

	public IntegerProperty P_VxActualProperty()
	{
		return P_VxActualProperty;
	}

	public IntegerProperty P_VyActualProperty()
	{
		return P_VyActualProperty;
	}

	// Getters and Setters

	public Node getBall()
	{
		return ball;
	}

	public Circle getShape()
	{
		return ball;
	}

	public ArrayList<Node> getNodes()
	{
		ArrayList<Node> getNodes = new ArrayList<Node>();
		getNodes.add(ball);
		getNodes.add(labelNumber);
		return getNodes;
	}

	public int getBallNumber()
	{
		return ballNumber;
	}

	public void setMaxPosition(int maxX, int maxY)
	{
		this.ScreenMaxX = maxX;
		this.ScreenMaxY = maxY;
	}

	public void setStartSpeed(int startSpeedX, int startSpeedY)
	{
		this.VxActual = startSpeedX;
		this.VyActual = startSpeedY;
	}

	public void setStartPosition(int startPosX, int startPosY)
	{
		this.PositionX = startPosX;
		this.PositionY = startPosY;
	}

	public double getVxActual()
	{
		return VxActual;
	}

	public void setVxActual(double vxActual)
	{
		VxActual = vxActual;
	}

	public double getVyActual()
	{
		return VyActual;
	}

	public void setVyActual(double vyActual)
	{
		VyActual = vyActual;
	}

	public int getSize()
	{
		return Weight;
	}

	public void setSize(int size)
	{
		this.Weight = size;
	}

	public void setReferenceBallList(ObservableList<Ball> BALL_OBS_LIST_REFERENCE)
	{
		this.BALL_OBS_LIST_REFERENCE = BALL_OBS_LIST_REFERENCE;
		collisionBits = new CollisionBits(BALL_OBS_LIST_REFERENCE, ballBaseReference);

	}

	public void setGray()
	{
		color = Color.GRAY;
		ball.setFill(Color.GRAY);
	}

	public void setRED()
	{
		color = Color.RED;
		ball.setFill(Color.RED);
	}

	public void setBlue()
	{
		color = Color.BLUE;
		ball.setFill(Color.BLUE);
	}

	public Color getColor()
	{
		return color;
	}

	public int getMomentumX()
	{
		return (int) (VxActual * Weight);
	}

	public int getMomentumY()
	{
		return (int) (VyActual * Weight);
	}

	public void setGravity(double gravity)
	{
		this.Gravity = gravity;
	}

	@Override
	public String toString()
	{
		return "Ball [PositionXActual=" + PositionXActual + ", PositionYActual=" + PositionYActual + ", ballNumber=" + ballNumber + "]";
	}

	public CollisionBits getCollisionBits()
	{
		return collisionBits;
	}

	public double getPositionXActual()
	{
		return PositionXActual;
	}

	public void setPositionXActual(double positionXActual)
	{
		PositionXActual = positionXActual;
	}

	public double getPositionYActual()
	{
		return PositionYActual;
	}

	public void setPositionYActual(double positionYActual)
	{
		PositionYActual = positionYActual;
	}

}
