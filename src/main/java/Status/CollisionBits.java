package Status;

import static FX_Controllers.ControllerFXML_CollisionTable.controllerFXML_CollisionTable;
import static FX_Controllers.ControllerFXML_Base.controllerFXML_Base;
import static FX_Controllers.ControllerFXML_MessageTable.controllerFXML_MessageTable;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

import Calculations.CollisionsCalculations;
import GameObjects.Ball;
import Parameters.GeneralString;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CollisionBits implements GeneralString
{
	// Reference to BASE Ball
	private Ball referenceToBaseBall; 
	
	private CollisionsCalculations collisionsCalculations = new CollisionsCalculations();
	
	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;
	private ObservableList<statusBit> statusBitList = FXCollections.observableArrayList();;

	// used to create new interior class
	private int bitRange; 
	// used to avoid compare the same ball in loop foreach
	private int lockBall; 

	public CollisionBits(ObservableList<Ball> ballListReference, Ball baseBall)
	{
		this.BALL_OBS_LIST_REFERENCE = ballListReference;
		this.lockBall = baseBall.getParameter().getBaseNumber();
		this.referenceToBaseBall = baseBall;

		bitRange = BALL_OBS_LIST_REFERENCE.size();

		for (int i = 0; i < bitRange; i++)
		{
			if (i != lockBall)
				statusBitList.add(new statusBit(i, false, BALL_OBS_LIST_REFERENCE.get(i)));
			else
				statusBitList.add(new statusBit(i, true, BALL_OBS_LIST_REFERENCE.get(i)));
		}
	}

	public int getCounter(int ballNumber)
	{
		return statusBitList.get(ballNumber).getCounter();
	}

	public void setOccupied(int ballNumber)
	{
		statusBitList.get(ballNumber).setOccupied(true);
		statusBitList.get(ballNumber).increaseCounter();
	}

	public void resetOccupied(int ballNumber)
	{
		statusBitList.get(ballNumber).setOccupied(false);
	}

	public boolean isOccupied(int ballNumber)
	{
		return statusBitList.get(ballNumber).isOccupied();
	}

	public void updateRange()
	{
		bitRange = BALL_OBS_LIST_REFERENCE.size();
		statusBitList.clear();
		for (int i = 0; i < bitRange; i++)
		{
			if (i != lockBall)
				statusBitList.add(new statusBit(i, false, BALL_OBS_LIST_REFERENCE.get(i)));
			else
				statusBitList.add(new statusBit(i, true, BALL_OBS_LIST_REFERENCE.get(i)));
		}
	}

	public int getSizeOfRange()
	{
		return statusBitList.size();
	}

	// Interior class, creating amount of BALL_LIST size
	// This class safe the status of intersects of two balls
	public class statusBit
	{
		private BooleanProperty booleanProperty = new SimpleBooleanProperty(true);
		private Circle collisionPoint = new Circle();

		private Ball referenceToCollisionBall;
		private Runnable runableCollisionBit;
		private Thread threadCollisionBit;

		private int counter = 0;
		private int statusForBall = 0;
		private boolean occupied = false;
		private boolean statusLock = false;

		private long tStart = 0;
		private long tEnd = 0;
		private long tDelta = 0;

		public statusBit(int statusForBall, boolean statusLock, Ball referenceToCollisionBall)
		{
			this.referenceToCollisionBall = referenceToCollisionBall;
			this.occupied = false;
			this.booleanProperty.set(false);
			this.statusLock = statusLock;
			this.statusForBall = statusForBall;
		
			controllerFXML_Base.getRightPane().getChildren().add(collisionPoint);

			booleanProperty.addListener(new ChangeListener<Boolean>()
			{

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Button node = (Button) getNodeFromGridPane(controllerFXML_CollisionTable.mainGrid, lockBall, statusForBall);
					if (newValue == true && oldValue == false)
					{
						referenceToBaseBall.safeCollisionPosition();
						tStart = System.currentTimeMillis();
						node.setStyle(styleButton2);
						collisionsCalculations.CalculateAngelAndPointOfCollision(referenceToBaseBall, referenceToCollisionBall, collisionPoint);
						collisionPoint.setFill(Color.RED);
					}
					if (newValue == false && oldValue == true)
					{
						referenceToBaseBall.resetCollisionPosition();
						tEnd = System.currentTimeMillis();
						node.setStyle(styleButton1);
						tDelta = tEnd - tStart;
						controllerFXML_MessageTable.appendMessage(String.format("%d <-> %d Counter %d Time %d", lockBall, statusForBall, counter, tDelta));
						collisionPoint.setFill(Color.TRANSPARENT);
					}
				}
			});

		}
		

		private void cyclickCheckingColision()
		{
			runableCollisionBit = new Runnable()
			{
				public void run()
				{
					synchronized (this)
					{
						while (occupied)
						{
							referenceToBaseBall.resetPosition();
							try
							{
								Thread.sleep(5);
							} catch (InterruptedException e)
							{

								e.printStackTrace();
							}
						}
					}
				}
			};

			threadCollisionBit = new Thread(runableCollisionBit);
			threadCollisionBit.start();
			threadCollisionBit.setName("Ball " + lockBall + " Cyclick Checking Collision Bit with: " + statusForBall);
		}

		// Function to find Node in GridPane by column and rows (matrix X x Y)
		private Node getNodeFromGridPane(GridPane gridPane, int col, int row)
		{
			for (Node node : gridPane.getChildren())
			{
				if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
				{
					return node;
				}
			}
			return null;
		}

		public void increaseCounter()
		{
			counter++;
		}

		public int getCounter()
		{
			return counter;
		}

		public boolean isOccupied()
		{
			return occupied;
		}

		public void setOccupied(boolean occupied)
		{
			this.occupied = occupied;
			booleanProperty.set(occupied);
		}

		public boolean isStatusLock()
		{
			return statusLock;
		}

		public void setStatusLock(boolean statusLock)
		{
			this.statusLock = statusLock;
		}

		public int getStatusForBall()
		{
			return statusForBall;
		}

		public void setStatusForBall(int statusForBall)
		{
			this.statusForBall = statusForBall;
		}
		public Node getNode()
		{
			return collisionPoint;
		}

	}

	public void setStatusBitList(ObservableList<statusBit> statusBitList)
	{
		this.statusBitList = statusBitList;
	}

	public ObservableList<statusBit> getStatusBitList()
	{
		return statusBitList;
	}
	public ArrayList<Node> getNodes()
	{
		ArrayList<Node> nodes =  new ArrayList<>();
		nodes.clear();
		for (statusBit statusBit : statusBitList)
		{
			nodes.add(statusBit.getNode());
		}
		return nodes;
	}

}
