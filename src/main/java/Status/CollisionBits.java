package Status;

import static FX_Controllers.ControllerFXML_CollisionTable2.controllerFXML_CollisionTable2;

import GameObjects.Ball;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class CollisionBits
{
	static String style1 = "-fx-base: LIGHTGRAY";
	static String style2 = "-fx-base: RED";
	static String nameBall = "%d < c:%d t:%4d > %d";

	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;
	private ObservableList<statusBit> statusBitList = FXCollections.observableArrayList();;

	private int bitRange; // used to create new interior class
	private int lockBall; // used to avoid compare the same ball in loop foreach

	// IntegerProperty P_BallNumerProperty = new SimpleIntegerProperty();
	//
	// public IntegerProperty P_VyActualProperty()
	// {
	// return P_VyActualProperty;
	// }

	public CollisionBits(ObservableList<Ball> ballListReference, int lockBall)
	{
		this.BALL_OBS_LIST_REFERENCE = ballListReference;
		this.lockBall = lockBall;

		bitRange = BALL_OBS_LIST_REFERENCE.size();

		for (int i = 0; i < bitRange; i++)
		{
			if (i != lockBall)
				statusBitList.add(new statusBit(i, false));
			else
				statusBitList.add(new statusBit(i, true));
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
				statusBitList.add(new statusBit(i, false));
			else
				statusBitList.add(new statusBit(i, true));
		}
		// System.out.println("Lock ball: " + lockBall + " status bit size: " +
		// statusBitList.size());
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

		private int counter = 0;
		private boolean occupied;
		private boolean statusLock;
		private int statusForBall;

		private long tStart = 0;
		private long tEnd = 0;
		private long tDelta = 0;
		// double elapsedSeconds = tDelta / 1000.0;

		public statusBit(int statusForBall, boolean statusLock)
		{
			this.occupied = false;
			this.booleanProperty.set(false);
			this.statusLock = statusLock;
			this.statusForBall = statusForBall;

			booleanProperty.addListener(new ChangeListener<Boolean>()
			{

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
				{
					Button node = (Button) getNodeFromGridPane(controllerFXML_CollisionTable2.mainGrid, lockBall, statusForBall);
					if (newValue == true && oldValue == false)
					{
						tStart = System.currentTimeMillis();
						node.setStyle(style2);
					}
					if (newValue == false && oldValue == true)
					{
						tEnd = System.currentTimeMillis();
						node.setStyle(style1);
						tDelta = tEnd - tStart;
						tEnd = 0;
						tStart = 0;
						System.out.println(String.format("%d <-> %d Counter %d Time %d\n", lockBall, statusForBall, counter, tDelta));
					}
					

					// Dokoncz mierzenie czasu jak trwa zderzenie
				}
			});

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

	}

	public void setStatusBitList(ObservableList<statusBit> statusBitList)
	{
		this.statusBitList = statusBitList;
	}

	public ObservableList<statusBit> getStatusBitList()
	{
		return statusBitList;
	}

}
